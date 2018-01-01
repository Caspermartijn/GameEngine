package utils;

import java.nio.FloatBuffer;

import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class MatrixUtils {

	public static void store(FloatBuffer buffer, Matrix3f matrix) {
		buffer.put(0, matrix.m00);
		buffer.put(1, matrix.m01);
		buffer.put(2, matrix.m02);
		buffer.put(3, matrix.m10);
		buffer.put(4, matrix.m11);
		buffer.put(5, matrix.m12);
		buffer.put(6, matrix.m20);
		buffer.put(7, matrix.m21);
		buffer.put(8, matrix.m22);
	}

	public static void store(FloatBuffer buffer, Matrix4f matrix) {
		buffer.put(0, matrix.m00());
		buffer.put(1, matrix.m01());
		buffer.put(2, matrix.m02());
		buffer.put(3, matrix.m03());
		
		buffer.put(4, matrix.m10());
		buffer.put(5, matrix.m11());
		buffer.put(6, matrix.m12());
		buffer.put(7, matrix.m13());
		
		buffer.put(8, matrix.m20());
		buffer.put(9, matrix.m21());
		buffer.put(10, matrix.m22());
		buffer.put(11, matrix.m23());
		
		buffer.put(12, matrix.m30());
		buffer.put(13, matrix.m31());
		buffer.put(14, matrix.m32());
		buffer.put(15, matrix.m33());
	}

}
