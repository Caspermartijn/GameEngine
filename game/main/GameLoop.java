package main;

import static org.lwjgl.opengl.GL11.glClear;
import static utils.tasks.Cleanup.cleanAll;

import static scenes.Scene.renderScene;
import static scenes.Scene.setCurrentScene;
import static renderer.textRendering.TextMaster.renderAllTexts;
import static renderer.hudRenderer.HudRenderer.renderAllHuds;
import static renderer.hudRenderer.HudRenderer.renderAllHudTextures;
import static utils.RenderItem.renderItems;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static engine.GLSettings.*;
import static engine.Display.*;
import static audio.Sound2DMaster.*;
import static engine.Mouse.*;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import controlls.FreeCam;
import engine.Display;
import engine.DisplayBuilder;
import entities.Entity;
import entities.Light;
import entities.TimeShip;
import hitbox.HitBox;
import hud.Hud;
import hud.HudManager;
import hud.HudTexture;
import launcher.Launcher;
import log.Log;
import objects.Camera;
import objects.FPS_Player;
import objects.Model_3D;
import objects.Skybox;
import renderer.MasterRenderer;
import renderer.skyboxRenderer.SkyboxRenderer;
import scenes.Scene;
import texts.Text;
import textures.Texture;
import utils.RenderItem;
import utils.SourceFile;
import utils.maths.Maths;
import utils.models.ModelMaster;

public class GameLoop {

	public static Launcher l;
	private static Camera camera;

	public static void main(String[] args) {
		String title = "I am the best of the 2 of us. (Except in water and animation)";
		l = new Launcher(title) {

			private static final long serialVersionUID = 001L;

			@Override
			public void play() {
				startGame(this.getTitle());
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
		l.create();
	}

	public static void spaceScene(MasterRenderer renderer, SkyboxRenderer skyboxRenderer) {
		Scene scene = new Scene("TimeMasters", renderer, skyboxRenderer) {

		};
		setMouseEnabled(false);
		TimeShip ship = new TimeShip(new Vector3f(), new Vector3f());
		ship.setControllable(false);
		HitBox playerHitBox = new HitBox(-3, 3, 0, 7, -3, 3);
		FPS_Player player = new FPS_Player(ModelMaster.getModel("timecube_1"), new Vector3f(2000, 100, 2000),
				new Vector3f(), playerHitBox, 0);
		FreeCam freecam = new FreeCam();
		GameLoop.camera = freecam;
		new RenderItem() {

			@Override
			public void render() {
				freecam.updateInputs();
			}
		};
		player.getTransform().setPosition(new Vector3f(0, 100, 0));

		SourceFile ame_nebula = new SourceFile("/res/skybox/space_1");
		Skybox skybox = new Skybox(new SourceFile[] { new SourceFile(ame_nebula, "face_right.png"),
				new SourceFile(ame_nebula, "face_left.png"), new SourceFile(ame_nebula, "face_bottom.png"),
				new SourceFile(ame_nebula, "face_top.png"), new SourceFile(ame_nebula, "face_back.png"),
				new SourceFile(ame_nebula, "face_front.png") }, 512);

		scene.skybox = skybox;

		Model_3D timemastersHQ = ModelMaster.getModel("timemasters_hq_1");
		Model_3D timeship_1_inner = ModelMaster.getModel("timeship_1_inner").setBackfaceCullingEnabled(true);

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
		scene.entities.add(ship);
		scene.entities.add(inner);
		setCurrentScene(scene);
	}

	public static void startGame(String title) {
		try {
			createDisplay(new DisplayBuilder(1280, 720).setTitle(title).setFullscreen(false).setVsync(false)
					.setSamples(8).setFpsCap(60));

			Log.init();
			Log.append("log initialized", new Vector3f(0.349019608f, 0, 1));
			setMouseEnabled(true);

			setClearColor(new Vector4f(0, 0.25f, 1, 1));
			setDepthTesting(true);

			SkyboxRenderer skyboxRenderer = new SkyboxRenderer();
			MasterRenderer master = new MasterRenderer();

			spaceScene(master, skyboxRenderer);

			master.setProjectionMatrix(camera.getProjectionMatrix());
			Texture sideTex = Texture.getTextureBuilder(new SourceFile("/res/guis/hud/hud_side.png")).create();
			Vector2f scale = Maths.getNormalizedSize(1280, 200);
			HudTexture hud = new HudTexture(sideTex, new Vector2f(0, (1 - scale.y) + 0.075f), scale);
			hud.setRotation(180);

			Text fps = new Text(Display.getFPS() + "", 0.78f, "candara", new Vector2f(0, 0), 10, false);
			Text delta = new Text("delta: ", 0.78f, "candara", new Vector2f(0, 0.02f), 10, false);
			Text ping = new Text("ping: ", 0.78f, "candara", new Vector2f(0, 0.04f), 10, false);

			fps.setColor(0, 0.25f, 1);
			delta.setColor(0, 0.25f, 1);
			ping.setColor(0, 0.25f, 1);

			Hud test_hud = new Hud(1f, new Vector2f()) {
				public void init() {
					addHud(hud);
					addText(fps);
					addText(delta);
					addText(ping);
					for (Text text : Log.logTexts) {
						addText(text);
						text.applyChanges();
					}
				}
			};

			while (!Display.isCloseRequested()) {
				glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

				updateData(fps, delta, ping);

				renderItems();

				renderScene(camera);

				renderAllHuds(test_hud);

				renderAllHudTextures(HudManager.getAllHudTextures());

				renderAllTexts();

				swapBuffers();
				updateEvents();

			}

			cleanAll();
			disposeDisplay();

			l.closeApp();
		} catch (Exception e) {
			e.printStackTrace();
			l.closeApp();
		}

	}

	private static void updateData(Text fps, Text delta, Text ping) {
		fps.setText("fps: " + Display.getFPS());
		fps.applyChanges();

		delta.setText("delta: " + Display.getDelta());
		delta.applyChanges();

		ping.setText("ping: ");
		ping.applyChanges();
	}

}
