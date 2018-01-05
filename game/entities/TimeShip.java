package entities;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import engine.Display;
import engine.Keyboard;
import engine.Mouse;
import objects.Camera;
import utils.EulerTransform.RotationType;
import utils.ModelLoader;
import utils.SourceFile;

public class TimeShip extends Entity {

	private TimeShipCamera camera = new TimeShipCamera();
	private boolean controllable = false;
	private Vector3f velocity = new Vector3f();
	private float pitch, yaw;
	
	private static final float CAMERA_HEIGHT = 8;
	private static final float CAMERA_DISTANCE = 30;
	private static final float CAMERA_ANGLE = 5;
	
	private static final float ACCELERATION_SPEED = 50;
	private static final float BRAKING_SPEED = 75;
	private static final float MAX_FRONT_SPEED = 80;
	private static final float MAX_SIDE_SPEED = 20;
	private static final float MOUSE_SENSITY = 100;
	
	public TimeShip(Vector3f position, Vector3f rotation) {
		super(ModelLoader.getModel(new SourceFile("/res/models/timeship_1/model.obj"), new SourceFile("/res/models/timeship_1/texture.png")), position, rotation, 1f);
		super.getTransform().setType(RotationType.YXZ);
	}

	@Override public void update() {
		if (controllable) {
			
			yaw += Mouse.getMouseDX() * MOUSE_SENSITY;
			pitch += Mouse.getMouseDY() * MOUSE_SENSITY;
			
			super.getTransform().rotX = pitch;
			super.getTransform().rotY = yaw;
			
			boolean forwards = false, backwards = false, left = false, right = false, up = false, down = false;
			
			if (Keyboard.isKeyDown(GLFW.GLFW_KEY_W))
				forwards = true;
			if (Keyboard.isKeyDown(GLFW.GLFW_KEY_S))
				backwards = true;
			if (Keyboard.isKeyDown(GLFW.GLFW_KEY_A))
				left = true;
			if (Keyboard.isKeyDown(GLFW.GLFW_KEY_D))
				right = true;
			if (Keyboard.isKeyDown(GLFW.GLFW_KEY_LEFT_CONTROL))
				down = true;
			if (Keyboard.isKeyDown(GLFW.GLFW_KEY_SPACE))
				up = true;
			
			if (forwards && backwards) 
				forwards = backwards = false;
			if (left && right) 
				left = right = false;
			if (up && down)
				up = down = false;
			
			
			
			super.getTransform().posX += velocity.x * Display.getFrameTime();
			super.getTransform().posY += velocity.y * Display.getFrameTime();
			super.getTransform().posZ += velocity.z * Display.getFrameTime();
			
			Vector4f v = new Vector4f(0, CAMERA_HEIGHT, CAMERA_DISTANCE, 1);
			Matrix4f.transform(getTransformationMatrix(), v, v);
			float distance = (float) Math.sqrt(CAMERA_DISTANCE*CAMERA_DISTANCE + CAMERA_HEIGHT*CAMERA_HEIGHT);
			camera.x = v.x;
			camera.y = v.y;
			camera.z = v.z;
			camera.pitch = CAMERA_ANGLE-pitch;
			camera.yaw = -yaw;
			
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
