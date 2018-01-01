package loader.modelLoader;

import java.io.InputStream;
import java.util.HashMap;

public class ModelMaster {

	private static HashMap<String, ModelData> models = new HashMap<String, ModelData>();

	public static void loadModels(String modelsPath) {
		InputStream inputStream = Class.class.getResourceAsStream("/res/models/modelNames.dat");
		
	}

}
