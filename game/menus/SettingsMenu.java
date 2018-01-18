package menus;

import static engine.Mouse.setMouseEnabled;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import engine.Display;
import entities.Entity;
import entities.Light;
import entities.TimeShip;
import gamestates.GamePerspective;
import guis.ButtonComponent;
import guis.GUI;
import guis.ImageComponent;
import guis.QuadComponent;
import guis.TextComponent;
import menus.settings.AudioMenu;
import menus.settings.DisplayMenu;
import menus.settings.GraphicsMenu;
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

	private Entity timematers;

	private DisplayMenu displaySettings;
	private GraphicsMenu graphicsSettings;
	private AudioMenu audioSettings;

	@SuppressWarnings("unused")
	public SettingsMenu(MasterRenderer master, SkyboxRenderer skyboxRenderer) {
		this.skyboxRenderer = skyboxRenderer;
		this.master = master;
		super.setPosition(0f, 0.0f);
		images();
		buttons();
		// texts();
		camera = new Camera();
		camera.setNewProj();

		camera.x = -793;
		camera.y = 47;
		camera.z = 58;

		camera.pitch = -4.814f;
		camera.yaw = 117.0847f + 3;
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

		Model_3D timemastersHQ = ModelMaster.getOBJModel("timemasters_hq_1");
		timematers = new Entity(timemastersHQ, new Vector3f(0, 0, 0), new Vector3f(0, 180 + 40, 0), 4.6f);
		TimeShip timeship_1 = new TimeShip(new Vector3f(0, -20.5f, 10f), new Vector3f(0, 90, 0), 0.4f);
		TimeShip timeship_2 = new TimeShip(new Vector3f(0, -20.5f, -10f), new Vector3f(0, -90, 0), 0.4f);
		TimeShip timeship_3 = new TimeShip(new Vector3f(10f, -20.5f, 0), new Vector3f(0, 180, 0), 0.4f);
		timematers.addChild(timeship_1);
		timematers.addChild(timeship_2);
		timematers.addChild(timeship_3);

		entities.add(timematers);

		GamePerspective settingsMenu = new GamePerspective("settings_menu") {

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

		displaySettings = new DisplayMenu();
		graphicsSettings = new GraphicsMenu();
		audioSettings = new AudioMenu();

		hideAllSubGuis();
		displaySettings.showAll();
	}

	List<ButtonComponent> buttons = new ArrayList<ButtonComponent>();

	public ButtonComponent display_button, graphics_button, audio_button, keybindings_button, controller_button;

	public void buttons() {
		float spaceBetw = 0.11f;
		float startFloat = 0.41f;

		float y = 0.25f;

		display_button = new ButtonComponent(this, startFloat, y, 0.1f, 0.06f);
		graphics_button = new ButtonComponent(this, startFloat + spaceBetw, y, 0.1f, 0.06f);
		audio_button = new ButtonComponent(this, startFloat + spaceBetw * 2, y, 0.1f, 0.06f);
		keybindings_button = new ButtonComponent(this, startFloat + spaceBetw * 3, y, 0.1f, 0.06f);
		controller_button = new ButtonComponent(this, startFloat + spaceBetw * 4, y, 0.1f, 0.06f);
		buttons.add(display_button);
		buttons.add(graphics_button);
		buttons.add(audio_button);
		buttons.add(keybindings_button);
		buttons.add(controller_button);

		for (ButtonComponent button : buttons) {
			button.setOutlineWidth(0.001f);
			button.setOutlineColor(new Vector4f(0.3f, 0.3f, 0.3f, 0.7f));
			button.setBackgroundColor(new Vector4f(0.7f, 0.7f, 0.7f, 0.7f));
			button.setTextPosition(new Vector2f(0, 0.0137f));
			button.setClickDelay(500);
			button.setHoverEvent(new Runnable() {

				@Override
				public void run() {
					button.playHover(1.05f);
				}

			});
		}
		display_button.setText("Display", "candara", 1.3f);
		graphics_button.setText("Graphics", "candara", 1.3f);
		audio_button.setText("Audio", "candara", 1.3f);
		keybindings_button.setText("Keybindings", "candara", 1.3f);
		controller_button.setText("Controller", "candara", 1.3f);

		display_button.setClickEvent(new Runnable() {

			@Override
			public void run() {
				hideAllSubGuis();
				displaySettings.showAll();
			}

		});

		graphics_button.setClickEvent(new Runnable() {

			@Override
			public void run() {
				hideAllSubGuis();
				graphicsSettings.showAll();
			}

		});

		audio_button.setClickEvent(new Runnable() {

			@Override
			public void run() {
				hideAllSubGuis();
				audioSettings.showAll();
			}

		});
	}

	private void hideAllSubGuis() {
		displaySettings.hideAll();
		audioSettings.hideAll();
		graphicsSettings.hideAll();
	}

	public void images() {
		ImageComponent background = new ImageComponent(this, new SourceFile("/res/guis/menus/menus_background_2.png"));
		background.setPosition(0.65f, 0.5f);
		background.setSize(Maths.getFrom720toCurrentDisplaySize(new Vector2f(1000, 720)));
		background.setRotation(180);
		QuadComponent quad = new QuadComponent(this, 0.41f, 0.2f + 0.15f, 0.541f, 0.5f);
		quad.setBackgroundColor(new Vector4f(0.078431f, 0.168627f, 0.29803f, 1f));

		ImageComponent text = new ImageComponent(this, new SourceFile("/res/guis/menus/titles/Settings.png"));
		text.setPosition(0.515f, 0.15f);
		text.setSize(Maths.getFrom720toCurrentDisplaySize(new Vector2f(510, 150)));
	}

	@SuppressWarnings("unused")
	private void texts() {
		TextComponent settings = new TextComponent(this, "Settings (this will be an image)", "candara", 3.4f, 200,
				false);
		settings.setColor(0, 0, 0, 1);
		settings.setPosition(0.41f, 0.075f);
	}

	@Override
	public void renderComponents() {
		timematers.getTransform().rotY += 5 * Display.getFrameTime();

		skyboxRenderer.render(skybox, camera);
		master.render(camera, lights, entities);
		master.unprepare();
		super.renderComponents();
		displaySettings.renderComponents();
		audioSettings.renderComponents();
		graphicsSettings.renderComponents();
	}
}
