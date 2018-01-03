package files.configData;

public class ConfigBoolean extends Config {

	private boolean data;

	public ConfigBoolean(String dat, boolean data) {
		super(dat, ConfigType.Boolean);
		this.data = data;
	}

	
	
	public boolean isData() {
		return data;
	}

	public void setData(boolean data) {
		this.data = data;
	}

}
