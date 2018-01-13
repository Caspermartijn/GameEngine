package guis;

public abstract class GUIComponent {
	
	private boolean hidden;
	private String id;
	private GUI container;
	
	public GUIComponent(GUI container) {
		this.container = container;
		if (container != null)
			container.addComponent(this);
	}
	
	public void show() {
		hidden = false;
	}

	public void hide() {
		hidden = true;
	}

	public boolean isHidden() {
		return hidden;
	}
	
	public void setID(String id) {
		this.id = id;
	}
	
	public String getID() {
		return id;
	}
	
	protected GUI getContainer() {
		return container;
	}

	public abstract void render();
	public abstract void delete();

}
