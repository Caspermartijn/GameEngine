package utils.guis;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

public class Collision {

	public static boolean rectanglePointCollision(Vector2f point, Vector4f rectangle) {
		float x1 = rectangle.x;
		float x2 = x1 + rectangle.z;
		float y1 = rectangle.y;
		float y2 = y1 + rectangle.w;
		if (point.x > x1 && point.x < x2 && point.y > y1 && point.y < y2) {
			return true;
		}
		return false;
	}
	
}
