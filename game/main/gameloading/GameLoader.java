package main.gameloading;

import java.util.ArrayList;
import java.util.List;

import debug.DefaultCommands;

public class GameLoader {

	private static List<LoadItem> loadItems = new ArrayList<LoadItem>();

	static List<LoadItemHigh> high = new ArrayList<LoadItemHigh>();
	static List<LoadItemLow> low = new ArrayList<LoadItemLow>();
	static List<LoadItemMedium> medium = new ArrayList<LoadItemMedium>();

	public static void init() {
		DefaultCommands.compile();
		
		sort();
		load();
	}

	private static void load() {
		if (true) {
			for (LoadItemHigh item : high) {
				item.load();
			}
			for (LoadItemMedium item : medium) {
				item.load();
			}
			for (LoadItemLow item : low) {
				item.load();
			}
		}
	}

	private static void sort() {
		if (true) {
			for (LoadItem item : loadItems) {
				switch (item.getImportance()) {
				case High:
					high.add((LoadItemHigh) item);
					break;
				case Low:
					low.add((LoadItemLow) item);
					break;
				case Medium:
					medium.add((LoadItemMedium) item);
					break;
				}
			}
		}
	}

}
