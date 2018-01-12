package renderer.imageRenderer;

import engine.GLSettings;
import images.Image;
import renderer.MasterRenderer;
import utils.tasks.Cleanup;

public class ImageRenderer extends Cleanup {

	private static ImageShader shader;

	public ImageRenderer() {
		super();
	}
	
	public static void init() {
		shader = new ImageShader();
	}

	public static void renderImage(Image image) {
		GLSettings.enableAlphaBlending();
		GLSettings.setDepthTesting(true);

		shader.start();
		shader.matrix.loadMatrix(image.getMatrix());
		shader.texture.bindTexture(image.getTexture());
		MasterRenderer.renderQuad();
		shader.stop();
		
		GLSettings.disableBlending();
		GLSettings.setDepthTesting(false);
		
	}

	public void delete() {
		shader.delete();
	}

}
