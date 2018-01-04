package engine;

import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_FOCUSED;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_SAMPLES;
import static org.lwjgl.glfw.GLFW.GLFW_STENCIL_BITS;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetWindowAttrib;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.system.MemoryUtil;

public class Display {

	private static int WIDTH;
	private static int HEIGHT;

	private static long lastTime;
	private static double delta;

	private static int fps = 0;
	private static int frames = 0;
	private static double time;

	private static long id;

	public static void createDisplay(DisplayBuilder builder) {
		GLFWErrorCallback.createPrint(System.err).set();

		if (!glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}

		WIDTH = builder.width;
		HEIGHT = builder.height;

		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
		glfwWindowHint(GLFW_STENCIL_BITS, 4);
		glfwWindowHint(GLFW_SAMPLES, builder.samples);

		long monitor = !builder.fullscreen ? 0 : glfwGetPrimaryMonitor();
		id = glfwCreateWindow(WIDTH, HEIGHT, builder.title, monitor, 0);
		if (id == MemoryUtil.NULL)
			throw new RuntimeException("Failed to create the GLFW window");

		glfwShowWindow(id);
		glfwMakeContextCurrent(id);
		glfwSwapInterval(builder.vsync ? 1 : 0);

		GL.createCapabilities();

		GL11.glEnable(GL13.GL_MULTISAMPLE);

		Mouse.init();
		Keyboard.init();
	}

	public static void disposeDisplay() {
		Callbacks.glfwFreeCallbacks(id);
		glfwDestroyWindow(id);

		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

	/** updates events, frametime and fps counter */
	public static void updateEvents() {
		Mouse.update();
		glfwPollEvents();
		if (lastTime == 0)
			lastTime = System.nanoTime();
		long actualTime = System.nanoTime();
		long diffrence = actualTime - lastTime;
		delta = diffrence * 0.000000001d;
		lastTime = actualTime;

		time += delta;
		frames++;
		if (time > 1) {
			System.out.println(frames);
			time = 0;
			fps = frames;
			frames = 0;
		}
	}

	public static boolean isActive() {
		return glfwGetWindowAttrib(id, GLFW_FOCUSED) == 1;
	}

	public static void swapBuffers() {
		glfwSwapBuffers(id);
	}

	public static boolean isCloseRequested() {
		return glfwWindowShouldClose(id);
	}

	public static double getFrameTime() {
		return delta;
	}

	public static int getWidth() {
		return WIDTH;
	}

	public static int getHeight() {
		return HEIGHT;
	}

	public static int getFPS() {
		return fps;
	}

	public static DisplayBuilder getDisplayBuilder(int width, int height) {
		return DisplayBuilder.getNewDisplayBuilder(width, height);
	}

	public static long getWindowID() {
		return id;
	}

	public static float getAspectRatio() {
		return (float) WIDTH / (float) HEIGHT;
	}
}
