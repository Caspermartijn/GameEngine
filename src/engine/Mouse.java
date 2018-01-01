package engine; 

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

public class Mouse {

	private static float mousePosX, mousePosY;
	private static float mouseDX, mouseDY;
	private static float rotation = 0;
	private static float drotation = 0;
	private static float oldRotation = 0;
	private static boolean cursor = true;
	private static float oldX, oldY;

	protected static void init() {
		GLFW.glfwSetCursorPosCallback(Display.getWindowID(), (window, x, y) -> {
			if (oldX == 0 && oldY == 0) {
				oldX=(float) x;
				oldY=(float) y;
			}
			
			mousePosX = (float) (x / Display.getWidth() * 2 - 1);
			mousePosY = (float) (y / Display.getHeight() * 2 - 1);
			mouseDX = (float) ((x-oldX) / Display.getWidth() * 2);
			mouseDY = (float) ((y-oldY) / Display.getHeight() * 2);
			oldX = (float) x;
			oldY = (float) y;
		});

		GLFW.glfwSetScrollCallback(Display.getWindowID(), (window, x, y) -> {
			rotation = (float) y;
			drotation = (float) (y-oldRotation);
			oldRotation = (float) y;
		});
	}

	protected static void update() {
		drotation = 0;
		mouseDX = 0;
		mouseDY = 0;
	}
	
	public static boolean buttonPressed(int button) {
		return GLFW.glfwGetMouseButton(Display.getWindowID(), button) == GL11.GL_TRUE;
	}

	public static void setMouseEnabled(boolean value) {
		cursor = value;
		GLFW.glfwSetInputMode(Display.getWindowID(), GLFW.GLFW_CURSOR,
				(value) ? GLFW.GLFW_CURSOR_NORMAL : GLFW.GLFW_CURSOR_DISABLED);
	}

	public static void setCursorPosition(float x, float y) {
		GLFW.glfwSetCursorPos(Display.getWindowID(), (x / 2 + 0.5f) * Display.getWidth(),
				(y / 2 + 0.5f) * Display.getHeight());
	}

	public static float getMouseX() {
		return mousePosX;
	}

	public static float getMouseY() {
		return mousePosY;
	}

	public static float getMouseRotation() {
		return rotation;
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
