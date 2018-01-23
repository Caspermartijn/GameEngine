package main;

import static audio.Sound2DMaster.getSong;
import static audio.Sound2DMaster.loadSound;
import static audio.Sound2DMaster.playSound;
import static audio.Sound2DMaster.setGeneralVolume;
import static audio.Sound2DMaster.setVolume;
import static engine.Display.createDisplay;
import static engine.Display.disposeDisplay;
import static engine.Display.swapBuffers;
import static engine.Display.updateEvents;
import static engine.GLSettings.setClearColor;
import static engine.GLSettings.setDepthTesting;
import static engine.Mouse.setMouseEnabled;
import static gamestates.GamePerspective.renderGameState;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static scenes.Scene.renderScene;
import static scenes.Scene.setCurrentScene;
import static utils.RenderItem.renderItems;
import static utils.tasks.Cleanup.cleanAll;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import controlls.FreeCam;
import debug.DebugGui;
import engine.Display;
import engine.DisplayBuilder;
import engine.Keyboard;
import entities.Entity;
import entities.Light;
import entities.TimeShip;
import gamestates.GamePerspective;
import guis.FPS_HUD;
import guis.LoadingGui;
import hitbox.HitBox;
import launcher.Launcher;
import log.Log;
import main.gameloading.GameLoader;
import menus.CoopMenu;
import menus.MainMenu;
import menus.SettingsMenu;
import objects.Camera;
import objects.FPS_Player;
import objects.Model_3D;
import objects.Skybox;
import renderer.MasterRenderer;
import renderer.skyboxRenderer.SkyboxRenderer;
import scenes.Scene;
import scenes.SceneLoader;
import texts.Fonts;
import utils.RenderItem;
import utils.SourceFile;
import utils.models.ModelMaster;

public class GameLoop {

	public static Launcher l;
	private static Camera camera;

	public static void main(String[] args) {
		String title = "VectorEngine";
		startApplication(title);

		l = new Launcher(title) {

			private static final long serialVersionUID = 001L;

			@Override
			public void play() {
				startApplication(this.getTitle());
			}

			@Override
			public void credits() {

			}

			@Override
			public void options() {

			}

			@Override
			public void quit() {
				this.closeApp();
			}

			@Override
			public void loadData() {

			}

			@Override
			public void launcherLoad() {
				try {
					loadSound("track1", "/res/sounds/songs/film_guitar_theme_music.wav");
					loadSound("track2", "/res/sounds/songs/that_feeling.wav");
					loadSound("hoverpop", "/res/sounds/launcher/hoverpop.wav");
					loadSound("play_click", "/res/sounds/launcher/play_click.wav");
					loadSound("back1", "/res/sounds/background/back1.wav");
				} catch (Exception e) {
				}
				playSound("track2");
				getSong("track2").loop();
				setGeneralVolume(10);
				setVolume("hoverpop", 20);
				setVolume("play_click", 30);
			}

		};
		// l.create();
	}

	public static void spaceScene(MasterRenderer renderer, SkyboxRenderer skyboxRenderer) {
		Scene scene = new Scene("TimeMasters", renderer, skyboxRenderer) {

		};
		setMouseEnabled(false);
		TimeShip ship = new TimeShip(new Vector3f(), new Vector3f());
		ship.setControllable(true);
		FPS_Player player = new FPS_Player(new Vector3f(0, 0, 0));
		FreeCam freecam = new FreeCam();
		GameLoop.camera = player;
		new RenderItem() {
			boolean b = false;

			@Override
			public void render() {
				if (GamePerspective.currentState.getGameStateName().equalsIgnoreCase("ingame")) {
					if (GameLoop.camera == freecam) {
						freecam.updateInputs();
					} else {
						player.updateInputs();
					}
				}
				if (Keyboard.isKeyDown(GLFW.GLFW_KEY_F2)) {
					if (!b) {
						if (GameLoop.camera == freecam) {
							GameLoop.camera = player;
							b = true;
						} else {
							GameLoop.camera = freecam;
							b = true;
						}
					}
				} else {
					b = false;
				}
			}
		};

		SourceFile ame_nebula = new SourceFile("/res/skybox/space_1");
		Skybox skybox = new Skybox(new SourceFile[] { new SourceFile(ame_nebula, "face_right.png"),
				new SourceFile(ame_nebula, "face_left.png"), new SourceFile(ame_nebula, "face_bottom.png"),
				new SourceFile(ame_nebula, "face_top.png"), new SourceFile(ame_nebula, "face_back.png"),
				new SourceFile(ame_nebula, "face_front.png") }, 512);

		scene.skybox = skybox;

		Model_3D timemastersHQ = ModelMaster.getOBJModel("timemasters_hq_1");
		Model_3D timeship_1_inner = ModelMaster.getOBJModel("timeship_1_inner").setBackfaceCullingEnabled(true);
		Model_3D nazi = ModelMaster.getDFModel("nazi_1");

		Entity naziE = new Entity(nazi, new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 100);

		Entity ent = new Entity(timemastersHQ, new Vector3f(0, 0, 0), new Vector3f(0, 180 + 40, 0), 5);
		TimeShip timeship_1 = new TimeShip(new Vector3f(0, -20.5f, 10f), new Vector3f(0, 90, 0), 0.4f);
		TimeShip timeship_2 = new TimeShip(new Vector3f(0, -20.5f, -10f), new Vector3f(0, -90, 0), 0.4f);
		TimeShip timeship_3 = new TimeShip(new Vector3f(10f, -20.5f, 0), new Vector3f(0, 180, 0), 0.4f);
		ent.addChild(timeship_1);
		ent.addChild(timeship_2);
		ent.addChild(timeship_3);

		Entity inner = new Entity(timeship_1_inner, new Vector3f(0, 0, -0), new Vector3f(), 4);

		float uppderDeck_ymin = 12.5f;
		float uppderDeck_ymax = 13 - 0.1f;

		HitBox floor1 = new HitBox(35, -35, 0, -2, 40, -12);
		HitBox floor2 = new HitBox(23.5f, 3.5f + 0.5f, uppderDeck_ymax, uppderDeck_ymin, 40, -12);
		HitBox floor3 = new HitBox(-3.5f - 0.5f, -23.5f, uppderDeck_ymax, uppderDeck_ymin, -12, 40);
		HitBox floor4 = new HitBox(4.3f, -4.3f, uppderDeck_ymax, uppderDeck_ymin, -12, 10 - 1.3f);
		floor2.setRotation(new Vector3f(0, 1, 0));
		floor3.setRotation(new Vector3f(0, -1, 0));
		inner.addHitBox(floor1);
		inner.addHitBox(floor2);
		inner.addHitBox(floor3);
		inner.addHitBox(floor4);

		Light sun = new Light(new Vector3f(2000, 2000, 2000), new Vector3f(1, 1, 1));

		skybox.setRotationSpeed(20);
		scene.lights.add(sun);
		scene.entities.add(ent);
		scene.entities.add(naziE);
		scene.entities.add(ship);
		scene.entities.add(inner);
		setCurrentScene(scene);
	}

	@SuppressWarnings("unused")
	public static void startApplication(String title) {
		try {
			Settings.load();
			createDisplay(new DisplayBuilder(Settings.windowSizeValues[0], Settings.windowSizeValues[1]).setTitle(title)
					.setFullscreen(Settings.fullscreenValue).setVsync(Settings.vsyncValue).setSamples(8).setFpsCap(60));
			setClearColor(new Vector4f(0, 0.25f, 1, 1));
			setDepthTesting(true);

			MasterRenderer master = new MasterRenderer();
			SkyboxRenderer skyboxRenderer = new SkyboxRenderer();

			GameLoader.init();

			Fonts.addFont("pdark", new SourceFile("/res/fonts/pdark.png"), new SourceFile("/res/fonts/pdark.fnt"));

			DebugGui debug = new DebugGui();
			Log.init(debug);
			Log.append("log added to debug", false, new Vector3f(0.349019608f, 0, 1));
			debug.hide();

			spaceScene(master, skyboxRenderer);
			setCurrentScene(SceneLoader.getScene(master, skyboxRenderer, "test_scene"));

			master.setProjectionMatrix(camera.getProjectionMatrix());

			MainMenu main = new MainMenu(master, skyboxRenderer);
			SettingsMenu settings = new SettingsMenu(master, skyboxRenderer);
			CoopMenu coop = new CoopMenu(master, skyboxRenderer);

			new LoadingGui(1000) {

				@Override
				public void afterLoad() {
					GamePerspective.switchGameState("main_menu");
				}

				@Override
				public void midLoad() {
				}
			};

			FPS_HUD hud = new FPS_HUD();

			GamePerspective inGame = new GamePerspective("ingame") {

				@Override
				public void render() {
					renderScene(camera);
					hud.renderComponents();
				}

				@Override
				public void start() {
					setMouseEnabled(false);
				}

				@Override
				public void stop() {

				}
			};

			while (!Display.isCloseRequested() && !Display.hasToClose()) {
				glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

				renderItems();
				renderGameState();

				debug.update(camera);
				debug.renderComponents();

				swapBuffers();
				updateEvents();
			}

			Settings.save();
			cleanAll();
			disposeDisplay();

			if (l != null) {
				l.closeApp();
			}
		} catch (Exception e) {
			e.printStackTrace();
			l.closeApp();
		}

	}

}
