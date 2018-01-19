package guis;

import utils.SourceFile;

public class FPS_HUD extends GUI {

	private SourceFile corner = new SourceFile("/res/guis/hud/hud_corner.png");
	private SourceFile mid = new SourceFile("/res/guis/hud/hud_mid.png");
	private SourceFile side = new SourceFile("/res/guis/hud/hud_side.png");

	public FPS_HUD() {

		images();
		
	}

	private void images() {
		ImageComponent left_corner = new ImageComponent(this, corner);
		ImageComponent right_corner = new ImageComponent(this, corner);
		
	}

}
