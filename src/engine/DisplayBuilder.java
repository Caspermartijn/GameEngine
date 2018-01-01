package engine;

public class DisplayBuilder {

	protected int width, height;
	protected String title = "";
	protected boolean fullscreen = false;
	protected boolean vsync = true;
	protected int samples = 0;
	
	public DisplayBuilder(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public DisplayBuilder setTitle(String title) {
		this.title = title;
		return this;
	}
	
	public DisplayBuilder setFullscreen(boolean fullscreen) {
		this.fullscreen = fullscreen;
		return this;
	}
	
	public DisplayBuilder setVsync(boolean vsync) {
		this.vsync = vsync;
		return this;
	}
	
	public DisplayBuilder setSamples(int samples) {
		this.samples = samples;
		return this;
	}
	
	protected static DisplayBuilder getNewDisplayBuilder(int width, int height) {
		return new DisplayBuilder(width, height);
	}

	public void create() {
		Display.createDisplay(this);
	}
}
