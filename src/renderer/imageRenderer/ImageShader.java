package renderer.imageRenderer;

import shaders.uniforms.ShaderProgram;
import shaders.uniforms.Uniform;
import shaders.uniforms.UniformMat4;
import shaders.uniforms.UniformSampler;
import shaders.uniforms.UniformVec3;
import utils.SourceFile;

public class ImageShader extends ShaderProgram {

	private static final SourceFile VERTEX_FILE = new SourceFile("/shaders/image/shader.vsh");
	private static final SourceFile FRAGMENT_FILE = new SourceFile("/shaders/image/shader.fsh");

	public UniformSampler texture = new UniformSampler("sampler");
	public UniformMat4 matrix = new UniformMat4("matrix");

	public UniformVec3 color_override = new UniformVec3("color_override");
	
	protected ImageShader() {
		super.init(ShaderProgram.newShaderProgram().addInput(0, "in_Position").addInput(1, "in_TextureCoords").addOutput(0,
				"out_Color")); 
	}

	protected SourceFile getVertexFile() {
		return VERTEX_FILE;
	}

	protected SourceFile getFragmentFile() {
		return FRAGMENT_FILE;
	}

	protected Uniform[] getAllUniforms() {
		return new Uniform[] { texture, matrix };
	}

}
