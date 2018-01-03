package files.configData;

import org.lwjgl.util.vector.Vector2f;

public class ConfigVector2f extends Config {

	private Vector2f data;

	public ConfigVector2f(String dir, Vector2f data) {
		super(dir, ConfigType.Vec2);
		this.data = data;
	}

	public Vector2f getData() {
		return data;
	}

	public void setData(Vector2f data) {
		this.data = data;
	}

}
