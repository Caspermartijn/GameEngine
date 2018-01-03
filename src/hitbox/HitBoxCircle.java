package hitbox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import objects.Vao;

public class HitBoxCircle extends HBox {

	public float distance;

	public float yMax;
	public float yMin;

	public HitBoxCircle(float distance, float yMax, float yMin) {
		this.distance = distance;
		this.yMax = yMax;
		this.yMin = yMin;
		HitBoxManager.addHitBox(this);
		super.setRawModel(HitBoxCircle.getCylinderModel(this));
	}

	public float getDistance() {
		return distance;
	}

	public float getyMax() {
		return yMax;
	}

	public float getyMin() {
		return yMin;
	}

	public static Vao getCylinderModel(HitBoxCircle box) {
		Vao model = Vao.create();

		HashMap<Integer, float[]> v = getCylinderLocs(box);

		float[] positions = new float[v.size() * 3];

		for (int i = 0; i < v.size(); i++) {
			float[] aa = v.get(i);

			positions[i] = aa[0];
			positions[i + 1] = aa[1];
			positions[i + 2] = aa[2];
			i++;
			i++;
		}
		return model.loadToVAO(positions);
	}

	private static HashMap<Integer, float[]> getCylinderLocs(HitBoxCircle box) {
		HashMap<Integer, float[]> returnH = new HashMap<Integer, float[]>();

		List<float[]> circlePos = new ArrayList<float[]>();

		for (int i = 0; i < 360; i += 15) {
			float x = (float) (box.distance * Math.sin(i));
			float z = (float) (box.distance * Math.cos(i));
			float[] ffs = { x, z };
			circlePos.add(ffs);
		}
		for (float[] f : circlePos) {
			float[] re = new float[3];
			re[0] = f[0];
			re[1] = box.yMin;
			re[2] = f[1];
			returnH.put(returnH.size(), re);

		}
		float[] ra = new float[3];
		ra[0] = 0;
		ra[1] = box.yMin;
		ra[2] = 0;
		returnH.put(returnH.size(), ra);
		float[] ri = new float[3];
		ri[0] = 0;
		ri[1] = box.yMax;
		ri[2] = 0;
		returnH.put(returnH.size(), ri);
		for (float[] f : circlePos) {
			float[] re = new float[3];
			re[0] = f[0];
			re[1] = box.yMax;
			re[2] = f[1];
			returnH.put(returnH.size(), re);

		}
		return returnH;
	}

}
