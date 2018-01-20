package renderer.armatureRenderer;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import components.ArmatureComponent;
import components.Component;
import entities.Entity;
import objects.Camera;

public class ArmatureRenderer {

	private static ArmatureShader shader;

	public static void init() {
		shader = new ArmatureShader();
	}

	public static void render(Vector3f direction, List<Entity> animatedEntities, Camera camera) {
		shader.start();
		shader.projectionViewMatrix.loadMatrix(camera.getViewMatrix());
		shader.lightDirection.loadVec3(direction);
		for (Entity animatedEntity : animatedEntities) {
			shader.diffuseMap.bindTexture(animatedEntity.getModel().getTexture());
			shader.modelMatrix.loadMatrix(animatedEntity.getTransform().toMatrix());
			ArmatureComponent c = (ArmatureComponent) animatedEntity.getComponent(Component.Type.ARMATURE);
			shader.jointTransforms.loadMatrixArray(c.getMatrices());
			animatedEntity.getModel().getMesh().bind(0, 1, 2, 3, 4);
			GL11.glDrawElements(GL11.GL_TRIANGLES, animatedEntity.getModel().getMesh().getIndexCount(),
					GL11.GL_UNSIGNED_INT, 0);
			animatedEntity.getModel().getMesh().unbind(0, 1, 2, 3, 4);
		}
		shader.stop();
	}

	public static void delete() {
		shader.delete();
	}
}
