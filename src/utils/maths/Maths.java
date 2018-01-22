package utils.maths;

import java.util.Random;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import engine.Display;

public class Maths {

	public static Random RANDOM = new Random();

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

	public static Vector2f getNormalizedSize(float sizeX, float sizeY) {
		float xFac = Display.getWidth() / 1280;
		float yFac = Display.getHeight() / 720;

		float x = (sizeX / 1280) * xFac;
		float y = (sizeY / 720) * yFac;
		return new Vector2f(x, y);
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
		} else {
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

	public static float distance(org.lwjgl.util.vector.Vector3f vector3f, org.lwjgl.util.vector.Vector3f vector3f2) {
		float x = vector3f.x - vector3f2.x;
		float z = vector3f.z - vector3f2.z;
		float dis = (float) Math.sqrt(x * x + z * z);
		return dis;
	}

	public static Vector2f openGLtoIce(Vector2f vec) {
		Vector2f returnVec = new Vector2f();
		returnVec.x = ((vec.x + 1) / 2);
		returnVec.y = ((vec.y + 1) / 2);
		return returnVec;
	}

	public static Vector2f getFrom720toCurrentDisplaySize(Vector2f size) {
		float x = size.x / 1280;
		float y = size.y / 720;
		return new Vector2f(Display.getWidth() * x, Display.getHeight() * y);
	}

	public static Vector3f linearInterpolation(Vector3f v1, Vector3f v2, float blend) {
		return new Vector3f(linearInterpolation(v1.x, v2.x, blend), linearInterpolation(v1.y, v2.y, blend),
				linearInterpolation(v1.z, v2.z, blend));
	}

	public static Vector2f getFrom720toCurrentDisplaySize(float x, float y) {
		x = x / 1280;
		y = y / 720;
		return new Vector2f(Display.getWidth() * x, Display.getHeight() * y);
	}

	public static Vector2f getFrom1920toCurrentDisplaySize(float x, float y) {
		x = x / 1920;
		y = y / 1080;
		return new Vector2f(Display.getWidth() * x, Display.getHeight() * y);
	}

}
