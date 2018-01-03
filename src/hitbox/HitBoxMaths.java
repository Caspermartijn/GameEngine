package hitbox;

import org.lwjgl.util.vector.Vector3f;

public class HitBoxMaths {

	public static boolean isColliding(HBox box1, HBox box2) {
		boolean isBox = false;
		if (box1 instanceof HitBox && box2 instanceof HitBox) {
			isBox = isCollidingSquare((HitBox) box1, (HitBox) box2);
		}
		if ((box1 instanceof HitBox && box2 instanceof HitBoxCircle)) {
			isBox = isCollidingCSHybrid(box1, box2);
		}
		if ((box2 instanceof HitBox && box1 instanceof HitBoxCircle)) {
			isBox = isCollidingCSHybrid(box1, box2);
		}
		if (box1 instanceof HitBoxCircle && box2 instanceof HitBoxCircle) {
			isBox = isCollidingCircle((HitBoxCircle) box1, (HitBoxCircle) box2);
		}
		return isBox;
	}

	private static boolean isCollidingCSHybrid(HBox box1, HBox box2) {
		HBox[] boxes = new HBox[2];
		HitBoxCircle circular = null;
		HitBox squaire = null;
		if (box1 instanceof HitBoxCircle) {
			circular = (HitBoxCircle) box1;
		} else {
			squaire = (HitBox) box1;
		}
		if (box2 instanceof HitBoxCircle) {
			circular = (HitBoxCircle) box2;
		} else {
			squaire = (HitBox) box2;
		}
		boxes[0] = squaire;
		boxes[1] = circular;
		return isColliding(boxes);
	}

	private static boolean isCollidingCircle(HitBoxCircle box1, HitBoxCircle box2) {
		HitBoxCircle[] boxes = { (HitBoxCircle) box1, (HitBoxCircle) box2 };
		return isColliding(boxes);
	}

	private static boolean isColliding(HBox[] boxes) {
		HitBoxCircle circular = (HitBoxCircle) boxes[1];
		HitBox squaire = (HitBox) boxes[0];
		boolean isBox = false;

		isBox = tier1Check(circular, squaire);

		if (!isBox) {
			isBox = tier2Check(circular, squaire);
		}
		return isBox;
	}

	private static boolean tier2Check(HitBoxCircle circular, HitBox squaire) {
		boolean isBox = false;
		float[] dat = rotate(squaire.getPosition().x, squaire.getPosition().z, circular.getPosition().x,
				circular.getPosition().z, squaire.getRotation().y, circular.getRotation().y);
		Vector3f checkedPos = new Vector3f(dat[0], circular.getPosition().y, dat[1]);
		float bX = checkedPos.x;
		float bZ = checkedPos.z;
		float aX = squaire.getPosition().x;
		float aZ = squaire.getPosition().z;
		float aXmin = aX + squaire.getxMin();
		float aXmax = aX + squaire.getxMax();
		float aZmin = aZ + squaire.getzMin();
		float aZmax = aZ + squaire.getzMax();
		if (bX >= aXmin && bX <= aXmax) {
			if (bZ >= aZ) {
				float tot_off = circular.getDistance() + aZmax;
				float abrange = aZ - bZ;
				double norm = Math.sqrt(abrange * abrange);
				if (norm <= tot_off) {
					isBox = true;
				}
			}
			if (!isBox) {
				if (bZ < aZ) {
					float tot_off = circular.getDistance() + -aZmin;
					float abrange = aZ - bZ;
					double norm = Math.sqrt(abrange * abrange);
					if (norm <= tot_off) {
						isBox = true;
					}
				}
			}
		}

		if (!isBox) {
			if (bZ >= aZmin && bZ <= aZmax) {
				if (bX >= aX) {
					float tot_off = circular.getDistance() + aXmax;
					float abrange = aX - bX;
					double norm = Math.sqrt(abrange * abrange);
					if (norm <= tot_off) {
						isBox = true;
					}
				}
				if (!isBox) {
					if (bX < aX) {
						float tot_off = circular.getDistance() + -aXmin;
						float abrange = aX - bX;
						double norm = Math.sqrt(abrange * abrange);
						if (norm <= tot_off) {
							isBox = true;
						}
					}
				}
			}
		}
		return isBox;
	}

	private static boolean tier1Check(HitBoxCircle circular, HitBox squaire) {
		boolean isBox = false;
		float[] dat = rotate(squaire.getPosition().x, squaire.getPosition().z, circular.getPosition().x,
				circular.getPosition().z, squaire.getRotation().y, circular.getRotation().y);
		Vector3f checkedPos = new Vector3f(dat[0], circular.getPosition().y, dat[1]);
		Vector3f[] corners = generateCorners(squaire, 0);
		if (true) {
			for (Vector3f vec : corners) {
				float x = squaire.getPosition().x + vec.x;
				float z = squaire.getPosition().z + vec.z;
				float xOf = x - checkedPos.x;
				float zOf = z - checkedPos.z;
				float dis = (float) Math.sqrt(xOf * xOf + zOf * zOf);
				if (dis < circular.getDistance()) {
					isBox = true;
				}
			}
		}
		return isBox;
	}

	private static boolean isColliding(HitBoxCircle[] boxes) {
		HitBoxCircle checker = boxes[0];
		HitBoxCircle checked = boxes[1];
		float collideRange = checker.getDistance() + checked.getDistance();
		float distance = HitBoxManager.distance(checker.getPosition(), checked.getPosition());
		boolean isBox = false;
		if (collideRange >= distance) {
			isBox = true;
		}
		return isBox;
	}

	private static boolean isCollidingSquare(HitBox box1, HitBox box2) {
		HitBox[] boxes = { (HitBox) box1, (HitBox) box2 };

		boolean isBox = false;

		if (isColliding(true, boxes)) {
			isBox = true;
		}
		if (isColliding(false, boxes)) {
			isBox = true;
		}
		return isBox;
	}

	private static boolean isColliding(boolean b, HitBox[] boxes) {
		int i1 = 0;
		int i2 = 0;
		if (b) {
			i1 = 0;
			i2 = 1;
		} else {
			i1 = 1;
			i2 = 0;
		}

		HitBox checker = boxes[i1];
		HitBox checked = boxes[i2];

		boolean inBox = false;
		if (true) {
			float[] dat = rotate(checker.getPosition().x, checker.getPosition().z, checked.getPosition().x,
					checked.getPosition().z, checker.getRotation().y, checked.getRotation().y);
			float yRot = dat[2];
			Vector3f checkedPos = new Vector3f(dat[0], checked.getPosition().y, dat[1]);
			Vector3f[] corners = generateCorners(checked, yRot);
			for (Vector3f vec : corners) {
				float x = checkedPos.x + vec.x;
				float z = checkedPos.z + vec.z;
				float checkerxMax = checker.getPosition().x + checker.getxMax();
				float checkerxMin = checker.getPosition().x + checker.getxMin();
				float checkerzMax = checker.getPosition().z + checker.getzMax();
				float checkerzMin = checker.getPosition().z + checker.getzMin();
				if (x > checkerxMin && x < checkerxMax) {
					if (z < checkerzMax && z > checkerzMin) {
						inBox = true;
					}
				}
			}

		}
		return inBox;
	}

	private static Vector3f[] generateCorners(HitBox checked, float yRot) {
		Vector3f[] corners = new Vector3f[checked.corners.length];
		if (true) {
			for (int i = 0; i < corners.length; i++) {
				Vector3f corner = checked.corners[i];
				float[] dat = rotate(0, 0, corner.x, corner.z, yRot, 0);
				Vector3f cornerPos = new Vector3f(dat[0] * checked.getScale(), corner.getY(),
						dat[1] * checked.getScale());
				corners[i] = cornerPos;
			}
		}
		return corners;
	}

	private static float[] rotate(float inx, float inz, float outx, float outz, float inRot, float outRot) {
		float yrot = -inRot;
		float xoff = outx - inx;
		float zoff = outz - inz;
		float xcalc1 = (float) (xoff * Math.sin(Math.toRadians(yrot + 90)));
		float zcalc1 = (float) (xoff * Math.cos(Math.toRadians(yrot + 90)));
		float xcalc2 = (float) (zoff * Math.sin(Math.toRadians(yrot)));
		float zcalc2 = (float) (zoff * Math.cos(Math.toRadians(yrot)));
		float[] returnFlt = new float[3];
		returnFlt[0] = inx - xcalc1 + xcalc2;
		returnFlt[1] = inz - zcalc1 + zcalc2;
		returnFlt[2] = inRot + outRot;
		return returnFlt;
	}

}