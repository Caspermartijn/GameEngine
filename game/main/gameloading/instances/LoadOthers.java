package main.gameloading.instances;

import debug.DefaultCommands;
import main.gameloading.LoadItemMedium;

public class LoadOthers extends LoadItemMedium {

	@Override
	public void load() {
		DefaultCommands.compile();		
	}

}
