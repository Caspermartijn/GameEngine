package objects;

import engine.res.ENGINE_RES;
import textures.Texture;

public class Model_3D {

	private Vao mesh;
	private Texture texture;
	private Texture colorMap = ENGINE_RES.emptyColorMap;
	private Texture specularMap = ENGINE_RES.emptySpecularMap;

	private boolean backfaceCullingEnabled = true;

	public Model_3D(Vao mesh, Texture texture) {
		this.mesh = mesh;
		this.texture = texture;
	}

	public Vao getMesh() {
		return mesh;
	}

	public void setColorMap(Texture colorMap) {
		this.colorMap = colorMap;
	}

	public void setSpecularMap(Texture specularMap) {
		this.specularMap = specularMap;
	}

	public Texture getTexture() {
		return texture;
	}

	public Texture getColorMap() {
		return colorMap;
	}

	public Texture getSpecularMap() {
		return specularMap;
	}

	public boolean isBackfaceCullingEnabled() {
		return backfaceCullingEnabled;
	}

	public Model_3D setBackfaceCullingEnabled(boolean backfaceCullingEnabled) {
		this.backfaceCullingEnabled = backfaceCullingEnabled;
		return this;
	}

}
