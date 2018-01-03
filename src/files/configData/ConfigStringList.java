package files.configData;

import java.util.List;

public class ConfigStringList extends Config {

	private List<String> data;

	public ConfigStringList(String dir, List<String> data) {
		super(dir, ConfigType.StringList);
		this.data = data;
	}

	public List<String> getData() {
		return data;
	}

	public void setData(List<String> data) {
		this.data = data;
	}

}
