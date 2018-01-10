package controlls;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.vector.Matrix4f;

import engine.Display;
import engine.Keyboard;
import engine.Mouse;
import objects.Camera;
import utils.maths.Maths;

public class FreeCam extends Camera {

	private final static float FOV = 90;
	private final static float NEAR_PLANE = 0.1f;
	private final static float FAR_PLANE = 2000;

	private static final float MOUSE_SENSITY = 100;
	private static final float SPEED = 20;

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
		pitch = Maths.clamp(-90, 90, pitch);

		float factor = (pitch / 90);
		float factor2 = (pitch / 90);
		factor = 1 - (float) Math.sqrt(factor * factor);
		factor2 = (float) Math.sqrt(factor * factor) - 1;

		float SPEED = FreeCam.SPEED;

		float xIncr = 0;
		float zIncr = 0;

		float xIncr2 = 0;
		float zIncr2 = 0;
		float xIncr3 = 0;
		float zIncr3 = 0;

		float yIncr = 0;
		float yIncr2 = 0;

		if (Keyboard.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)) {
			SPEED *= 2;
		}

		if (Keyboard.isKeyDown(GLFW.GLFW_KEY_W)) {
			xIncr += Math.sin(Math.toRadians(yaw)) * Display.getFrameTime() * SPEED;
			zIncr -= Math.cos(Math.toRadians(yaw)) * Display.getFrameTime() * SPEED;

			yIncr += Math.sin(Math.toRadians(-pitch)) * Display.getFrameTime() * SPEED;

		}
		if (Keyboard.isKeyDown(GLFW.GLFW_KEY_S)) {
			xIncr += Math.sin(Math.toRadians(yaw)) * Display.getFrameTime() * -SPEED;
			zIncr -= Math.cos(Math.toRadians(yaw)) * Display.getFrameTime() * -SPEED;

			yIncr += Math.sin(Math.toRadians(-pitch)) * Display.getFrameTime() * -SPEED;
		}
		if (Keyboard.isKeyDown(GLFW.GLFW_KEY_A)) {
			xIncr2 -= Math.sin(Math.toRadians(yaw + 90)) * Display.getFrameTime() * SPEED;
			zIncr2 += Math.cos(Math.toRadians(yaw + 90)) * Display.getFrameTime() * SPEED;
		}
		if (Keyboard.isKeyDown(GLFW.GLFW_KEY_D)) {
			xIncr2 -= Math.sin(Math.toRadians(yaw + 90)) * Display.getFrameTime() * -SPEED;
			zIncr2 += Math.cos(Math.toRadians(yaw + 90)) * Display.getFrameTime() * -SPEED;
		}
		/*
		if (Keyboard.isKeyDown(GLFW.GLFW_KEY_SPACE)) {
			xIncr3 -= Math.sin(Math.toRadians(yaw)) * Display.getFrameTime() * -SPEED;
			zIncr3 += Math.cos(Math.toRadians(yaw)) * Display.getFrameTime() * -SPEED;

			yIncr2 += Math.cos(Math.toRadians(-pitch)) * Display.getFrameTime() * SPEED;
		}
		if (Keyboard.isKeyDown(GLFW.GLFW_KEY_LEFT_CONTROL)) {
			xIncr3 += Math.sin(Math.toRadians(yaw)) * Display.getFrameTime() * -SPEED;
			zIncr3 -= Math.cos(Math.toRadians(yaw)) * Display.getFrameTime() * -SPEED;

			yIncr2 += Math.cos(Math.toRadians(-pitch)) * Display.getFrameTime() * -SPEED;
		}*/
		xIncr *= factor;
		zIncr *= factor;
		xIncr3 *= -factor2;
		zIncr3 *= -factor2;

		super.x += xIncr;
		super.x += xIncr2;
		super.x += xIncr3;

		super.y += yIncr;
		super.y -= yIncr2;

		super.z += zIncr;
		super.z += zIncr2;
		super.z += zIncr3;

		super.updateMatrix();
	}

}
