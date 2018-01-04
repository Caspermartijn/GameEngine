package engine.options.objects;

public abstract class Option implements IOption {

	boolean enabled;
	int selectedValue = 0;
	
	@Override
	public void enable() {
		enabled = true;
	}

	@Override
	public void disable() {
		enabled = false;
	}
	
	@Override
	public boolean isEnabled() {
		return enabled;
	}
	
	protected void setSelectedValue(int i) {
		selectedValue = i;
	}
	
	@Override
	public int getValue() {
		return selectedValue;
	}
	
}
