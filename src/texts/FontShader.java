package texts;

import shaderObjects.ShaderProgram;
import shaderObjects.UniformSampler;
import shaderObjects.UniformVec2;
import shaderObjects.UniformVec4;
import utils.SourceFile;

public class FontShader extends ShaderProgram {

	private static final SourceFile VERTEX_FILE = new SourceFile("/shaders/font/shader.vsh");
	private static final SourceFile FRAGMENT_FILE = new SourceFile("/shaders/font/shader.fsh");
	
	public UniformVec4 color = new UniformVec4("color");
	public UniformSampler texture = new UniformSampler("sampler");
	public UniformVec2 translation = new UniformVec2("translation");
	public UniformVec2 fontSettings = new UniformVec2("fontSettings");
	
	protected FontShader() {
		super(ShaderProgram.newShaderProgram(VERTEX_FILE, FRAGMENT_FILE).addInput(0, "inPosition").addInput(1, "inTextureCoords").addOutput(0, "outColor"));
		super.storeAllUniformLocations(color, texture, translation, fontSettings);
		super.start();
		texture.loadTexUnit(0);
		super.stop();
	}
	
}
