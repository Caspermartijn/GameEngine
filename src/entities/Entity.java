package entities;

import org.joml.Vector3f;

import objects.Model_3D;

public abstract class Entity implements IEntity {

	private Vector3f position;
	private Vector3f rotation;

	private float scale;

	private Model_3D model;

	public Entity(Model_3D model, Vector3f position, Vector3f rotation, float scale) {
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
		this.model = model;
	}

	@Override
	public void update() {
		
	}
	
	public Vector3f getPosition() {
		return position;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public float getScale() {
		return scale;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}

	public Model_3D getModel() {
		return model;
	}

}
