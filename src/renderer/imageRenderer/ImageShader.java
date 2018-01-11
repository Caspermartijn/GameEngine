package renderer.imageRenderer;

import shaders.ShaderProgram;
import shaders.uniforms.*;
import utils.SourceFile;

public class ImageShader extends ShaderProgram {

	private static final SourceFile VERTEX_FILE = new SourceFile("/shaders/image/shader.vsh");
	private static final SourceFile FRAGMENT_FILE = new SourceFile("/shaders/image/shader.fsh");

	public UniformSampler texture = new UniformSampler("sampler");
	public UniformMat4 matrix = new UniformMat4("matrix");

	protected ImageShader() {
		super(ShaderProgram.newShaderProgram().addInput(0, "in_Position").addInput(1, "in_TextureCoords").addOutput(0,
				"out_Color"));
	}

	@Override
	protected SourceFile getVertexFile() {
		return VERTEX_FILE;
	}

	@Override
	protected SourceFile getFragmentFile() {
		return FRAGMENT_FILE;
	}

	@Override
	protected Uniform[] getAllUniforms() {
		return new Uniform[] { texture, matrix };
	}

}
