package utils;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class Vector {

	public static Vector3f add(Vector3f left, Vector3f right, Vector3f dest) {
		if (dest == null)
			return new Vector3f(left.x + right.x, left.y + right.y, left.z + right.z);
		else {
			dest.set(left.x + right.x, left.y + right.y, left.z + right.z);
			return dest;
		}
	}

	public static final Vector3f normalise(Vector3f vector) {
		float len = vector.length();
		if (len != 0.0f) {
			float l = 1.0f / len;
			return scale(vector, l);
		} else
			throw new IllegalStateException("Zero length vector");
	}

	public static Vector3f scale(Vector3f vec, float scale) {
		vec.x *= scale;
		vec.y *= scale;
		vec.z *= scale;
		return vec;
	}

	public static Vector3f sub(Vector3f left, Vector3f right, Vector3f dest) {
		if (dest == null)
			return new Vector3f(left.x - right.x, left.y - right.y, left.z - right.z);
		else {
			dest.set(left.x - right.x, left.y - right.y, left.z - right.z);
			return dest;
		}
	}

	public static Vector2f sub(Vector2f left, Vector2f right, Vector2f dest) {
		if (dest == null)
			return new Vector2f(left.x - right.x, left.y - right.y);
		else {
			dest.set(left.x - right.x, left.y - right.y);
			return dest;
		}
	}

	public static Vector2f add(Vector2f left, Vector2f right, Vector2f dest) {
		if (dest == null)
			return new Vector2f(left.x + right.x, left.y + right.y);
		else {
			dest.set(left.x + right.x, left.y + right.y);
			return dest;
		}
	}

}
