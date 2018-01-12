package renderer.textRendering;

import org.lwjgl.opengl.GL11;

import engine.GLSettings;
import objects.Vao;
import shaders.uniforms.ShaderProgram;
import texts.Fonts;
import texts.Text;
import utils.tasks.Cleanup;

public class FontRenderer extends Cleanup{

	private static FontShader shader = new FontShader();
	
	public FontRenderer() {
		super();
	}
	
	public static void cleanUp() {
		shader.delete();
	}

	public static void renderText(Text text) {
		shader.start();
		GLSettings.enableAlphaBlending();
		GLSettings.setDepthTesting(false);
		
		Vao vao = text.getMesh();
		vao.bind(0, 1);
		
		shader.texture.bindTexture(Fonts.getFont(text.getFont()).getTexture());
		shader.color.loadVec4(text.getColor());
		shader.translation.loadVec2(text.getPosition());
		shader.fontSettings.loadVec2(Fonts.getFont(text.getFont()).getWidth(), Fonts.getFont(text.getFont()).getSmoothness());
		
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, text.getVertexCount());
		
		vao.unbind(0, 1);
		
		shader.stop(); 
	}
	
	public static ShaderProgram getShader() {
		return shader;
	}

	public static void init() {
		
	}

	public void delete() {
		cleanUp();
	}
	
}
