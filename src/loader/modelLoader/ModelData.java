package loader.modelLoader;

import utils.SourceFile;

public class ModelData {

	private SourceFile model;
	private SourceFile texture;

	public ModelData(SourceFile model, SourceFile texture) {
		this.model = model;
		this.texture = texture;
	}

	public SourceFile getModel() {
		return model;
	}

	public SourceFile getTexture() {
		return texture;
	}

}
