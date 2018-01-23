package terrains;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import objects.Vao;
import terrains.terrainTexture.TerrainPack;
import textures.Texture;
import utils.maths.Maths;

public class Terrain {

	public static final float SIZE = 800;
	public static final float MAX_HEIGHT = 50;
	private static final float MAX_PIXEL_COLOUR = 256 * 256 * 256;
	public float[][] heights;

	private float x;
	private float z;
	private int gridX;
	private int gridZ;
	private float actuallX;
	private float actuallZ;
	private Vao model;
	private TerrainPack pack;

	private Matrix4f transformationMatrix;

	private BufferedImage heightMap;

	public Terrain(int gridX, int gridZ, TerrainPack pack, int rows) {
		this.pack = pack;
		this.x = gridX * SIZE;
		this.z = gridZ * SIZE;
		this.gridX = gridX;
		this.gridZ = gridZ;
		this.actuallX = gridX * SIZE + (SIZE / 2);
		this.actuallZ = gridZ * SIZE + (SIZE / 2);
		try {
			heightMap = ImageIO.read(Class.class.getResourceAsStream(pack.getHeight()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		generateTerrain();
	}

	public Matrix4f getTransMatrix() {
		return transformationMatrix;
	}

	public float getActuallX() {
		return actuallX;
	}

	public float getActuallZ() {
		return actuallZ;
	}

	public float getX() {
		return x;
	}

	public Texture getBlendMapID() {
		return pack.getBlend().getTexture();
	}

	public float getZ() {
		return z;
	}

	public Vao getModel() {
		return model;
	}

	public TerrainPack getPack() {
		return this.pack;
	}

	public void generateTerrain() {
		this.model = generateTerrain(heightMap);
	}

	private Vao generateTerrain(BufferedImage image) {
		int VERTEX_COUNT = image.getHeight();

		int count = VERTEX_COUNT * VERTEX_COUNT;
		heights = new float[VERTEX_COUNT][VERTEX_COUNT];
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count * 2];
		int[] indices = new int[6 * (VERTEX_COUNT - 1) * (VERTEX_COUNT * 1)];
		int vertexPointer = 0;

		if (true) {
			for (int i = 0; i < VERTEX_COUNT; i++) {
				for (int j = 0; j < VERTEX_COUNT; j++) {

					int j2 = (int) (VERTEX_COUNT - j);

					vertices[vertexPointer * 3] = (float) j / ((float) VERTEX_COUNT - 1) * SIZE;
					float height = getHeight(j2, i, image);
					vertices[vertexPointer * 3 + 1] = height;
					heights[j][i] = height;
					vertices[vertexPointer * 3 + 2] = (float) i / ((float) VERTEX_COUNT - 1) * SIZE;
					Vector3f normal = calculateNormal(j2, i, image);
					normals[vertexPointer * 3] = normal.x;
					normals[vertexPointer * 3 + 1] = normal.y;
					normals[vertexPointer * 3 + 2] = normal.z;

					textureCoords[vertexPointer * 2] = (float) j2 / ((float) VERTEX_COUNT - 1);
					textureCoords[vertexPointer * 2 + 1] = (float) i / ((float) VERTEX_COUNT - 1);

					vertexPointer++;
				}
			}

			int pointer = 0;
			for (int gz = 0; gz < VERTEX_COUNT - 1; gz++) {
				for (int gx = 0; gx < VERTEX_COUNT - 1; gx++) {
					int topLeft = (gz * VERTEX_COUNT) + gx;
					int topRight = topLeft + 1;
					int bottomLeft = ((gz + 1) * VERTEX_COUNT) + gx;
					int bottomRight = bottomLeft + 1;
					indices[pointer++] = topLeft;
					indices[pointer++] = bottomLeft;
					indices[pointer++] = topRight;
					indices[pointer++] = topRight;
					indices[pointer++] = bottomLeft;
					indices[pointer++] = bottomRight;
				}
			}
		}

		Vao terrainModel = Vao.create();

		terrainModel.bind();
		terrainModel.setVertexCount(vertices.length * 3);
		terrainModel.createStaticIndexBuffer(indices);
		terrainModel.createStaticAttribute(0, 3, vertices);
		terrainModel.createStaticAttribute(1, 2, textureCoords);
		terrainModel.createStaticAttribute(2, 3, normals);
		terrainModel.unbind();

		return terrainModel;
	}

	private Vector3f calculateNormal(int x, int z, BufferedImage image) {
		float heightL = getHeight(x - 1, z, image);
		float heightR = getHeight(x + 1, z, image);
		float heightD = getHeight(x, z - 1, image);
		float heightU = getHeight(x, z + 1, image);
		Vector3f normal = new Vector3f(heightL - heightR, 2f, heightD - heightU);
		normal.normalise();
		return normal;
	}

	private float getHeight(int x, int z, BufferedImage image) {
		if (x < 0 || x >= image.getHeight() || z < 0 || z >= image.getHeight()) {
			return 0;
		}
		float height = image.getRGB(x, z);
		height += MAX_PIXEL_COLOUR / 2f;
		height /= MAX_PIXEL_COLOUR / 2f;
		height *= MAX_HEIGHT;
		return height;
	}

	public int getGridZ() {
		return gridZ;
	}

	public int getGridX() {
		return gridX;
	}

	public float getTextureXOffset() {
		return getGridX();
	}

	public float getTextureYOffset() {
		return getGridZ();
	}

	public float getHeightOfTerrain(float worldX, float worldZ) {// TODO CLEAN
		// IT UP
		float terrainX = worldX;
		float terrainZ = worldZ;
		float gridSquareSize = SIZE / ((float) heights.length - 1);
		int gridX = (int) Math.floor(terrainX / gridSquareSize);
		int gridZ = (int) Math.floor(terrainZ / gridSquareSize);

		if (gridX >= heights.length - 1 || gridZ >= heights.length - 1 || gridX < 0 || gridZ < 0) {
			return 0;
		}

		float xCoord = (terrainX % gridSquareSize) / gridSquareSize;
		float zCoord = (terrainZ % gridSquareSize) / gridSquareSize;
		float answer;

		if (xCoord <= (1 - zCoord)) {
			answer = Maths.barryCentric(new Vector3f(0, heights[gridX][gridZ], 0),
					new Vector3f(1, heights[gridX + 1][gridZ], 0), new Vector3f(0, heights[gridX][gridZ + 1], 1),
					new Vector2f(xCoord, zCoord));
		} else {
			answer = Maths.barryCentric(new Vector3f(1, heights[gridX + 1][gridZ], 0),
					new Vector3f(1, heights[gridX + 1][gridZ + 1], 1), new Vector3f(0, heights[gridX][gridZ + 1], 1),
					new Vector2f(xCoord, zCoord));
		}

		return answer;
	}

}
