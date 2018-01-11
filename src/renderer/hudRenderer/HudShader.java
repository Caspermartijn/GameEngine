package renderer.hudRenderer;

import shaders.Shader;
import shaders.ShaderProgram;
import shaders.uniforms.UniformMat4;

public class HudShader extends ShaderProgram{
	
	public UniformMat4 location_transformationMatrix = new UniformMat4("transformationMatrix");

	protected HudShader() {
		super(ShaderProgram.newShaderProgram(Shader.vertexShaderHud(), Shader.getFragmentFileHud()).addInput(0, "position")
				.addOutput(0, "out_Color"));
		super.storeAllUniformLocations(location_transformationMatrix);
	}

}
