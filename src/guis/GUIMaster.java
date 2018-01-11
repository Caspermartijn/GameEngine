package guis;

import java.util.ArrayList;

public class GUIMaster {

	private static  ArrayList<GUI> guis = new ArrayList<>();
	
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
}

