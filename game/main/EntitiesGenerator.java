package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.lwjgl.util.vector.Vector2f;

import terrains.Terrain;

public class EntitiesGenerator {
	
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		List<Vector2f> taken = new ArrayList<Vector2f>();
		
		Random r = new Random();
		for (int i = 0; i < 250; i++) {
			UUID uuid = UUID.randomUUID();
			String model = "tree_1";
			float x = r.nextInt((int) Terrain.SIZE);
			String y = "th";
			float z = r.nextInt((int) Terrain.SIZE);

			float rotY = r.nextInt(365);

			System.out.println("entity:" + model + ":" + x + ":" + y + ":" + z + ":0:" + rotY + ":0:4:"
					+ uuid.toString() + ":null");

		}

	}
}
