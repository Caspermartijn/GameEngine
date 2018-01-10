package shaders;

import org.lwjgl.opengl.GL20;

import textures.Texture;

public class UniformSampler extends Uniform {

	private int currentValue;
	
	public UniformSampler(String name) {
		super(name);
	}

	public void loadTexUnit(int texUnit) {
		if (currentValue != texUnit) {
			GL20.glUniform1i(super.getLocation(), texUnit);
			currentValue = texUnit;
		}
	}
	
	public void bindTexture(Texture texture) {
		texture.bindToUnit(currentValue);
	}

}
