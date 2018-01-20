package renderer.terrainRenderer;

import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import entities.Light;
import shaders.uniforms.ShaderProgram;
import shaders.uniforms.Uniform;
import shaders.uniforms.UniformFloat;
import shaders.uniforms.UniformFloatArray;
import shaders.uniforms.UniformMat4;
import shaders.uniforms.UniformSampler;
import shaders.uniforms.UniformVec2;
import shaders.uniforms.UniformVec3;
import shaders.uniforms.UniformVec3Array;
import utils.SourceFile;

public class TerrainShader extends ShaderProgram {
	private static final SourceFile VERTEX_FILE = new SourceFile("/shaders/terrain/shader.vsh");
	private static final SourceFile FRAGMENT_FILE = new SourceFile("/shaders/terrain/shader.fsh");

	public UniformMat4 location_projectionMatrix = new UniformMat4("projectionMatrix");
	public UniformMat4 location_viewMatrix = new UniformMat4("viewMatrix");

	public UniformVec3Array lightPosition = new UniformVec3Array("lightPosition", 4);
	public UniformVec3Array lightColour = new UniformVec3Array("lightColour", 4);
	public UniformVec3Array attenuation = new UniformVec3Array("attenuation", 4);
	public UniformFloatArray tileAm = new UniformFloatArray("tileAm", 4);

	public UniformFloat location_shineDamper = new UniformFloat("shineDamper");
	public UniformFloat location_reflectivity = new UniformFloat("reflectivity");
	public UniformVec3 location_skyColor = new UniformVec3("skyColor");

	public UniformSampler location_red = new UniformSampler("red");
	public UniformSampler location_green = new UniformSampler("green");
	public UniformSampler location_blue = new UniformSampler("blue");
	public UniformSampler location_back = new UniformSampler("black");

	public UniformSampler location_normalRed = new UniformSampler("normalRed");
	public UniformSampler location_normalGreen = new UniformSampler("normalGreen");
	public UniformSampler location_normalBlue = new UniformSampler("normalBlue");
	public UniformSampler location_normalBack = new UniformSampler("normalBack"); 

	public UniformSampler location_blend = new UniformSampler("blend");
	public UniformSampler location_shadowMap = new UniformSampler("shadowMap");

	public UniformFloat location_renderDistance = new UniformFloat("renderDistance");
	public UniformFloat location_numberOfRows = new UniformFloat("numberOfRows");
	public UniformVec2 location_offset = new UniformVec2("offset");
	public UniformMat4 location_toShadowMapSpace = new UniformMat4("toShadowMapSpace");

	public UniformFloat location_density = new UniformFloat("density");
	public UniformFloat location_gradient = new UniformFloat("gradient");
	public UniformFloat location_shadowDistance = new UniformFloat("shadowDistance");
	public UniformFloat location_shadowMapSize = new UniformFloat("shadowMapSize");

	public TerrainShader() {
		super.init(ShaderProgram.newShaderProgram().addInput(0, "position").addInput(1, "textureCoordinates")
				.addInput(2, "normal").addInput(3, "tangent").addOutput(0, "out_Color"));
		start();
		location_back.loadTexUnit(0);
		location_red.loadTexUnit(1);
		location_green.loadTexUnit(2);
		location_blue.loadTexUnit(3);

		location_blend.loadTexUnit(4);

		location_normalBack.loadTexUnit(5);
		location_normalRed.loadTexUnit(5);
		location_normalGreen.loadTexUnit(5);
		location_normalBlue.loadTexUnit(5);
		stop();
	}

	public void loadTileAmounts(int back, int red, int green, int blue) {
		tileAm.loadFloatArray(new float[] { back, red, green, blue });
	}

	public void loadLights(List<Light> lights) {
		Vector3f[] color = new Vector3f[4];
		Vector3f[] position = new Vector3f[4];
		Vector3f[] attenuationa = new Vector3f[4];
		for (int i = 0; i < 4; i++) {
			if (i < (lights.size())) {
				color[i] = lights.get(i).getColour();
				position[i] = lights.get(i).getPosition();
				attenuationa[i] = lights.get(i).getAttenuation();
			} else {
				color[i] = new Vector3f(0, 0, 0);
				position[i] = new Vector3f(0, 0, 0);
				attenuationa[i] = new Vector3f(0, 0, 1);
			}
		}

		lightColour.loadVector3fArray(color);
		lightPosition.loadVector3fArray(position);
		attenuation.loadVector3fArray(attenuationa);
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
		return new Uniform[] { location_back, location_blend, location_blue, location_density, location_gradient,
				location_green, location_normalBack, location_normalBlue, location_normalGreen, location_normalRed,
				location_numberOfRows, location_offset, location_projectionMatrix, location_red, location_reflectivity,
				location_renderDistance, location_shadowDistance, location_shadowMap, location_shadowMapSize,
				location_shineDamper, location_toShadowMapSpace, location_viewMatrix, location_skyColor, lightColour,
				lightPosition, attenuation, tileAm };
	}

}
