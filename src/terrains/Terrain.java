package terrains;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import objects.Vao;
import terrains.terrainTexture.TerrainPack;
import textures.Texture;
import utils.maths.Maths;

public class Terrain {

	public static final float SIZE = 500;
	public static final float MAX_HEIGHT = 200;
	private static final float MAX_PIXEL_COLOR = 256 * 256 * 256;
	public float[][] heights;

	private float x;
	private float z;
	private int gridX;
	private int gridZ;
	private float actuallX;
	private float actuallZ;
	private Vao model;
	private TerrainPack pack;
	private int rows;

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
		this.rows = rows;
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
		try {
			this.model = generateTerrain(heightMap, rows, new Vector2f(gridX, gridZ));
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	private Vao generateTerrain(BufferedImage heightMap, int numberOfRows, Vector2f row) throws IOException {

		BufferedImage image = heightMap;

		int VERTEX_COUNT = image.getHeight() / numberOfRows;

		int offX = (int) (row.x * (VERTEX_COUNT - 1));
		int offY = (int) (row.y * (VERTEX_COUNT - 1));

		heights = new float[VERTEX_COUNT][VERTEX_COUNT];
		int count = VERTEX_COUNT * VERTEX_COUNT;

		List<Vertex1c> verticesL = new ArrayList<Vertex1c>();
		List<Vector2f> texturesL = new ArrayList<Vector2f>();
		List<Vector3f> normalsL = new ArrayList<Vector3f>();
		List<Integer> indicesL = new ArrayList<Integer>();

		for (int i = 0; i < VERTEX_COUNT; i++) {
			for (int j = 0; j < VERTEX_COUNT; j++) {
				int nJ = offX + j;
				int nI = offY + i;

				float posX = (float) nJ / ((float) VERTEX_COUNT - 1) * SIZE;
				float height = getHeight(nJ, nI, image);
				heights[j][i] = height;
				float posY = height;
				float posZ = (float) nI / ((float) VERTEX_COUNT - 1) * SIZE;

				Vector3f vertex = new Vector3f(posX, posY, posZ);
				Vertex1c newVertex = new Vertex1c(verticesL.size(), vertex);
				verticesL.add(newVertex);

				Vector3f normal = calculateNormal(nJ, nI, image);
				normalsL.add(normal);

				float texX = (float) nJ / ((float) VERTEX_COUNT - 1);
				float texZ = (float) nI / ((float) VERTEX_COUNT - 1);
				Vector2f texture = new Vector2f(texX, texZ);
				texturesL.add(texture);

			}
		}

		for (int gz = 0; gz < VERTEX_COUNT - 1; gz++) {
			for (int gx = 0; gx < VERTEX_COUNT - 1; gx++) {
				int topLeft = (gz * VERTEX_COUNT) + gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz + 1) * VERTEX_COUNT) + gx;
				int bottomRight = bottomLeft + 1;

				Vertex1c topL = TerrainMaths.processVertex(topLeft, verticesL, indicesL);
				Vertex1c bottomL = TerrainMaths.processVertex(bottomLeft, verticesL, indicesL);
				Vertex1c topR = TerrainMaths.processVertex(topRight, verticesL, indicesL);
				TerrainMaths.calculateTangents(topL, bottomL, topR, texturesL);

				Vertex1c topR2 = TerrainMaths.processVertex(topRight, verticesL, indicesL);
				Vertex1c botL = TerrainMaths.processVertex(bottomLeft, verticesL, indicesL);
				Vertex1c botR = TerrainMaths.processVertex(bottomRight, verticesL, indicesL);
				TerrainMaths.calculateTangents(topR2, botL, botR, texturesL);
			}
		}
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count * 2];
		float[] tangents = new float[count * 3];

		@SuppressWarnings("unused")
		float furthest = TerrainMaths.convertDataToArrays(verticesL, texturesL, normalsL, vertices, textureCoords,
				normals, tangents);

		int[] indices = TerrainMaths.convertIndicesListToArray(indicesL);
		Vao vao = Vao.create();
		return vao.loadToVAO(vertices, textureCoords, normals, indices, tangents);
	}

	private float getNoice(int x, int z, BufferedImage image) {
		if (x < 0 || x >= image.getHeight() || z < 0 || z >= image.getHeight()) {
			return 0;
		}
		float height = image.getRGB(x, z);
		height += MAX_PIXEL_COLOR / 2f;
		height /= MAX_PIXEL_COLOR / 2f;
		height *= MAX_HEIGHT;
		return height;
	}

	private float getInterpolatedHeight(float x, float z, BufferedImage image) {
		int intX = (int) x;
		int intZ = (int) z;
		float fracX = x - intX;
		float fracZ = z - intZ;

		float v1 = getNormalizedHeight(intX, intZ, image);
		float v2 = getNormalizedHeight(intX + 1, intZ, image);
		float v3 = getNormalizedHeight(intX, intZ + 1, image);
		float v4 = getNormalizedHeight(intX + 1, intZ + 1, image);

		float i1 = interpolate(v1, v2, fracX);
		float i2 = interpolate(v3, v4, fracX);

		return interpolate(i1, i2, fracZ);
	}

	private float getHeight(float x, float z, BufferedImage image) {
		return getInterpolatedHeight(x, image.getHeight() - z, image);
	}

	private float interpolate(float a, float b, float blend) {
		double theta = blend * Math.PI;
		float f = (float) (1f - Math.cos(theta)) * 0.5f;
		return a * (1f - f) + b * f;
	}

	private float getNormalizedHeight(int x, int z, BufferedImage image) {
		float corners = (getNoice(x - 1, z - 1, image) + getNoice(x - 1, z + 1, image) + getNoice(x + 1, z - 1, image)
				+ getNoice(x + 1, z + 1, image)) / 16f;

		float sides = (getNoice(x - 1, z, image) + getNoice(x, z - 1, image) + getNoice(x + 1, z, image)
				+ getNoice(x, z + 1, image)) / 8f;

		float center = getNoice(x, z, image) / 4f;
		return corners + sides + center;
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

	public Vector2f getNormalOfTerrain(float x, float z) {
		float height1 = getHeightOfTerrain(x + 1, z);
		float height2 = getHeightOfTerrain(x - 1, z);
		float a = height2 - height1;
		float differenceHx = (float) Math.sqrt(a * a);

		float height3 = getHeightOfTerrain(x, z + 1);
		float height4 = getHeightOfTerrain(x, z - 1);
		float b = height4 - height3;
		float differenceHz = (float) Math.sqrt(b * b);

		float rotX = (float) Math.tanh(differenceHx / 3);
		float rotZ = (float) Math.tanh(differenceHz / 3);
		return new Vector2f(rotX, rotZ);
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

}
