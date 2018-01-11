package images;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import engine.Display;
import textures.Texture;
import utils.SourceFile;

public class Image {

	private float x, y;
	private Texture texture;
	private float scale = 1f;
	
	public Image(SourceFile texture) {
		this.texture = ModelLoader.loadTexture(texture);
	}
	
	public Image(SourceFile texture, float x, float y) {
		this.texture = ModelLoader.loadTexture(texture);
		this.x = x;
		this.y = y;
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

	public Texture getTexture() {
		return texture;
	}
	
	public Matrix4f getMatrix() {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		
		float posX = (getX()-0.5f)*2.0f + scale * texture.getWidth() / Display.getWidth();
		float posY = (getY()-0.5f)*-2.0f - scale * texture.getHeight() / Display.getHeight();
		Matrix4f.translate(new Vector3f(posX, posY, 0), matrix, matrix);
		Matrix4f.scale(new Vector3f(scale * texture.getWidth() / Display.getWidth(), scale * texture.getHeight() / Display.getHeight(), 1), matrix, matrix);	
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
	
}
