package newtime.prototype;

import java.util.Arrays;

import newtime.core.Game;

public class Prototype extends Game {

	public static void main(String[] args) {
		
		boolean forceD3D = false;
		boolean forceOpenGL = false;
		boolean forceNative = false;		
		
		for(String arg : args) {
			if(arg.toLowerCase().equals("-forced3d")) {
				forceD3D = true;
				continue;
			}
			if(arg.toLowerCase().equals("-forceopengl")) {
				forceOpenGL = true;
				continue;
			}
			if(arg.toLowerCase().equals("-forcenative")) {
				forceNative = true;
				continue;
			}
		}
		new Prototype(1280,720,forceD3D,forceOpenGL,forceNative);
	}
	
	public Prototype(int screenWidth, int screenHeight, boolean forceD3D, boolean forceOpenGL, boolean forceNative) {
		super(new MainGameState(), screenWidth, screenHeight, forceD3D, forceOpenGL, forceNative);
	}

}
