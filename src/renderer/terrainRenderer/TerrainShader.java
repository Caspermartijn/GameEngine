package renderer.terrainRenderer;

import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import entities.Light;
import shaders.uniforms.*;
import utils.SourceFile;

public class TerrainShader extends ShaderProgram {

	private static final SourceFile FRAGMENT_FILE = new SourceFile("/shaders/terrain/shader.fsh");
	private static final SourceFile VERTEX_FILE = new SourceFile("/shaders/terrain/shader.vsh");

	public UniformMat4 location_projectionMatrix = new UniformMat4("projectionMatrix");
	public UniformMat4 location_viewMatrix = new UniformMat4("viewMatrix");

	public UniformVec3Array location_lightPosition = new UniformVec3Array("lightPosition", 4);
	public UniformVec3Array location_lightColour = new UniformVec3Array("lightColour", 4);
	public UniformVec3Array location_attenuation = new UniformVec3Array("attenuation", 4);

	public UniformFloat location_shineDamper = new UniformFloat("shineDamper");
	public UniformFloat location_reflectivity = new UniformFloat("reflectivity");

	public UniformVec3 location_skyColour = new UniformVec3("skyColour");

	public UniformVec4 location_plane = new UniformVec4("plane");

	public UniformSampler location_backgroundTexture = new UniformSampler("backgroundTexture");
	public UniformSampler location_rTexture = new UniformSampler("rTexture");
	public UniformSampler location_gTexture = new UniformSampler("gTexture");
	public UniformSampler location_bTexture = new UniformSampler("bTexture");
	public UniformSampler location_blendMap = new UniformSampler("blendMap");


	public TerrainShader() {
		super.init(ShaderProgram.newShaderProgram().addInput(0, "position").addInput(1, "textureCoordinates").addInput(2, "normal").addOutput(0, "out_Color"));
		start();
		location_backgroundTexture.loadTexUnit(0);
		location_rTexture.loadTexUnit(1);
		location_gTexture.loadTexUnit(2);
		location_bTexture.loadTexUnit(3);
		location_blendMap.loadTexUnit(4);
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
		return new Uniform[] { location_projectionMatrix, location_viewMatrix, location_lightPosition,
				location_lightColour, location_attenuation, location_shineDamper, location_reflectivity,
				location_skyColour, location_plane, location_backgroundTexture, location_rTexture, location_gTexture,
				location_bTexture, location_blendMap };
	}

	public void loadLights(List<Light> lights) {
		Vector3f[] a = new Vector3f[4];
		Vector3f[] b = new Vector3f[4];
		Vector3f[] c = new Vector3f[4];
		if (true) {
			for (int i = 0; i < 4; i++) {
				if(i >= lights.size()) {
					a[i] = new Vector3f(0, 0, 1);
					b[i] = new Vector3f(0, 0, 0);
					c[i] = new Vector3f(0, 0, 0);
				}else {
					a[i] = lights.get(i).getAttenuation();
					b[i] = lights.get(i).getPosition();
					c[i] = lights.get(i).getColour();
				}
			}
		}
		location_attenuation.loadVector3fArray(a);
		location_lightPosition.loadVector3fArray(b);
		location_lightColour.loadVector3fArray(c);
	}

}
