package engine.res;

import textures.Texture;
import utils.SourceFile;
import utils.models.ModelLoader;

public class ENGINE_RES {

	public static Texture emptyColorMap;
	public static Texture emptySpecularMap;

	public static void init() {
		Texture t = ModelLoader.loadTexture(new SourceFile("/engine/res/black.png"));
		emptyColorMap = t;
		emptySpecularMap = t;
	}

}
