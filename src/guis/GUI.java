package guis;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;

public class GUI {

	private ArrayList<GUIComponent> components = new ArrayList<>();
	private Vector2f position = new Vector2f();

	public void renderComponents() {
		for (GUIComponent c : components) {
			if (!c.isHidden()) {
				c.render();
			}
		}
	}

	public GUIComponent getByID(String id) {
		for (GUIComponent c : components) { 
			if (c.getID().equals(id))
				return c;
		}
		return null;
	}

	protected void addComponent(GUIComponent c) {
		this.components.add(c);
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(float x, float y) {
		position.set(x, y);
	}

	public void setPosition(Vector2f v) {
		position = v;
	}

	public void delete() {
		for (GUIComponent c : components) {
			c.delete();
		}
	}

	public void hideAll() {
		for (GUIComponent c : components) {
			c.hide();
		}
	}

	public void showAll() {
		for (GUIComponent c : components) {
			c.show();
		}
	}

}
