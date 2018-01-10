package objects;

import textures.Texture;

public class Model_3D {

	private Vao mesh;
	private Texture texture;

	private boolean backfaceCullingEnabled = true;

	public Model_3D(Vao mesh, Texture texture) {
		this.mesh = mesh;
		this.texture = texture;
	}

	public Vao getMesh() {
		return mesh;
	}

	public Texture getTexture() {
		return texture;
	}

	public boolean isBackfaceCullingEnabled() {
		return backfaceCullingEnabled;
	}

	public Model_3D setBackfaceCullingEnabled(boolean backfaceCullingEnabled) {
		this.backfaceCullingEnabled = backfaceCullingEnabled;
		return this;
	}

}
