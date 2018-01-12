package texts;

import java.util.ArrayList;

import renderer.textRendering.FontRenderer;

public class TextMaster {

	private static ArrayList<Text> texts = new ArrayList<>();
	private static FontRenderer renderer = new FontRenderer();
	
	public static void removeText(Text text) {
		texts.remove(text);
	} 
	
	public static void addText(Text text) {
		texts.add(text);
	}
	
	public static void cleanUp() {
		FontRenderer.cleanUp();
	}
	
	public static void renderAll() {
		for (Text t : texts) {
			FontRenderer.renderText(t);
		}
	}

	public static FontRenderer getRenderer() {
		return renderer;
	}

}
