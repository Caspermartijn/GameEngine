package shaders.uniforms;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;

public class UniformMat4 extends Uniform{
	
	private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

	public UniformMat4(String name) {
		super(name);
	}
	
	public void loadMatrix(Matrix4f matrix){
		matrix.store(matrixBuffer);
		matrixBuffer.flip();
		GL20.glUniformMatrix4fv(getLocation(), false, matrixBuffer);
	}

}
