package renderer;

import java.util.List;

import entities.Entity;
import objects.Camera;
import renderer.entityRenderer.EntityRenderer;
import renderer.entityRenderer.EntityShader;

public class MasterRenderer {

	private EntityShader entityShader = new EntityShader();
	private EntityRenderer entityRenderer;

	public MasterRenderer() {
		entityRenderer = new EntityRenderer(entityShader, projectionMatrix);
	}

	public void render(Camera camera, List<Entity> entities) {

	}

}
