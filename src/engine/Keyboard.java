package engine;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

public class Keyboard {

	private static KeyboardListener listener;
	private static ArrayList<Key> keys = new ArrayList<>();

	private static long holdStart = System.currentTimeMillis();
	private static final long holdingLimit = 300;
	private static boolean holding;
	
	protected static void init() {
		addAllChars();
		
		GLFW.glfwSetKeyCallback(EngineDisplay.getWindowID(), (window, key, scancode, action, mods) -> {
			if (listener == null)
				return;
			
			if (action != 2 && action != 0)
				return;
			
			if (action == 2 && !holding) {
				holding = true;
				holdStart = System.currentTimeMillis();
			}
			if (action != 2) {
				holding = false;
			}
			if (System.currentTimeMillis() - holdStart < holdingLimit && action == 2) {
				return;
			}
				
			for (Key k : keys) {
				if (k.getCode() == key) {
					if (isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT))
						listener.keyInput(k.getShiftCharacter());
					else if (isKeyDown(GLFW.GLFW_KEY_RIGHT_ALT))
						listener.keyInput(k.getAltCharacter());
					else
						listener.keyInput(k.getCharacter());
					break;
				}
			}
		});
	}

	private static void addAllChars() {
		keys.add(new Key(GLFW.GLFW_KEY_Q, 'q', 'Q', '@'));
		keys.add(new Key(GLFW.GLFW_KEY_W, 'w', 'W'));
		keys.add(new Key(GLFW.GLFW_KEY_E, 'e', 'E', '€'));
		keys.add(new Key(GLFW.GLFW_KEY_R, 'r', 'R'));
		keys.add(new Key(GLFW.GLFW_KEY_T, 't', 'T'));
		keys.add(new Key(GLFW.GLFW_KEY_Y, 'z', 'Z'));
		keys.add(new Key(GLFW.GLFW_KEY_U, 'u', 'U'));
		keys.add(new Key(GLFW.GLFW_KEY_I, 'i', 'I'));
		keys.add(new Key(GLFW.GLFW_KEY_O, 'o', 'O'));
		keys.add(new Key(GLFW.GLFW_KEY_P, 'p', 'P'));
		keys.add(new Key(GLFW.GLFW_KEY_A, 'a', 'A'));
		keys.add(new Key(GLFW.GLFW_KEY_S, 's', 'S'));
		keys.add(new Key(GLFW.GLFW_KEY_D, 'd', 'D'));
		keys.add(new Key(GLFW.GLFW_KEY_F, 'f', 'F'));
		keys.add(new Key(GLFW.GLFW_KEY_G, 'g', 'G'));
		keys.add(new Key(GLFW.GLFW_KEY_H, 'h', 'H'));
		keys.add(new Key(GLFW.GLFW_KEY_J, 'j', 'J'));
		keys.add(new Key(GLFW.GLFW_KEY_K, 'k', 'K'));
		keys.add(new Key(GLFW.GLFW_KEY_L, 'l', 'L'));
		keys.add(new Key(GLFW.GLFW_KEY_Z, 'y', 'Y'));
		keys.add(new Key(GLFW.GLFW_KEY_X, 'x', 'X'));
		keys.add(new Key(GLFW.GLFW_KEY_C, 'c', 'C'));
		keys.add(new Key(GLFW.GLFW_KEY_V, 'v', 'V'));
		keys.add(new Key(GLFW.GLFW_KEY_B, 'b', 'B'));
		keys.add(new Key(GLFW.GLFW_KEY_N, 'n', 'N'));
		keys.add(new Key(GLFW.GLFW_KEY_M, 'm', 'M'));
		
		keys.add(new Key(GLFW.GLFW_KEY_BACKSPACE, '\b'));
		keys.add(new Key(GLFW.GLFW_KEY_SPACE, ' '));
		
		keys.add(new Key(GLFW.GLFW_KEY_1, '1', '!'));
		keys.add(new Key(GLFW.GLFW_KEY_2, '2', '"', '²'));
		keys.add(new Key(GLFW.GLFW_KEY_3, '3', '§', '³'));
		keys.add(new Key(GLFW.GLFW_KEY_4, '4', '$'));
		keys.add(new Key(GLFW.GLFW_KEY_5, '5', '%'));
		keys.add(new Key(GLFW.GLFW_KEY_6, '6', '&'));
		keys.add(new Key(GLFW.GLFW_KEY_7, '7', '/', '{'));
		keys.add(new Key(GLFW.GLFW_KEY_8, '8', '(', '['));
		keys.add(new Key(GLFW.GLFW_KEY_9, '9', ')', ']'));
		keys.add(new Key(GLFW.GLFW_KEY_0, '0', '=', '}'));
		
		keys.add(new Key(GLFW.GLFW_KEY_COMMA, ',', ';'));
		keys.add(new Key(GLFW.GLFW_KEY_PERIOD, '.', ':'));
		keys.add(new Key(GLFW.GLFW_KEY_MINUS, 'ß', '?'));
		keys.add(new Key(GLFW.GLFW_KEY_SLASH, '-', '_'));
		
		//todo: add more keys
	}

	public static void setListener(KeyboardListener l) {
		listener = l;
	}

	public static void removeListener() {
		listener = null;
	}

	public static boolean isKeyDown(int key) {
		return GLFW.glfwGetKey(EngineDisplay.getWindowID(), key) == GL11.GL_TRUE;
	}

}
