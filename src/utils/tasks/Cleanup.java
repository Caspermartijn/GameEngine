package utils.tasks;

import java.util.ArrayList;
import java.util.List;

import objects.Vao;
import shaders.ShaderProgram;
import textures.Texture;

public abstract class Cleanup {

	public static List<Cleanup> list = new ArrayList<Cleanup>();

	public static void cleanAll() {
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

	public abstract void delete(); 

	public boolean cleaned = false;

}
