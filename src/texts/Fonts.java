package texts;

import java.util.HashMap;

import utils.SourceFile;

public class Fonts {

	private static HashMap<String, FontType> fonts = new HashMap<>();

	public static void addFont(String name, SourceFile texture, SourceFile fontFile) {
		fonts.put(name, new FontType(texture, fontFile));
	}

	public static void addFont(String name, SourceFile texture, SourceFile fontFile, float width, float smoothness) {
		fonts.put(name, new FontType(texture, fontFile, width, smoothness));
	}

	public static FontType getFont(String name) {
		if (fonts.get(name) != null) {
			return fonts.get(name);
		} else {
			Fonts.addFont("candara", new SourceFile("/engine/res/candara.png"), new SourceFile("/engine/res/candara.fnt"));
			return fonts.get("candara");
		}
	}

	public static void delete() {
		for (FontType f : fonts.values()) {
			f.delete();
		}
	}

}
