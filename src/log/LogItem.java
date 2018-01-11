package log;

import org.lwjgl.util.vector.Vector3f;

public class LogItem{
	
	private String string;
	private Vector3f color = Log.DEFAULT_COLOR;

	public LogItem(String string, Vector3f color) {
		this.string = string;
		this.color = color;
	}
	
	public LogItem(String string) {
		this.string = string;
	}

	public String getString() {
		return string;
	}

	public Vector3f getColor() {
		return color;
	}

	public void setColor(Vector3f color) {
		this.color = color;
	}

	
	
	
}
