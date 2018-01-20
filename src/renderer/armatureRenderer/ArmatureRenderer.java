package renderer.armatureRenderer;

import org.lwjgl.opengl.GL11;

import components.ArmatureComponent;
import components.Component;
import entities.Entity;
import renderer.MasterRenderer;

public class ArmatureRenderer {

	private static ArmatureShader shader;

	public static void init() {
		shader = new ArmatureShader();
	}

	public static void render(Entity entity, Camera camera) {
		shader.start();
		shader.projectionViewMatrix.loadMatrix(MasterRenderer.getRenderCamera().getCameraMatrix());
		shader.lightDirection.loadVec3(MasterRenderer.getLightDirection());
		shader.diffuseMap.bindTexture(entity.getTexture());
		shader.modelMatrix.loadMatrix(entity.getTransform().toMatrix());
		ArmatureComponent c = (ArmatureComponent) entity.getModel().getComponent(Component.Type.ARMATURE);
		shader.jointTransforms.loadMatrixArray(c.getMatrices());
		
		entity.getModel().getMesh().bind(0, 1, 2, 3, 4);
		GL11.glDrawElements(GL11.GL_TRIANGLES, entity.getModel().getMesh().getIndexCount(),
				GL11.GL_UNSIGNED_INT, 0);
		entity.getModel().getMesh().unbind(0, 1, 2, 3, 4);
		
		shader.stop();
	}

	public static void delete() {
		shader.delete();
	}
}
