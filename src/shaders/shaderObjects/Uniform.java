package shaders.shaderObjects;

import org.lwjgl.opengl.GL20;

public abstract class Uniform {
	
	private static final int NOT_FOUND = -1;
	
	private String name;
	private int location;
	
	protected Uniform(String name){
		this.name = name;
	}
	
	protected boolean storeUniformLocation(int programID){
		location = GL20.glGetUniformLocation(programID, name);
		if(location == NOT_FOUND){
			System.err.println("No uniform variable called " + name + " found!");
			return false;
		}
		return true;
	}
	
	protected int getLocation(){
		return location;
	}

}
