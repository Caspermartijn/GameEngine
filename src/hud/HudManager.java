package hud;

import java.util.ArrayList;
import java.util.List;

public class HudManager {

	private static List<HudTexture> hudstex = new ArrayList<HudTexture>();
	private static List<Hud> huds = new ArrayList<Hud>();

	public static void addHUDTEX(HudTexture hud) {
		hudstex.add(hud);
	}

	public static void removeHUDTEX(HudTexture hud) {
		hudstex.remove(hud);
	}

	public static void addHUD(Hud hud) {
		huds.add(hud);
	}

	public static void removeHUD(Hud hud) {
		huds.remove(hud);
	}

	public static List<HudTexture> getAllHudTextures() {
		return hudstex;
	}

	public static List<Hud> getAllHUDS() {
		return huds;
	}

}
