package renderer.textRendering;

import org.lwjgl.opengl.GL11;

import engine.GLSettings;
import objects.Vao;
import texts.Text;

public class FontRenderer {

	private FontShader shader = new FontShader();
	
	public void cleanUp() {
		shader.delete();
	}

	public void renderText(Text text) {
		shader.start();
		GLSettings.enableAlphaBlending();
		GLSettings.setDepthTesting(false);
		
		Vao vao = text.getMesh();
		vao.bind(0, 1);
		
		shader.texture.bindTexture(text.getFont().getTexture());
		shader.color.loadVec4(text.getColor());
		shader.translation.loadVec2(text.getPosition());
		shader.fontSettings.loadVec2(text.getFont().getWidth(), text.getFont().getSmoothness());
		
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, text.getVertexCount());
		
		vao.unbind(0, 1);
		
		shader.stop();
	}
	
}
