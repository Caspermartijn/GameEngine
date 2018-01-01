package renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;

import entities.Entity;
import entities.Light;
import objects.Camera;
import objects.Model_3D;
import renderer.entityRenderer.EntityRenderer;
import renderer.entityRenderer.EntityShader;

public class MasterRenderer {

	public static final float RED = 0.1f;
	public static final float GREEN = 0.4f;
	public static final float BLUE = 0.2f;

	private EntityShader entityShader = new EntityShader();
	private EntityRenderer entityRenderer;

	private Map<Model_3D, List<Entity>> entities = new HashMap<Model_3D, List<Entity>>();

	public MasterRenderer() {
		entityRenderer = new EntityRenderer(entityShader);
	}

	public void setProjectionMatrix(Matrix4f matrix) {
		entityRenderer.setProjectionMatrix(matrix);
	}

	public void render(Camera camera, List<Light> lights, List<Entity> entities) {
		for (Entity ent : entities) {
			processEntity(ent);
		}
		render(lights, camera, new Vector4f(0, 0, 1, 0));
	}

	public void render(List<Light> lights, Camera camera, Vector4f clipPlane) {
		prepare();
		entityShader.start();
		shader.loadClipPlane(clipPlane);
		shader.loadSkyColour(RED, GREEN, BLUE);
		shader.loadLights(lights);
		shader.loadViewMatrix(camera);
		renderer.render(entities);
		entityShader.stop();
		entities.clear();
	}

	public void processEntity(Entity entity) {
		Model_3D entityModel = entity.getModel();
		List<Entity> batch = entities.get(entityModel);
		if (batch != null) {
			batch.add(entity);
		} else {
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			entities.put(entityModel, newBatch);
		}
	}

	public static void enableCulling() {
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}

	public static void disableCulling() {
		GL11.glDisable(GL11.GL_CULL_FACE);
	}

	public void prepare() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(RED, GREEN, BLUE, 1);
	}

}
