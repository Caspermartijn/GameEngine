package renderer.entityRenderer;

import java.util.List;
import java.util.Map;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import entities.Entity;
import objects.Model_3D;
import objects.Vao;
import textures.Texture;

public class EntityRenderer {

	private EntityShader shader;

	public EntityRenderer(EntityShader shader, Matrix4f projectionMatrix) {
		this.shader = shader;
		shader.start();
		shader.projectionMatrix.loadMatrix(projectionMatrix);
		shader.stop();
	}

	public void render(Map<Model_3D, List<Entity>> entities) {
		for (Model_3D model : entities.keySet()) {
			prepareModel_3D(model);
			List<Entity> batch = entities.get(model);
			for (Entity entity : batch) {
				prepareInstance(entity);
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getMesh().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
			unbindModel_3D();
		}
	}

	private void prepareModel_3D(Model_3D model) {
		Vao vao = model.getMesh();
		vao.bind(0, 1, 2);
		Texture texture = model.getTexture();
		shader.useFakeLighting.loadBoolean(false);
		shader.reflectivity.loadFloat(0);
		shader.shineDamper.loadFloat(1);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.id);
	}

	private void unbindModel_3D() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}

	private void prepareInstance(Entity entity) {
		shader.transformationMatrix.loadMatrix(entity.getTransformationMatrix());
		shader.offset.loadVec2(0, 0);
	}

}
