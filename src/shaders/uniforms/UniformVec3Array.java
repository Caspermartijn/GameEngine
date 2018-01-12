package shaders.uniforms;

import org.lwjgl.util.vector.Vector3f;

public class UniformVec3Array extends Uniform{
	
	private UniformVec3[] matrixUniforms;
	
	public UniformVec3Array(String name, int size) {
		super(name);
		matrixUniforms = new UniformVec3[size];
		for(int i=0;i<size;i++){
			matrixUniforms[i] = new UniformVec3(name + "["+i+"]");
		}
	}
	
	@Override
	protected boolean storeUniformLocation(int programID) {
		boolean success = true;
		for(UniformVec3 matrixUniform : matrixUniforms){
			if (!matrixUniform.storeUniformLocation(programID))
				success = false;
		}
		return success;
	}

	public void loadVector3fArray(Vector3f[] matrices){
		for(int i=0;i<matrices.length;i++){
			matrixUniforms[i].loadVec3(matrices[i]);
		}
	}

}
