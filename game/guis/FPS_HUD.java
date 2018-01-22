package guis;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import scenes.Scene;
import utils.SourceFile;
import utils.maths.Maths;

public class FPS_HUD extends GUI {

	private SourceFile corner = new SourceFile("/res/guis/hud/hud_corner.png");
	private SourceFile mid = new SourceFile("/res/guis/hud/hud_mid.png");
	private SourceFile side = new SourceFile("/res/guis/hud/hud_side.png");

	private SourceFile side_right = new SourceFile("/res/guis/hud/hud_side_right.png");
	private SourceFile side_left = new SourceFile("/res/guis/hud/hud_side_left.png");

	private SourceFile ammo_icon = new SourceFile("/res/guis/hud/ammo_icon.png");
	private SourceFile armor_icon = new SourceFile("/res/guis/hud/armor_icon.png");
	private SourceFile health_icon = new SourceFile("/res/guis/hud/health_icon.png");
	private SourceFile objective_icon = new SourceFile("/res/guis/hud/objective_icon.png");

	public FPS_HUD() {
		images();
		texts();
	}

	private void texts() {
		float heightAdd = 0.03f;
		float heightAddRight = 0.0175f;
		ammo = new TextComponent(this, "200/100", "candara", 2.75f, 200, false);
		ammo.setPosition(0.8f - 0.005f, 0.89f + 0.0075f + heightAddRight);

		health = new TextComponent(this, "100", "candara", 1.5f, 200, false);
		health.setPosition(0.95f, 0.89f + heightAddRight);

		armor = new TextComponent(this, "100", "candara", 1.5f, 200, false);
		armor.setPosition(0.95f, 0.935f + heightAddRight);

		objective = new TextComponent(this, "Objective", "candara", 1.2f, 200, false);
		objective.setPosition(0.005f + 0.010f + 0.0065f, 0.855f + heightAdd);

		float height_add = .04f;

		objective_title = new TextComponent(this, "Objective", "candara", 1f, 200, false);
		objective_title.setPosition(0.005f, 0.855f + heightAdd + height_add);

		objective_abriefiation = new TextComponent(this, "Objective abriefiation", "candara", 0.75f, 200, false);
		objective_abriefiation.setPosition(0.005f, 0.885f + heightAdd + height_add);

		float color = 0.06f;

		ammo.setColor(color, color, color, 1);
		health.setColor(color, color, color, 1);
		armor.setColor(color, color, color, 1);
		objective.setColor(color, color, color, 1);
		objective_title.setColor(color, color, color, 1);
		objective_abriefiation.setColor(color, color, color, 1);

	}

	private TextComponent ammo;
	private TextComponent armor;
	private TextComponent health;

	private TextComponent objective;
	private TextComponent objective_title;
	private TextComponent objective_abriefiation;

	@SuppressWarnings("unused")
	private void images() {
		float heightAdd = 0.03f;
		float heightAddRight = 0.0175f;
		List<ImageComponent> comps = new ArrayList<ImageComponent>();
		ImageComponent left_corner = new ImageComponent(this, corner);
		ImageComponent right_corner = new ImageComponent(this, corner);

		ImageComponent left_mid = new ImageComponent(this, mid);
		ImageComponent right_mid = new ImageComponent(this, mid);

		ImageComponent left_top = new ImageComponent(this, side);
		ImageComponent right_top = new ImageComponent(this, side);

		ImageComponent left_side = new ImageComponent(this, side_right);
		ImageComponent right_side = new ImageComponent(this, side_left);

		ImageComponent armor_icon_comp = new ImageComponent(this, armor_icon);
		ImageComponent health_icon_comp = new ImageComponent(this, health_icon);
		ImageComponent ammo_icon_comp = new ImageComponent(this, ammo_icon);
		ImageComponent objective_icon_comp = new ImageComponent(this, objective_icon);

		ammo_icon_comp.setSize(Maths.getFrom720toCurrentDisplaySize(38, 38));
		armor_icon_comp.setSize(Maths.getFrom720toCurrentDisplaySize(22.66666f, 22.66666f));
		health_icon_comp.setSize(Maths.getFrom720toCurrentDisplaySize(20, 20));
		objective_icon_comp.setSize(Maths.getFrom720toCurrentDisplaySize(28.666f, 28.666f));

		ammo_icon_comp.setOverrideColor(new Vector3f(0.06f, 0.06f, 0.06f), new Vector3f(-0.1f, -0.1f, -0.1f),
				new Vector3f(0.1f, 0.1f, 0.1f));
		armor_icon_comp.setOverrideColor(new Vector3f(0.06f, 0.06f, 0.06f), new Vector3f(-0.1f, -0.1f, -0.1f),
				new Vector3f(0.1f, 0.1f, 0.1f));
		health_icon_comp.setOverrideColor(new Vector3f(0.06f, 0.06f, 0.06f), new Vector3f(-0.1f, -0.1f, -0.1f),
				new Vector3f(0.1f, 0.1f, 0.1f));

		armor_icon_comp.setPosition(0.95f - 0.01f, 0.935f + 0.0225f + heightAddRight);
		health_icon_comp.setPosition(0.95f - 0.01f, 0.89f + 0.02f + heightAddRight);
		ammo_icon_comp.setPosition(0.8f - 0.0175f, 0.89f + 0.046f + heightAddRight);

		objective_icon_comp.setPosition(0.005f + 0.008f, 0.855f + 0.003f + 0.013f + heightAdd);

		Vector2f cornerSize = Maths.getFrom720toCurrentDisplaySize(new Vector2f(1280 * 0.1f, 720 * 0.065f));
		Vector2f cornerSize2 = Maths.getFrom720toCurrentDisplaySize(new Vector2f(1280 * 0.065f, 720 * 0.1f));

		left_corner.setRotation(-90);
		left_corner.setPosition(0.2f, 0.9f + heightAdd);
		left_corner.setSize(cornerSize.x, cornerSize.y);
		comps.add(left_corner);

		right_corner.setRotation(0);
		right_corner.setPosition(0.8f, 0.9f + heightAdd);
		right_corner.setSize(cornerSize2.x, cornerSize2.y);
		comps.add(right_corner);

		float a = 0.234375f / 2;
		float b = 0.41666666666f / 2;

		Vector2f vec = Maths.getFrom1920toCurrentDisplaySize(325.3f, 342f);

		float midWidth = vec.x;
		float midHeight = vec.y;

		Vector2f leftMidPosition = new Vector2f(left_corner.getPosition().x, left_corner.getPosition().y);
		left_mid.setSize(new Vector2f(midWidth, midHeight));
		left_mid.setPosition(leftMidPosition.x - a, leftMidPosition.y + b);
		comps.add(left_mid);

		left_top.setSize(midWidth, cornerSize2.y);
		left_top.setPosition(leftMidPosition.x - a, leftMidPosition.y);
		comps.add(left_top);

		left_side.setSize(cornerSize2.x, midHeight);
		left_side.setPosition(leftMidPosition.x, leftMidPosition.y + b);
		comps.add(left_side);

		Vector2f rightMidPosition = new Vector2f(right_corner.getPosition().x, right_corner.getPosition().y);
		right_mid.setSize(new Vector2f(midWidth, midHeight));
		right_mid.setPosition(rightMidPosition.x + a, rightMidPosition.y + b);
		comps.add(right_mid);

		right_top.setSize(midWidth, cornerSize2.y);

		right_top.setPosition(rightMidPosition.x + a, rightMidPosition.y);

		comps.add(right_top);

		right_side.setSize(cornerSize2.x, midHeight);

		right_side.setPosition(rightMidPosition.x, rightMidPosition.y + b);

		comps.add(right_side);

		Vector3f blueoption = new Vector3f(0.290196078f, 0.49019607f, 0.807843f);
		Vector3f redoption = new Vector3f(237f / 255f, 87f / 255f, 61f / 255f);
		Vector3f greenoption = new Vector3f(141f / 255f, 252f / 255f, 141f / 255f);
		for (ImageComponent comp : comps) {
			comp.setOverrideColor(greenoption);
		}
	}

	@Override
	public void renderComponents() {
		if (Scene.getCurrentscene() != null) {
			objective_title.setText(Scene.getCurrentscene().getObjective()[0]);
			objective_abriefiation.setText(Scene.getCurrentscene().getObjective()[1]);
		}

		super.renderComponents();
	}

}
