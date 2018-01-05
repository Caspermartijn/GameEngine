package utils.models;

import java.util.HashMap;

import objects.Model_3D;
import utils.SourceFile;

public class ModelMaster {

	private static HashMap<String, Model_3D> models = new HashMap<String, Model_3D>();

	public static Model_3D getModel(String modelName) {
		if (models.containsKey(modelName)) {
			return models.get(modelName);
		} else {
			Model_3D model = ModelLoader.getModel(new SourceFile("/res/models/" + modelName + "/model.obj"),
					new SourceFile("/res/models/" + modelName + "/texture.png"));
			models.put(modelName, model);
			return model;
		}
	}

	public static void loadModels(String path) {
		
	}

	public static void loadModels(SourceFile modelsFile) {

	}

}
