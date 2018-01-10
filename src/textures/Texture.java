package textures;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import utils.SourceFile;
import utils.tasks.Cleanup;

public class Texture extends Cleanup {

	public int id;

	private static int created, deleted;

	protected Texture(int id) {
		this.id = id;
	}

	public static Texture createTextureObject(int id) {
		created++;
		return new Texture(id);
	}

	public static TextureBuilder getTextureBuilder(SourceFile textureFile) {
		created++;
		return new TextureBuilder(textureFile);
	}

	public void bindToUnit(int unit) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + unit);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
	}

	@Override
	public void delete() {
		deleted++;
		super.cleaned = true;
		GL11.glDeleteTextures(id);
	}

	public static void deleteTexture(int id) {
		deleted++;

		GL11.glDeleteTextures(id);
	}

	public static void printLog() {
		System.out.println("Texture_Log[created: " + created + " , deleted:" + deleted + "]");
	}
}
