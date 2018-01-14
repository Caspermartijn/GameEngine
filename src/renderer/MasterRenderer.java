package renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;

import engine.GLSettings;
import entities.Entity;
import entities.Light;
import hitbox.HitBoxMaster;
import objects.Camera;
import objects.Model_3D;
import objects.Vao;
import renderer.entityRenderer.EntityRenderer;
import renderer.entityRenderer.EntityShader;
import renderer.imageRenderer.ImageRenderer;
import renderer.lineRenderer.LineRenderer;
import renderer.quadRenderer.QuadRenderer;
import renderer.terrainRenderer.TerrainRenderer;
import renderer.terrainRenderer.TerrainShader;
import renderer.textRendering.FontRenderer;
import terrains.Terrain;
import terrains.terrainTexture.TerrainTexturePack;
import utils.tasks.Cleanup;

public class MasterRenderer extends Cleanup {

	public static final float RED = 0.1f;
	public static final float GREEN = 0.4f;
	public static final float BLUE = 0.2f;

	public static float density = 0.0035f;
	public static float gradient = 5f;

	private EntityShader entityShader = new EntityShader();
	private EntityRenderer entityRenderer;

	private TerrainShader terrainShader = new TerrainShader();
	private TerrainRenderer terrainRenderer;

	public LineRenderer linerenderer;

	private Map<Model_3D, List<Entity>> entities = new HashMap<Model_3D, List<Entity>>();
	private Map<TerrainTexturePack, List<Terrain>> terrains = new HashMap<TerrainTexturePack, List<Terrain>>();

	public MasterRenderer() {
		entityRenderer = new EntityRenderer(entityShader);
		terrainRenderer = new TerrainRenderer(terrainShader);
		linerenderer = new LineRenderer();
		new FontRenderer();
		new QuadRenderer();
		new ImageRenderer();
		init();
	}

	public void setProjectionMatrix(Matrix4f matrix) {
		entityRenderer.setProjectionMatrix(matrix);
		terrainRenderer.setProjMat(matrix);
		linerenderer.setProjectionMatrix(matrix);
	}

	private void updateEntities(List<Entity> entities) {
		for (Entity ent : entities) {
			entititesAmount++;
			ent.update();
			processEntity(ent);
			updateEntities(ent.getChildrenEntities());
		}
	}

	public void render(Camera camera, List<Light> light, List<Entity> entities) {
		updateEntities(entities);
		render(light, camera, new Vector4f(0, 0, 1, 0));
		lightsAmount = light.size();
	}

	public void render(List<Light> lights, Camera camera, Vector4f clipPlane) {
		GLSettings.setBackFaceCulling(true);
		prepare();
		entityShader.start();
		entityShader.location_lightColour.loadVec3(lights.get(0).getColour());
		entityShader.location_lightPosition.loadVec3(lights.get(0).getPosition());
		entityShader.location_viewMatrix.loadMatrix(camera.getViewMatrix());
		entityRenderer.render(entities);
		entityShader.stop();
		if(HitBoxMaster.renderHitBoxes == true) {
		linerenderer.renderHitBoxes(camera, HitBoxMaster.hitBoxes);
		}
		terrainShader.start();
		terrainShader.loadLights(lights);
		terrainShader.location_viewMatrix.loadMatrix(camera.getViewMatrix());
		terrainShader.loadTileAmounts(100, 100, 100, 100);
		terrainRenderer.loadFog(density, gradient, RED, GREEN, BLUE);
		terrainRenderer.render(terrains, null, new Vector4f(0, 1, 0, -Terrain.MAX_HEIGHT));
		terrainShader.stop();
		entities.clear();
		terrains.clear();
		GLSettings.setBackFaceCulling(false);
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

	public void prepare() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}

	public void unprepare() {
		GL11.glDisable(GL11.GL_DEPTH_TEST);
	}

	public void delete() {
	}

	private static Vao quad;

	private static int entititesAmount;
	private static int terrainsAmount;
	private static int lightsAmount;

	public static int getEntitiesAmount() {
		int i = entititesAmount;
		entititesAmount = 0;
		return i;
	}

	public static int getTerrainsAmount() {
		int i = terrainsAmount;
		terrainsAmount = 0;
		return i;
	}

	public static int getLightsAmount() {
		int i = lightsAmount;
		lightsAmount = 0;
		return i;
	}

	public static void init() {
		FontRenderer.init();
		QuadRenderer.init();
		ImageRenderer.init();
		quad = Vao.create();
		float[] positions = { -1, 1, -1, -1, 1, 1, 1, -1 };
		quad = quad.loadToVAO(positions, 2);
	}

	public static void renderQuad() {
		quad.bind(0);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
		quad.unbind(0);
	}

}
