package texts;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

import objects.Vao;
import utils.tasks.Cleanup;

public class Text extends Cleanup {

	private String textString;
	private float fontSize;
	private float start_fontSize;

	private Vao textMeshVao;
	private int vertexCount;
	private Vector4f color = new Vector4f(0f, 0f, 0f, 1f);

	private Vector2f position;
	private float lineMaxSize;
	private int numberOfLines;

	private String font;

	private boolean centerText = false;

	public Text(String text, float fontSize, String font, Vector2f position, float maxLineLength, boolean centered) {
		this.textString = text;
		this.fontSize = fontSize;
		start_fontSize = fontSize;
		this.font = font;
		this.position = position;
		this.lineMaxSize = maxLineLength;
		this.centerText = centered;
		buildMesh();
	}

	private void buildMesh() {
		if (textMeshVao != null) {
			textMeshVao.delete();
		}

		TextMeshData data = Fonts.getFont(font).loadText(this);
		textMeshVao = data.toVao();
		vertexCount = data.getVertexCount();
		Vao.vaosDeleted--;
		Vao.vaosCreated--;
	}
	
	public float getStartFontSize() {
		return start_fontSize;
	}

	public void delete() {
		textMeshVao.delete();
		textMeshVao = null;
	}

	public String getFont() {
		return font;
	}

	public Vector4f getColor() {
		return color;
	}

	public int getNumberOfLines() {
		return numberOfLines;
	}

	public Vector2f getPosition() {
		return position;
	}

	public Vao getMesh() {
		return textMeshVao;
	}

	public int getVertexCount() {
		return this.vertexCount;
	}

	public float getFontSize() {
		return fontSize;
	}

	protected void setNumberOfLines(int number) {
		this.numberOfLines = number;
	}

	protected boolean isCentered() {
		return centerText;
	}

	protected float getMaxLineSize() {
		return lineMaxSize;
	}

	protected String getTextString() {
		return textString;
	}

	@Override
	public String toString() {
		return textString;
	}

	public void setText(String text) {
		this.textString = text;
	}

	public void setFontSize(float size) {
		this.fontSize = size;
	}

	public void setMaxLineLength(float length) {
		this.lineMaxSize = length;
	}

	public void setFont(String font) {
		this.font = font;
	}

	public void setTextCentered(boolean centered) {
		this.centerText = centered;
	}

	public void setColor(float r, float g, float b) {
		color.set(r, g, b, 1.0f);
	}

	public void setColor(float r, float g, float b, float a) {
		color.set(r, g, b, a);
	}

	public void setPosition(float x, float y) {
		position.set(x, y);
	}

	public void setPosition(Vector2f v) {
		position = v;
	}

	public void applyChanges() {
		buildMesh();
	}

	public Vector2f getCharacterPosition(int character) {
		return Fonts.getFont(font).getCharacterPosition(character, this);
	}

	public String getText() {
		return textString;
	}
	
	
}
