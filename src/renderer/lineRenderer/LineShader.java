package renderer.lineRenderer;

import shaders.ShaderProgram;
import shaders.UniformMat4;

public class LineShader extends ShaderProgram {

	public UniformMat4 location_transformationMatrix = new UniformMat4("transformationMatrix");
	public UniformMat4 location_projectionViewMatrix = new UniformMat4("projectionViewMatrix");
	public UniformMat4 location__viewMatrix = new UniformMat4("viewMatrix");

	public LineShader() {
		super(ShaderProgram.newShaderProgram(getVertexFile(), getFragmentFile()).addInput(0, "in_position").addOutput(0,
				"out_Color"));
		super.storeAllUniformLocations(location__viewMatrix, location_projectionViewMatrix,
				location_transformationMatrix);
	}

	private static String[] getVertexFile() {
		String[] returnSS = new String[] { 
				"#version 150", 
				"in vec4 in_position;",
				"uniform mat4 viewMatrix;",
				"uniform mat4 transformationMatrix;",
				"uniform mat4 projectionViewMatrix;", 
				"void main() {",
					"vec4 worldPosition = transformationMatrix * in_position;",
					"gl_Position = projectionViewMatrix * viewMatrix * worldPosition;", 
				"}" };
		return returnSS;
	}

	private static String[] getFragmentFile() {
		String[] returnSS = new String[] { 
				"#version 150",
				"out vec4 out_Color;",
				"void main() {", 
				"out_Color = vec4(1.0, 1.0, 1.0, 1.0);", 
				"}" 
				};
		return returnSS;
	}
}
