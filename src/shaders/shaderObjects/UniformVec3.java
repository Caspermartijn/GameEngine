package shaders.shaderObjects;

import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Vector3f;

public class UniformVec3 extends Uniform {
	private float currentX;
	private float currentY;
	private float currentZ;

	public UniformVec3(String name) {
		super(name);
	}

	public void loadVec3(Vector3f vector) {
		loadVec3(vector.x, vector.y, vector.z);
	}

	public void loadVec3(float x, float y, float z) {
		if (x != currentX || y != currentY || z != currentZ) {
			this.currentX = x;
			this.currentY = y;
			this.currentZ = z;
			GL20.glUniform3f(super.getLocation(), x, y, z);
		}
	}

}
