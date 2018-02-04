package utils.transformations;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import utils.maths.Maths;

public class QuaternionTransform {

	public float x, y, z;
	public Quaternion quaternion;
	
	public QuaternionTransform() {
		quaternion = new Quaternion(0, 0, 0);
	}
	
	public QuaternionTransform(Matrix4f matrix) {
		x = matrix.m03;
		y = matrix.m13;
		z = matrix.m23;
		quaternion = new Quaternion(matrix);
	}

	public QuaternionTransform(Vector3f translation, Quaternion rotation) {
		x = translation.x;
		y = translation.y;
		z = translation.z;
		quaternion = rotation;
	}
	
	public QuaternionTransform(float x, float y, float z, float qx, float qy, float qz, float qw) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		quaternion=new Quaternion(qx, qy, qz, qw);
	} 
	
	public QuaternionTransform(float x, float y, float z, float qdx, float qdy, float qdz) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		quaternion = new Quaternion(qdx, qdy, qdz);
	}
	
	public Matrix4f toMatrix() {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(new Vector3f(x, y, z), matrix, matrix);
		Matrix4f.mul(matrix, quaternion.toRotationMatrix(), matrix);
		return matrix;
	}
	
	public Vector3f getTranslation() {
		return new Vector3f(x, y, z);
	}
	
	public Quaternion getRotation() {
		return quaternion;
	}
	
	public QuaternionTransform interpolate(QuaternionTransform start, QuaternionTransform end, float blend) {
		blend = Maths.clamp(0, 1, blend);
		Vector3f position = Maths.interpolate(start.getTranslation(), end.getTranslation(), blend);
		Quaternion rot = Quaternion.slerp(start.getRotation(), end.getRotation(), blend);
		return new QuaternionTransform(position, rot);
	}
}
