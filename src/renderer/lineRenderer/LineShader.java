package renderer.lineRenderer;

import shaders.Shader;
import shaders.ShaderProgram;
import shaders.uniforms.UniformMat4;
import shaders.uniforms.UniformVec4;

public class LineShader extends ShaderProgram {

	public UniformMat4 location_transformationMatrix = new UniformMat4("transformationMatrix");
	public UniformMat4 location_projectionViewMatrix = new UniformMat4("projectionViewMatrix");
	public UniformMat4 location__viewMatrix = new UniformMat4("viewMatrix");

	public UniformVec4 color = new UniformVec4("color");

	public LineShader() {
		super(ShaderProgram.newShaderProgram(Shader.getVertexFileLine(), Shader.getFragmentFileLine())
				.addInput(0, "in_position").addOutput(0, "out_Color"));
		super.storeAllUniformLocations(location__viewMatrix, location_projectionViewMatrix,
				location_transformationMatrix);
	}

}
