package guis;

import org.lwjgl.util.vector.Vector2f;

import utils.SourceFile;
import utils.maths.Maths;

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

		ImageComponent left_mid = new ImageComponent(this, mid);
		ImageComponent right_mid = new ImageComponent(this, mid);

		ImageComponent left_top = new ImageComponent(this, side);
		ImageComponent right_top = new ImageComponent(this, side);

		ImageComponent left_side = new ImageComponent(this, side);
		ImageComponent right_side = new ImageComponent(this, side);
		
		Vector2f cornerSize = Maths.getFrom720toCurrentDisplaySize(Maths.getSqaireSize(200));
		System.out.println(cornerSize);
		left_corner.setRotation(-90);
		left_corner.setPosition(0.9f, 0.9f);
		left_corner.setSize(cornerSize.x, cornerSize.y);
	}

}
