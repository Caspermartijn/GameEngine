package utils.maths;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Maths {

	public static float clamp(float min, float max, float value) {
		return Float.min(max, Float.max(min, value));
	}

	public static float sinWaveInterpolation(float a, float b, float blend) {
		blend = clamp(0, 1, blend);
		float newBlend = (float) Math.sin(Math.toRadians(blend * 90));
		return linearInterpolation(a, b, newBlend);
	}

	public static float linearInterpolationBlendToSinWave(float blend) {
		return (float) Math.sin(Math.toRadians(blend * 90));
	}

	public static float linearInterpolation(float a, float b, float blend) {
		blend = clamp(0, 1, blend);
		float diffrence = b - a;
		return a + diffrence * blend;
	}

	public static Vector3f interpolate(Vector3f start, Vector3f end, float progression) {
		float x = start.x + (end.x - start.x) * progression;
		float y = start.y + (end.y - start.y) * progression;
		float z = start.z + (end.z - start.z) * progression;
		return new Vector3f(x, y, z);
	}

	public static float barryCentric(Vector3f p1, Vector3f p2, Vector3f p3, Vector2f pos) {
		float det = (p2.z - p3.z) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.z - p3.z);
		float l1 = ((p2.z - p3.z) * (pos.x - p3.x) + (p3.x - p2.x) * (pos.y - p3.z)) / det;
		float l2 = ((p3.z - p1.z) * (pos.x - p3.x) + (p1.x - p3.x) * (pos.y - p3.z)) / det;
		float l3 = 1.0f - l1 - l2;
		return l1 * p1.y + l2 * p2.y + l3 * p3.y;
	}

	public static float[] controllerInterval(boolean b, float intervall, float[] value) {
		if (b) {
			float backupx = value[0];
			float backupz = value[1];
			if (value[0] >= 0) {
				if (value[0] < intervall) {
					value[0] = 0;
				}
			} else {
				if (value[0] > -intervall) {
					value[0] = 0;
				}
			}

			if (value[1] >= 0) {
				if (value[1] < intervall) {
					value[1] = 0;
				}
			} else {
				if (value[1] > -intervall) {
					value[1] = 0;
				}
			}

			if (value[0] > 0.2f || value[0] < -0.2f) {
				value[1] = backupz;
			}

			if (value[1] > 0.2f || value[1] < -0.2f) {
				value[0] = backupx;
			}
		}else {
			float backupx = value[2];
			float backupz = value[3];
			if (value[2] >= 0) {
				if (value[2] < intervall) {
					value[2] = 0;
				}
			} else {
				if (value[2] > -intervall) {
					value[2] = 0;
				}
			}

			if (value[3] >= 0) {
				if (value[3] < intervall) {
					value[3] = 0;
				}
			} else {
				if (value[3] > -intervall) {
					value[3] = 0;
				}
			}

			if (value[2] > 0.2f || value[2] < -0.2f) {
				value[3] = backupz;
			}

			if (value[3] > 0.2f || value[3] < -0.2f) {
				value[2] = backupx;
			}
		}
		return value;
	}

}
