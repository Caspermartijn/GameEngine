package engine.options.objects;

public class OptionCheckBox extends Option {

	public void check() {
		setSelectedValue(1);
	}

	public void uncheck() {
		setSelectedValue(0);
	}

}
