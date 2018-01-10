package objects;

import java.util.ArrayList;
import java.util.List;

import entities.Entity;

public class EntityList {

	private List<Entity> entities = new ArrayList<Entity>();

	public EntityList(List<Entity> entities) {
		this.entities = entities;
	}

	public void add(Entity entity) {
		entities.add(entity);
	}
	
	public Entity get(int i) {
		return entities.get(i);
	}
	
	public void remove(Entity entity) {
		entities.remove(entity);
	}
	
	public List<Entity> getList() {
		return entities;
	}
	
}
