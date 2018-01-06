package objects;

import org.lwjgl.util.vector.Vector3f;

public class AutoPilotCheckPoint {

	private Vector3f position;
	private float speed;

	public AutoPilotCheckPoint(float speed, Vector3f position) {
		this.position = position;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public static float[] calculateAngle(Vector3f position, AutoPilotCheckPoint checkpoint) {
		float x = position.x - checkpoint.position.x;
		float z = position.z - checkpoint.position.z;
		float y = position.y - checkpoint.position.y;

		float offsetXZ = (float) Math.sqrt((x * x) + (z * z));

		float angleRadians = (float) Math.atan2(z, x);

		float angleRadians2 = (float) Math.atan2(offsetXZ, y);

		// angle in degrees
		float angleDeg = (float) (angleRadians * 180 / Math.PI);
		float yaw = angleDeg;

		float pitch = (float) (angleRadians2 * 180 / Math.PI);

		yaw = 360 + yaw;
		pitch = 360 + pitch;
		yaw = (yaw + 360) % 360;
		pitch = (pitch + 360) % 360;

		float[] rett = new float[] { yaw, pitch };
		return rett;
	}

}
