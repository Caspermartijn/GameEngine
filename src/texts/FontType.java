package texts;

import textures.Texture;
import utils.SourceFile;

public class FontType {

	private Texture textureAtlas;
	private TextMeshCreator loader;

	private float width = 0.5f, smoothness = 0.1f;
	
	public FontType(SourceFile texture, SourceFile fontFile) {
		this.textureAtlas = Texture.getTextureBuilder(texture).normalMipMap().create();
		this.loader = new TextMeshCreator(fontFile);
	}
	
	public FontType(SourceFile texture, SourceFile fontFile, float width, float smoothness) {
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
}
