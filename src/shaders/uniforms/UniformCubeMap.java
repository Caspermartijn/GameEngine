package shaders.uniforms;

import org.lwjgl.opengl.GL20;

import textures.CubeMapTexture;

public class UniformCubeMap extends Uniform {

	private int currentValue;
	
	public UniformCubeMap(String name) {
		super(name);
	}

	public void loadTexUnit(int texUnit) {
		if (currentValue != texUnit) {
			GL20.glUniform1i(super.getLocation(), texUnit);
			currentValue = texUnit;
		}
	}
	
	public void bindTexture(CubeMapTexture texture) {
		texture.bindToUnit(currentValue);
	}

}
