package engine;

import javax.swing.JPanel;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
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

		if (!GLFW.glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}

		WIDTH = builder.width;
		HEIGHT = builder.height;

		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
		GLFW.glfwWindowHint(GLFW.GLFW_SAMPLES, builder.samples);

		long monitor = !builder.fullscreen ? 0 : GLFW.glfwGetPrimaryMonitor();
		id = GLFW.glfwCreateWindow(WIDTH, HEIGHT, builder.title, monitor, 0);
		if (id == MemoryUtil.NULL)
			throw new RuntimeException("Failed to create the GLFW window");

		GLFW.glfwShowWindow(id);
		GLFW.glfwMakeContextCurrent(id);
		GLFW.glfwSwapInterval(builder.vsync ? 1 : 0);

		GL.createCapabilities();

		GL11.glEnable(GL13.GL_MULTISAMPLE);

		Mouse.init();
		Keyboard.init();
	}

	public static void disposeDisplay() {
		Callbacks.glfwFreeCallbacks(id);
		GLFW.glfwDestroyWindow(id);

		GLFW.glfwTerminate();
		GLFW.glfwSetErrorCallback(null).free();
	}

	/** updates events, frametime and fps counter */
	public static void update() {
		Mouse.update();
		GLFW.glfwPollEvents();

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
		return GLFW.glfwGetWindowAttrib(id, GLFW.GLFW_FOCUSED) == 1;
	}

	public static void swapBuffers() {
		GLFW.glfwSwapBuffers(id);
	}

	public static boolean isCloseRequested() {
		return GLFW.glfwWindowShouldClose(id);
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
