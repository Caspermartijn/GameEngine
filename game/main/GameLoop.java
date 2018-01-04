package main;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import controlls.FPSCamera;
import engine.Display;
import engine.DisplayBuilder;
import engine.GLSettings;
import engine.Mouse;
import entities.Entity;
import entities.Light;
import entities.TimeShip;
import launcher.Launcher;
import loader.modelLoader.ModelMaster;
import objects.Camera;
import objects.Model_3D;
import objects.Skybox;
import objects.Vao;
import renderer.MasterRenderer;
import renderer.skyboxRenderer.SkyboxRenderer;
import renderer.textRendering.TextMaster;
import scenes.Scene;
import shaders.shaderObjects.ShaderProgram;
import texts.Fonts;
import texts.Text;
import textures.Texture;
import utils.ModelLoader;
import utils.SourceFile;

public class GameLoop {

	public static Launcher l;

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

		};
		l.create();
	}

	public static Scene currentScene;

	private static Camera camera;

	public static void exampleScene(MasterRenderer renderer, SkyboxRenderer skyboxRenderer) {
		Scene scene = new Scene("example", renderer, skyboxRenderer) {

		};
		SourceFile ame_nebula = new SourceFile("/res/skybox/space_1");
		Skybox skybox = new Skybox(new SourceFile[] { new SourceFile(ame_nebula, "face_right.png"),
				new SourceFile(ame_nebula, "face_left.png"), new SourceFile(ame_nebula, "face_bottom.png"),
				new SourceFile(ame_nebula, "face_top.png"), new SourceFile(ame_nebula, "face_back.png"),
				new SourceFile(ame_nebula, "face_front.png") }, 512);
		scene.skybox = skybox;
		Model_3D timemastersHQ = ModelLoader.getModel(new SourceFile("/res/models/timemaster_HQ_1/model.obj"),
				new SourceFile("/res/models/timemaster_HQ_1/texture.png"));
		Entity ent = new Entity(timemastersHQ, new Vector3f(0, 0, 0), new Vector3f(0, 180 + 40, 0), 20);
		Light sun = new Light(new Vector3f(2000, 2000, 2000), new Vector3f(1, 1, 1));

		TimeShip ship = new TimeShip(new Vector3f(), new Vector3f());
		ship.setControllable(true);
		GameLoop.camera = ship.getCamera();
		camera.z = 10f;

		skybox.setRotationSpeed(20);
		scene.lights.add(sun);
		scene.entities.add(ent);
		scene.entities.add(ship);
		currentScene = scene;
	}

	public static void startGame() {
		Display.createDisplay(new DisplayBuilder(1920, 1080).setTitle("testEngine").setFullscreen(true).setVsync(false)
				.setSamples(8));
		Mouse.setMouseEnabled(false);

		GLSettings.setClearColor(new Vector4f(0, 0.25f, 1, 1));
		GLSettings.setDepthTesting(true);

		// FPSCamera camera = new FPSCamera();

		SkyboxRenderer skyboxRenderer = new SkyboxRenderer();
		MasterRenderer master = new MasterRenderer();
		exampleScene(master, skyboxRenderer);
		master.setProjectionMatrix(camera.getProjectionMatrix());

		Fonts.addFont("candara", new SourceFile("/res/candara.png"), new SourceFile("/res/candara.fnt"));

		ModelMaster.loadModels("");

		Text testText = new Text("", 5, "candara", new Vector2f(0, 0), 10, false);
		testText.setColor(1, 1, 1);
		TextMaster.addText(testText);

		while (!Display.isCloseRequested()) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

			currentScene.render(camera);

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
	}

}
