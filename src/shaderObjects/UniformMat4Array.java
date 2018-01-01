package shaderObjects;

import org.lwjgl.util.vector.Matrix4f;

public class UniformMat4Array extends Uniform{
	
	private UniformMat4[] matrixUniforms;
	
	public UniformMat4Array(String name, int size) {
		super(name);
		matrixUniforms = new UniformMat4[size];
		for(int i=0;i<size;i++){
			matrixUniforms[i] = new UniformMat4(name + "["+i+"]");
		}
	}
	
	@Override
	protected boolean storeUniformLocation(int programID) {
		boolean success = true;
		for(UniformMat4 matrixUniform : matrixUniforms){
			if (!matrixUniform.storeUniformLocation(programID))
				success = false;
		}
		return success;
	}

	public void loadMatrixArray(Matrix4f[] matrices){
		for(int i=0;i<matrices.length;i++){
			matrixUniforms[i].loadMatrix(matrices[i]);
		}
	}
	
	

}
