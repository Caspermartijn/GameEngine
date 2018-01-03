package entities;

import org.lwjgl.util.vector.Vector3f;

import utils.ModelLoader;
import utils.SourceFile;

public class TimeShip extends Entity {

	public TimeShip(Vector3f position, Vector3f rotation) {
		super(ModelLoader.getModel(new SourceFile("/res/models/timeship_1/model.obj"), new SourceFile("/res/models/timeship_1/texture.png")), position, rotation, 1f);
	}

}
