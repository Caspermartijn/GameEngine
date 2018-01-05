package guis;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

import engine.Mouse;
import texts.Text;
import texts.TextMaster;
import utils.Delay;
import utils.guis.Collision;
import utils.maths.Vector;

public class ButtonComponent extends QuadComponent {

	private int button = GLFW.GLFW_MOUSE_BUTTON_1;
	private Runnable clickEvent;
	private Delay delay = new Delay(200);
	private Text text;
	private Vector2f textPosition = new Vector2f();

	public ButtonComponent(GUI container, float x, float y, float width, float height) {
		super(container, x, y, width, height);
	}

	@Override
	public void render() {
		if (Mouse.buttonPressed(button) && clickEvent != null) {
			Vector2f mouse = new Vector2f((float) Mouse.getMouseX(), (float) Mouse.getMouseY());
			if (Collision.rectanglePointCollision(mouse, new Vector4f(getX(), getY(), getWidth(), getHeight()))
					&& delay.activate()) {
				clickEvent.run();
			}
		}

		super.render();

		if (text != null) {
			text.setPosition(Vector.add(new Vector2f(getX(), getY()), textPosition, null));
			TextMaster.getRenderer().renderText(text);
		}
	}

	public int getButton() {
		return button;
	}

	public void setButton(int button) {
		this.button = button;
	}

	public Runnable getClickEvent() {
		return clickEvent;
	}

	public void setClickEvent(Runnable clickEvent) {
		this.clickEvent = clickEvent;
	}

	public void setClickDelay(long delay) {
		this.delay.setDelay(delay);
	}

	public void setText(String message, String font, float fontSize) {
		if (text != null)
			text.delete();
		text = new Text(message, fontSize, font, new Vector2f(super.getX(), super.getY()), super.getWidth(), true);
	}

	public void setTextPosition(Vector2f position) {
		this.textPosition = position;
	}

	@Override
	public void delete() {
		if (text != null) {
			text.delete();
		}
	}

	public Text getText() {
		return text;
	}
}
