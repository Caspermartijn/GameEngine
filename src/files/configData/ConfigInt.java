package files.configData;

public class ConfigInt extends Config {

	private int data;

	public ConfigInt(String dat, int data) {
		super(dat, ConfigType.Int);
		this.data = data;
	}

	public int getData() {
		return data;
	}

	public void setData(int data) {
		this.data = data;
	}

}
