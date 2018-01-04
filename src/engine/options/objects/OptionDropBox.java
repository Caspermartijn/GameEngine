package engine.options.objects;

public class OptionDropBox extends Option {

	private String[] values;

	public OptionDropBox(String[] values) {
		this.values = values;
	}

	public String[] getValues() {
		return values;
	}

	public void setValues(String[] values) {
		this.values = values;
	}

	public String getValue(int i) {
		return values[i];
	}

}
