package terrains.terrainTexture;

import java.util.HashMap;

public class TerrainTexturePack {

	public static HashMap<Integer, TerrainTexturePack> packs = new HashMap<Integer, TerrainTexturePack>();

	private TerrainTexture back;
	private TerrainTexture red;
	private TerrainTexture green;
	private TerrainTexture blue;

	public TerrainTexturePack(TerrainTexture back, TerrainTexture red, TerrainTexture green, TerrainTexture blue) {
		this.back = back;
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	public static TerrainTexturePack getPack(int id) {
		return packs.get(id);
	}

	public TerrainTexture getBack() {
		return back;
	}

	public TerrainTexture getRed() {
		return red;
	}

	public TerrainTexture getGreen() {
		return green;
	}

	public TerrainTexture getBlue() {
		return blue;
	}

}
