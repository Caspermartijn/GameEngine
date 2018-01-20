package guis;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import images.Image;
import renderer.imageRenderer.ImageRenderer;
import utils.SourceFile;

public class ImageComponent extends GUIComponent {

	private Image img;
	private float x, y;
	private Vector3f colorOverride = new Vector3f(-1f, -1f, -1f);

	public ImageComponent(GUI container, SourceFile file) {
		super(container);
		img = new Image(file);
	}

	@Override
	public void render() {
		img.setPosition(super.getContainer().getPosition().getX() + x, super.getContainer().getPosition().getY() + y);
		ImageRenderer.renderImage(img, colorOverride);
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

	public void setOverrideColor(Vector3f vec) {
		colorOverride = vec;
	}

}
