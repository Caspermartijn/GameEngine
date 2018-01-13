package menus;

import static engine.Mouse.setMouseEnabled;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import entities.Light;
import entities.TimeShip;
import gamestates.GameState;
import guis.GUI;
import guis.ImageComponent;
import objects.Camera;
import objects.Model_3D;
import objects.Skybox;
import renderer.MasterRenderer;
import renderer.skyboxRenderer.SkyboxRenderer;
import utils.SourceFile;
import utils.maths.Maths;
import utils.models.ModelMaster;

public class SettingsMenu extends GUI {
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	private ArrayList<Light> lights = new ArrayList<Light>();

	private Skybox skybox;

	private Camera camera;

	private MasterRenderer master;
	private SkyboxRenderer skyboxRenderer;

	@SuppressWarnings("unused")
	public SettingsMenu(MasterRenderer master, SkyboxRenderer skyboxRenderer) {
		this.skyboxRenderer = skyboxRenderer;
		this.master = master;
		super.setPosition(0f, 0.0f);
		images();

		camera = new Camera();
		camera.setNewProj();

		camera.x = -793;
		camera.y = 47;
		camera.z = 58;

		camera.pitch = -4.814f;
		camera.yaw = 117.0847f+3;
		camera.updateMatrix();

		Light sun = new Light(new Vector3f(2000, 2000, 2000), new Vector3f(1, 1, 1));
		lights.add(sun);
		master.setProjectionMatrix(camera.getProjectionMatrix());

		SourceFile ame_nebula = new SourceFile("/res/skybox/space_1");
		Skybox skybox = new Skybox(new SourceFile[] { new SourceFile(ame_nebula, "face_right.png"),
				new SourceFile(ame_nebula, "face_left.png"), new SourceFile(ame_nebula, "face_bottom.png"),
				new SourceFile(ame_nebula, "face_top.png"), new SourceFile(ame_nebula, "face_back.png"),
				new SourceFile(ame_nebula, "face_front.png") }, 512);
		this.skybox = skybox;

		Model_3D timemastersHQ = ModelMaster.getModel("timemasters_hq_1");
		Entity ent = new Entity(timemastersHQ, new Vector3f(0, 0, 0), new Vector3f(0, 180 + 40, 0), 5);
		TimeShip timeship_1 = new TimeShip(new Vector3f(0, -20.5f, 10f), new Vector3f(0, 90, 0), 0.4f);
		TimeShip timeship_2 = new TimeShip(new Vector3f(0, -20.5f, -10f), new Vector3f(0, -90, 0), 0.4f);
		TimeShip timeship_3 = new TimeShip(new Vector3f(10f, -20.5f, 0), new Vector3f(0, 180, 0), 0.4f);
		ent.addChild(timeship_1);
		ent.addChild(timeship_2);
		ent.addChild(timeship_3);

		entities.add(ent);
		
		GameState settingsMenu = new GameState("main_menu") {

			@Override
			public void render() {
				renderComponents();
			}

			@Override
			public void start() {
				setMouseEnabled(true);
			}

			@Override
			public void stop() {
				
			}
		};
	}

	public void images() {
		ImageComponent background = new ImageComponent(this, new SourceFile("/res/guis/menus/main/back.png"));
		background.setPosition(0.65f, 0.5f);
		background.setSize(Maths.getFrom720toCurrentDisplaySize(new Vector2f(1000, 720)));background.setRotation(180);
	}

	@Override
	public void renderComponents() {
		skyboxRenderer.render(skybox, camera);
		master.render(camera, lights, entities);
		master.unprepare();
		super.renderComponents();
	}
}
