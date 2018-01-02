package main;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;

import controlls.FPSCamera;
import engine.Display;
import engine.DisplayBuilder;
import engine.GLSettings;
import loader.modelLoader.ModelMaster;
import objects.Skybox;
import objects.Vao;
import renderer.skyboxRenderer.SkyboxRenderer;
import renderer.textRendering.TextMaster;
import shaders.shaderObjects.ShaderProgram;
import texts.Fonts;
import texts.Text;
import textures.Texture;
import utils.SourceFile;

public class EngineTester {

	public static void main(String[] args) {
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

		while (!Display.isCloseRequested()) {
			Display.update();
		
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			
			camera.updateInputs();
			
			skyboxRenderer.render(skybox, camera);
			
			TextMaster.renderAll();
			
			Display.swapBuffers();
		}

		testText.delete();
		TextMaster.cleanUp();
		Fonts.delete();
		Display.disposeDisplay();
		skyboxRenderer.delete();
		
		Vao.printLog();
		ShaderProgram.printLog();
		Texture.printLog();
	}

}
