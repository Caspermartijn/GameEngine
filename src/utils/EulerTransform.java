package utils;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class EulerTransform {

	public float posX, posY, posZ, rotX, rotY, rotZ, scaleX = 1, scaleY = 1, scaleZ = 1;

	public EulerTransform() {
	}

	public EulerTransform(float posX, float posY, float posZ, float rotX, float rotY, float rotZ, float scaleX,
			float scaleY, float scaleZ) {
		super();
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		this.scaleZ = scaleZ;
	}

	public Matrix4f toMatrix() {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(new Vector3f(posX, posY, posZ), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rotX), new Vector3f(1, 0, 0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rotY), new Vector3f(0, 0, 1), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rotZ), new Vector3f(0, 1, 0), matrix, matrix);
		Matrix4f.scale(new Vector3f(scaleX, scaleY, scaleZ), matrix, matrix);
		return matrix;
	}

	public void setPosition(Vector3f pos) {
		posX = pos.x;
		posY = pos.y;
		posZ = pos.z;
	}

	public void setRotation(Vector3f rot) {
		this.rotX = rot.x;
		this.rotY = rot.y;
		this.rotZ = rot.z;
	}

	public void setScale(Vector3f scale) {
		this.scaleX = scale.x;
		this.scaleY = scale.y;
		this.scaleZ = scale.z;
	}

	public void setScale(float scale) {
		this.scaleX = scale;
		this.scaleY = scale;
		this.scaleZ = scale;
	}

	public Vector3f getPosition() {
		return new Vector3f(posX, posY, posZ);
	}

	public Vector3f geRotation() {
		return new Vector3f(rotX, rotY, rotZ);
	}

	public Vector3f getScale() {
		return new Vector3f(scaleX, scaleY, scaleZ);
	}

}
