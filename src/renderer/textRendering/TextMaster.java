package renderer.textRendering;

import java.util.ArrayList;

import texts.Text;

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

}
