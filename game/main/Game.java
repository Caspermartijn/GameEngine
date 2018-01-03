package main;

import java.util.ArrayList;
import java.util.List;

import entities.Entity;
import entities.IEntity;

public class Game {

	public static List<Entity> entities = new ArrayList<Entity>();

	public static void update() {
		for(IEntity ent : entities) {
			ent.update();
		}
	}
	
}
