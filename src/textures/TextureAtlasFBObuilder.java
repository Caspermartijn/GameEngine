package textures;

import org.lwjgl.opengl.GL30;

import images.Image;
import renderer.imageRenderer.ImageRenderer;
import utils.SourceFile;

public class TextureAtlasFBObuilder {

	public static TextureAtlasFBO create(SourceFile[] files) {
		int fbo_id = GL30.glGenFramebuffers();
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, fbo_id);
		int rowLenght = 0;
		if ((files.length & 1) == 0) {
			rowLenght = files.length / 2;
		} else {
			rowLenght = (files.length + 1) / 2;
		}

		int index = 0;

		float factor = 1 / rowLenght;
		float width = 1280 * factor;
		float height = 720 * factor;

		float x_offset = 0;
		float y_offset = 0;

		for (int x = 0; x < rowLenght; x++) {
			if (files[index] != null) {
				Image i = new Image(files[index]);
				i.setPosition(x_offset, y_offset);
				i.setWidth(width);
				i.setHeight(height);
				x_offset += factor;
				y_offset += factor;
				ImageRenderer.renderImage(i);
			}
			index++;
		}
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
		return new TextureAtlasFBO(fbo_id);
	}

}
