package guis;

import org.lwjgl.util.vector.Vector2f;

import utils.SourceFile;
import utils.maths.Maths;

public class FPS_HUD extends GUI {

	private SourceFile corner = new SourceFile("/res/guis/hud/hud_corner.png");
	private SourceFile mid = new SourceFile("/res/guis/hud/hud_mid.png");
	private SourceFile side = new SourceFile("/res/guis/hud/hud_side.png");

	private SourceFile side_right = new SourceFile("/res/guis/hud/hud_side_right.png");
	private SourceFile side_left = new SourceFile("/res/guis/hud/hud_side_left.png");

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

		ImageComponent left_side = new ImageComponent(this, side_right);
		ImageComponent right_side = new ImageComponent(this, side_left);

		Vector2f cornerSize = Maths.getFrom720toCurrentDisplaySize(new Vector2f(1280 * 0.1f, 720 * 0.065f));
		Vector2f cornerSize2 = Maths.getFrom720toCurrentDisplaySize(new Vector2f(1280 * 0.065f, 720 * 0.1f));
		left_corner.setRotation(-90);
		left_corner.setPosition(0.2f, 0.9f);
		left_corner.setSize(cornerSize.x, cornerSize.y);

		right_corner.setRotation(0);
		right_corner.setPosition(0.8f, 0.9f);
		right_corner.setSize(cornerSize2.x, cornerSize2.y);

		float a = 0.234375f / 2;
		float b = 0.41666666666f / 2;

		float midWidth = 325.3f;
		float midHeight = 342f;

		Vector2f leftMidPosition = new Vector2f(left_corner.getPosition().x, left_corner.getPosition().y);
		left_mid.setSize(new Vector2f(midWidth, midHeight));
		left_mid.setPosition(leftMidPosition.x - a, leftMidPosition.y + b);

		left_top.setSize(midWidth, cornerSize2.y);
		left_top.setPosition(leftMidPosition.x - a, leftMidPosition.y);

		left_side.setSize(cornerSize2.x, midHeight);
		left_side.setPosition(leftMidPosition.x, leftMidPosition.y + b);

		Vector2f rightMidPosition = new Vector2f(right_corner.getPosition().x, right_corner.getPosition().y);
		right_mid.setSize(new Vector2f(midWidth, midHeight));
		right_mid.setPosition(rightMidPosition.x + a, rightMidPosition.y + b);

		right_top.setSize(midWidth, cornerSize2.y);
		right_top.setPosition(rightMidPosition.x + a, rightMidPosition.y);

		right_side.setSize(cornerSize2.x, midHeight);
		right_side.setPosition(rightMidPosition.x, rightMidPosition.y + b);
	}

}
