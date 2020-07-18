package newtime.gfx;

import java.awt.Graphics;
import java.awt.Polygon;

public abstract class Renderable extends Polygon {
	
	public abstract void render(RenderableContext context);

}