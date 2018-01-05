package entities;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import engine.Display;
import engine.Keyboard;
import engine.Mouse;
import objects.Camera;
import utils.maths.Maths;
import utils.transformations.EulerTransform.RotationType;
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
	
	private static final float ACCELERATION_SPEED = 80;
	private static final float FRONT_BRAKING_SPEED = 100;
	private static final float BRAKING_SPEED = 50;
	private static final float MAX_FRONT_SPEED = 200;
	private static final float MAX_SPEED = 20;
	
	private static final float MOUSE_SENSITY = 20;
	private static final float MAX_ROTATION = 600;
	
	private static final float STABILIZATION_SPEED = 50;
	
	public TimeShip(Vector3f position, Vector3f rotation) {
		super(ModelLoader.getModel(new SourceFile("/res/models/timeship_1/model.obj"), new SourceFile("/res/models/timeship_1/texture.png")), position, rotation, 1f);
		super.getTransform().setType(RotationType.YXZ);
	}

	@Override public void update() {
		if (controllable) {
			
			float yawOffset = -(float) (Mouse.getMouseDX() * MOUSE_SENSITY);
			float pitchOffset = (float) (Mouse.getMouseDY() * MOUSE_SENSITY);
			
			float maxRot = (float) (MAX_ROTATION * Display.getFrameTime());
			yaw += Maths.clamp(-maxRot, maxRot, yawOffset);
			pitch +=  Maths.clamp(-maxRot, maxRot, pitchOffset);
			
			yaw = (yaw + 360) % 360;
			pitch = (pitch + 360) % 360;
			
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
			
			if (forwards) {
				if (velocity.z > -MAX_FRONT_SPEED) {
					velocity.z -= ACCELERATION_SPEED * Display.getFrameTime();
				} else {
					velocity.z = -MAX_FRONT_SPEED;
				}
			} else if (backwards) {
				if (velocity.z < MAX_SPEED) {
					velocity.z += ACCELERATION_SPEED * Display.getFrameTime();
				} else {
					velocity.z = MAX_SPEED;
				}
			} else {
				if (velocity.z > 0) {
					float newVelocity = (float) (velocity.z - BRAKING_SPEED * Display.getFrameTime());
					if (newVelocity < 0)
						velocity.z = 0;
					else 
						velocity.z = newVelocity;
				} else if (velocity.z < 0) {
					float newVelocity = (float) (velocity.z + FRONT_BRAKING_SPEED * Display.getFrameTime());
					if (newVelocity > 0)
						velocity.z = 0;
					else 
						velocity.z = newVelocity;
				}
			}
			
			if (left) {
				if (velocity.x > -MAX_SPEED) {
					velocity.x -= ACCELERATION_SPEED * Display.getFrameTime();
				} else {
					velocity.x = -MAX_SPEED;
				}
			} else if (right) {
				if (velocity.x < MAX_SPEED) {
					velocity.x += ACCELERATION_SPEED * Display.getFrameTime();
				} else {
					velocity.x = MAX_SPEED;
				}
			} else {
				if (velocity.x > 0) {
					float newVelocity = (float) (velocity.x - BRAKING_SPEED * Display.getFrameTime());
					if (newVelocity < 0)
						velocity.x = 0;
					else 
						velocity.x = newVelocity;
				} else if (velocity.x < 0) {
					float newVelocity = (float) (velocity.x + BRAKING_SPEED * Display.getFrameTime());
					if (newVelocity > 0)
						velocity.x = 0;
					else 
						velocity.x = newVelocity;
				}
			}
			
			if (down) {
				if (velocity.y > -MAX_SPEED) {
					velocity.y -= ACCELERATION_SPEED * Display.getFrameTime();
				} else {
					velocity.y = -MAX_SPEED;
				}
			} else if (up) {
				if (velocity.y < MAX_SPEED) {
					velocity.y += ACCELERATION_SPEED * Display.getFrameTime();
				} else {
					velocity.y = MAX_SPEED;
				}
			} else {
				if (velocity.y > 0) {
					float newVelocity = (float) (velocity.y - BRAKING_SPEED * Display.getFrameTime());
					if (newVelocity < 0)
						velocity.y = 0;
					else 
						velocity.y = newVelocity;
				} else if (velocity.y < 0) {
					float newVelocity = (float) (velocity.y + BRAKING_SPEED * Display.getFrameTime());
					if (newVelocity > 0)
						velocity.y= 0;
					else 
						velocity.y = newVelocity;
				}
			}
			
			if (Keyboard.isKeyDown(GLFW.GLFW_KEY_C)) {
				if (pitch != 0) {
					if (pitch > 180) {
						float newPitch = pitch += STABILIZATION_SPEED * Display.getFrameTime();
						if (newPitch > 360)
							pitch = 0;
						else 
							pitch = newPitch;
					} else {
						float newPitch = pitch -= STABILIZATION_SPEED * Display.getFrameTime();
						if (newPitch < 0)
							pitch = 0;
						else 
							pitch = newPitch;
					}
				}
			}
			
			
			Vector4f m = new Vector4f();
			Matrix4f.transform(getTransformationMatrix(), new Vector4f(velocity.x, velocity.y, velocity.z, 0.0f), m);
			super.getTransform().posX += m.x * Display.getFrameTime();
			super.getTransform().posY += m.y * Display.getFrameTime();
			super.getTransform().posZ += m.z * Display.getFrameTime();
			
			Vector4f v = new Vector4f(0, CAMERA_HEIGHT, CAMERA_DISTANCE, 1);
			Matrix4f.transform(getTransformationMatrix(), v, v);
			camera.x = v.x;
			camera.y = v.y;
			camera.z = v.z;
			camera.pitch = CAMERA_ANGLE-pitch;
			camera.yaw = -yaw;
			camera.roll = 0;
			
			camera.updateMatrix();
		}
	}
	
	public static void move(Vector3f start, float yaw, float pitch, float speed) {
		start.x += Math.sin(Math.toRadians(yaw)) * speed;
		start.y -= Math.sin(Math.toRadians(pitch)) * speed; 
		start.z += Math.cos(Math.toRadians(yaw)) * speed;
		start.z -= Math.sin(Math.toRadians(pitch)) * speed;

	}
	
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
