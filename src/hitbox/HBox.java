package hitbox;

import org.lwjgl.util.vector.Vector3f;

import objects.Vao;

public abstract class HBox {

	protected Vector3f position = new Vector3f();
	protected Vector3f rotation = new Vector3f();
	protected float scale = 1;

	protected int hitBoxID;

	protected Vao rawModel;
	
	public void setID(int id) {
		this.hitBoxID = id;
	}

	public int getHitBoxID() {
		return hitBoxID;
	}

	public void setPosition(Vector3f pos) {
		position = pos;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public float getScale() {
		return this.scale;
	}

	public Vector3f getPosition() {
		return this.position;
	}

	public void setRotation(Vector3f rot) {
		rotation = rot;
	}

	public Vector3f getRotation() {
		return this.rotation;
	}

	public Vao getRawModel() {
		return rawModel;
	}
	
	public void setRawModel(Vao model){
		this.rawModel = model;
	}

}
