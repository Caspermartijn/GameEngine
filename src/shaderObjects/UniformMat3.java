package shaderObjects;

import java.nio.FloatBuffer;

import org.joml.Matrix3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import utils.Matrix;

public class UniformMat3 extends Uniform {

	private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(9);

	protected UniformMat3(String name) {
		super(name);
	}

	public void loadMatrix(Matrix3f matrix) {
		Matrix.store(matrix, matrixBuffer);
		matrixBuffer.flip();
		GL20.glUniformMatrix3fv(getLocation(), false, matrixBuffer);
	}

}
