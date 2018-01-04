package terrains;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import main.Game;

public class TerrainManager {

	public static int packs = 0;

	public static Terrain[][] terrains;
	public static int terrainRows;

	public static int[][] texturesPackTerrain;

	public static Terrain getCurrentTerrain(Vector3f pos) {
		int gridX = (int) (pos.x / Terrain.SIZE + 1);
		int gridZ = (int) (pos.z / Terrain.SIZE + 1);
		return terrains[gridX - 1][gridZ - 1];
	}

	public static Terrain getCurrentTerrain(float x, float z) {
		int gridX = (int) (x / Terrain.SIZE + 1);
		int gridZ = (int) (z / Terrain.SIZE + 1);
		return terrains[gridX - 1][gridZ - 1];
	}

	public static Vector2f getAngleTerrain(float x, float z) {
		Terrain t = getCurrentTerrain(x, z);
		return t.getNormalOfTerrain(x, z);
	}

	public static void loadTerrains(TerrainPack pack, int rows) {
		texturesPackTerrain = generatePackToTerrain(rows);

		terrainRows = rows;
		BufferedImage heightMap = null;

		try {
			heightMap = ImageIO.read(Class.class.getResourceAsStream(pack.getHeight().toLowerCase() + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		terrains = new Terrain[rows][rows];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < rows; j++) {
				Terrain t = new Terrain(i, j, pack, heightMap, rows);
				Game.terrains.add(t);
				terrains[i][j] = t;
			}
		}
	}

	private static int[][] generatePackToTerrain(int rows) {
		int[][] ints = new int[rows][rows];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < rows; j++) {
				ints[i][j] = 0;
			}
		}
		return ints;
	}

	public static void loadTerrains(int[][] packToTerrain, TerrainPack pack, int rows) {
		texturesPackTerrain = packToTerrain;
		terrainRows = rows;
		BufferedImage heightMap = null;

		try {
			heightMap = ImageIO
					.read(Class.class.getResourceAsStream("/res/" + pack.getHeight().toLowerCase() + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		terrains = new Terrain[rows][rows];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < rows; j++) {
				Terrain t = new Terrain(i, j, pack, heightMap, rows);
				Game.terrains.add(t);
				terrains[i][j] = t;
			}
		}
	}

	public static float getHeight(float x, float z) {
		Terrain t = getCurrentTerrain(x, z);
		return t.getHeightOfTerrain(x - (t.getGridX() * Terrain.SIZE), z - (t.getGridZ() * Terrain.SIZE));
	}

	public static double getWorldSize() {
		return terrainRows * Terrain.SIZE;
	}

}
