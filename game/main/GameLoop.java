package main;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import audio.Sound2DMaster;
import controlls.FreeCam;
import engine.Display;
import engine.DisplayBuilder;
import engine.GLSettings;
import engine.Mouse;
import entities.Entity;
import entities.Light;
import entities.TimeShip;
import hitbox.HitBox;
import launcher.Launcher;
import objects.Camera;
import objects.FPS_Player;
import objects.Model_3D;
import objects.Skybox;
import objects.Vao;
import renderer.MasterRenderer;
import renderer.skyboxRenderer.SkyboxRenderer;
import renderer.textRendering.TextMaster;
import scenes.Scene;
import shaders.ShaderProgram;
import texts.Fonts;
import texts.Text;
import textures.Texture;
import utils.RenderItem;
import utils.SourceFile;
import utils.models.ModelMaster;

public class GameLoop {

	public static Launcher l;
	private static Camera camera;

	public static void main(String[] args) {
		l = new Launcher("testEngine") {

			private static final long serialVersionUID = 001L;

			@Override
			public void play() {
				startGame();
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
					Sound2DMaster.loadSound("track1", "/res/sounds/songs/film_guitar_theme_music.wav");
					Sound2DMaster.loadSound("track2", "/res/sounds/songs/that_feeling.wav");
					Sound2DMaster.loadSound("hoverpop", "/res/sounds/launcher/hoverpop.wav");
					Sound2DMaster.loadSound("play_click", "/res/sounds/launcher/play_click.wav");
					Sound2DMaster.loadSound("back1", "/res/sounds/background/back1.wav");
				} catch (Exception e) {
				}
				Sound2DMaster.play("track2");
				Sound2DMaster.getSong("track2").loop();
				Sound2DMaster.setGeneralVolume(10);
				Sound2DMaster.setVolume("hoverpop", 20);
				Sound2DMaster.setVolume("play_click", 30);
			}

		};
		l.create();
	}

	public static void spaceScene(MasterRenderer renderer, SkyboxRenderer skyboxRenderer) {
		Scene scene = new Scene("example", renderer, skyboxRenderer) {

		};
		Mouse.setMouseEnabled(false);
		TimeShip ship = new TimeShip(new Vector3f(), new Vector3f());
		ship.setControllable(false);
		HitBox playerHitBox = new HitBox(-3, 3, 0, 7, -3, 3);
		FPS_Player player = new FPS_Player(ModelMaster.getModel("timecube_1"), new Vector3f(2000, 100, 2000),
				new Vector3f(), playerHitBox, 0);
		GameLoop.camera = player.getCamera();
		new RenderItem() {

			@Override
			public void render() {
				player.updateInputs();
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
		Scene.setCurrentScene(scene);
	}

	public static void startGame() {
		try {
			Display.createDisplay(new DisplayBuilder(1280, 720).setTitle("GameEngine").setFullscreen(false)
					.setVsync(false).setSamples(8).setFpsCap(60));
			Mouse.setMouseEnabled(true);

			GLSettings.setClearColor(new Vector4f(0, 0.25f, 1, 1));
			GLSettings.setDepthTesting(true);

			SkyboxRenderer skyboxRenderer = new SkyboxRenderer();
			MasterRenderer master = new MasterRenderer();

			spaceScene(master, skyboxRenderer);

			master.setProjectionMatrix(camera.getProjectionMatrix());

			Text testText = new Text("", 5, "candara", new Vector2f(0, 0), 10, false);
			testText.setColor(1, 1, 1);
			TextMaster.addText(testText);

			while (!Display.isCloseRequested()) {
				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

				RenderItem.renderItems();

				Scene.renderScene(camera);

				TextMaster.renderAll();

				Display.swapBuffers();
				Display.updateEvents();
			}
			testText.delete();
			TextMaster.cleanUp();
			Fonts.delete();
			Display.disposeDisplay();
			skyboxRenderer.delete();
			master.delete();
			Vao.printLog();
			ShaderProgram.printLog();
			Texture.printLog();
			l.closeApp();
		} catch (Exception e) {
			e.printStackTrace();
			l.closeApp();
		}

	}

}
