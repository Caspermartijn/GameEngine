package renderer.textRendering;

import shaders.ShaderProgram;
import shaders.UniformSampler;
import shaders.UniformVec2;
import shaders.UniformVec4;
import utils.SourceFile;

public class FontShader extends ShaderProgram {

	private static final SourceFile FRAGMENT_FILE = new SourceFile("/shaders/font/shader.fsh");

	public UniformVec4 color = new UniformVec4("color");
	public UniformSampler texture = new UniformSampler("sampler");
	public UniformVec2 translation = new UniformVec2("translation");
	public UniformVec2 fontSettings = new UniformVec2("fontSettings");

	protected FontShader() {
		super(ShaderProgram.newShaderProgram(getVertexFile(), FRAGMENT_FILE).addInput(0, "inPosition")
				.addInput(1, "inTextureCoords").addOutput(0, "outColor"));
		super.storeAllUniformLocations(color, texture, translation, fontSettings);
		super.start();
		texture.loadTexUnit(0);
		super.stop();
	}

	private static String[] getVertexFile() {
		String[] returnSS = new String[] { "#version 400 core", "in vec2 inPosition;", "in vec2 inTextureCoords;",
				"out vec2 passTextureCoords;", "uniform vec2 translation;", "", "void main(void) {",
				"	gl_Position = vec4(inPosition + translation * vec2(2.0, -2.0), 0.0, 1.0);",
				"	passTextureCoords = inTextureCoords;", "}" };
		return returnSS;
	}

}
