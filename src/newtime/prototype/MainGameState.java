package newtime.prototype;

import java.awt.Color;
import java.util.ArrayList;

import newtime.core.Game;
import newtime.core.GameObject;
import newtime.core.IGameState;
import newtime.gfx.RenderableContext;

public class MainGameState implements IGameState {

	public void init(Game game) {
		ArrayList<GameObject> gameObjects = game.getGameObjects();
		for(int y = 0; y < 1; y++) {
			for(int x = 0; x < 1; x++) {
				gameObjects.add(new Test(game, x*32, y*32, 32, 32));
			}
		}
	}

	public void tick(Game game) {
		ArrayList<GameObject> gameObjects = game.getGameObjects();
		for(int i = 0; i < gameObjects.size(); i++) {
			GameObject object = gameObjects.get(i);
			if(object != null) {
				object.tick(game);
			}
		}
	}

	public void exit(Game game) {
		
	}

}

class Test extends GameObject {
	
	public Test(Game game, float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		this.setActive(true);
		this.setVisible(game.getScreen(), true);
	}
	
	public void tick(Game game) {
		super.tick(game);		
		this.x += 0.5f;
	}
	
	public void render(RenderableContext context) {
		super.render(context);
		context.graphics.setColor(Color.RED);
		context.graphics.fillRect((int)this.x, (int)this.y, (int)this.width, (int)this.height);
		context.graphics.setColor(Color.BLACK);
		context.graphics.drawRect((int)this.x, (int)this.y, (int)this.width, (int)this.height);
	}
	
}