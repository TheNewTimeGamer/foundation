package newtime.gfx;

import java.awt.AWTException;
import java.awt.BufferCapabilities;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.ImageCapabilities;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.VolatileImage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import newtime.gfx.resources.GraphicsManager;

public class Screen extends Canvas {
	
	protected final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
	protected ExecutorService threadPool = Executors.newFixedThreadPool(AVAILABLE_PROCESSORS);
	
	protected Frame frame;
	
	protected Color clearColor = Color.BLACK;
	
	protected Renderable[][] renderables = new Renderable[64][5120];
		
	protected BufferCapabilities bufferCapabilities;
	
	public static Screen initializeAcceleratedScreen(int frameWidth, int frameHeight, boolean forceD3D, boolean forceOpenGL, boolean forceNative) {
		String osName = System.getProperty("os.name");		
		
		System.out.println("Operating System: " + osName);
		System.out.println("Force D3D: " + forceD3D);
		System.out.println("Force openGL: " + forceOpenGL);
		System.out.println("Force native: " + forceNative);
		
		if((!forceOpenGL && !forceNative && osName.toLowerCase().contains("windows")) || forceD3D) {
			System.setProperty("sun.java2d.transaccel", "True");
			System.setProperty("sun.java2d.d3d", "True");
			System.setProperty("sun.java2d.ddforcevram", "True");
		}else if(!forceNative || forceOpenGL){		
			System.setProperty("sun.java2d.opengl", "True");
			System.setProperty("Dsun.java2d.opengl", "True");
		}
		return new Screen(frameWidth, frameHeight, forceNative);
	}
	
	private Screen(int frameWidth, int frameHeight, boolean forceNative) {
		this.frame = new Frame();
		this.frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.frame.setSize(frameWidth, frameHeight);
		this.frame.setLocationRelativeTo(null);
		this.frame.add(this);
		this.frame.setVisible(true);
		
		if(!forceNative) {
			this.bufferCapabilities = new BufferCapabilities(new ImageCapabilities(true), new ImageCapabilities(true), BufferCapabilities.FlipContents.BACKGROUND);
		}else {
			System.out.println("Forcing native bufferCapabilities");
			this.bufferCapabilities = GraphicsManager.graphicsConfiguration.getBufferCapabilities();
		}
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			try {
				createBufferStrategy(2, this.bufferCapabilities);
			} catch (AWTException e) {
				e.printStackTrace();
			}
			return;
		}
				
		Graphics nativeGraphics = bs.getDrawGraphics();
		
		for(int z = 0; z < this.renderables.length; z++) {
			if(this.renderables[z] == null) {continue;}
			Future[] futures = new Future[this.AVAILABLE_PROCESSORS];
			for(int x = 0; x < this.AVAILABLE_PROCESSORS; x++) {
				futures[x] = threadPool.submit(new RenderWorker(this, z, this.renderables[z], this.AVAILABLE_PROCESSORS, x, bs.getDrawGraphics()));
			}
			
			boolean completed;
			do {
				completed = true;
				try {
					for(int i = 0; i < futures.length; i++) {
						if(futures[i].get() != null) {completed = false;}
					}
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			}while(!completed);
		}
			
		nativeGraphics.dispose();
		bs.show();
	}
		
	public Color getClearColor() {
		return this.clearColor;
	}
	
	public void setClearColor(Color clearColor) {
		this.clearColor = clearColor;
	}
	
	public void setClearColor(int r, int g, int b) {
		this.clearColor = new Color(r,g,b);
	}
	
	public void setClearColor(int r, int g, int b, int a) {
		this.clearColor = new Color(r,g,b,a);
	}
	
	public int getProcessorCount() {
		return this.AVAILABLE_PROCESSORS;
	}
	
	public int addRenderable(Renderable renderable, int zIndex) {
		Renderable[] layer = this.renderables[zIndex];
		for(int i = 0; i < layer.length; i++) {
			if(layer[i] == null) {
				layer[i] = renderable;
				return i;
			}
		}
		return -1;
	}
	
	public Renderable putRenderable(Renderable renderable, int zIndex, int index) {
		Renderable temp = this.renderables[zIndex][index];
		this.renderables[zIndex][index] = renderable;
		return temp;
	}
	
	public void removeRenderable(int zIndex, int index) {
		this.renderables[zIndex][index] = null;
	}
	
	public void clearLayer(int zIndex) {
		this.renderables[zIndex] = new Renderable[this.renderables[zIndex].length];
	}
	
	public void clearRenderables() {
		this.renderables = new Renderable[this.renderables[0].length][this.renderables.length];
	}
		
}

class RenderWorker implements Runnable {
	
	private final Screen screen;
	
	private final Renderable[] renderables;
	
	private final int threadCount;
	private final int index;
	
	private final int zIndex;
	
	private final Graphics graphicsContext;
		
	public RenderWorker(Screen screen, int zIndex, Renderable[] renderables, int threadCount, int index, Graphics graphicsContext) {
		this.screen = screen;
		this.renderables = renderables;
		
		this.threadCount = threadCount;
		this.index = index;
		
		this.graphicsContext = graphicsContext;
		this.zIndex = zIndex;
	}
	
	public void run() {
		for(int i = index; i < renderables.length; i+=threadCount) {
			if(i >= renderables.length) {break;}
			if(renderables[i] != null) {
				renderables[i].render(new RenderableContext(screen, zIndex, i, graphicsContext));
			}
		}
	}
	
}
