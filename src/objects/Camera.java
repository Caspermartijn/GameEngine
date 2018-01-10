package objects;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import engine.Display;

public class Camera {

	private final static float FOV = 90;
	private final static float NEAR_PLANE = 0.1f;
	private final static float FAR_PLANE = 2000;
	
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
	
	public void setNewProj() {
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
		float x_scale = y_scale / aspectRatio;
		float frustum_length = FAR_PLANE - NEAR_PLANE;

		projection.m00 = x_scale;
		projection.m11 = y_scale;
		projection.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
		projection.m23 = -1;
		projection.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
		projection.m33 = 0;
		
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
		view.setIdentity();
		Matrix4f.rotate((float) Math.toRadians(pitch), new Vector3f(1, 0, 0), view, view);
		Matrix4f.rotate((float) Math.toRadians(yaw), new Vector3f(0, 1, 0), view, view);
		Matrix4f.rotate((float) Math.toRadians(roll), new Vector3f(0, 0, 1), view, view);
		Matrix4f.translate(new Vector3f(-x, -y, -z), view, view);
		Matrix4f.mul(projection, view, camera);
	}

}
