package renderer.quadRenderer;

import org.lwjgl.opengl.GL11;

import engine.GLSettings;
import guis.QuadComponent;
import objects.Vao;

public class QuadRenderer {

	private QuadShader shader = new QuadShader();
	private Vao quad;
	private int vertexCount;
	
	public void init() {
		quad = Vao.create();
		quad.bind();
		quad.createStaticAttribute(0, new float[] {-1, -1, 1, -1, -1, 1, 1, -1, 1, 1, -1, 1}, 2);
		vertexCount = 8;
		quad.unbind();
	}
	
	public void renderQuad(QuadComponent quad) {
		GLSettings.setDepthTesting(false);
		GLSettings.enableAlphaBlending();
		
		shader.start();
		this.quad.bind(0);
		
		shader.color.loadVec4(quad.getBackgroundColor());
		shader.outlineColor.loadVec4(quad.getOutlineColor());
		shader.transform.loadMatrix(quad.getBackgroundTransform());
		shader.dimensions.loadVec4(quad.getInnerDimensions());
		
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertexCount);
		
		this.quad.unbind(0);
		shader.stop();
	}
	
	public void cleanUp() {
		shader.delete();
		quad.delete();
	}
	
}
