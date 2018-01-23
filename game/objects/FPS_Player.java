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

	public static final float GRAFITY = -7.5f;
	private static final float JUMP_POWER = 1.3f;

	private FPS_HUD hud;

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

	public void updateInputs() {
		float dx = (float) (Mouse.getMouseDX() * MOUSE_SENSITY);
		float dy = (float) (Mouse.getMouseDY() * MOUSE_SENSITY);
		super.yaw += dx;
		super.pitch += dy;
		pitch = Maths.clamp(-70, 80, pitch);

		float xIncr = 0;
		float zIncr = 0;

		float xIncr2 = 0;
		float zIncr2 = 0;

		if (Keyboard.isKeyDown(GLFW.GLFW_KEY_W)) {
			xIncr += Math.sin(Math.toRadians(yaw)) * Display.getFrameTime() * SPEED;
			zIncr -= Math.cos(Math.toRadians(yaw)) * Display.getFrameTime() * SPEED;
		}
		if (Keyboard.isKeyDown(GLFW.GLFW_KEY_S)) {
			xIncr += Math.sin(Math.toRadians(yaw)) * Display.getFrameTime() * -SPEED;
			zIncr -= Math.cos(Math.toRadians(yaw)) * Display.getFrameTime() * -SPEED;
		}
		if (Keyboard.isKeyDown(GLFW.GLFW_KEY_A)) {
			xIncr2 -= Math.sin(Math.toRadians(yaw + 90)) * Display.getFrameTime() * SPEED;
			zIncr2 += Math.cos(Math.toRadians(yaw + 90)) * Display.getFrameTime() * SPEED;
		}
		if (Keyboard.isKeyDown(GLFW.GLFW_KEY_D)) {
			xIncr2 -= Math.sin(Math.toRadians(yaw + 90)) * Display.getFrameTime() * -SPEED;
			zIncr2 += Math.cos(Math.toRadians(yaw + 90)) * Display.getFrameTime() * -SPEED;
		}
		if (Keyboard.isKeyDown(GLFW.GLFW_KEY_SPACE)) {
			jump();
		}

		super.x += xIncr;
		super.x += xIncr2;

		super.z += zIncr;
		super.z += zIncr2;

		upSpeed += GRAFITY * Display.getFrameTime();
		super.y += upSpeed;

		float terrainH = Scene.getCurrentscene().getTerrains().get(0).getHeightOfTerrain(this.x, this.z);
		if (super.y < terrainH + 5) {
			upSpeed = 0;
			isInAir = false;
			super.y = terrainH + 5;
		}

		super.updateMatrix();
	}

	private void jump() {
		if (!isInAir) {
			this.upSpeed = JUMP_POWER;
			isInAir = true;
		}
	}

}
