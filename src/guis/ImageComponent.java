package guis;

import images.Image;
import renderer.imageRenderer.ImageRenderer;
import utils.SourceFile;

public class ImageComponent extends GUIComponent {

	private Image img;
	private float x, y;
	
	public ImageComponent(GUI container, SourceFile file) {
		super(container);
		img = new Image(file);
	}
	
	@Override
	public void render() {
		img.setPosition(super.getContainer().getPosition().getX() + x, super.getContainer().getPosition().getY() + y);
		
		ImageRenderer.renderImage(img);
	}

	@Override
	public void delete() {
		img.delete();
	}
		
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void setScale(float scale) {
		img.setScale(scale);
	}
	
}
