package utils;

public class Switch {

	private boolean value;
	private long switchCooldown = 0;
	private long lastSwitch = 0;
	
	public Switch() {}
	
	public Switch(boolean value) {
		this.value = value;
	}
	
	public Switch(boolean value, long switchCooldownMS) {
		this.value = value;
		this.switchCooldown = switchCooldownMS;
	}
	
	public void setSwitchCooldown(long switchCooldownMS) {
		this.switchCooldown = switchCooldownMS;
	}
	
	public boolean getBooleanValue() {
		return value;
	}
	
	public void switchBoolean() {
		if (System.currentTimeMillis() < lastSwitch+switchCooldown)
			return;
		if (value)
			value = false;
		else
			value = true;
		lastSwitch = System.currentTimeMillis();
	}
	
}
