package guis;

import org.lwjgl.util.vector.Vector2f;

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

	public float[] getSize() {
		return new float[] { img.getWidth(), img.getHeight() };
	}

	public void setSize(float width, float height) {
		img.setWidth(width);
		img.setHeight(height);
	}

	public void setSize(Vector2f vec) {
		img.setWidth(vec.x);
		img.setHeight(vec.y);
	}

	public void setScale(float scale) {
		img.setScale(scale);
	}

	public void setRotation(float rotation) {
		img.setRotation(rotation);
	}

	public float getRotation() {
		return img.getRotation();
	}
	
	public Vector2f getPosition() {
		return new Vector2f(x, y);
	}
	
	public Vector2f getSizeOpenGL() {
		return img.getSizeOpenGL();
	}

}
