package renderer.armatureRenderer;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import components.AnimationComponent;
import components.Component;
import engine.GLSettings;
import entities.Entity;
import objects.Camera;
import utils.tasks.Cleanup;

public class ArmatureRenderer extends Cleanup {

	private static ArmatureShader shader;

	public ArmatureRenderer() {
		super();
	}
	
	public static void init() {
		shader = new ArmatureShader();
	}

	public static void render(Vector3f direction, List<Entity> animatedEntities, Camera camera) {
		GLSettings.disableBlending();
		
		shader.start();
		shader.projectionViewMatrix.loadMatrix(camera.getCameraMatrix());
		shader.lightDirection.loadVec3(direction);
		for (Entity animatedEntity : animatedEntities) {
			shader.diffuseMap.bindTexture(animatedEntity.getModel().getTexture());
			shader.modelMatrix.loadMatrix(animatedEntity.getTransform().toMatrix());
			AnimationComponent c = (AnimationComponent) animatedEntity.getComponent(Component.Type.ANIMATION);
			shader.jointTransforms.loadMatrixArray(c.getMatrices());
			animatedEntity.getModel().getMesh().bind(0, 1, 2, 3, 4);
			GL11.glDrawElements(GL11.GL_TRIANGLES, animatedEntity.getModel().getMesh().getIndexCount(),
					GL11.GL_UNSIGNED_INT, 0);			
			animatedEntity.getModel().getMesh().unbind(0, 1, 2, 3, 4);
		}
		shader.stop();
	}

	public void delete() {
		shader.delete();
		System.out.println("- ArmatureShader");
	}
}
