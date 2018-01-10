package renderer.skyboxRenderer;

import shaders.ShaderProgram;
import shaders.UniformCubeMap;
import shaders.UniformFloat;
import shaders.UniformMat4;
import utils.SourceFile;

public class SkyboxShader extends ShaderProgram {

	private static final SourceFile VERTEX_FILE = new SourceFile("/shaders/skybox/shader.vsh");

	public UniformCubeMap cubeMap = new UniformCubeMap("cubeMap");
	public UniformMat4 projectionMatrix = new UniformMat4("projectionMatrix");
	public UniformMat4 viewMatrix = new UniformMat4("viewMatrix");
	public UniformFloat size = new UniformFloat("scale");

	protected SkyboxShader() {
		super(ShaderProgram.newShaderProgram(VERTEX_FILE, getFragmentFile()).addInput(0, "position").addOutput(0,
				"out_Color"));
		super.storeAllUniformLocations(cubeMap, projectionMatrix, viewMatrix, size);
		start();
		cubeMap.loadTexUnit(0);
		stop();
	}

	private static String[] getFragmentFile() {
		String[] returnSS = new String[] { 
				"#version 400 core",

				"in vec3 textureCoords;", 
				"out vec4 out_Color;", 
				"uniform samplerCube cubeMap;",

				"void main(void) {",
				"out_Color = texture(cubeMap, textureCoords);", 
				"}" 
				};

		return returnSS;
	}

}
