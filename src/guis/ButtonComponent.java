package guis;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

import engine.Mouse;
import renderer.textRendering.FontRenderer;
import texts.Text;
import utils.Delay;
import utils.guis.Collision;

public class ButtonComponent extends QuadComponent {

	private int button = GLFW.GLFW_MOUSE_BUTTON_1;
	private Runnable clickEvent, hoverEvent;
	private Delay delay = new Delay(200);
	private Text text;
	private Vector2f textPosition = new Vector2f();

	public float ori_width, ori_height, ori_x, ori_y, ori_fontSize;

	public ButtonComponent(GUI container, float x, float y, float width, float height) {
		super(container, x, y, width, height);
		ori_width = width;
		ori_height = height;
		ori_x = x;
		ori_y = y;
	}

	@Override
	public void render() {
		Vector2f mouse = new Vector2f(Mouse.getMouseX(), Mouse.getMouseY());
		if (Collision.rectanglePointCollision(mouse, new Vector4f(getX(), getY(), getWidth(), getHeight()))) {
			if (hoverEvent != null) {
				hoverEvent.run();
			}
		}
		if (Mouse.buttonPressed(button) && clickEvent != null) {
			if (Collision.rectanglePointCollision(mouse, new Vector4f(getX(), getY(), getWidth(), getHeight()))
					&& delay.activate()) {
				clickEvent.run();
			}
		}

		super.render();

		super.setWidth(ori_width);
		super.setHeight(ori_height);
		super.setX(ori_x);
		super.setY(ori_y);
		if (text != null) {
			text.setPosition(Vector2f.add(new Vector2f(getX(), getY()), textPosition, null));
			FontRenderer.renderText(text);

			if (text.getFontSize() != ori_fontSize) {
				text.setFontSize(ori_fontSize);
				text.applyChanges();
			}

		}
	}

	public int getButton() {
		return button;
	}

	public Runnable getHoverEvent() {
		return hoverEvent;
	}

	public void playHover(float scale) {

		super.setWidth(ori_width * scale);
		super.setHeight(ori_height * scale);

		float xOff = (super.getWidth() - ori_width) / 2;
		float yOff = (super.getHeight() - ori_height) / 2;

		super.setX(ori_x - xOff);
		super.setY(ori_y - yOff);

		if (text != null) {
			text.setFontSize(ori_fontSize * scale);
			text.applyChanges();
		}
	}

	public void setHoverEvent(Runnable hoverEvent) {
		this.hoverEvent = hoverEvent;
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
		ori_fontSize = fontSize;
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
