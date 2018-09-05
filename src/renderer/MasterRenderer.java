package renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import engine.Display;
import engine.GLSettings;
import engine.res.ENGINE_RES;
import entities.Entity;
import entities.Light;
import guis.EntityComponent;
import hitbox.HitBoxMaster;
import objects.Camera;
import objects.Model_3D;
import objects.Vao;
import renderer.armatureRenderer.ArmatureRenderer;
import renderer.entityRenderer.EntityRenderer;
import renderer.entityRenderer.EntityShader;
import renderer.imageRenderer.ImageRenderer;
import renderer.lineRenderer.LineRenderer;
import renderer.quadRenderer.QuadRenderer;
import renderer.terrainRenderer.TerrainRenderer;
import renderer.terrainRenderer.TerrainShader;
import renderer.textRendering.FontRenderer;
import selector.SelectorManager;
import terrains.Terrain;
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

	public static Map<Model_3D, List<Entity>> entities = new HashMap<Model_3D, List<Entity>>();
	
	private List<Terrain> terrains = new ArrayList<Terrain>();

	private List<Entity> animatedEntity = new ArrayList<Entity>();

	private static List<EntityComponent> entityComponents = new ArrayList<EntityComponent>();

	public static Vector3f selectedColor = new Vector3f(255, 255, 255);

	public static List<Entity> allEntsWithHoverEvent = new ArrayList<Entity>();

	public MasterRenderer() {
		ENGINE_RES.init();
		entityRenderer = new EntityRenderer(entityShader);
		terrainRenderer = new TerrainRenderer(terrainShader, null);
		linerenderer = new LineRenderer();
		new FontRenderer();
		new QuadRenderer();
		new ImageRenderer();
		new ArmatureRenderer();
		init();
	}

	public void setProjectionMatrix(Matrix4f matrix) {
		entityRenderer.setProjectionMatrix(matrix);
		terrainRenderer.loadProjMatrix(matrix);
		linerenderer.setProjectionMatrix(matrix);
	}

	public static void addEntityComponents(EntityComponent comp) {
		entityComponents.add(comp);
	}

	private void updateEntities(List<Entity> entities) {
		for (Entity ent : entities) {
			entititesAmount++;
			ent.update();
			if (ent.hasHoverEvent()) {
				allEntsWithHoverEvent.add(ent);
			}
			if (ent.hasComponentType(components.Component.Type.ANIMATION)) {
				processAnimatedEntity(ent);
			} else {
				processEntity(ent);
			}
			updateEntities(ent.getChildrenEntities());
		}
	}

	private void processAnimatedEntity(Entity ent) {
		animatedEntity.add(ent);
	}

	private void updateTerrains(List<Terrain> terrains2) {
		for (Terrain terrain : terrains2) {
			terrainsAmount++;
			processTerrain(terrain);
		}
	}

	private void processTerrain(Terrain terrain) {
		terrains.add(terrain);
	}

	public void render(Camera camera, List<Light> light, List<Entity> entities, List<Terrain> terrains) {
		if (terrains != null) {
			updateTerrains(terrains);
		}
		updateEntities(entities);
		render(light, camera, new Vector4f(0, 0, 1, 0));
		lightsAmount = light.size();
	}

	public void render(Camera camera, List<Light> light, List<Entity> entities) {
		allEntsWithHoverEvent.clear();
		updateEntities(entities);
		render(light, camera, new Vector4f(0, 0, 1, 0));
		lightsAmount = light.size();
	}

	private int renderAMcolorRender = 0;

	public void render(List<Light> lights, Camera camera, Vector4f clipPlane) {
		for (EntityComponent component : entityComponents) {
			if (camera != null) {
				component.updateEntity(camera);
				processEntity(component.getEntity());
			}
		}

		renderHoverEntities();
		renderAMcolorRender++;
		if (renderAMcolorRender > Display.getFPS()) {
			selectedColor = SelectorManager.getColorFromFBOPixel(new Vector2f(0, 0), Display.getWidth(),
					Display.getHeight());
			renderAMcolorRender = 0;
		}
		GLSettings.setBackFaceCulling(true);
		prepare();
		entityShader.start();
		entityShader.location_lightColour.loadVec3(lights.get(0).getColour());
		entityShader.location_lightPosition.loadVec3(lights.get(0).getPosition());
		entityShader.location_viewMatrix.loadMatrix(camera.getViewMatrix());
		entityRenderer.render(camera, entities);
		entityShader.stop();
		if (HitBoxMaster.renderHitBoxes == true) {
			linerenderer.renderHitBoxes(camera, HitBoxMaster.hitBoxes);
		}

		ArmatureRenderer.render(lights.get(0).getPosition(), animatedEntity, camera);

		terrainShader.start();
		terrainShader.loadLights(lights);
		terrainShader.location_viewMatrix.loadMatrix(camera.getViewMatrix());
		terrainRenderer.render(terrains);
		terrainShader.stop();

		entityComponents.clear();
		entities.clear();
		terrains.clear();
		animatedEntity.clear();
		GLSettings.setBackFaceCulling(false);
	}

	private void renderHoverEntities() {
		// TODO Auto-generated method stub

	}

	public static void processEntity(Entity entity) {
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
		ArmatureRenderer.init();
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
