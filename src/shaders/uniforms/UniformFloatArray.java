package shaders.uniforms;

public class UniformFloatArray extends Uniform{
	
	private UniformFloat[] matrixUniforms;
	
	public UniformFloatArray(String name, int size) {
		super(name);
		matrixUniforms = new UniformFloat[size];
		for(int i=0;i<size;i++){
			matrixUniforms[i] = new UniformFloat(name + "["+i+"]");
		}
	}
	
	@Override
	protected boolean storeUniformLocation(int programID) {
		boolean success = true;
		for(UniformFloat matrixUniform : matrixUniforms){
			if (!matrixUniform.storeUniformLocation(programID))
				success = false;
		}
		return success;
	}

	public void loadFloatArray(float[] matrices){
		for(int i=0;i<matrices.length;i++){
			matrixUniforms[i].loadFloat(matrices[i]);
		}
	}

}

