package renderer.armatureRenderer;

import animation.Joint;
import shaders.uniforms.ShaderProgram;
import shaders.uniforms.Uniform;
import shaders.uniforms.UniformMat4;
import shaders.uniforms.UniformMat4Array;
import shaders.uniforms.UniformSampler;
import shaders.uniforms.UniformVec3;
import utils.SourceFile;

public class ArmatureShader extends ShaderProgram {

	private static final int MAX_JOINTS = Joint.MAX_JOINTS;

	public UniformMat4 projectionViewMatrix = new UniformMat4("projectionViewMatrix");
	public UniformVec3 lightDirection = new UniformVec3("lightDirection");
	public UniformMat4Array jointTransforms = new UniformMat4Array("jointTransforms", MAX_JOINTS);
	public UniformMat4 modelMatrix = new UniformMat4("modelMatrix");

	public UniformSampler diffuseMap = new UniformSampler("diffuseMap");

	public ArmatureShader() {

	}

	@Override
	protected SourceFile getVertexFile() {
		return new SourceFile("/shaders/armature/shader.vsh");
	}

	@Override
	protected SourceFile getFragmentFile() {
		return new SourceFile("/shaders/armature/shader.fsh");
	}

	@Override
	protected Uniform[] getAllUniforms() {
		return new Uniform[] { projectionViewMatrix, lightDirection, jointTransforms, modelMatrix, diffuseMap };
	}

}
