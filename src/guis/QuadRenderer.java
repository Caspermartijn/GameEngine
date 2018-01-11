package guis;

import engine.GLSettings;
import renderer.MasterRenderer;

public class QuadRenderer {

	private static QuadShader shader;
	
	public static void init() {
		shader = new QuadShader();
	}
	
	public static void renderQuad(QuadComponent quad) {
		GLSettings.setDepthTesting(false);
		GLSettings.enableAlphaBlending();
		
		shader.start();
		
		shader.color.loadVec4(quad.getBackgroundColor());
		shader.outlineColor.loadVec4(quad.getOutlineColor());
		shader.transform.loadMatrix(quad.getBackgroundTransform());
		shader.dimensions.loadVec4(quad.getInnerDimensions());
		
		MasterRenderer.renderQuad();
		
		shader.stop();
	}
	
	public static void delete() {
		shader.delete();
	}
	
}
