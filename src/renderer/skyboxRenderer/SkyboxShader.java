package renderer.skyboxRenderer;

import shaders.Shader;
import shaders.ShaderProgram;
import shaders.uniforms.UniformCubeMap;
import shaders.uniforms.UniformFloat;
import shaders.uniforms.UniformMat4;
import utils.SourceFile;

public class SkyboxShader extends ShaderProgram {

	private static final SourceFile VERTEX_FILE = new SourceFile("/shaders/skybox/shader.vsh");

	public UniformCubeMap cubeMap = new UniformCubeMap("cubeMap");
	public UniformMat4 projectionMatrix = new UniformMat4("projectionMatrix");
	public UniformMat4 viewMatrix = new UniformMat4("viewMatrix");
	public UniformFloat size = new UniformFloat("scale");

	protected SkyboxShader() {
		super(ShaderProgram.newShaderProgram(VERTEX_FILE, Shader.getFragmentFileSkybox()).addInput(0, "position")
				.addOutput(0, "out_Color"));
		super.storeAllUniformLocations(cubeMap, projectionMatrix, viewMatrix, size);
		start();
		cubeMap.loadTexUnit(0); 
		stop();
	}

}
