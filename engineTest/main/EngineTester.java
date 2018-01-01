package main;

import org.joml.Vector2f;

import engine.Display;
import engine.DisplayBuilder;
import loader.modelLoader.ModelMaster;
import objects.Camera;
import renderer.textRendering.TextMaster;
import texts.Fonts;
import texts.Text;
import utils.SourceFile;

public class EngineTester {

	public static void main(String[] args) {
		Display.createDisplay(
				new DisplayBuilder(1280, 720).setTitle("testEngine").setFullscreen(false).setVsync(false));

		Camera cam = new Camera();

		Fonts.addFont("candara", new SourceFile("/res/candara.png"), new SourceFile("/res/candara.fnt"));

		Text testText = new Text("We are the best", 5, "candara", new Vector2f(0, 0), 10, false);
		testText.setColor(1, 1, 1);
		TextMaster.addText(testText);
		System.out.println("test");
		ModelMaster.loadModels("");

		while (!Display.isCloseRequested()) {
			TextMaster.renderAll();

			Display.update();
			Display.swapBuffers();
		}

		Display.disposeDisplay();
	}

}
