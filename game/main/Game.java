package main;

import java.util.ArrayList;
import java.util.List;

import entities.Entity;
import entities.IEntity;
import entities.Light;
import terrains.Terrain;

public class Game {

	public static List<Entity> entities = new ArrayList<Entity>();
	public static List<Terrain> terrains = new ArrayList<Terrain>();
	public static List<Light> lights = new ArrayList<Light>();
	
	public static void update() {
		for(IEntity ent : entities) {
			ent.update();
		}
	}
	
}
