package renderer.quadRenderer;

import org.lwjgl.opengl.GL11;

import engine.GLSettings;
import guis.QuadComponent;
import objects.Vao;
import utils.tasks.Cleanup;

public class QuadRenderer extends Cleanup{

	private static QuadShader shader = new QuadShader();
	private static Vao quad;

	public QuadRenderer() {
		super();
	}
	
	public static void init() {
		quad = Vao.create();
		float[] positions = { -1, 1, -1, -1, 1, 1, 1, -1 };
		quad = quad.loadToVAO(positions, 2);
	}

	public static void renderQuad(QuadComponent quad) {
		GLSettings.setDepthTesting(false);
		GLSettings.enableAlphaBlending();

		shader.start();
		QuadRenderer.quad.bind(0);

		shader.color.loadVec4(quad.getBackgroundColor());
		shader.outlineColor.loadVec4(quad.getOutlineColor());
		shader.transform.loadMatrix(quad.getBackgroundTransform());
		shader.dimensions.loadVec4(quad.getInnerDimensions());

		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);

		QuadRenderer.quad.unbind(0);
		shader.stop();
	}

	public void delete() {
		shader.delete();
		quad.delete();
		System.out.println("- QuadShader");
	}

}
