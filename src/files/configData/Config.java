package files.configData;

public abstract class Config {

	protected String dat;

	protected ConfigType type;

	public ConfigType getType() {
		return type;
	}

	public Config(String dat, ConfigType type) {
		this.dat = dat;
		this.type = type;
	}

	public String getDat() {
		return dat;
	}

	public void setDat(String dat) {
		this.dat = dat;
	}

}
