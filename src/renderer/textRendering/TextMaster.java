package renderer.textRendering;

import java.util.ArrayList;
import java.util.Collection;

import texts.Text;
import utils.tasks.Cleanup;

public class TextMaster {

	private static ArrayList<Text> texts = new ArrayList<>();
	private static FontRenderer renderer = new FontRenderer();

	private static Cleanup cleanup;

	public static void removeText(Text text) {
		texts.remove(text);
	}

	public static void addText(Text text) {
		texts.add(text);
		if (TextMaster.cleanup == null) {
			TextMaster.cleanup = new Cleanup() {

				@Override
				public void delete() {
					TextMaster.delete();
				}

			};
		}
	}

	public static void renderTexts(Collection<Text> textss) {
		for (Text t : textss) {
			renderer.renderText(t); 
		}
	}
	
	public static void delete() {
		renderer.cleanUp();
	}

	public static void renderAll() {
		for (Text t : texts) {
			renderer.renderText(t); 
		}
	}

	public static FontRenderer getRenderer() { 
		return renderer;
	}

	public static void renderAllTexts() {
		renderAll();
	}

}
