package terrains.terrainTexture;

import textures.Texture;

public class TerrainTexture {

	private Texture texture;
	private Texture normalMap;
	private boolean useNormalMap = false;

	public TerrainTexture(Texture texture, Texture normalMap) {
		this.texture = texture;
		this.normalMap = normalMap;
		this.useNormalMap = true;
	}

	public TerrainTexture(Texture texture) {
		this.texture = texture;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public Texture getNormalMap() {
		return normalMap;
	}

	public void setNormalMap(Texture normalMap) {
		this.normalMap = normalMap;
		this.useNormalMap = true;
	}

	public boolean isUseNormalMap() {
		return useNormalMap;
	}

}
