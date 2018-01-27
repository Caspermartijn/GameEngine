package renderer.entityRenderer;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import engine.GLSettings;
import entities.Entity;
import objects.Camera;
import objects.Model_3D;
import objects.Vao;
import utils.maths.Matrix;
import utils.tasks.Cleanup;

public class EntityRenderer extends Cleanup {

	private EntityShader shader;

	public EntityRenderer(EntityShader shader) {
		super();
		this.shader = shader;
	}

	public void render(Camera camera, Map<Model_3D, List<Entity>> entities) {
		for (Model_3D model : entities.keySet()) {
			prepareTexturedModel(model);
			List<Entity> batch = entities.get(model);
			for (Entity entity : batch) {
				prepareInstance(camera, entity);
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getMesh().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
				GLSettings.setBackFaceCulling(true);
			}
			unbindTexturedModel(model.getMesh());
		}
	}

	private void prepareTexturedModel(Model_3D model) {
		if (!model.isBackfaceCullingEnabled()) {
			GLSettings.setBackFaceCulling(false);
		}
		Vao rawModel = model.getMesh();
		rawModel.bind(0, 1, 2);
		shader.location_shineDamper.loadFloat(1);
		shader.location_reflectivity.loadFloat(0);
		shader.texture.bindTexture(model.getTexture());
		shader.colorMap.bindTexture(model.getColorMap());
	}

	private void unbindTexturedModel(Vao vao) {
		vao.unbind(0, 1, 2);
		GLSettings.setBackFaceCulling(true);
	}

	private void prepareInstance(Camera camera, Entity entity) {
		Matrix4f transformationMatrix = entity.getTransformationMatrix();
		if (entity.getType() == Entity.Type.Camera) {
			transformationMatrix = Matrix.createTransformationMatrixRelativeCam(camera,
					entity.getTransform().getPosition(), entity.getTransform().rotX, entity.getTransform().rotY,
					entity.getTransform().rotZ,
					(entity.getTransform().scaleX + entity.getTransform().scaleY + entity.getTransform().scaleZ) / 3f);
		}
		shader.location_transformationMatrix.loadMatrix(transformationMatrix);

		shader.colorMapOffsetColor.loadVec3(entity.getColorMapOffsetColor());
	}

	public void setProjectionMatrix(Matrix4f matrix) {
		shader.start();
		shader.location_projectionMatrix.loadMatrix(matrix);
		shader.stop();
	}

	@Override
	public void delete() {
		shader.delete();
	}

}
