package entities;

import java.util.Random;

import org.lwjgl.util.vector.Vector3f;

import engine.EngineDisplay;
import objects.Model_3D;

public class TimeCube extends Entity {

	private float z = 0;
	private float y = 0;
	private float x = 0;

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

	@Override
	public void update() {
		this.getTransform().rotX += (x * EngineDisplay.getFrameTime());
		this.getTransform().rotY += (y * EngineDisplay.getFrameTime());
		this.getTransform().rotZ += (z * EngineDisplay.getFrameTime());
	}

}
