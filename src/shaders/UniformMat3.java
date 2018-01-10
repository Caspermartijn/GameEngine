package shaders;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix3f;

public class UniformMat3 extends Uniform {

	private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(9);

	protected UniformMat3(String name) {
		super(name);
	}

	public void loadMatrix(Matrix3f matrix) {
		matrix.store(matrixBuffer);
		matrixBuffer.flip();
		GL20.glUniformMatrix3fv(getLocation(), false, matrixBuffer);
	}

}
