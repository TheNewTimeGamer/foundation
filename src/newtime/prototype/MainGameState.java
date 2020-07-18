package newtime.prototype;

import java.util.ArrayList;

import newtime.core.Game;
import newtime.core.GameObject;
import newtime.core.IGameState;
import newtime.prototype.objects.ships.PlayerShip;

public class MainGameState implements IGameState {

	public void init(Game game) {
		ArrayList<GameObject> gameObjects = game.getGameObjects();
		gameObjects.add(new PlayerShip(game, "Patchwork"));
	}

	public void tick(Game game) {
		ArrayList<GameObject> gameObjects = game.getGameObjects();
		for(int i = 0; i < gameObjects.size(); i++) {
			GameObject object = gameObjects.get(i);
			if(object != null) {
				object.tick();
			}
		}
	}

	public void exit(Game game) {
		
	}

}
