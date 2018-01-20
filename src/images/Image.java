package images;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import engine.Display;
import textures.Texture;
import utils.SourceFile;
import utils.models.ModelLoader;

public class Image {

	private float x, y;
	private float width = 280, height = 280;
	private Texture texture;
	private float scale = 1f;
	public float rotation = 0;

	public Image(SourceFile texture) {
		this.texture = ModelLoader.loadTexture(texture);
	}

	public Image(SourceFile texture, float x, float y) {
		this.texture = ModelLoader.loadTexture(texture);
		this.x = x;
		this.y = y;
	}

	public Image(SourceFile texture, float x, float y, float width, float height) {
		this.texture = ModelLoader.loadTexture(texture);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public Image(SourceFile texture, float x, float y, float width, float height, float rotation) {
		this.texture = ModelLoader.loadTexture(texture);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.rotation = rotation;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public Texture getTexture() {
		return texture;
	}

	public Matrix4f getMatrix() {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();

		float posX = (getX() - 0.5f) * 2.0f + scale * texture.getWidth() / Display.getWidth();
		float posY = (getY() - 0.5f) * -2.0f - scale * texture.getHeight() / Display.getHeight();
		Matrix4f.translate(new Vector2f(posX, posY), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rotation), new Vector3f(0, 0, 1), matrix, matrix);
		Matrix4f.scale(new Vector3f(scale * width / Display.getWidth(), scale * height / Display.getHeight(), 1),
				matrix, matrix);
		return matrix;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public void delete() {
		texture.delete();
	}

	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Vector2f getSizeOpenGL() {
		float posX = texture.getWidth() / 1280;
		float posY = texture.getHeight() / 720;
		return new Vector2f(posX, posY);
	}

}
