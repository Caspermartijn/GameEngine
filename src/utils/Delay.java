package utils;

public class Delay {

	private long delay;
	private long lastActive;
	
	public Delay(long delay) {
		super();
		this.delay = delay;
	}

	public long getDelay() {
		return delay;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}
	
	public boolean activate() {
		if (System.currentTimeMillis() - lastActive > delay) {
			lastActive = System.currentTimeMillis();
			return true;
		}
		return false;
	}
	
}
