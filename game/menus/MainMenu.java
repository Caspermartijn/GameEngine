package menus;

import static engine.Mouse.setMouseEnabled;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import engine.Display;
import engine.Mouse;
import entities.Entity;
import entities.Light;
import entities.TimeShip;
import gamestates.GamePerspective;
import guis.ButtonComponent;
import guis.GUI;
import guis.LoadingGui;
import objects.Camera;
import objects.Model_3D;
import objects.Skybox;
import renderer.MasterRenderer;
import renderer.skyboxRenderer.SkyboxRenderer;
import utils.SourceFile;
import utils.models.ModelMaster;

public class MainMenu extends GUI {

	private ArrayList<Entity> entities = new ArrayList<Entity>();
	private ArrayList<Light> lights = new ArrayList<Light>();

	private Skybox skybox;

	private Camera camera;

	private MasterRenderer master;
	private SkyboxRenderer skyboxRenderer;

	private Entity movingTimeShip;

	@SuppressWarnings("unused")
	public MainMenu(MasterRenderer master, SkyboxRenderer skyboxRenderer) {
		this.skyboxRenderer = skyboxRenderer;
		this.master = master;
		super.setPosition(0f, 0.0f);
		images();
		buttons();
		texts();

		camera = new Camera();
		camera.setNewProj();

		camera.x = -704;
		camera.y = 131;
		camera.z = 266;

		camera.pitch = 0.0925175f;
		camera.yaw = 48.9f;
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

		movingTimeShip = new TimeShip(new Vector3f(-400, 131, 266), new Vector3f(0, 0, 0), 1f);
		entities.add(ent);
		entities.add(movingTimeShip);
		
		GamePerspective mainMenu = new GamePerspective("main_menu") {

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

	public void texts() {

	}

	List<ButtonComponent> buttons = new ArrayList<ButtonComponent>();

	public ButtonComponent caimpain_button, scenes_button, coop_button, settings_button, quit_button;

	public void buttons() {
		float spaceBetw = 0.12f;
		caimpain_button = new ButtonComponent(this, 0.06f, 0.35f, 0.2f, 0.1f);
		scenes_button = new ButtonComponent(this, 0.06f, 0.35f + spaceBetw, 0.2f, 0.1f);
		coop_button = new ButtonComponent(this, 0.06f, 0.35f + spaceBetw * 2, 0.2f, 0.1f);
		settings_button = new ButtonComponent(this, 0.06f, 0.35f + spaceBetw * 3, 0.2f, 0.1f);
		quit_button = new ButtonComponent(this, 0.06f, 0.35f + spaceBetw * 4, 0.2f, 0.1f);
		
		buttons.add(caimpain_button);
		buttons.add(scenes_button);
		buttons.add(coop_button);
		buttons.add(settings_button);
		buttons.add(quit_button);

		for (ButtonComponent button : buttons) {
			button.setOutlineWidth(0.002f);
			button.setOutlineColor(new Vector4f(0.3f, 0.3f, 0.3f, 0.7f));
			button.setBackgroundColor(new Vector4f(0.7f, 0.7f, 0.7f, 0.7f));
			button.setTextPosition(new Vector2f(0, 0.01f));
			button.setClickDelay(500);
			button.setHoverEvent(new Runnable() {
				@Override
				public void run() {
					button.playHover(1.05f);
				}
			});
		}
		caimpain_button.setText("Campain", "candara", 3f);
		scenes_button.setText("Scenes", "candara", 3f);
		coop_button.setText("Co-op", "candara", 3f);
		settings_button.setText("Settings", "candara", 3f);
		quit_button.setText("Quit", "candara", 3f);

		caimpain_button.setClickEvent(new Runnable() {

			@Override
			public void run() {
				campainButtonClick();
			}

		});

		scenes_button.setClickEvent(new Runnable() {

			@Override
			public void run() {
				scenesButtonClick();
			}

		});

		coop_button.setClickEvent(new Runnable() {

			@Override
			public void run() {
				coopButtonClick();
			}

		});

		settings_button.setClickEvent(new Runnable() {

			@Override
			public void run() {
				settingsButtonClick();
			}

		});

		quit_button.setClickEvent(new Runnable() {

			@Override
			public void run() {
				quitButtonClick();
			}

		});
	}

	public void campainButtonClick() {
		new LoadingGui(4000) {
			
			@Override
			public void midLoad() {
				
			}
			
			@Override
			public void afterLoad() {
				Mouse.setMouseEnabled(false);
				GamePerspective.switchGameState("ingame");
			}
		};
		
	}

	public void scenesButtonClick() {

	}

	public void coopButtonClick() {
		GamePerspective.switchGameState("coop_menu");
	}

	public void settingsButtonClick() {
		GamePerspective.switchGameState("settings_menu");
	}

	public void quitButtonClick() {
		Display.closeDisplay();
	}

	// =================Hover=================

	public void campainButtonHover() {
	}

	public void scenesButtonHover() {

	}

	public void coopButtonHover() {

	}

	public void settingsButtonHover() {

	}

	public void quitButtonHover() {

	}

	public void images() {
		//ImageComponent background = new ImageComponent(this, new SourceFile("/res/guis/menus/menus_background.png"));//ori: "/res/guis/menus/main/back.png"
		//background.setPosition(0.15f, 0.5f);
		//background.setSize(Maths.getFrom720toCurrentDisplaySize(new Vector2f(600, 720)));
	}

	float speed = 200;
	boolean enabled = true;

	@Override
	public void renderComponents() {

		if (enabled) {
			movingTimeShip.getTransform().posZ = (float) (movingTimeShip.getTransform().getPosition().z
					- 200 * Display.getFrameTime());
			if (movingTimeShip.getTransform().getPosition().z < -800) {
				movingTimeShip.getTransform().rotY = -180;
				movingTimeShip.getTransform().rotZ = -5;
				enabled = false;
			}
		} else {
			movingTimeShip.getTransform().posZ = (float) (movingTimeShip.getTransform().getPosition().z
					+ 200 * Display.getFrameTime());
			if (movingTimeShip.getTransform().getPosition().z > 800) {
				movingTimeShip.getTransform().rotZ = 5;
				movingTimeShip.getTransform().rotY = 0;
				enabled = true;
			}
		}

		skyboxRenderer.render(skybox, camera);
		master.render(camera, lights, entities);
		master.unprepare();
		super.renderComponents();
	}

}
