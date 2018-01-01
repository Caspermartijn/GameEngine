package texts;

import org.joml.Vector2f;

import textures.Texture;
import utils.SourceFile;

public class FontType {

	private Texture textureAtlas;
	private TextMeshCreator loader;

	private float width = 0.5f, smoothness = 0.1f;
	
	protected FontType(SourceFile texture, SourceFile fontFile) {
		this.textureAtlas = Texture.getTextureBuilder(texture).normalMipMap().create();
		this.loader = new TextMeshCreator(fontFile);
	}
	
	protected FontType(SourceFile texture, SourceFile fontFile, float width, float smoothness) {
		this.textureAtlas = Texture.getTextureBuilder(texture).normalMipMap().create();
		this.loader = new TextMeshCreator(fontFile);
		this.width = width; 
		this.smoothness = smoothness;
	}

	public void delete() {
		textureAtlas.delete();
	}
	
	public Texture getTexture() {
		return textureAtlas;
	}

	public TextMeshData loadText(Text text) {
		return loader.createTextMesh(text);
	}

	public void setFontSettings(float width, float smoothness) {
		this.width = width; 
		this.smoothness = smoothness;
	}

	public float getWidth() {
		return width;
	}

	public float getSmoothness() {
		return smoothness;
	}

	public Vector2f getCharacterPosition(int character, Text text) {
		return loader.getCharacterPosition(character, text);
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public void setSmoothness(float smoothness) {
		this.smoothness = smoothness;
	}
}
