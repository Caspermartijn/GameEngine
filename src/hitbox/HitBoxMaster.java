package hitbox;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import utils.maths.Maths;

public class HitBoxMaster {

	public static List<HBox> hitBoxes = new ArrayList<HBox>();
	public static boolean renderHitBoxes = false;

	public static boolean isInBox(HBox hitbox) {
		boolean inbox = false;
		if (true) {
			for (HBox box : getNearHitBoxes(hitbox)) {
				if (!inbox) {
					inbox = HitBoxMaths.isColliding(hitbox, box);
					if (inbox) {
						System.out.println("tests");
					}
				}
			}
		}
		return inbox;
	}

	public static Collection<HBox> getNearHitBoxes(HBox hitbox) {
		List<HBox> boxes = new ArrayList<HBox>();
		for (HBox box : hitBoxes) {
			if (Maths.distance(box.getPosition(), hitbox.getPosition()) < 20) {
				boxes.add(box); 
			}
		}
		return boxes;
	}

}
