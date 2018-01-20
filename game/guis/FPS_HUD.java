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

		Vector2f cornerSize = Maths.getFrom720toCurrentDisplaySize(new Vector2f(1280 * 0.1f, 720 * 0.065f));
		Vector2f cornerSize2 = Maths.getFrom720toCurrentDisplaySize(new Vector2f(1280 * 0.065f, 720 * 0.1f));
		left_corner.setRotation(-90);
		left_corner.setPosition(0.2f, 0.9f);
		left_corner.setSize(cornerSize.x, cornerSize.y);

		right_corner.setRotation(0);
		right_corner.setPosition(0.8f, 0.9f);
		right_corner.setSize(cornerSize2.x, cornerSize2.y);

		Vector2f leftMidPosition = new Vector2f(left_mid.getPosition().x - left_corner.getSizeOpenGL().x,
				left_mid.getPosition().y - left_corner.getSizeOpenGL().y);
		left_mid.setSize(new Vector2f(300, 300));
		left_mid.setPosition(leftMidPosition.x, leftMidPosition.y);
		System.out.println(left_mid.getPosition());
	}

}
