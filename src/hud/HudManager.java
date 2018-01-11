package hud;

import java.util.ArrayList;
import java.util.List;

public class HudManager {

	private static List<HudTexture> huds = new ArrayList<HudTexture>();

	public static void addHUD(HudTexture hud) {
		huds.add(hud);
	}
	
	public static void removeHUD(HudTexture hud) {
		huds.remove(hud);
	}

	public static List<HudTexture> getAllHUDS() {
		return huds;
	}
	
}
