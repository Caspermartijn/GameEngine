package controlls;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.vector.Matrix4f;

import engine.EngineDisplay;
import engine.Keyboard;
import engine.Mouse;
import objects.Camera;
import utils.Maths;

public class FPSCamera extends Camera {

	private final static float FOV = 90;
	private final static float NEAR_PLANE = 0.1f;
	private final static float FAR_PLANE = 2000;

	private static final float MOUSE_SENSITY = 100;
	private static final float SPEED = 20;

	protected void setProjectionMatrix(Matrix4f projection) {
		float aspectRatio = (float) EngineDisplay.getWidth() / (float) EngineDisplay.getHeight();
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
		float dx = (float) (Mouse.getMouseDX() * MOUSE_SENSITY) / 1000;
		float dy = (float) (Mouse.getMouseDY() * MOUSE_SENSITY) / 1000;
		super.yaw += dx;
		super.pitch += dy;
		pitch = Maths.clamp(-90, 90, pitch);

		if (Keyboard.isKeyDown(GLFW.GLFW_KEY_W)) {
			super.x += Math.sin(Math.toRadians(yaw)) * EngineDisplay.getFrameTime() * SPEED;
			super.z -= Math.cos(Math.toRadians(yaw)) * EngineDisplay.getFrameTime() * SPEED;
		}
		if (Keyboard.isKeyDown(GLFW.GLFW_KEY_S)) {
			super.x += Math.sin(Math.toRadians(yaw)) * EngineDisplay.getFrameTime() * -SPEED;
			super.z -= Math.cos(Math.toRadians(yaw)) * EngineDisplay.getFrameTime() * -SPEED;
		}
		if (Keyboard.isKeyDown(GLFW.GLFW_KEY_A)) {
			super.x -= Math.sin(Math.toRadians(yaw + 90)) * EngineDisplay.getFrameTime() * SPEED;
			super.z += Math.cos(Math.toRadians(yaw + 90)) * EngineDisplay.getFrameTime() * SPEED;
		}
		if (Keyboard.isKeyDown(GLFW.GLFW_KEY_D)) {
			super.x -= Math.sin(Math.toRadians(yaw + 90)) * EngineDisplay.getFrameTime() * -SPEED;
			super.z += Math.cos(Math.toRadians(yaw + 90)) * EngineDisplay.getFrameTime() * -SPEED;
		}
		if (Keyboard.isKeyDown(GLFW.GLFW_KEY_LEFT_CONTROL)) {
			super.y -= SPEED * EngineDisplay.getFrameTime();
		}
		if (Keyboard.isKeyDown(GLFW.GLFW_KEY_SPACE)) {
			super.y += SPEED * EngineDisplay.getFrameTime();
		}
		super.updateMatrix();
	}

}
