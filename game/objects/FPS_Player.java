package objects;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import engine.Display;
import engine.Keyboard;
import engine.Mouse;
import guis.FPS_HUD;
import scenes.Scene;
import utils.maths.Maths;

public class FPS_Player extends Camera {

	private final static float FOV = 90;
	private final static float NEAR_PLANE = 0.1f;
	private final static float FAR_PLANE = 2000;

	private static final float MOUSE_SENSITY = 100;
	private static final float SPEED = 20;

	public static final float GRAFITY = -5.50f;
	private static final float JUMP_POWER = 1.50f;

	private FPS_HUD hud;

	private float playerHeight = 5;

	public FPS_Player(Vector3f start_position) {
		super.x = start_position.x;
		super.y = start_position.y + 5;
		super.z = start_position.z;
		hud = new FPS_HUD();
	}

	private float upSpeed = 0;

	private boolean isInAir = false;

	public FPS_HUD getFPS_HUD() {
		return hud;
	}

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

	private float startXincr = 0, startZincr = 0, startXincr2 = 0, startZincr2 = 0;

	public void updateInputs() {
		float dx = (float) (Mouse.getMouseDX() * MOUSE_SENSITY);
		float dy = (float) (Mouse.getMouseDY() * MOUSE_SENSITY);
		super.yaw += dx;
		super.pitch += dy;
		pitch = Maths.clamp(-70, 80, pitch);

		float xIncr = startXincr;
		float zIncr = startZincr;

		float xIncr2 = startXincr2;
		float zIncr2 = startZincr2;

		float actual_speed = SPEED;

		if (Keyboard.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)) {
			if (!isInAir) {
				actual_speed = SPEED * 1.75f;
				if (crouched) {
					undoCrouch();
				}
			}
		}

		if (!isInAir) {
			if (Keyboard.isKeyDown(GLFW.GLFW_KEY_W)) {
				xIncr += Math.sin(Math.toRadians(yaw)) * Display.getFrameTime() * actual_speed;
				zIncr -= Math.cos(Math.toRadians(yaw)) * Display.getFrameTime() * actual_speed;

			}
			if (Keyboard.isKeyDown(GLFW.GLFW_KEY_S)) {
				xIncr += Math.sin(Math.toRadians(yaw)) * Display.getFrameTime() * -actual_speed;
				zIncr -= Math.cos(Math.toRadians(yaw)) * Display.getFrameTime() * -actual_speed;

			}
			if (Keyboard.isKeyDown(GLFW.GLFW_KEY_A)) {
				xIncr2 -= Math.sin(Math.toRadians(yaw + 90)) * Display.getFrameTime() * SPEED;
				zIncr2 += Math.cos(Math.toRadians(yaw + 90)) * Display.getFrameTime() * SPEED;
			}
			if (Keyboard.isKeyDown(GLFW.GLFW_KEY_D)) {
				xIncr2 -= Math.sin(Math.toRadians(yaw + 90)) * Display.getFrameTime() * -SPEED;
				zIncr2 += Math.cos(Math.toRadians(yaw + 90)) * Display.getFrameTime() * -SPEED;
			}
		}

		if (Keyboard.isKeyDown(GLFW.GLFW_KEY_SPACE)) {
			jump();
			setStartIncr(xIncr, zIncr, xIncr2, zIncr2);
		}
		if (Keyboard.isKeyDown(GLFW.GLFW_KEY_LEFT_CONTROL)) {
			if (!crouchedButtonPress) {
				crouchedButtonPress = true;
				if (!crouched) {
					crouch();
				} else {
					undoCrouch();
				}
			}
		} else {
			crouchedButtonPress = false;
		}

		super.x += xIncr;
		super.x += xIncr2;

		super.z += zIncr;
		super.z += zIncr2;

		upSpeed += GRAFITY * Display.getFrameTime();
		super.y += upSpeed;

		float terrainH = 0;
		if (Scene.getCurrentscene().getTerrains().size() >= 1) {
			terrainH = Scene.getCurrentscene().getTerrains().get(0).getHeightOfTerrain(this.x, this.z);
		}

		if (super.y < terrainH + playerHeight) {
			upSpeed = 0;
			isInAir = false;
			super.y = terrainH + playerHeight;
			resetStartVelocity();
		}

		if (crouched) {
			playerHeight = 2.5f;
		} else {
			playerHeight = 5;
		}

		super.updateMatrix();
	}

	private void setStartIncr(float xIncr, float zIncr, float xIncr2, float zIncr2) {
		startXincr = xIncr;
		startXincr2 = xIncr2;
		startZincr = zIncr;
		startZincr2 = zIncr2;
	}

	private void resetStartVelocity() {
		startXincr = 0;
		startXincr2 = 0;
		startZincr = 0;
		startZincr2 = 0;
	}

	private boolean crouched = false;
	private boolean crouchedButtonPress = false;

	private void crouch() {
		crouched = true;
	}

	private void undoCrouch() {
		crouched = false;
		playerHeight = 3.25f;
	}

	private void jump() {
		if (crouched) {
			undoCrouch();
		}
		if (!isInAir) {
			this.upSpeed = JUMP_POWER;
			isInAir = true;
		}
	}

}
