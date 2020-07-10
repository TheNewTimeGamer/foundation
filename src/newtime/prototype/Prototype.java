package newtime.prototype;

import java.util.Arrays;

import newtime.core.Game;

public class Prototype extends Game {

	public static void main(String[] args) {
		
		boolean forceOpenGL = false;
		boolean forceNative = false;
		
		for(String arg : args) {
			if(arg.toLowerCase().equals("-forceopengl")) {
				forceOpenGL = true;
				continue;
			}
			if(arg.toLowerCase().equals("-forcenative")) {
				forceNative = true;
				continue;
			}
		}
		new Prototype(1280,720,forceOpenGL, forceNative);
	}
	
	public Prototype(int screenWidth, int screenHeight, boolean forceOpenGL, boolean forceNative) {
		super(new MainGameState(), screenWidth, screenHeight, forceOpenGL, forceNative);
	}

}
