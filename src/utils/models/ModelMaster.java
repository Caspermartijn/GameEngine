package utils.models;

import java.util.HashMap;

import components.AnimationComponent;
import entities.Entity;
import loader.dfmeshLoader.DFModelLoader;
import objects.Model_3D;
import utils.SourceFile;

public class ModelMaster {

	private static HashMap<String, Model_3D> models = new HashMap<String, Model_3D>();

	public static Model_3D getOBJModel(String modelName) {
		if (models.containsKey(modelName)) {
			return models.get(modelName);
		} else {
			Model_3D model = ModelLoader.getOBJModel(new SourceFile("/res/models/" + modelName + "/model.obj"),
					new SourceFile("/res/models/" + modelName + "/texture.png"));

			String colorMapPath = "/res/models/" + modelName + "/colormap.png";
			if (Class.class.getResourceAsStream(colorMapPath) != null) {
				model.setColorMap(ModelLoader.loadTexture(new SourceFile(colorMapPath)));
			}
			models.put(modelName, model);
			return model;
		}
	}

	public static Model_3D getDFModel(String modelName) {
		if (models.containsKey(modelName)) {
			return models.get(modelName);
		} else {
			Model_3D model = ModelLoader.getDFModel(new SourceFile("/res/models/" + modelName + "/model.dfmesh"),
					new SourceFile("/res/models/" + modelName + "/texture.png"));
			models.put(modelName, model);
			return model;
		}
	}

	public static Model_3D getModel(String modelName, SourceFile folder) {
		Model_3D model = ModelLoader.getOBJModel(new SourceFile(folder, "model.obj"),
				new SourceFile(folder, "texture.png"));
		// model = loadModelData(model, folder.getPath() + modelName + "\\", modelName);
		models.put(modelName, model);
		return model;
	}

	public static void addAnimationComponentToEntity(String modelName, Entity e) {
		DFModelLoader.addArmatureComponent(new SourceFile("/res/models/" + modelName + "/model.dfmesh"), e);
	}
	
	public static void addAnimation(AnimationComponent c, String name) {
		c.addAnimation(DFModelLoader.loadAnimation(new SourceFile("/res/models/" + name + ".dfanim")));
	}

	public static void loadModels(String path) {

	}

	public static void loadModels(SourceFile modelsFile) {

	}

	public static void loadModel(String modelName, Model_3D model) {
		models.put(modelName, model);
	}

}
