package engine;

public class DisplaySettings {

	private int width;
	private int height;

	private int syncFPS = 60;
	private boolean fullscreenMode = false;

	private String title = "Engine";

	public DisplaySettings(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public DisplaySettings setWidth(int width) {
		this.width = width;
		return this;
	}

	public DisplaySettings setHeight(int height) {
		this.height = height;
		return this;
	}

	public DisplaySettings setSyncFPS(int syncFPS) {
		this.syncFPS = syncFPS;
		return this;
	}

	public DisplaySettings setFullscreenMode(boolean fullscreenMode) {
		this.fullscreenMode = fullscreenMode;
		return this;
	}

	public DisplaySettings setTitle(String title) {
		this.title = title;
		return this;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getSyncFPS() {
		return syncFPS;
	}

	public boolean isFullscreenMode() {
		return fullscreenMode;
	}

	public String getTitle() {
		return title;
	}
}
