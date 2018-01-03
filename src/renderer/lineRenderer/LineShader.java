package renderer.lineRenderer;

import shaders.shaderObjects.ShaderProgram;
import shaders.shaderObjects.UniformMat4;
import utils.SourceFile;

public class LineShader extends ShaderProgram {

	private static final SourceFile VERTEX_FILE = new SourceFile("/shaders/line/shader.vsh");
	private static final SourceFile FRAGMENT_FILE = new SourceFile("/shaders/line/shader.fsh");

	public UniformMat4 location_transformationMatrix = new UniformMat4("transformationMatrix");
	public UniformMat4 location_projectionViewMatrix = new UniformMat4("projectionViewMatrix");
	public UniformMat4 location__viewMatrix = new UniformMat4("viewMatrix");

	public LineShader() {
		super(ShaderProgram.newShaderProgram(VERTEX_FILE, FRAGMENT_FILE).addInput(0, "in_position").addOutput(0,
				"out_Color"));
		super.storeAllUniformLocations(location__viewMatrix, location_projectionViewMatrix,
				location_transformationMatrix);
	}
	
}
