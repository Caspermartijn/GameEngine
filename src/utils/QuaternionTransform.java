package utils;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class QuaternionTransform {

	public float x, y, z, scaleX=1, scaleY=1, scaleZ=1;
	public Quaternion quaternion;
	
	public QuaternionTransform() {
		quaternion = new Quaternion(0, 0, 0);
	}
	
	public QuaternionTransform(Matrix4f matrix) {
		x = matrix.m03();
		y = matrix.m13();
		z = matrix.m23();
		quaternion = new Quaternion(matrix);
	}

	public QuaternionTransform(Vector3f translation, Vector3f scale, Quaternion rotation) {
		x = translation.x;
		y = translation.y;
		z = translation.z;
		scaleX = scale.x;
		scaleY = scale.y;
		scaleZ = scale.z;
		quaternion = rotation;
	}
	
	public QuaternionTransform(float x, float y, float z, float scaleX, float scaleY, float scaleZ, float qx, float qy, float qz, float qw) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		this.scaleZ = scaleZ;
		quaternion=new Quaternion(qx, qy, qz, qw);
	} 
	
	public QuaternionTransform(float x, float y, float z, float scaleX, float scaleY, float scaleZ, float qdx, float qdy, float qdz) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		this.scaleZ = scaleZ;
		quaternion = new Quaternion(qdx, qdy, qdz);
	}
	
	public Matrix4f toMatrix() {
		Matrix4f matrix = new Matrix4f();
		matrix.identity();
		matrix.scale(new Vector3f(scaleX, scaleY, scaleZ));
		matrix.translate(new Vector3f(x, y, z));
		matrix.mul( quaternion.toRotationMatrix());
		return matrix;
	}
	
	public Vector3f getTranslation() {
		return new Vector3f(x, y, z);
	}
	
	public Vector3f getScale() {
		return new Vector3f(scaleX, scaleY, scaleZ);
	}
	
	public Quaternion getRotation() {
		return quaternion;
	}
	
	public QuaternionTransform interpolate(QuaternionTransform start, QuaternionTransform end, float blend) {
		blend = Maths.clamp(0, 1, blend);
		Vector3f position = Maths.interpolate(start.getTranslation(), end.getTranslation(), blend);
		Vector3f scale = Maths.interpolate(start.getScale(), end.getScale(), blend);
		Quaternion rot = Quaternion.slerp(start.getRotation(), end.getRotation(), blend);
		return new QuaternionTransform(position, scale, rot);
	}
}
