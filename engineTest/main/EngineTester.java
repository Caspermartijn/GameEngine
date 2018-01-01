package main;

import org.lwjgl.util.vector.Vector2f;

import engine.Display;
import engine.DisplayBuilder;
import objects.Camera;
import texts.FontType;
import texts.Text;
import texts.rendering.TextMaster;
import utils.SourceFile;

public class EngineTester {

	public static void main(String[] args) {
		Display.createDisplay(
				new DisplayBuilder(1280, 720).setTitle("testEngine").setFullscreen(false).setVsync(false));

		Camera cam = new Camera();

		FontType type = new FontType(new SourceFile("/res/candara.png"), new SourceFile("/res/candara.fnt"));

		Text testText = new Text("Test Casper is amazing", 10, type, new Vector2f(-1, -1), 10, true);
		testText.setColor(1, 1, 1);
		TextMaster.addText(testText);

		while (!Display.isCloseRequested()) {
			TextMaster.renderAll();

			Display.update();
			Display.swapBuffers();
		}

		Display.disposeDisplay();
	}

}
