package renderer.textRendering;

import shaders.Shader;
import shaders.ShaderProgram;
import shaders.uniforms.UniformSampler;
import shaders.uniforms.UniformVec2;
import shaders.uniforms.UniformVec4;
import utils.SourceFile;

public class FontShader extends ShaderProgram {

	private static final SourceFile FRAGMENT_FILE = new SourceFile("/shaders/font/shader.fsh");

	public UniformVec4 color = new UniformVec4("color");
	public UniformSampler texture = new UniformSampler("sampler");
	public UniformVec2 translation = new UniformVec2("translation");
	public UniformVec2 fontSettings = new UniformVec2("fontSettings");

	protected FontShader() {
		super(ShaderProgram.newShaderProgram(Shader.getVertexFileFont(), FRAGMENT_FILE).addInput(0, "inPosition")
				.addInput(1, "inTextureCoords").addOutput(0, "outColor"));
		super.storeAllUniformLocations(color, texture, translation, fontSettings);
		super.start();
		texture.loadTexUnit(0);
		super.stop();
	}

}
