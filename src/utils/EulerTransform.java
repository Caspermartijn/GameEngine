package utils;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class EulerTransform {

	public float posX, posY, posZ, rotX, rotY, rotZ, scaleX = 1, scaleY = 1, scaleZ = 1;
	
	public EulerTransform() {}

	public EulerTransform(float posX, float posY, float posZ, float rotX, float rotY, float rotZ, float scaleX, float scaleY,
			float scaleZ) {
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
		matrix.identity();
		matrix.translate(new Vector3f(posX, posY, posZ));
		matrix.rotate((float) Math.toRadians(rotX), new Vector3f(1, 0, 0));
		matrix.rotate((float) Math.toRadians(rotY), new Vector3f(0, 0, 1));
		matrix.rotate((float) Math.toRadians(rotZ), new Vector3f(0, 1, 0));
		matrix.scale(new Vector3f(scaleX, scaleY, scaleZ));
		return matrix;
	}
	
}
