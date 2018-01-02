package shaders.shaderObjects;

import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Vector2f;

public class UniformVec2 extends Uniform {

	private float currentX;
	private float currentY;

	public UniformVec2(String name) {
		super(name);
	}

	public void loadVec2(Vector2f vector) {
		loadVec2(vector.x, vector.y);
	}

	public void loadVec2(float x, float y) {
		if (x != currentX || y != currentY) {
			this.currentX = x;
			this.currentY = y;
			GL20.glUniform2f(super.getLocation(), x, y);
		}
	}

}
