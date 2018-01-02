package renderer.skyboxRenderer;

import shaders.shaderObjects.ShaderProgram;
import shaders.shaderObjects.UniformCubeMap;
import shaders.shaderObjects.UniformFloat;
import shaders.shaderObjects.UniformMat4;
import utils.SourceFile;

public class SkyboxShader extends ShaderProgram {

	private static final SourceFile VERTEX_FILE = new SourceFile("/shaders/skybox/shader.vsh");
	private static final SourceFile FRAGMENT_FILE = new SourceFile("/shaders/skybox/shader.fsh");

	public UniformCubeMap cubeMap = new UniformCubeMap("cubeMap");
	public UniformMat4 projectionMatrix = new UniformMat4("projectionMatrix");
	public UniformMat4 viewMatrix = new UniformMat4("viewMatrix");
	public UniformFloat size = new UniformFloat("scale");

	protected SkyboxShader() {
		super(ShaderProgram.newShaderProgram(VERTEX_FILE, FRAGMENT_FILE).addInput(0, "position").addOutput(0,
				"out_Color"));
		super.storeAllUniformLocations(cubeMap, projectionMatrix, viewMatrix, size);
		start();
		cubeMap.loadTexUnit(0);
		stop();
	}

}
