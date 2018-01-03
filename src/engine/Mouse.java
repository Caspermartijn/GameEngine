package engine; 

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

public class Mouse {

	private static double mousePosX, mousePosY;
	private static double mouseDX, mouseDY;
	private static double rotation = 0;
	private static double drotation = 0;
	private static double oldRotation = 0;
	private static boolean cursor = true;
	private static double oldX = Display.getWidth() / 2, oldY = Display.getHeight() / 2;

	protected static void init() {
		GLFW.glfwSetCursorPosCallback(Display.getWindowID(), (window, x, y) -> {
			mousePosX = (x / ((double) Display.getWidth()) * 2.0 - 1.0);
			mousePosY = (y / ((double) Display.getHeight()) * 2.0 - 1.0);
			mouseDX = ((x-oldX) / ((double) Display.getWidth()) * 2.0);
			mouseDY = ((y-oldY) / ((double) Display.getHeight()) * 2.0);
			oldX = x;
			oldY = y;
		});

		GLFW.glfwSetScrollCallback(Display.getWindowID(), (window, x, y) -> {
			rotation = y;
			drotation = (y-oldRotation);
			oldRotation = y;
		});
	}

	public static void update() {
		drotation = 0;
		mouseDX = 0;
		mouseDY = 0;
		
		if (!cursor) {
			GLFW.glfwSetCursorPos(Display.getWindowID(), Display.getWidth() / 2, Display.getHeight() / 2);
			oldX = Display.getWidth() / 2;
			oldY = Display.getHeight() / 2;
		}
	}
	
	public static boolean buttonPressed(int button) {
		return GLFW.glfwGetMouseButton(Display.getWindowID(), button) == GL11.GL_TRUE;
	}

	public static void setMouseEnabled(boolean value) {
		cursor = value;
		GLFW.glfwSetInputMode(Display.getWindowID(), GLFW.GLFW_CURSOR,
				(value) ? GLFW.GLFW_CURSOR_NORMAL : GLFW.GLFW_CURSOR_HIDDEN);
	}

	public static void setCursorPosition(float x, float y) {
		GLFW.glfwSetCursorPos(Display.getWindowID(), (x / 2 + 0.5f) * Display.getWidth(),
				(y / 2 + 0.5f) * Display.getHeight());
	}

	public static double getMouseX() {
		return mousePosX;
	}

	public static double getMouseY() {
		return mousePosY;
	}

	public static double getMouseRotation() {
		return rotation;
	}

	public static double getMouseDX() {
		return mouseDX;
	}

	public static double getMouseDY() {
		return mouseDY;
	}

	public static double getRotationD() {
		return drotation;
	}

	public static boolean isCursorActivated() {
		return cursor;
	}
}
