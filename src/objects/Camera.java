package objects;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import utils.Matrix;

public class Camera {

	private Matrix4f projection;
	private Matrix4f view;
	private Matrix4f camera;
	public float x, y, z, yaw, pitch, roll;

	public Camera() {
		camera = new Matrix4f();
		camera.identity();
		projection = new Matrix4f();
		projection.identity();
		view = new Matrix4f();
		view.identity();
		setProjectionMatrix(projection);
	}

	protected void setProjectionMatrix(Matrix4f projection) {
	}

	public Matrix4f getCameraMatrix() {
		return camera;
	}

	public Matrix4f getViewMatrix() {
		return view;
	}

	public Matrix4f getProjectionMatrix() {
		return projection;
	}

	public void updateMatrix() {
		yaw = (yaw + 360) % 360;
		pitch = (pitch + 180 + 360) % 360 - 180;
		roll = (roll + 360) % 360;
		view.identity();
		view.rotate((float) Math.toRadians(pitch), new Vector3f(1, 0, 0));
		view.rotate((float) Math.toRadians(yaw), new Vector3f(0, 1, 0));
		view.rotate((float) Math.toRadians(roll), new Vector3f(0, 0, 1));
		view.translate(new Vector3f(-x, -y, -z));
		Matrix.mul(projection, view, camera);
	}

}
