package loader.modelLoader;

import java.util.HashMap;

import objects.Model_3D;

public class Models {

	private static HashMap<String, Model_3D> models = new HashMap<String, Model_3D>();

	public static void addModel(String model_name, Model_3D model) {
		models.put(model_name, model);
	}
	
	public static Model_3D getModel_3D(String string) {
		return models.get(string);
	}

}
