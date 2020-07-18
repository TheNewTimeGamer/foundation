package newtime.prototype.objects.ships;

import newtime.core.Game;
import newtime.core.GameObject;

public abstract class ShipPart extends GameObject {

	protected Ship parent;
	
	public ShipPart(Game game, Ship parent, int xOffset, int yOffset) {
		super(game);
		this.parent = parent;
	}

	public abstract void performAction(Ship target);
	
}
