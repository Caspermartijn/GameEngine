package textures;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import utils.SourceFile;
import utils.models.ModelLoader;
import utils.tasks.Cleanup;

public class Texture extends Cleanup {

	public int id;
	private int width, height;

	public static int created, deleted;

	private boolean deletedB = false;

	private String name;

	protected Texture(int id) {
		created++;
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

	public void setName(String s) {
		name = s;
	}

	public void delete() {
		if (!deletedB) {
			deleteTexture(id);
			deletedB = true;
		}
		if (name != null) {
			if (ModelLoader.textures.containsKey(name)) {
				ModelLoader.textures.remove(name);
			}
		}
	}

	public void setDimensions(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public static void deleteTexture(int id) {
		deleted++;
		GL11.glDeleteTextures(id);
	}

	public static void printLog() {
		System.out.println("Texture_Log[created: " + created + " , deleted: " + deleted + "]");
	}
}
