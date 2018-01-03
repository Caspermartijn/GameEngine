package entities;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import objects.Model_3D;

public class Entity implements IEntity {

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

	public Matrix4f getTransformationMatrix() {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.scale(new Vector3f(scale, scale, scale), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rotation.x), new Vector3f(1, 0, 0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rotation.y), new Vector3f(0, 1, 0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rotation.z), new Vector3f(0, 0, 1), matrix, matrix);
		Matrix4f.translate(position, matrix, matrix);
		return matrix;
	}

	public void increasePosition(float f, float g, float h) {
		position.x += f;
		position.y += g;
		position.z += h;
	}

	public float getRotX() {
		return rotation.x;
	}

	public float getRotY() {
		return rotation.y;
	}

	public float getRotZ() {
		return rotation.z;
	}

}
