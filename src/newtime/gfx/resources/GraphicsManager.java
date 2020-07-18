package newtime.gfx.resources;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GraphicsManager {

	public static GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
	public static GraphicsConfiguration graphicsConfiguration = graphicsEnvironment.getDefaultScreenDevice().getDefaultConfiguration();
	
	private ITexture[] textures = new ITexture[1024];
	
	public GraphicsManager() {}
	
	public GraphicsManager(File file) {
		if(!file.exists()) {
			System.err.println("File does not exist: " + file.getAbsolutePath());
			return;
		}
		if(file.isDirectory()) {
			loadDirectory(file, true);
		}else if(file.isFile()) {
			loadFile(file, true);
		}
	}
	
	public int loadDirectory(File file, boolean nativeTexture) {
		File[] files = file.listFiles();
		int successful = 0;
		for(File f : files) {
			if(loadFile(f, nativeTexture)) {
				successful++;
			}
		}
		return successful;
	}
	
	public boolean loadFile(File file, boolean nativeTexture) {
		try {
			String name = file.getName().split("\\.")[0];
			BufferedImage image = ImageIO.read(file);
			ITexture texture = null;
			if(nativeTexture) {
				texture = new NativeTexture(name, image);
			}else {
				texture = new Texture(name, image);
			}
			if(addTexture(texture) >= 0) {
				return true;
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public int addTexture(ITexture texture) {
		for(int i = 0; i < textures.length; i++) {
			if(textures[i] == null) {
				textures[i] = texture;
				return i;
			}
		}
		return -1;
	}
	
	
}
