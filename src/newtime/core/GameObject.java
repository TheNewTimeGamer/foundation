package newtime.core;

import newtime.gfx.Renderable;
import newtime.gfx.RenderableContext;
import newtime.gfx.Screen;

public abstract class GameObject extends Renderable {

	protected float x, y;
	protected float width, height;
	
	private boolean active = false;
	private boolean visible = false;
	
	private int zIndex = 0;
	
	public void tick(Game game) {
		if(!this.active) {return;}
	}
	
	public void render(RenderableContext context) {
		if(!this.visible) {
			context.screen.removeRenderable(context.zIndex, context.index);
		}
	}
	
	public boolean isVisible() {
		return this.visible;
	}
	
	public void setVisible(Screen screen, boolean visible) {
		this.visible = visible;
		if(this.visible) {
			screen.addRenderable(this, zIndex);
		}
	}
	
	public int getLayer() {
		return this.zIndex;
	}
	
	public void setLayer(int zIndex) {
		if(this.visible) {
			System.err.println("Can't change zIndex while object is still in render buffer");
			return;
		}
		this.zIndex = zIndex;
	}
	
	public boolean getActive() {
		return this.active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	
	
}
