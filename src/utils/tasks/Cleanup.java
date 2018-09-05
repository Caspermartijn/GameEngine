package utils.tasks;

import java.util.ArrayList;
import java.util.List;

import objects.Vao;
import shaders.uniforms.ShaderProgram;
import textures.Texture;

public abstract class Cleanup {

	public static List<Cleanup> list = new ArrayList<Cleanup>();

	public static void cleanAll() {
		System.out.println("Starting cleanup:");
		for (Cleanup cleanup : list) {
			if (!cleanup.cleaned) { 
				cleanup.delete();
			}
		}
		Vao.printLog();
		ShaderProgram.printLog();
		Texture.printLog();
	}

	public Cleanup() {
		Cleanup.list.add(this);
	}

	public static void addCleanup(Cleanup c) {
		Cleanup.list.add(c);
	}
	
	public abstract void delete(); 

	public boolean cleaned = false;

}
