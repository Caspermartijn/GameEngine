package engine;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

public class Mouse {

	private static double mousePosX, mousePosY;
	private static float mouseDX, mouseDY;
	private static double rotation = 0;
	private static float drotation = 0;
	private static float oldRotation = 0;
	private static boolean cursor = true;
	private static float oldX = -1, oldY = -1;

	protected static void init() {
		GLFW.glfwSetCursorPosCallback(Display.getWindowID(), (window, x, y) -> {

			if (oldX == -1 && oldY == -1) {
				oldX = (float) x;
				oldY = (float) y;
			}

			mousePosX = x;
			mousePosY = y;
		});

		GLFW.glfwSetScrollCallback(Display.getWindowID(), (window, x, y) -> {

			rotation = y;

		});
	}

	protected static void update() {
		drotation = (float) (rotation - oldRotation);
		mouseDX = (float) ((mousePosX - oldX) / Display.getWidth());
		mouseDY = (float) ((mousePosY - oldY) / Display.getHeight());

		oldX = (float) mousePosX;
		oldY = (float) mousePosY;

	}

	public static boolean buttonPressed(int button) {
		return GLFW.glfwGetMouseButton(Display.getWindowID(), button) == GL11.GL_TRUE;
	}

	public static void setMouseEnabled(boolean value) {
		cursor = value;
		GLFW.glfwSetInputMode(Display.getWindowID(), GLFW.GLFW_CURSOR,
				(value) ? GLFW.GLFW_CURSOR_NORMAL : GLFW.GLFW_CURSOR_DISABLED);
	}

	public static float getMouseX() {
		return (float) (mousePosX / Display.getWidth());
	}

	public static float getMouseY() {
		return (float) (mousePosY / Display.getHeight());
	}

	public static float getMouseRotation() {
		return (float) rotation;
	}

	public static float getMouseDX() {
		return mouseDX;
	}

	public static float getMouseDY() {
		return mouseDY;
	}

	public static float getRotationD() {
		return drotation;
	}

	public static boolean isCursorActivated() {
		return cursor;
	}
}
