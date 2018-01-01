package loader.objLoader;

import loader.objLoader.objConverter.ModelData;
import loader.objLoader.objConverter.OBJFileLoader;
import objects.Vao;
import utils.SourceFile;

public class OBJLoader {

	public static Vao loadObjModel(SourceFile file) {
		Vao vao = Vao.create();
		ModelData data = OBJFileLoader.loadOBJ(file);
		return vao.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());
	}

}
