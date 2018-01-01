package utils;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class Quaternion {

	public float x, y, z, w;

	public Quaternion(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		normalize();
	}

	public Quaternion(float dx, float dy, float dz) {
		setFromEulerAngles(dx, dy, dz);
	}

	public Quaternion(Matrix4f matrix) {
		float diagonal = matrix.m00 + matrix.m11 + matrix.m22;
		if (diagonal > 0) {
			float w4 = (float) (Math.sqrt(diagonal + 1f) * 2f);
			this.w = w4 / 4f;
			this.x = (matrix.m21 - matrix.m12) / w4;
			this.y = (matrix.m02 - matrix.m20) / w4;
			this.z = (matrix.m10 - matrix.m01) / w4;
		} else if ((matrix.m00 > matrix.m11) && (matrix.m00 > matrix.m22)) {
			float x4 = (float) (Math.sqrt(1f + matrix.m00 - matrix.m11 - matrix.m22) * 2f);
			this.w = (matrix.m21 - matrix.m12) / x4;
			this.x = x4 / 4f;
			this.y = (matrix.m01 + matrix.m10) / x4;
			this.z = (matrix.m02 + matrix.m20) / x4;
		} else if (matrix.m11 > matrix.m22) {
			float y4 = (float) (Math.sqrt(1f + matrix.m11 - matrix.m00 - matrix.m22) * 2f);
			this.w = (matrix.m02 - matrix.m20) / y4;
			this.x = (matrix.m01 + matrix.m10) / y4;
			this.y = y4 / 4f;
			this.z = (matrix.m12 + matrix.m21) / y4;
		} else {
			float z4 = (float) (Math.sqrt(1f + matrix.m22 - matrix.m00 - matrix.m11) * 2f);
			this.w = (matrix.m10 - matrix.m01) / z4;
			this.x = (matrix.m02 + matrix.m20) / z4;
			this.y = (matrix.m12 + matrix.m21) / z4;
			this.z = z4 / 4f;
		}
		this.normalize();
	}

	public Matrix4f toRotationMatrix() {
		Matrix4f matrix = new Matrix4f();
		final float xy = x * y;
		final float xz = x * z;
		final float xw = x * w;
		final float yz = y * z;
		final float yw = y * w;
		final float zw = z * w;
		final float xSquared = x * x;
		final float ySquared = y * y;
		final float zSquared = z * z;
		matrix.m00 = 1 - 2 * (ySquared + zSquared);
		matrix.m01 = 2 * (xy - zw);
		matrix.m02 = 2 * (xz + yw);
		matrix.m03 = 0;
		matrix.m10 = 2 * (xy + zw);
		matrix.m11 = 1 - 2 * (xSquared + zSquared);
		matrix.m12 = 2 * (yz - xw);
		matrix.m13 = 0;
		matrix.m20 = 2 * (xz - yw);
		matrix.m21 = 2 * (yz + xw);
		matrix.m22 = 1 - 2 * (xSquared + ySquared);
		matrix.m23 = 0;
		matrix.m30 = 0;
		matrix.m31 = 0;
		matrix.m32 = 0;
		matrix.m33 = 1;
		return matrix;
	}

	public void normalize() {
		float mag = (float) Math.sqrt(w * w + x * x + y * y + z * z);
		w /= mag;
		x /= mag;
		y /= mag;
		z /= mag;
	}

	public void setFromEulerAngles(float x, float y, float z) {
		x = (float) Math.toRadians(x);
		y = (float) Math.toRadians(y);
		z = (float) Math.toRadians(z);
		
		float t0 = (float) Math.cos(x / 2f);
		float t1 = (float) Math.cos(y / 2f);
		float t2 = (float) Math.cos(z / 2f);
		float t3 = (float) Math.sin(x / 2f);
		float t4 = (float) Math.sin(y / 2f);
		float t5 = (float) Math.sin(z / 2f);
		
		this.x = t3 * t1 * t2 + t0 * t4 * t5;
		this.y = t0 * t4 * t2 - t3 * t1 * t5;
		this.z = t0 * t1 * t5 + t3 * t4 * t2;
		this.w = t0 * t1 * t2 - t3 * t4 * t5;
		
		this.normalize();
	}
	
	public Vector3f toEulerAngles() {
		this.normalize();
		
		float t0 = -2f*(y*z - w*x);
		float t1 = w*w - x*x - y*y + z*z;
		float t2 = 2f*(x*z + w*y);
		float t3 = -2f*(x*y - w*z);
		float t4 = w*w + x*x - y*y - z*z;
		
		float x = (float) Math.toDegrees(Math.atan2(t0, t1));
		float y = (float) Math.toDegrees(Math.asin(t2));
		float z = (float) Math.toDegrees(Math.atan2(t3, t4));
		
		return new Vector3f(x, y, z);
	}

	public static Quaternion slerp(Quaternion start, Quaternion end, float progression) {
		start.normalize();
		end.normalize();
		final float d = start.x * end.x + start.y * end.y + start.z * end.z + start.w * end.w;
		float absDot = d < 0f ? -d : d;
		float scale0 = 1f - progression;
		float scale1 = progression;

		if ((1 - absDot) > 0.1f) {

			final float angle = (float) Math.acos(absDot);
			final float invSinTheta = 1f / (float) Math.sin(angle);
			scale0 = ((float) Math.sin((1f - progression) * angle) * invSinTheta);
			scale1 = ((float) Math.sin((progression * angle)) * invSinTheta);
		}

		if (d < 0f) {
			scale1 = -scale1;
		}
		float newX = (scale0 * start.x) + (scale1 * end.x);
		float newY = (scale0 * start.y) + (scale1 * end.y);
		float newZ = (scale0 * start.z) + (scale1 * end.z);
		float newW = (scale0 * start.w) + (scale1 * end.w);
		return new Quaternion(newX, newY, newZ, newW);
	}

	public static Quaternion mul(Quaternion q, Quaternion r) {
		q.normalize();
		r.normalize();
		float w = (r.w * q.w - r.x * q.x - r.y * q.y - r.z * q.z);
		float x = (r.w * q.x + r.x * q.w - r.y * q.z + r.z * q.y);
		float y = (r.w * q.y + r.x * q.z + r.y * q.w - r.z * q.x);
		float z = (r.w * q.z - r.x * q.y + r.y * q.x + r.z * q.w);
		Quaternion product = new Quaternion(x, y, z, w);
		product.normalize();
		return product;
	}

	public static Quaternion invert(Quaternion q) {
		q.normalize();
		float w = q.w / (q.w * q.w);
		float x = -q.x / (q.x * q.x);
		float y = -q.y / (q.y * q.y);
		float z = -q.z / (q.z * q.z);
		Quaternion invert = new Quaternion(x, y, z, w);
		invert.normalize();
		return invert;
	}

	@Override
	public String toString() {
		return "Quaternion[" + w + ", " + x + ", " + y + ", " + z + "]";
	}

}
