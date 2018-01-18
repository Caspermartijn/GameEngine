package scenes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entities.Entity;
import entities.Light;
import hitbox.HBox;
import objects.Camera;
import objects.Model_3D;
import objects.Skybox;
import renderer.MasterRenderer;
import renderer.skyboxRenderer.SkyboxRenderer;
import terrains.Terrain;
import textures.Texture;
import utils.tasks.Cleanup;

public abstract class Scene extends Cleanup implements IScene {

	private static Scene current_scene;

	public static void setCurrentScene(Scene scene) {
		Scene.current_scene = scene;
	}

	public static Scene getCurrentscene() {
		return current_scene;
	}

	public static void renderScene(Camera camera) {
		current_scene.render(camera);
	}

	public HashMap<String, Model_3D> models = new HashMap<String, Model_3D>();
	public List<Entity> entities = new ArrayList<Entity>();
	public List<Light> lights = new ArrayList<Light>();
	public List<HBox> hitboxes = new ArrayList<HBox>();
	public List<Terrain> terrains = new ArrayList<Terrain>();

	public Skybox skybox;

	private String base_name;

	private String displayName = "test";

	private String[] objective;

	private MasterRenderer masterRenderer;
	private SkyboxRenderer skyboxRenderer;

	private Texture preview;

	public Scene(String base_name, MasterRenderer masterRenderer, SkyboxRenderer skyboxRenderer) {
		this.base_name = base_name;
		this.masterRenderer = masterRenderer;
		this.skyboxRenderer = skyboxRenderer;
	}

	public void addModel(String model_name, Model_3D model) {
		models.put(model_name, model);
	}

	@Override
	public void delete() {
		for(Model_3D model : models.values()) {
			model.getMesh().delete();
			model.getTexture().delete();
		}
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Texture getPreview() {
		return preview;
	}

	public void setPreview(Texture preview) {
		this.preview = preview;
	}

	public String[] getObjective() {
		return objective;
	}

	public void setObjective(String[] objective) {
		this.objective = objective;
	}

	public void addHitBox(HBox box) {
		hitboxes.add(box);
	}

	public Skybox getSkyBox() {
		return skybox;
	}

	public void setBox(Skybox box) {
		this.skybox = box;
	}

	public List<Entity> getEntities() {
		return entities;
	}

	public List<Light> getLights() {
		return lights;
	}

	public List<HBox> getHitboxes() {
		return hitboxes;
	}

	public List<Terrain> getTerrains() {
		return terrains;
	}

	public String getBase_name() {
		return base_name;
	}

	public MasterRenderer getMasterRenderer() {
		return masterRenderer;
	}

	public SkyboxRenderer getSkyboxRenderer() {
		return skyboxRenderer;
	}

	@Override
	public void render(Camera camera) {
		skyboxRenderer.render(skybox, camera);
		masterRenderer.render(camera, lights, entities);
		masterRenderer.unprepare();
	}

	public Model_3D getModel(String model_name) {
		return models.get(model_name.toLowerCase());
	}

}
