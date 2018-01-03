package files.configData;

import org.lwjgl.util.vector.Vector3f;

public class ConfigVector3f extends Config {

	private Vector3f data;

	public ConfigVector3f(String dir, Vector3f data) {
		super(dir, ConfigType.Vec3);
		this.data = data;
	}

	public Vector3f getData() {
		return data;
	}

	public void setData(Vector3f data) {
		this.data = data;
	}

}
