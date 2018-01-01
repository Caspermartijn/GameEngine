package textures;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class CubeMapTexture {

	private int id;
	private int size;
	public static final int RIGHT = 0, LEFT = 1, DOWN = 2, UP = 3, BACK = 4, FRONT = 5;

	protected CubeMapTexture(int id, int size) {
		this.id = id;
		this.size = 0;
	}
	
	public void bindToUnit(int unit) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + unit);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, id);
	}

	public void storeImage(int face, TextureData data) {
		if (face < 0 || face > 5)
			throw new IllegalArgumentException("invalid face");
		if (data.getWidth() != size || data.getHeight() != size)
			throw new IllegalArgumentException(
					"the width and height of the TextureData must be eqaul to the size of the CubeMapTexture");

		GL11.glTexImage2D(GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X + face, 0, GL11.GL_RGBA8, size, size, 0, GL11.GL_RGBA,
				GL11.GL_UNSIGNED_BYTE, data.getBuffer());
	}

	public void delete() {
		GL11.glDeleteTextures(id);
		id = 0;
	}

	public int getID() {
		return id;
	}

	public static CubeMapTexture newCubeMapTexture(int size) {
		return new CubeMapTexture(TextureLoader.createEmptyCubeMap(size), size);
	}

	public static CubeMapTexture createCubeMapTextureObject(int id, int size) {
		return new CubeMapTexture(id, size);
	}

}
