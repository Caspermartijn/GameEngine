package files.configData;

public class ConfigString extends Config {

	private String data;

	public ConfigString(String dir, String data) {
		super(dir, ConfigType.String);
		this.data = data;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
