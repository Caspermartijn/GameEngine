package entities;

import java.util.Random;

import org.lwjgl.util.vector.Vector3f;

import engine.Display;
import objects.Model_3D;
import utils.models.ModelMaster;

public class TimeCube extends Entity {

	private float z = 0;
	private float y = 0;
	private float x = 0;
	
	private static Model_3D model = ModelMaster.getOBJModel("timecube_1");

	public TimeCube(Model_3D model, Vector3f position, Vector3f rotation, float scale) {
		super(model, position, rotation, scale);
		Random r2 = new Random();
		z = r2.nextInt(20);
		y = r2.nextInt(20);
		x = r2.nextInt(20);
		z *= 10;
		y *= 10;
		x *= 10;
	}

	public TimeCube(Vector3f position, Vector3f rotation, float scale) {
		super(model, position, rotation, scale);
		Random r2 = new Random();
		z = r2.nextInt(20);
		y = r2.nextInt(20);
		x = r2.nextInt(20);
		z *= 10;
		y *= 10;
		x *= 10;
	}

	@Override
	public void update() {
		this.getTransform().rotX += (x * Display.getFrameTime());
		this.getTransform().rotY += (y * Display.getFrameTime());
		this.getTransform().rotZ += (z * Display.getFrameTime());
	}

}
