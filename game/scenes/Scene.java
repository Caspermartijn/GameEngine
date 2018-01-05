package scenes;

import java.util.ArrayList;
import java.util.List;

import entities.Entity;
import entities.Light;
import hitbox.HBox;
import objects.Camera;
import objects.Skybox;
import renderer.MasterRenderer;
import renderer.skyboxRenderer.SkyboxRenderer;
import terrains.Terrain;

public abstract class Scene implements IScene {

	public List<Entity> entities = new ArrayList<Entity>();
	public List<Light> lights = new ArrayList<Light>();
	public List<HBox> hitboxes = new ArrayList<HBox>();
	public List<Terrain> terrains = new ArrayList<Terrain>();
	public Skybox skybox;
	
	private String base_name;
	
	private MasterRenderer masterRenderer;
	private SkyboxRenderer skyboxRenderer;
	
	public Scene(String base_name, MasterRenderer masterRenderer, SkyboxRenderer skyboxRenderer) {
		this.base_name = base_name;
		this.masterRenderer = masterRenderer;
		this.skyboxRenderer = skyboxRenderer;
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
		for(Entity ent : entities) {
			ent.update();
		}
		skyboxRenderer.render(skybox, camera);
		masterRenderer.render(camera, lights, entities);
		masterRenderer.unprepare();		
	}
	
}
