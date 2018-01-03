package renderer.entityRenderer;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import entities.Entity;
import objects.Model_3D;
import objects.Vao;
import textures.Texture;

public class EntityRenderer {

	private EntityShader shader;

	public EntityRenderer(EntityShader shader) {
		this.shader = shader;
	}
	
	public void setProjectionMatrix(Matrix4f matrix){
		shader.start();
		shader.projectionMatrix.loadMatrix(matrix);
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
			unbindModel_3D(model);
		}
	}

	private void prepareModel_3D(Model_3D model) {
		Vao vao = model.getMesh();
		vao.bind(0, 1, 2);
		Texture texture = model.getTexture();
		shader.useFakeLighting.loadBoolean(false);
		shader.reflectivity.loadFloat(0);
		shader.shineDamper.loadFloat(1);
		shader.modelTexture.bindTexture(texture);
	}

	private void unbindModel_3D(Model_3D model) {
		model.getMesh().unbind(0, 1, 2);
	}

	private void prepareInstance(Entity entity) {
		shader.transformationMatrix.loadMatrix(entity.getTransformationMatrix());
		shader.offset.loadVec2(0, 0);
	}

}
