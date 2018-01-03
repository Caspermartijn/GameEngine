package files.configData;

public class ConfigFloat extends Config {

	private float data;

	public ConfigFloat(String dat, float data) {
		super(dat, ConfigType.Float);
		this.data = data;
	}

	public float getData() {
		return data;
	}

	public void setData(float data) {
		this.data = data;
	}

}
