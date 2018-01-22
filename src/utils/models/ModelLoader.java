package utils.models;

import java.io.IOException;
import java.util.HashMap;

import loader.dfmeshLoader.DFModelLoader;
import loader.objLoader.OBJLoader;
import objects.Model_3D;
import objects.Vao;
import textures.Texture;
import utils.SourceFile;

public class ModelLoader {

	public static HashMap<String, Texture> textures = new HashMap<String, Texture>();

	private static Texture tex;
	private static Vao model;
	private static boolean errorModelB = false;

	public static Model_3D getOBJModel(SourceFile model, SourceFile textureFile) {
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

	public static Texture loadTexture(SourceFile textureFile) {
		String filename = textureFile.getName();
		Texture texture = null;
		if (!textures.containsKey(filename)) {
			texture = Texture.getTextureBuilder(textureFile).create();
			textures.put(filename, texture);
		} else {
			texture = textures.get(filename);
		}
		return texture;
	}

	public static Texture loadTexture(SourceFile textureFile, boolean antiDupe) {
		String filename = textureFile.getName();
		Texture texture = null;
		if (antiDupe) {
			if (!textures.containsKey(filename)) {
				texture = Texture.getTextureBuilder(textureFile).create();
				textures.put(filename, texture);
			} else {
				texture = textures.get(filename);
			}
		} else {
			texture = Texture.getTextureBuilder(textureFile).create();
		}
		return texture;
	}

	public static Model_3D getDFModel(SourceFile model, SourceFile textureFile) {
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
		vao = DFModelLoader.loadDFMeshModel(model, true);
		texture = Texture.getTextureBuilder(textureFile).create();
		return new Model_3D(vao, texture);
	}

}
