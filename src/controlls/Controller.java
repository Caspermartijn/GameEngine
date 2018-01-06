package controlls;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.vector.Vector2f;

import utils.maths.Maths;

public class Controller {

	private int controller;

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
		return data[button - 10000] == 1;
	}

	public Vector2f getLeftStick() {
		FloatBuffer fb = GLFW.glfwGetJoystickAxes(controller);
		float[] data = new float[fb.capacity()];
		fb.get(data);
		data = Maths.controllerInterval(true,0.2f, data);
		return new Vector2f(data[0], data[1]);
	}

	public Vector2f getRightStick() {
		FloatBuffer fb = GLFW.glfwGetJoystickAxes(controller);
		float[] data = new float[fb.capacity()];
		fb.get(data);
		data = Maths.controllerInterval(false,0.2f, data);
		return new Vector2f(data[2], data[3]);
	}

	private boolean getBoolean(byte data) {
		if(data == 1) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean getButtonThumbL() {
		ByteBuffer buffer = GLFW.glfwGetJoystickButtons(controller);
		byte[] data = new byte[buffer.capacity()];
		buffer.get(data);
		return getBoolean(data[8]);
	}

	public boolean getButtonThumbR() {
		ByteBuffer buffer = GLFW.glfwGetJoystickButtons(controller);
		byte[] data = new byte[buffer.capacity()];
		buffer.get(data);
		return getBoolean(data[9]);
	}

	public float getButtonLT_L2() {
		FloatBuffer fb = GLFW.glfwGetJoystickAxes(controller);
		float[] data = new float[fb.capacity()];
		fb.get(data);
		return data[4];
	}

	public float getButtonRT_R2() {
		FloatBuffer fb = GLFW.glfwGetJoystickAxes(controller);
		float[] data = new float[fb.capacity()];
		fb.get(data);
		return data[5];
	}

	public boolean getButtonLS_L1() {
		ByteBuffer buffer = GLFW.glfwGetJoystickButtons(controller);
		byte[] data = new byte[buffer.capacity()];
		buffer.get(data);
		return getBoolean(data[8]);
	}

	public boolean getButtonRS_R1() {
		ByteBuffer buffer = GLFW.glfwGetJoystickButtons(controller);
		byte[] data = new byte[buffer.capacity()];
		buffer.get(data);
		return getBoolean(data[9]);
	}

	public boolean getButtonA_X() {
		ByteBuffer buffer = GLFW.glfwGetJoystickButtons(controller);
		byte[] data = new byte[buffer.capacity()];
		buffer.get(data);
		return getBoolean(data[0]);
	}

	public boolean getButtonX_SQUAIRE() {
		ByteBuffer buffer = GLFW.glfwGetJoystickButtons(controller);
		byte[] data = new byte[buffer.capacity()];
		buffer.get(data);
		return getBoolean(data[2]);
	}

	public boolean getButtonY_TRIANGLE() {
		ByteBuffer buffer = GLFW.glfwGetJoystickButtons(controller);
		byte[] data = new byte[buffer.capacity()];
		buffer.get(data);
		return getBoolean(data[3]);
	}

	public boolean getButtonB_CIRCLE() {
		ByteBuffer buffer = GLFW.glfwGetJoystickButtons(controller);
		byte[] data = new byte[buffer.capacity()];
		buffer.get(data);
		return getBoolean(data[1]);
	}

	public boolean getButtonUp() {
		ByteBuffer buffer = GLFW.glfwGetJoystickButtons(controller);
		byte[] data = new byte[buffer.capacity()];
		buffer.get(data);
		return getBoolean(data[10]);
	}

	public boolean getButtonDown() {
		ByteBuffer buffer = GLFW.glfwGetJoystickButtons(controller);
		byte[] data = new byte[buffer.capacity()];
		buffer.get(data);
		return getBoolean(data[12]);
	}

	public boolean getButtonLeft() {
		ByteBuffer buffer = GLFW.glfwGetJoystickButtons(controller);
		byte[] data = new byte[buffer.capacity()];
		buffer.get(data);
		return getBoolean(data[13]);
	}

	public boolean getButtonRight() {
		ByteBuffer buffer = GLFW.glfwGetJoystickButtons(controller);
		byte[] data = new byte[buffer.capacity()];
		buffer.get(data);
		return getBoolean(data[11]);
	}

}
