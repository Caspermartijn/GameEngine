package renderer.hudRenderer;

import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import org.lwjgl.util.vector.Matrix4f;

import engine.GLSettings;
import hud.HudTexture;
import objects.Vao;
import utils.maths.Matrix;

public class HudRenderer {

	private final Vao quad;
	private HudShader shader;

	public HudRenderer() {
		float[] positions = { -1, 1, -1, -1, 1, 1, 1, -1 };
		quad = Vao.create().loadToVAO(positions, 2);
		shader = new HudShader();
	}

	public void render(List<HudTexture> guis) {
		GLSettings.disableBlending();
		shader.start();
		glBindVertexArray(quad.id);
		glEnableVertexAttribArray(0);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_DEPTH_TEST);
		for (HudTexture gui : guis) {
			glActiveTexture(GL_TEXTURE0);
			glBindTexture(GL_TEXTURE_2D, gui.getTexture().id);
			Matrix4f matrix = Matrix.createTransformationMatrix(gui.getPosition(), gui.getScale(), gui.getRotation());
			shader.location_transformationMatrix.loadMatrix(matrix);
			glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
		}
		glDisable(GL_DEPTH_TEST);
		glDisable(GL_BLEND);
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
		shader.stop();
	}

}
