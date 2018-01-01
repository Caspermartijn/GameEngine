package guis;

public abstract class GUIComponent {
	
	private boolean hidden;
	private String id;
	
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
	
	public abstract void render();
	public abstract void delete();

}
