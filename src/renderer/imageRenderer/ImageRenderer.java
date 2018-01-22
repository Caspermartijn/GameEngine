package renderer.imageRenderer;

import org.lwjgl.util.vector.Vector3f;

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
		// GLSettings.setDepthTesting(true);

		shader.start();
		shader.matrix.loadMatrix(image.getMatrix());
		shader.color_override.loadVec3(new Vector3f(-1f, -1f, -1f));
		shader.texture.bindTexture(image.getTexture());
		MasterRenderer.renderQuad();
		shader.stop();

		GLSettings.disableBlending();
		// GLSettings.setDepthTesting(false);
	}

	private static final Vector3f[] colorPickerForOverride = new Vector3f[] { new Vector3f(-0.1f, -0.1f, -0.1f),
			new Vector3f(1.3f, 1.3f, 1.3f) };

	public static void renderImage(Image image, Vector3f color_override) {
		GLSettings.enableAlphaBlending();
		// GLSettings.setDepthTesting(true);

		shader.start();
		shader.matrix.loadMatrix(image.getMatrix());
		shader.texture.bindTexture(image.getTexture());
		shader.color_override.loadVec3(color_override);
		shader.color_override_picker.loadVector3fArray(colorPickerForOverride);
		MasterRenderer.renderQuad();
		shader.stop();

		GLSettings.disableBlending();
		// GLSettings.setDepthTesting(false);
	}

	public void delete() {
		shader.delete();
	}

	public static void renderImage(Image image, Vector3f colorOverride, Vector3f[] colorToOVerride) {
		GLSettings.enableAlphaBlending();
		// GLSettings.setDepthTesting(true);

		shader.start();
		shader.matrix.loadMatrix(image.getMatrix());
		shader.texture.bindTexture(image.getTexture());
		shader.color_override.loadVec3(colorOverride);
		shader.color_override_picker.loadVector3fArray(colorToOVerride);
		MasterRenderer.renderQuad();
		shader.stop();

		GLSettings.disableBlending();
		// GLSettings.setDepthTesting(false);
	}

}
