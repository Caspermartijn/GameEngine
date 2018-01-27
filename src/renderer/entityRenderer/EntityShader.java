package renderer.entityRenderer;

import shaders.uniforms.ShaderProgram;
import shaders.uniforms.Uniform;
import shaders.uniforms.UniformFloat;
import shaders.uniforms.UniformMat4;
import shaders.uniforms.UniformSampler;
import shaders.uniforms.UniformVec3;
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
	public UniformSampler colorMap = new UniformSampler("colorMap");
	public UniformVec3 colorMapOffsetColor = new UniformVec3("colorMapOffsetColor");

	public EntityShader() {
		super.init(ShaderProgram.newShaderProgram().addInput(0, "position").addInput(1, "textureCoords").addOutput(0,
				"out_Color"));
		start();
		texture.loadTexUnit(0);
		colorMap.loadTexUnit(3);
		stop();
	}

	@Override
	protected SourceFile getVertexFile() {
		return VERTEX_FILE;
	}

	@Override
	protected SourceFile getFragmentFile() {
		return FRAGMENT_FILE;
	}

	@Override
	protected Uniform[] getAllUniforms() {
		return new Uniform[] { texture, location_lightColour, location_lightPosition, location_projectionMatrix,
				location_reflectivity, location_shineDamper, location_transformationMatrix, location_viewMatrix,
				colorMap, colorMapOffsetColor };
	}

}
