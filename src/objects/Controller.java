package objects;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.vector.Vector2f;

public class Controller {

	private int controller;
	
	public static final int CONTROLLER_BUTTON_A =10000, CONTROLLER_BUTTON_B = 10001, CONTROLLER_BUTTON_X = 10002, CONTROLLER_BUTTON_Y = 10003; 
	public static final int CONTROLLER_BUTTON_LB = 10004, CONTROLLER_BUTTON_RB = 10005;
	public static final int CONTROLLER_BUTTON_BACK = 10006, CONTROLLER_BUTTON_START = 10007;
	public static final int CONTROLLER_LEFT_STICK = 10008, CONTROLLER_RIGHT_STICK = 10009;
	public static final int CONTROLLER_DPAD_UP =10010, CONTROLLER_DPAD_RIGHT = 10011, CONTROLLER_DPAD_DOWN = 10012, CONTROLLER_DPAD_LEFT = 10013; 
	
	public Controller(int controller) {
		this.controller = controller;
	}
	
	public String getName() {
		return GLFW.glfwGetJoystickName(controller);
	}
	
	public boolean isActive() {
		return GLFW.glfwJoystickPresent(controller);
	}

	public boolean isButtonsDown(int button) {
		ByteBuffer bb = GLFW.glfwGetJoystickButtons(controller);
		byte[] data = new byte[bb.capacity()];
		bb.get(data);
		return data[button-10000] == 1;
	}
	
	public Vector2f getLeftStick() {
		FloatBuffer fb = GLFW.glfwGetJoystickAxes(controller);
		float[] data = new float[fb.capacity()];
		fb.get(data);
		return new Vector2f(data[0], data[1]);
	}
	
	public Vector2f getRightStick() {
		FloatBuffer fb = GLFW.glfwGetJoystickAxes(controller);
		float[] data = new float[fb.capacity()];
		fb.get(data);
		return new Vector2f(data[2], data[3]);
	}
	
	public float getButtonLT() {
		FloatBuffer fb = GLFW.glfwGetJoystickAxes(controller);
		float[] data = new float[fb.capacity()];
		fb.get(data);
		return data[4];
	}
	
	public float getButtonRT() {
		FloatBuffer fb = GLFW.glfwGetJoystickAxes(controller);
		float[] data = new float[fb.capacity()];
		fb.get(data);
		return data[5];
	}
}
