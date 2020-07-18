package newtime.gfx.resources;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Texture implements ITexture {

	private String name;
	private BufferedImage image;

	public Texture(String name, BufferedImage image) {
		BufferedImage compatibleImage = GraphicsManager.graphicsConfiguration.createCompatibleImage(image.getWidth(), image.getHeight());
		Graphics g = compatibleImage.getGraphics();
		g.drawImage(image, 0, 0, compatibleImage.getWidth(), compatibleImage.getHeight(), null);
		g.dispose();
		this.name = name;
		this.image = compatibleImage;
	}

	public String getName() {
		return this.name;
	}

	public Image getImage() {
		return this.image;
	}

}
