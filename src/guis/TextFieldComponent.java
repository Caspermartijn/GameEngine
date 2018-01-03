package guis;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

import engine.Keyboard;
import engine.KeyboardListener;
import engine.Mouse;
import texts.Text;
import texts.TextMaster;
import utils.Collision;
import utils.Switch;
import utils.Vector;

public class TextFieldComponent extends QuadComponent {

	private static TextFieldComponent active;

	private Text text;
	private String textString = "";
	private int button = GLFW.GLFW_MOUSE_BUTTON_1;
	private Vector2f textPosition = new Vector2f();
	private Text curser;
	private Switch showCurser = new Switch(500L);
	private Runnable changeEvent;

	private KeyboardListener listener = new KeyboardListener() {
		@Override
		public void keyInput(char c) {
			if (c == '\b') {
				if (textString.length() > 0)
					textString = textString.substring(0, textString.length() - 1);
			} else {
				textString = textString.concat(((Character) c).toString());
			}
			if (changeEvent != null)
				changeEvent.run();
			text.setText(textString);
			text.applyChanges();
		}
	};

	public TextFieldComponent(GUI container, float x, float y, float width, float height, String font, float fontSize,
			boolean textCentered) {
		super(container, x, y, width, height);
		text = new Text("", fontSize, font, new Vector2f(x, y), width, textCentered);
		curser = new Text("|", fontSize, font, new Vector2f(x, y), width, false);
	}

	public void setTextPosition(Vector2f position) {
		this.textPosition = position;
	}

	public void setText(String text) {
		this.textString = text;
		this.text.setText(text);
		this.text.applyChanges();
	}

	@Override
	public void render() {

		if (!isActive() && Mouse.buttonPressed(button)) {
			Vector2f mouse = new Vector2f((float) Mouse.getMouseX(), (float) Mouse.getMouseY());
			if (Collision.rectanglePointCollision(mouse, new Vector4f(getX(), getY(), getWidth(), getHeight()))) {
				activate();
			}
		}

		super.render();

		Vector2f position = Vector.add(new Vector2f(getX(), getY()), textPosition, null);
		text.setPosition(position);
		TextMaster.getRenderer().renderText(text);

		if (isActive()) {
			showCurser.switchBoolean();
			if (showCurser.getBooleanValue()) {
				curser.setPosition(Vector.add(position, text.getCharacterPosition(textString.length()), null));
				TextMaster.getRenderer().renderText(curser);
			}
		}
	}

	public boolean isActive() {
		return active == this;
	}

	public void activate() {
		showCurser.setValue(false);
		active = this;
		Keyboard.setListener(listener);
	}

	public void deactivate() {
		active = null;
		Keyboard.removeListener();
	}

	public String getText() {
		return text.toString();
	}

	@Override
	public void delete() {
		text.delete();
		curser.delete();
	}

	public int getButton() {
		return button;
	}

	public void setButton(int button) {
		this.button = button;
	}

	public void setChangeEvent(Runnable changeEvent) {
		this.changeEvent = changeEvent;
	}
}
