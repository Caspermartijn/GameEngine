package hitboxes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import hitbox.HBox;
import hitbox.HitBox;
import hitbox.HitBoxCircle;

public class HitBoxManager {

	public static HashMap<Integer, HBox> hitboxes = new HashMap<Integer, HBox>();
	public static boolean showHitBoxLines = true;

	public static float checkDistance = 0;

	public static void addHitBox(HBox box) {
		int id = hitboxes.size();
		box.setID(id);
		hitboxes.put(id, box);
		updateRange(box);
	}

	private static void updateRange(HBox box) {
		if (box instanceof HitBox) {
			HitBox hbox = (HitBox) box;
			float xmin = toPositive(hbox.getxMin());
			float xmax = toPositive(hbox.getxMax());
			float zmin = toPositive(hbox.getzMin());
			float zmax = toPositive(hbox.getzMax());
			float checkDistance = HitBoxManager.checkDistance / 2 - 1;
			if (checkDistance < xmin) {
				HitBoxManager.checkDistance = xmin * 2 + 1;
			}
			if (checkDistance < xmax) {
				HitBoxManager.checkDistance = xmax * 2 + 1;
			}
			if (checkDistance < zmin) {
				HitBoxManager.checkDistance = zmin * 2 + 1;
			}
			if (checkDistance < zmax) {
				HitBoxManager.checkDistance = zmax * 2 + 1;
			}
		} else if (box instanceof HitBoxCircle) {
			HitBoxCircle circle = (HitBoxCircle) box;
			if (checkDistance < circle.getDistance()) {
				checkDistance = circle.getDistance() * 2 + 1;
			}
		}
	}

	private static float toPositive(float f) {
		return (float) Math.sqrt(f * f);
	}

	public static Collection<HBox> getNearHitBoxes(HBox hitbox) {
		List<HBox> boxes = new ArrayList<HBox>();
		for (HBox box : hitboxes.values()) {
			if (distance(box.getPosition(), hitbox.getPosition()) < 10) {
				boxes.add(box);
			}
		}
		return boxes;
	}

	public static float distance(Vector3f position, Vector3f position2) {
		float xOff = position2.x - position.x;
		float zOff = position2.z - position.z;
		return (float) Math.sqrt((xOff * xOff) + (zOff * zOff));
	}

}
