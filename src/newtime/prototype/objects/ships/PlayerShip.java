package newtime.prototype.objects.ships;

import java.awt.event.KeyEvent;

import newtime.core.Game;
import newtime.gfx.RenderableContext;
import newtime.prototype.logic.Faction;

public class PlayerShip extends Ship {
	
	public PlayerShip(Game game, String name) {
		super(game, name, Faction.PLAYER, Ship.TYPE_PLAYER);
		this.setVisible(this.game.getScreen(), true);
		this.setActive(true);
	}
	
	public PlayerShip(Game game, String name, ShipPart[] parts) {
		super(game, name, Faction.PLAYER, Ship.TYPE_PLAYER, parts);
		this.setVisible(this.game.getScreen(), true);
		this.setActive(true);
	}
	
	public void tick() {
		super.tick();
		boolean[] keyboard = this.game.getScreen().keyboard;
		if(keyboard[KeyEvent.VK_UP]) {
			this.translate((int)(Math.cos(this.rotation)*10), (int)(Math.sin(this.rotation)*10));
		}
		if(keyboard[KeyEvent.VK_DOWN]) {
			this.translate(-(int)(Math.cos(this.rotation)*10), -(int)(Math.sin(this.rotation)*10));
		}
		if(keyboard[KeyEvent.VK_LEFT]) {
			this.rotation -= 0.025;
		}
		if(keyboard[KeyEvent.VK_RIGHT]) {
			this.rotation += 0.025;
		}
	}
	
	public void render(RenderableContext context) {
		super.render(context);
	}

}
