package renderer.quadRenderer;

import shaders.ShaderProgram;
import shaders.UniformMat4;
import shaders.UniformVec4;
import utils.SourceFile;

public class QuadShader extends ShaderProgram {

	private static final SourceFile FRAGMENT_FILE = new SourceFile("/shaders/quad/shader.fsh");

	public UniformMat4 transform = new UniformMat4("transform");
	public UniformVec4 dimensions = new UniformVec4("dimensions");
	public UniformVec4 color = new UniformVec4("color");
	public UniformVec4 outlineColor = new UniformVec4("outlineColor");

	protected QuadShader() {
		super(ShaderProgram.newShaderProgram(vertexShader(), FRAGMENT_FILE).addInput(0, "inPosition").addOutput(0,
				"outColor"));
		super.storeAllUniformLocations(transform, color, outlineColor, dimensions);
	}

	private static String[] vertexShader() {
		String[] returnSS = new String[] { "#version 400 core",
				"in vec2 inPosition;",
				"out vec2 position;",
				"uniform mat4 transform;",
				"void main(void) {", "gl_Position = transform * vec4(inPosition, 0.0, 1.0);",
				"position = gl_Position.xy;", "}"
		};
		return returnSS;
	}

}
