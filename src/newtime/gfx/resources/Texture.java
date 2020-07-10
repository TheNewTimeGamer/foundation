package newtime.gfx.resources;

import java.awt.Image;
import java.awt.image.BufferedImage;

public class Texture implements ITexture {

	private String name;
	private BufferedImage image;

	public Texture(String name, BufferedImage image) {
		this.name = name;
		this.image = image;
	}

	public String getName() {
		return this.name;
	}

	public Image getImage() {
		return this.image;
	}

}
