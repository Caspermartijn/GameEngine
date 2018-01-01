package textures;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import utils.SourceFile;



public class Texture {

	public int id;

	protected Texture(int id) {
		this.id = id;
	}

	public static Texture createTextureObject(int id) {
		return new Texture(id);
	}

	public static TextureBuilder getTextureBuilder(SourceFile textureFile) {
		return new TextureBuilder(textureFile);
	}

	public void bindToUnit(int unit) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + unit);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
	}

	public void delete() {
		GL11.glDeleteTextures(id);
	}

	public static void deleteTexture(int id) {
		GL11.glDeleteTextures(id);
	}

}
