package terrains.terrainTexture;

public class TerrainPack {
	
	private TerrainTexturePack[] pack;
	private TerrainTexture blend;
	private String height;


	public TerrainPack(TerrainTexturePack[] pack, TerrainTexture blend, String height) {
		this.pack = pack;
		this.blend = blend;
		this.height = height;
	}

	public TerrainTexturePack[] getPack() {
		return pack;
	}

	public void setPack(TerrainTexturePack[] pack) {
		this.pack = pack;
	}

	public TerrainTexturePack getPack(int i) {
		return pack[i];
	}

	public TerrainTexture getBlend() {
		return blend;
	}

	public String getHeight() {
		return height;
	}

}
