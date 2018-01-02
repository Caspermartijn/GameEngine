package utils;

import org.lwjgl.util.vector.Vector3f;

public class Maths {

	public static float clamp(float min, float max, float value) {
		return Float.min(max, Float.max(min, value));
	}
	
	public static float sinWaveInterpolation(float a, float b, float blend) {
		blend = clamp(0, 1, blend);
		float newBlend = (float) Math.sin(Math.toRadians(blend*90));
		return linearInterpolation(a, b, newBlend);
	}
	
	public static float linearInterpolationBlendToSinWave(float blend) {
		return (float) Math.sin(Math.toRadians(blend*90));
	}
	
	public static float linearInterpolation(float a, float b, float blend) {
		blend = clamp(0, 1, blend);
		float diffrence = b-a;
		return a + diffrence*blend;
	}
	
	public static Vector3f interpolate(Vector3f start, Vector3f end, float progression) {
		float x = start.x + (end.x - start.x) * progression;
		float y = start.y + (end.y - start.y) * progression;
		float z = start.z + (end.z - start.z) * progression;
		return new Vector3f(x, y, z);
	}
	
}