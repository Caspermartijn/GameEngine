package main;

import java.util.ArrayList;
import java.util.List;

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
import entities.TimeCube;
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
import shaders.shaderObjects.ShaderProgram;
import texts.Fonts;
import texts.Text;
import textures.Texture;
import utils.ModelLoader;
import utils.SourceFile;

public class GameLoop {

	public static Launcher l;

	public static void main(String[] args) {
		l = new Launcher("test") {

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

		};
		l.create();
	}

	public static void startGame() {
		Display.createDisplay(
				new DisplayBuilder(1920, 1080).setTitle("testEngine").setFullscreen(true).setVsync(false).setSamples(8));
		Mouse.setMouseEnabled(false);
		
		 FPSCamera camera = new FPSCamera();
		SourceFile ame_nebula = new SourceFile("/res/skybox/space_1");
		Skybox skybox = new Skybox(new SourceFile[] { new SourceFile(ame_nebula, "face_right.png"),
				new SourceFile(ame_nebula, "face_left.png"), new SourceFile(ame_nebula, "face_bottom.png"),
				new SourceFile(ame_nebula, "face_top.png"), new SourceFile(ame_nebula, "face_back.png"),
				new SourceFile(ame_nebula, "face_front.png") }, 512);
		SkyboxRenderer skyboxRenderer = new SkyboxRenderer();
		skyboxRenderer.init();

		GLSettings.setClearColor(new Vector4f(0, 0.25f, 1, 1));
		GLSettings.setDepthTesting(true);

		Fonts.addFont("candara", new SourceFile("/res/candara.png"), new SourceFile("/res/candara.fnt"));

		Text testText = new Text("", 5, "candara", new Vector2f(0, 0), 10, false);
		testText.setColor(1, 1, 1);
		TextMaster.addText(testText);
		System.out.println("test");
		ModelMaster.loadModels("");

		Model_3D testmdl = ModelLoader.getModel(new SourceFile("/res/models/timemaster_hq_1/model.obj"),
				new SourceFile("/res/models/timemaster_hq_1/texture.png"));

		TimeShip ship = new TimeShip(new Vector3f(), new Vector3f());
		ship.setControllable(false);
		camera.z = 10f;

		MasterRenderer master = new MasterRenderer();
		master.setProjectionMatrix(camera.getProjectionMatrix());

		Entity ent = new Entity(testmdl, new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 10) {
		};

		Light sun = new Light(new Vector3f(2000, 2000, 2000), new Vector3f(1, 1, 1));

		skybox.setRotationSpeed(20);
		List<Light> lights = new ArrayList<Light>();
		lights.add(sun);
		Game.entities.add(ent);
	//	Game.entities.add(ship);

		/*
		TimeCube timeCube_1 = new TimeCube(ModelLoader.getModel(new SourceFile("/res/models/timecube_1/model.obj"),
				new SourceFile("/res/models/timecube_1/texture.png")), new Vector3f(0,0,-1.5f), new Vector3f(), 0.6f);
		
		TimeCube timeCube_2 = new TimeCube(ModelLoader.getModel(new SourceFile("/res/models/timecube_1/model.obj"),
				new SourceFile("/res/models/timecube_1/texture.png")), new Vector3f(0,0,-1.5f), new Vector3f(), 0.6f);
		
		ship.addChild(timeCube_1);ship.addChild(timeCube_2);
		Game.entities.add(timeCube_1);
		Game.entities.add(timeCube_2);*/
		while (!Display.isCloseRequested()) {
			Display.update();
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			Game.update();
			
			camera.updateInputs();
			ship.update();

			skyboxRenderer.render(skybox, camera);
			master.render(camera, sun, Game.entities);
			master.unprepare();

			TextMaster.renderAll();

			Display.swapBuffers();
		}

		testText.delete();
		TextMaster.cleanUp();
		Fonts.delete();
		Display.disposeDisplay();
		skyboxRenderer.delete();
		ent.delete();
		ship.delete();
		master.delete();

		Vao.printLog();
		ShaderProgram.printLog();
		Texture.printLog();

		l.closeApp();
	}

}
