package utils;

import loader.objLoader.OBJLoader;
import objects.Model_3D;
import textures.Texture;

public class ModelLoader {

	public static Model_3D getModel(SourceFile model, SourceFile texture) {
		return new Model_3D(OBJLoader.loadObjModel(model), Texture.getTextureBuilder(texture).create());
	}

	/*
	 * public static Model_3D getNormalMappedModel(SourceFile model, SourceFile
	 * texture){ Vao model = return new Model_3D(model, texture); }
	 */

}
