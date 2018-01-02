package guis;

import java.util.ArrayList;

import renderer.quadRenderer.QuadRenderer;

public class GUIMaster {

	private static  ArrayList<GUI> guis = new ArrayList<>();
	private static QuadRenderer quadRenderer = new QuadRenderer();
	
	public static void init() {
		quadRenderer.init();
	}
	
	public static void cleanUp() {
		quadRenderer.cleanUp();
		guis.clear();
	}
	
	public static void removeGUI(GUI gui) {
		guis.remove(gui);
	}
	
	public static void addGUI(GUI gui) {
		guis.add(gui);
	}
	
	public static void renderAll() {
		for (GUI gui : guis) {
			gui.renderComponents();
		}
	}
	
	public static QuadRenderer getQuadRenderer() {
		return quadRenderer;
	}
}
