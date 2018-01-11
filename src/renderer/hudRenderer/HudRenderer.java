package renderer.hudRenderer;

import java.util.Collection;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import org.lwjgl.util.vector.Matrix4f;

import engine.GLSettings;
import hud.Hud;
import hud.HudTexture;
import objects.Vao;
import renderer.textRendering.TextMaster;
import utils.maths.Matrix;
import utils.tasks.Cleanup;

public class HudRenderer extends Cleanup {

	private static Vao quad;
	private static HudShader shader;

	public HudRenderer() {
		super();
		float[] positions = { -1, 1, -1, -1, 1, 1, 1, -1 };
		quad = Vao.create().loadToVAO(positions, 2);
		shader = new HudShader();
	}

	public static void renderAllHuds(Hud... allHUDS) {
		for (Hud hud : allHUDS) {
			renderAllHudTextures(hud.getAllHuds());
			TextMaster.renderTexts(hud.getAllTexts());
		}
	}
	
	public static void renderAllHuds(Collection<Hud> allHUDS) {
		for (Hud hud : allHUDS) {
			renderAllHudTextures(hud.getAllHuds());
			TextMaster.renderTexts(hud.getAllTexts());
		}
	}

	public static void renderAllHudTextures(Collection<HudTexture> allHUDS) {
		GLSettings.disableBlending();
		shader.start();
		glBindVertexArray(quad.id);
		glEnableVertexAttribArray(0);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_DEPTH_TEST);
		for (HudTexture gui : allHUDS) {
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

	@Override
	public void delete() {
		shader.delete();
	}

}
