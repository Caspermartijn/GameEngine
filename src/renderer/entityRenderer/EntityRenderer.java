package renderer.entityRenderer;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import entities.Entity;
import objects.Model_3D;
import objects.Vao;

public class EntityRenderer {

	private EntityShader shader;

	public EntityRenderer(EntityShader shader) {
		this.shader = shader;
	}

	public void render(Map<Model_3D, List<Entity>> entities) {
		for (Model_3D model : entities.keySet()) {
			prepareTexturedModel(model);
			List<Entity> batch = entities.get(model);
			for (Entity entity : batch) {
				prepareInstance(entity);
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getMesh().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
			unbindTexturedModel(model.getMesh());
		}
	}

	private void prepareTexturedModel(Model_3D model) {
		Vao rawModel = model.getMesh();
		rawModel.bind(0, 1, 2);
		shader.location_shineDamper.loadFloat(1);
		shader.location_reflectivity.loadFloat(0);
		shader.texture.bindTexture(model.getTexture());
	}

	private void unbindTexturedModel(Vao vao) {
		vao.unbind(0,1,2);
	}

	private void prepareInstance(Entity entity) {
		Matrix4f transformationMatrix = entity.getLocalTransformationMatrix();
		shader.location_transformationMatrix.loadMatrix(transformationMatrix);
	}

	public void setProjectionMatrix(Matrix4f matrix) {
		shader.start();
		shader.location_projectionMatrix.loadMatrix(matrix);
		shader.stop();		
	}

}
