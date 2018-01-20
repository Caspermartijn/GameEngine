package renderer.textRendering;

import shaders.uniforms.ShaderProgram;
import shaders.uniforms.Uniform;
import shaders.uniforms.UniformSampler;
import shaders.uniforms.UniformVec2;
import shaders.uniforms.UniformVec4;
import utils.SourceFile;

public class FontShader extends ShaderProgram {

	private static final SourceFile FRAGMENT_FILE = new SourceFile("/shaders/font/shader.fsh");

	private static final SourceFile VERTEX_FILE = new SourceFile("/shaders/font/shader.vsh");

	public UniformVec4 color = new UniformVec4("color");
	public UniformSampler texture = new UniformSampler("sampler");
	public UniformVec2 translation = new UniformVec2("translation");
	public UniformVec2 fontSettings = new UniformVec2("fontSettings");

	protected FontShader() {
		super.init(ShaderProgram.newShaderProgram().addInput(0, "inPosition").addInput(1, "inTextureCoords").addOutput(0,
				"outColor"));
		super.start();
		texture.loadTexUnit(0);
		super.stop();
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
		return new Uniform[] { color, texture, translation, fontSettings };
	}
	
}
