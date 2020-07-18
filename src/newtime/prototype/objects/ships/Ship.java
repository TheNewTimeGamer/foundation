package newtime.prototype.objects.ships;

import java.awt.Color;
import java.awt.Rectangle;

import newtime.core.Game;
import newtime.core.GameObject;
import newtime.gfx.RenderableContext;
import newtime.gfx.Screen;
import newtime.prototype.logic.Faction;

public class Ship extends GameObject {

	public static final int TYPE_PLAYER = -1;
	
	public static final int TYPE_DRONE = 0;
	public static final int TYPE_CORVETTE = 1;
	public static final int TYPE_FRIGATE = 2;
	public static final int TYPE_DREADNAUGHT = 3;
	public static final int TYPE_CARRIER = 4;
	public static final int TYPE_BATTLESHIP = 5;
	public static final int TYPE_MOTHERSHIP = 6;
		
	protected String name;
	protected Faction faction;
	protected int type;
	
	protected ShipPart[] parts = null;
		
	public Ship(Game game, String name, Faction faction, int type) {
		super(game);
		this.name = name;
		this.faction = faction;
		this.type = type;
		this.parts = new ShipPart[] {
			new ShipPartBlock(this.game, this, 0, 0),
			new ShipPartBlock(this.game, this, 1, 0),
			new ShipPartBlock(this.game, this, 2, 0)
		};
	}
	
	public Ship(Game game, String name, Faction faction, int type, ShipPart[] parts) {
		super(game);
		this.name = name;
		this.faction = faction;
		this.type = type;
		
		this.parts = parts;
	}
	
	public void tick() {		
		for(ShipPart part : parts) {
			if(part != null) {
				part.tick();
			}
		}
	}
	
	public void render(RenderableContext context) {
		Rectangle boundingBox = this.getBounds();
		double centerX = boundingBox.getCenterX();
		double centerY = boundingBox.getCenterY();
		
		context.graphics.rotate(this.rotation, centerX, centerY);
		
		for(ShipPart part : parts) {
			if(part != null) {
				part.render(context);
			}
		}
				
		context.graphics.rotate(-this.rotation, centerX, centerY);		
	}
	
	public void setActive(boolean active) {
		super.setActive(active);
		for(ShipPart part : parts) {
			if(part != null) {
				part.setActive(active);
			}
		}
	}

	public void setVisible(Screen screen, boolean visible) {
		super.setVisible(screen, visible);
		for(ShipPart part : parts) {
			if(part != null) {
				part.setVisible(screen, visible);
			}
		}
	}
	
	public void translate(int deltaX, int deltaY) {
		super.translate(deltaX, deltaY);
		for(ShipPart part : parts) {
			if(part != null) {
				part.translate(deltaX, deltaY);
			}
		}
	}
	
	public Rectangle getBounds() {
		int minX = 1000;
		int minY = 1000;
		int maxX = -1000;
		int maxY = -1000;	
		
		for(ShipPart part : parts) {
			if(part != null) {
				Rectangle bounds = part.getBounds();
				if(bounds.x < minX) {
					minX = bounds.x;
				}
				if(bounds.y < minY) {
					minY = bounds.y;
				}
				if(bounds.width > maxX) {
					maxX = bounds.width;
				}
				if(bounds.height > maxY) {
					maxY = bounds.height;
				}
			}
		}
		
		return new Rectangle(minX, minY, maxX, maxY);
	}
	

}

