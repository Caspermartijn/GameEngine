package entities;

import org.lwjgl.util.vector.Vector3f;

import objects.Model_3D;
import utils.ModelLoader;
import utils.SourceFile;

public class TimeShip extends Entity {

	public TimeShip(Model_3D model, Vector3f position, Vector3f rotation) {
		super(ModelLoader.getModel(new SourceFile(""), new SourceFile("")), position, rotation, 1f);
	}

}
