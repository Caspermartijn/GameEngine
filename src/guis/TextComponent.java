package guis;

import org.lwjgl.util.vector.Vector2f;

import renderer.textRendering.FontRenderer;
import texts.Text;


public class TextComponent extends GUIComponent {
	
	private GUI container;
	private Text text;
	private Vector2f textPosition;
	
	public TextComponent(GUI container, String text, String font, float fontSize, float lineLength, boolean centered) {
		super(container);
		this.text = new Text(text, fontSize, font, new Vector2f(0, 0), lineLength, centered);
		textPosition = new Vector2f();
		this.container = container;
	}
	
	@Override
	public void render() {
		text.setPosition(getPosition());
		
		FontRenderer.renderText(text);
	}
	
	public Vector2f getPosition() {
		return Vector2f.add(container.getPosition(), textPosition, null);
	}
	
	public void setColor(float r, float g, float b, float a) {
		text.setColor(r, g, b, a);
	}
	
	public void setPosition(float x, float y) {
		textPosition = new Vector2f(x, y);
	}

	public void setText(String text) {
		if (!this.text.getText().equals(text)) {
			this.text.setText(text);
			this.text.applyChanges();
		}
	}
	
	public String getText() {
		return text.getText();
	}
	
	@Override
	public void delete() {
		text.delete();
	}
}
