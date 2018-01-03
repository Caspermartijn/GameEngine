package files.configData;

import org.lwjgl.util.vector.Vector4f;

public class ConfigVector4f extends Config {

	private Vector4f data;

	public ConfigVector4f(String dir, Vector4f data) {
		super(dir,ConfigType.Vec4);
		this.data = data;
	}

	public Vector4f getData() {
		return data;
	}

	public void setData(Vector4f data) {
		this.data = data;
	}

}
