package entities;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import engine.Display;
import engine.Keyboard;
import engine.Mouse;
import objects.Camera;
import utils.ModelLoader;
import utils.SourceFile;

public class TimeShip extends Entity {

	private TimeShipCamera camera = new TimeShipCamera();
	private boolean controllable = false;
	private Vector3f velocity = new Vector3f();
	private float pitch, yaw;
	
	private static final float ACCELERATION_SPEED = 50;
	private static final float BRAKING_SPEED = 75;
	private static final float MAX_FRONT_SPEED = 80;
	private static final float MAX_SIDE_SPEED = 20;
	private static final float MOUSE_SENSITY = 100;
	
	public TimeShip(Vector3f position, Vector3f rotation) {
		super(ModelLoader.getModel(new SourceFile("/res/models/timeship_1/model.obj"), new SourceFile("/res/models/timeship_1/texture.png")), position, rotation, 1f);
	}

	@Override public void update() {
		if (controllable) {
			
			yaw += Mouse.getMouseDX() * MOUSE_SENSITY;
			pitch += Mouse.getMouseDY() * MOUSE_SENSITY;
			
			super.getTransform().rotX = pitch;
			super.getTransform().rotY = yaw;
			
//			System.out.println(yaw + " " + pitch);
			
			if (Keyboard.isKeyDown(GLFW.GLFW_KEY_W)) {
				if (velocity.z > -MAX_FRONT_SPEED)
					move(velocity, yaw, pitch,  (float) (-ACCELERATION_SPEED * Display.getFrameTime()));
			} else {
				if (velocity.z < 0)
					move(velocity, yaw, pitch,  (float) (BRAKING_SPEED * Display.getFrameTime()));
			}
			
			if (Keyboard.isKeyDown(GLFW.GLFW_KEY_S)) {
				if (velocity.z < MAX_SIDE_SPEED)
					move(velocity, yaw, pitch,  (float) (ACCELERATION_SPEED * Display.getFrameTime()));
			} else {
				if (velocity.z > 0)
					move(velocity, yaw, pitch,  (float) (-BRAKING_SPEED * Display.getFrameTime()));
			}
			
//			System.out.println(velocity.z);
			
			super.getTransform().posX += velocity.x * Display.getFrameTime();
			super.getTransform().posY += velocity.y * Display.getFrameTime();
			super.getTransform().posZ += velocity.z * Display.getFrameTime();
			
			camera.x = super.getTransform().posX;
			camera.y = super.getTransform().posY + 5;
			camera.z = super.getTransform().posZ + 30;
			camera.updateMatrix();
		}
	}
	
	public static void move(Vector3f start, float yaw, float pitch, float speed) {
		start.x += Math.sin(Math.toRadians(yaw)) * speed;
		start.y -= Math.sin(Math.toRadians(pitch)) * speed; 
		start.z += Math.cos(Math.toRadians(yaw)) * speed;
		start.z -= Math.sin(Math.toRadians(pitch)) * speed;

	}
	
//	public static void main(String[] args) {
//		Vector3f vec = new Vector3f();
//		move(vec, 0f, -90f, 1f);
//		System.out.println(vec);
//	}
	
	public Camera getCamera() {
		return camera;
	}

	public boolean isControllable() {
		return controllable;
	}

	public void setControllable(boolean controllable) {
		this.controllable = controllable;
	}
}

class TimeShipCamera extends Camera {
	
	private final static float FOV = 90;
	private final static float NEAR_PLANE = 0.1f;
	private final static float FAR_PLANE = 2000;

	protected void setProjectionMatrix(Matrix4f projection) {
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
	
}
