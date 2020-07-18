package newtime.gfx;

import java.awt.Graphics2D;

public class RenderableContext {

	public final Screen screen;
	public final Graphics2D graphics;

	public final int zIndex;
	public final int index;

	public RenderableContext(Screen screen, int zIndex, int index, Graphics2D graphics) {
		this.screen = screen;
		this.zIndex = zIndex;
		this.index = index;
		this.graphics = graphics;
	}

	public void fillRect(float x, float y, float width, float height) {

	}

}
