package audio;

import org.lwjgl.util.vector.Vector3f;

public class AudioListener {
	private Vector3f position;

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}
}
