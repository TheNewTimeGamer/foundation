package newtime.prototype.objects.ships;

import java.awt.Color;

import newtime.core.Game;
import newtime.gfx.RenderableContext;

public class ShipPartBlock extends ShipPart {

	public ShipPartBlock(Game game, Ship parent, int xOffset, int yOffset) {
		super(game, parent, xOffset, yOffset);
		this.addPoint(0, 0);
		this.addPoint(32, 0);
		this.addPoint(32, 32);
		this.addPoint(0, 32);
		this.translate(xOffset*32, yOffset*32);
	}
	
	public void performAction(Ship target) {}

	public void render(RenderableContext context) {
		context.graphics.setColor(Color.WHITE);
		context.graphics.fillPolygon(this);
		context.graphics.setColor(Color.BLACK);
		context.graphics.drawPolygon(this);
	}
	
}
