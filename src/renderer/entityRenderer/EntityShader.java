package renderer.entityRenderer;

import shaders.ShaderProgram;
import shaders.UniformFloat;
import shaders.UniformMat4;
import shaders.UniformSampler;
import shaders.UniformVec3;
import shaders.shaderObjects.*;
import utils.SourceFile;

public class EntityShader extends ShaderProgram {

	private static final SourceFile VERTEX_FILE = new SourceFile("/shaders/entity/shader.vsh");
	private static final SourceFile FRAGMENT_FILE = new SourceFile("/shaders/entity/shader.fsh");

	public UniformMat4 location_transformationMatrix = new UniformMat4("transformationMatrix");
	public UniformMat4 location_projectionMatrix = new UniformMat4("projectionMatrix");
	public UniformMat4 location_viewMatrix = new UniformMat4("viewMatrix");
	public UniformVec3 location_lightPosition = new UniformVec3("lightPosition");
	public UniformVec3 location_lightColour = new UniformVec3("lightColour");
	public UniformFloat location_shineDamper = new UniformFloat("shineDamper");
	public UniformFloat location_reflectivity = new UniformFloat("reflectivity");
	public UniformSampler texture = new UniformSampler("modelTexture");

	public EntityShader() {
		super(ShaderProgram.newShaderProgram(VERTEX_FILE, FRAGMENT_FILE).addInput(0, "position")
				.addInput(1, "textureCoords").addOutput(0, "out_Color"));
		super.storeAllUniformLocations(texture, location_lightColour, location_lightPosition, location_projectionMatrix,
				location_reflectivity, location_shineDamper, location_transformationMatrix, location_viewMatrix);
		start();
		texture.loadTexUnit(0);
		stop();
	} 

}
