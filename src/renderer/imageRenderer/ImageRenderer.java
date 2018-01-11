package renderer.imageRenderer;

import engine.GLSettings;
import images.Image;
import renderer.MasterRenderer;

public class ImageRenderer {

	private static ImageShader shader;
	
	public static void init() {
		shader = new ImageShader();
	}
	
	public static void renderImage(Image image) {
		GLSettings.enableAlphaBlending();
		GLSettings.setDepthTesting(false);
		
		shader.start();
		shader.matrix.loadMatrix(image.getMatrix());
		shader.texture.bindTexture(image.getTexture());
		
		MasterRenderer.renderQuad();
		
		shader.stop();
	}
	
	public static void delete() {
		shader.delete();
	}
	
}
