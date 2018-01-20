package objects;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import engine.Display;
import engine.Keyboard;
import engine.Mouse;
import entities.Entity;
import hitbox.HBox;
import hitbox.HitBox;
import hitbox.HitBoxMaster;
import utils.maths.Maths;

public class FPS_Player extends Entity {

	private final static float FOV = 90;
	private final static float NEAR_PLANE = 0.1f;
	private final static float FAR_PLANE = 2000;

	private static final float MOUSE_SENSITY = 100;
	private static final float SPEED = 20;

	public FPS_Player(Model_3D model, Vector3f position, Vector3f rotation, HitBox playerHitBox, float scale) {
		super(model, position, rotation, scale);
		hitbox = playerHitBox;
		camera.setNewProj();
	}

	private Camera camera = new Camera(); 
	private float cameraheight = 7;

	private HBox hitbox;

	private boolean b = false;

	private float upSpeed = 0;

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

	public void updateInputs() {

		float dx = (float) (Mouse.getMouseDX() * MOUSE_SENSITY);
		float dy = (float) (Mouse.getMouseDY() * MOUSE_SENSITY);
		camera.yaw += dx;

		camera.pitch += dy;
		camera.pitch = Maths.clamp(-90, 90, camera.pitch);

		if (Keyboard.isKeyDown(GLFW.GLFW_KEY_H)) {
			b = true;
		}

		if (Keyboard.isKeyDown(GLFW.GLFW_KEY_W)) {
			super.getTransform().posX += Math.sin(Math.toRadians(camera.yaw + 90)) * Display.getFrameTime() * SPEED;
			super.getTransform().posZ -= Math.cos(Math.toRadians(camera.yaw + 90)) * Display.getFrameTime() * SPEED;
		}
		if (Keyboard.isKeyDown(GLFW.GLFW_KEY_S)) {
			super.getTransform().posX += Math.sin(Math.toRadians(camera.yaw + 90)) * Display.getFrameTime() * -SPEED;
			super.getTransform().posZ -= Math.cos(Math.toRadians(camera.yaw + 90)) * Display.getFrameTime() * -SPEED;
		}
		if (Keyboard.isKeyDown(GLFW.GLFW_KEY_A)) {
			super.getTransform().posX -= Math.sin(Math.toRadians(camera.yaw)) * Display.getFrameTime() * SPEED;
			super.getTransform().posZ += Math.cos(Math.toRadians(camera.yaw)) * Display.getFrameTime() * SPEED;
		}
		if (Keyboard.isKeyDown(GLFW.GLFW_KEY_D)) {
			super.getTransform().posX -= Math.sin(Math.toRadians(camera.yaw)) * Display.getFrameTime() * -SPEED;
			super.getTransform().posZ += Math.cos(Math.toRadians(camera.yaw)) * Display.getFrameTime() * -SPEED;
		}

		float actualY = cameraheight + getTransform().posY;

		super.getTransform().rotY = camera.yaw;

		camera.x = getTransform().posX;
		camera.y = actualY;
		camera.x = getTransform().posZ;

		if (b) {
			upSpeed += WorldSettings.GRAFITY / 100 * Display.getFrameTime();
		}
		super.getTransform().posY += upSpeed;

		if (HitBoxMaster.isInBox(hitbox)) {
			System.out.println("hey");
			super.getTransform().posY -= upSpeed;
			upSpeed = 0;
		}

		camera.updateMatrix();
	}

	public Camera getCamera() {
		return camera;
	}
	
	@Override
	public void update() {
		hitbox.setPosition(getTransform().getPosition());
	}

}
