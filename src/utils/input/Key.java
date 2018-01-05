package utils.input;

import engine.Keyboard;

public class Key {

	private int target;
	private boolean state;
	private boolean lastState;
	
	public Key(int target) {
		this.target = target;
	}
	
	public void update() {
		this.lastState = state;
		this.state = Keyboard.isKeyDown(target);
	}
	
	public boolean isDown() {
		return state; 
	}
	
	public boolean isReleased() {
		return lastState && !state;
	}
	
	public boolean isPressed() {
		return !lastState && state;
	}
	
}
