package utils.models;

import java.io.IOException;

import loader.objLoader.OBJLoader;
import objects.Model_3D;
import objects.Vao;
import textures.Texture;
import utils.SourceFile;

public class ModelLoader {

	private static Texture tex;
	private static Vao model;
	private static boolean errorModelB = false;

	public static Model_3D getModel(SourceFile model, SourceFile textureFile) {
		if (!errorModelB) {
			try {
				ModelLoader.model = OBJLoader.loadObjModel(new SourceFile("/engine/res/error.obj"));
				ModelLoader.tex = Texture.getTextureBuilder(new SourceFile("/engine/res/error.png")).create();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Vao vao = ModelLoader.model;
		Texture texture = ModelLoader.tex;
		try {
			vao = OBJLoader.loadObjModel(model);
			texture = Texture.getTextureBuilder(textureFile).create();

		} catch (IOException e) {
			vao = ModelLoader.model;
			texture = ModelLoader.tex;
		}
		return new Model_3D(vao, texture);
	}

}
