package hud;

import org.lwjgl.util.vector.Vector2f;

import textures.Texture;

public class HudTexture {
	
	private Texture texture;
	private Vector2f position;
	private Vector2f scale;
	
	private float rotation = 0;
	
	public HudTexture(Texture texture, Vector2f position, Vector2f scale) {
		this.texture = texture;
		this.position = position;
		this.scale = scale;
		HudManager.addHUD(this);
	}

	public Texture getTexture() {
		return texture;
	}

	public Vector2f getPosition() {
		return position;
	}

	public Vector2f getScale() {
		return scale;
	}
	
	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

}
