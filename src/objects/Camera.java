package objects;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import utils.Matrix;

public class Camera {

	private Matrix4f projection;
	private Matrix4f view;
	private Matrix4f camera;
	public float x, y, z, yaw, pitch, roll;

	public Camera() {
		camera = new Matrix4f();
		camera.setIdentity();
		projection = new Matrix4f();
		projection.setIdentity();
		view = new Matrix4f();
		view.setIdentity();
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
		view.setIdentity();
		Matrix4f.rotate((float) Math.toRadians(pitch), new Vector3f(1, 0, 0), view, view);
		Matrix4f.rotate((float) Math.toRadians(yaw), new Vector3f(0, 1, 0), view, view);
		Matrix4f.rotate((float) Math.toRadians(roll), new Vector3f(0, 0, 1), view, view);
		Matrix4f.translate(new Vector3f(-x, -y, -z), view, view);
		Matrix4f.mul(projection, view, camera);
	}

}
