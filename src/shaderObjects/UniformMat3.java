package shaderObjects;

import java.nio.FloatBuffer;

import org.joml.Matrix3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import utils.MatrixUtils;

public class UniformMat3 extends Uniform{

	private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(9);
	
	protected UniformMat3(String name) {
		super(name);	
	}
	
	public void loadMatrix(Matrix3f matrix){
		MatrixUtils.store(matrixBuffer, matrix);
		matrixBuffer.flip();
		GL20.glUniformMatrix3fv(getLocation(), false, matrixBuffer);
	}

}
