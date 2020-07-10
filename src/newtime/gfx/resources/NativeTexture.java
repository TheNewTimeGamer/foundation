package newtime.gfx.resources;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;

public class NativeTexture implements ITexture{
	
	private String name;
	private VolatileImage volatileImage;
		
	public NativeTexture(String name, BufferedImage image) {
		this.name = name;
		this.volatileImage = GraphicsManager.graphicsConfiguration.createCompatibleVolatileImage(image.getWidth(), image.getHeight());
		Graphics g = this.volatileImage.getGraphics();
		g.drawImage(image, 0, 0, this.volatileImage.getWidth(), this.volatileImage.getHeight(), null);
		g.dispose();
	}

	public String getName() {
		return this.name;
	}

	public Image getImage() {
		if(volatileImage.contentsLost()) {
			volatileImage.validate(GraphicsManager.graphicsConfiguration);
		}
		return volatileImage;
	}

}
