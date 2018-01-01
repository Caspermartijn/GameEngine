package shaderObjects;

import org.lwjgl.opengl.GL20;

public class UniformFloat extends Uniform{
	
	private float currentValue;
	
	public UniformFloat(String name){
		super(name);
	}
	
	public void loadFloat(float value){
		if(currentValue!=value){
			GL20.glUniform1f(super.getLocation(), value);
			currentValue = value;
		}
	}

}
