package renderer.terrainRenderer;

import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import entities.Light;
import shaders.ShaderProgram;
import shaders.uniforms.UniformFloat;
import shaders.uniforms.UniformMat4;
import shaders.uniforms.UniformSampler;
import shaders.uniforms.UniformVec2;
import shaders.uniforms.UniformVec3;
import utils.SourceFile;

public class TerrainShader extends ShaderProgram {
	private static final SourceFile VERTEX_FILE = new SourceFile("/shaders/terrain/shader.vsh");
	private static final SourceFile FRAGMENT_FILE = new SourceFile("/shaders/terrain/shader.fsh");

	public UniformMat4 location_projectionMatrix = new UniformMat4("projectionMatrix");
	public UniformMat4 location_viewMatrix = new UniformMat4("viewMatrix");
	public UniformVec3[] location_lightPosition = new UniformVec3[4];
	public UniformVec3[] location_lightColour = new UniformVec3[4];
	public UniformVec3[] location_attenuation = new UniformVec3[4];
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

	public UniformFloat[] location_tileAm = new UniformFloat[4];
	public UniformFloat location_renderDistance = new UniformFloat("renderDistance");
	public UniformFloat location_numberOfRows = new UniformFloat("numberOfRows");
	public UniformVec2 location_offset = new UniformVec2("offset");
	public UniformMat4 location_toShadowMapSpace = new UniformMat4("toShadowMapSpace");

	public UniformFloat location_density = new UniformFloat("density");
	public UniformFloat location_gradient = new UniformFloat("gradient");
	public UniformFloat location_shadowDistance = new UniformFloat("shadowDistance");
	public UniformFloat location_shadowMapSize = new UniformFloat("shadowMapSize");

	public TerrainShader() {
		super(ShaderProgram.newShaderProgram(VERTEX_FILE, FRAGMENT_FILE).addInput(0, "position")
				.addInput(1, "textureCoordinates").addInput(2, "normal").addInput(3, "tangent")
				.addOutput(0, "out_Color"));
		super.storeAllUniformLocations(location_back, location_blend, location_blue, location_density,
				location_gradient, location_green, location_normalBack, location_normalBlue, location_normalGreen,
				location_normalRed, location_numberOfRows, location_offset, location_projectionMatrix, location_red,
				location_reflectivity, location_renderDistance, location_shadowDistance, location_shadowMap,
				location_shadowMapSize, location_shineDamper, location_toShadowMapSpace, location_viewMatrix,
				location_skyColor);

		for (int i = 0; i < 4; i++) {
			location_attenuation[i] = new UniformVec3("attenuation[" + i + "]");
			location_tileAm[i] = new UniformFloat("tileAm[" + i + "]");
			location_lightPosition[i] = new UniformVec3("lightPosition[" + i + "]");
			location_lightColour[i] = new UniformVec3("lightColour[" + i + "]");
			super.storeAllUniformLocations(location_attenuation[i], location_lightColour[i], location_lightPosition[i],
					location_tileAm[i]);
		}

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
		location_tileAm[0].loadFloat(back);
		location_tileAm[1].loadFloat(red);
		location_tileAm[2].loadFloat(green);
		location_tileAm[3].loadFloat(blue);
	}

	public void loadLights(List<Light> lights) {
		for (int i = 0; i < 4; i++) {
			if (i < (lights.size())) {
				location_lightColour[i].loadVec3(lights.get(i).getColour());
				location_lightPosition[i].loadVec3(lights.get(i).getPosition());
				location_attenuation[i].loadVec3(lights.get(i).getAttenuation());
			} else {
				location_lightColour[i].loadVec3(new Vector3f(0, 0, 0));
				location_lightPosition[i].loadVec3(new Vector3f(0, 0, 0));
				location_attenuation[i].loadVec3(new Vector3f(0, 0, 1));
			}
		}
	}

}
