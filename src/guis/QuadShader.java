package guis;

import shaderObjects.ShaderProgram;
import shaderObjects.UniformMat4;
import shaderObjects.UniformVec4;
import utils.SourceFile;

public class QuadShader extends ShaderProgram {

	private static final SourceFile VERTEX_FILE = new SourceFile("/shaders/quad/shader.vsh");
	private static final SourceFile FRAGMENT_FILE = new SourceFile("/shaders/quad/shader.fsh");
	
	public UniformMat4 transform = new UniformMat4("transform");
	public UniformVec4 dimensions = new UniformVec4("dimensions");
	public UniformVec4 color = new UniformVec4("color");
	public UniformVec4 outlineColor = new UniformVec4("outlineColor");
	
	protected QuadShader() {
		super(ShaderProgram.newShaderProgram(VERTEX_FILE, FRAGMENT_FILE).addInput(0, "inPosition").addOutput(0, "outColor"));
		super.storeAllUniformLocations(transform, color, outlineColor, dimensions);
	}

}
