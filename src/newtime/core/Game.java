package newtime.core;

import java.awt.Color;
import java.util.ArrayList;

import newtime.gfx.Screen;

public class Game implements Runnable {
	
	protected int state = 0;
	protected IGameState[] states = new IGameState[64];
	
	protected Screen screen;
	
	protected ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
	
	protected Thread thread;
	
	protected int tickRate = 60;
	
	public Game(IGameState initialGameState, int screenWidth, int screenHeight, boolean forceD3D, boolean forceOpenGL, boolean forceNative) {
		this.states[0] = initialGameState;
		
		this.thread = new Thread(this);
		
		this.screen = Screen.initializeAcceleratedScreen(screenWidth, screenHeight, forceD3D, forceOpenGL, forceNative);
		this.screen.setClearColor(Color.BLUE);
		
		this.thread.start();
	}
	
	private boolean running = true;
	
	public void run() {
		this.states[state].init(this);
		
		long lastTick = 0;		
				
		while(running) {
			if(System.currentTimeMillis()-lastTick > (1000/tickRate)) {
				this.tick();
				lastTick = System.currentTimeMillis();
			}
			this.screen.render();
		}
		close();
	}
	
	public void close() {
		System.exit(0);
	}
	
	public void tick() {
		states[state].tick(this);
	}
	
	public Screen getScreen() {
		return this.screen;
	}
	
	public void setScreen(Screen screen) {
		this.screen = screen;
	}
	
	public int getState() {
		return this.state;
	}
	
	public IGameState getCurrentGameState() {
		return this.states[this.state];
	}
	
	public boolean setState(int i) {
		if(state < 0 || state >= states.length) { System.err.println("Invalid gamestate: " + i + ". min: 0, max: " + (states.length-1) + "."); return false; }
		if(states[state] == null) { System.err.println("Invalid gamestate: " + i + ". null value."); return false; }
		states[state].exit(this);		
		state = i;		
		states[state].init(this);
		return true;
	}
	
	public ArrayList<GameObject> getGameObjects(){
		return this.gameObjects;
	}
	
}
