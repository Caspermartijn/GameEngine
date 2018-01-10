package renderer.quadRenderer;

import shaders.Shader;
import shaders.ShaderProgram;
import shaders.uniforms.UniformMat4;
import shaders.uniforms.UniformVec4;
import utils.SourceFile;

public class QuadShader extends ShaderProgram {

	private static final SourceFile FRAGMENT_FILE = new SourceFile("/shaders/quad/shader.fsh");

	public UniformMat4 transform = new UniformMat4("transform");
	public UniformVec4 dimensions = new UniformVec4("dimensions");
	public UniformVec4 color = new UniformVec4("color");
	public UniformVec4 outlineColor = new UniformVec4("outlineColor");

	protected QuadShader() {
		super(ShaderProgram.newShaderProgram(Shader.vertexShaderQuad(), FRAGMENT_FILE).addInput(0, "inPosition")
				.addOutput(0, "outColor"));
		super.storeAllUniformLocations(transform, color, outlineColor, dimensions);
	}

}
