package guis;

import org.lwjgl.util.vector.Vector2f;

import texts.Text;
import texts.TextMaster;
import utils.maths.Vector;

public class TextComponent extends GUIComponent {
	
	private GUI container;
	private Text text;
	private Vector2f textPosition;
	
	public TextComponent(GUI container, String text, String font, float fontSize, float lineLength, boolean centered) {
		this.text = new Text(text, fontSize, font, new Vector2f(0, 0), lineLength, centered);
		textPosition = new Vector2f();
		this.container = container;
		container.addComponent(this);
	}
	
	@Override
	public void render() {
		text.setPosition(getPosition());
		
		TextMaster.getRenderer().renderText(text);
	}
	
	public Vector2f getPosition() {
		return Vector.add(container.getPosition(), textPosition, null);
	}
	
	public void setColor(float r, float g, float b, float a) {
		text.setColor(r, g, b, a);
	}
	
	public void setPosition(float x, float y) {
		textPosition = new Vector2f(x, y);
	}

	public void setText(String text) {
		this.text.setText(text);
		this.text.applyChanges();
	}
	
	public String getText() {
		return text.getText();
	}
	
	@Override
	public void delete() {
		text.delete();
	}
}
