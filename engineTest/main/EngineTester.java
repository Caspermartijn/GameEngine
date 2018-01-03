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
import entities.Entity;
import entities.Light;
import launcher.Launcher;
import loader.modelLoader.ModelMaster;
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

public class EngineTester {

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

			}

		};
		l.create();
	}

	public static void startGame() {
		Display.createDisplay(
				new DisplayBuilder(1280, 720).setTitle("testEngine").setFullscreen(false).setVsync(false));

		FPSCamera camera = new FPSCamera();
		SourceFile ame_nebula = new SourceFile("/res/skybox/ame_nebula");
		Skybox skybox = new Skybox(
				new SourceFile[] { new SourceFile(ame_nebula, "RIGHT.png"), new SourceFile(ame_nebula, "LEFT.png"),
						new SourceFile(ame_nebula, "BOTTOM.png"), new SourceFile(ame_nebula, "TOP.png"),
						new SourceFile(ame_nebula, "BACK.png"), new SourceFile(ame_nebula, "FRONT.png") },
				512);
		SkyboxRenderer skyboxRenderer = new SkyboxRenderer();
		skyboxRenderer.init();

		GLSettings.setClearColor(new Vector4f(0, 0.25f, 1, 1));
		GLSettings.setDepthTesting(true);

		Fonts.addFont("candara", new SourceFile("/res/candara.png"), new SourceFile("/res/candara.fnt"));

		Text testText = new Text("We are the best", 5, "candara", new Vector2f(0, 0), 10, false);
		testText.setColor(1, 1, 1);
		TextMaster.addText(testText);
		System.out.println("test");
		ModelMaster.loadModels("");

		Model_3D testmdl = ModelLoader.getModel(new SourceFile("/res/models/human_1/model.obj"),
				new SourceFile("/res/models/timeship_1/texture.png"));

		MasterRenderer master = new MasterRenderer();
		master.setProjectionMatrix(camera.getProjectionMatrix());

		Entity ent = new Entity(testmdl, new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 20) {
		};

		Light sun = new Light(new Vector3f(2000, 2000, 2000), new Vector3f(1, 1, 1));

		// camera.z = -10;
		skybox.setRotationSpeed(20);
		List<Light> lights = new ArrayList<Light>();
		lights.add(sun);
		List<Entity> entities = new ArrayList<Entity>();
		entities.add(ent);
		while (!Display.isCloseRequested()) {
			Display.update();
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

			System.out.println(camera.x + " " + camera.y + " " + camera.z);

			camera.updateInputs();
			
			skyboxRenderer.render(skybox, camera);
			master.render(camera, sun, entities);
			
			master.unprepare();
			
			TextMaster.renderAll();

			Display.swapBuffers();
		}

		testText.delete();
		TextMaster.cleanUp();
		Fonts.delete();
		Display.disposeDisplay();
		skyboxRenderer.delete();
		testmdl.delete();
		master.delete();

		Vao.printLog();
		ShaderProgram.printLog();
		Texture.printLog();

		l.closeApp();
	}

}
