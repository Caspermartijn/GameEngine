package entities;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import components.Component;
import components.TracerComponent;
import hitbox.HBox;
import objects.Model_3D;
import utils.transformations.EulerTransform;

public class Entity implements IEntity {

	private EulerTransform transform;

	private Model_3D model;

	private Entity parent;

	private List<Entity> children = new ArrayList<Entity>();

	private List<HBox> hitboxes = new ArrayList<HBox>();

	private List<Component> components = new ArrayList<Component>();

	public Entity(Model_3D model, Vector3f position, Vector3f rotation, float scale) {
		transform = new EulerTransform();
		transform.setPosition(position);
		transform.setRotation(rotation);
		transform.setScale(scale);
		this.model = model;
	}

	public void addHitBox(HBox box) {
		hitboxes.add(box);
	}

	public List<HBox> getHitboxes() {
		return hitboxes;
	}

	public void addChild(Entity entity) {
		children.add(entity);
		entity.setParent(this);
	}

	protected void setParent(Entity entity) {
		this.parent = entity;
	}

	public Entity getParent() {
		return parent;
	}

	@Override
	public void update() {
		for (HBox box : hitboxes) {
			box.setPosition(this.getTransform().getPosition());
		}
		for (Component component : components) {
			component.update();

			if (component instanceof TracerComponent) {
				TracerComponent comp = (TracerComponent) component;
				comp.setEnd(this.getTransform().getPosition());
			}
		}
	}

	public EulerTransform getTransform() {
		return transform;
	}

	public Model_3D getModel() {
		return model;
	}

	public Matrix4f getTransformationMatrix() {
		if (parent != null) {
			return Matrix4f.mul(parent.getTransformationMatrix(), getLocalTransformationMatrix(), null);
		} else {
			return getLocalTransformationMatrix();
		}

	}

	public Matrix4f getLocalTransformationMatrix() {
		return transform.toMatrix();
	}

	public List<Entity> getChildrenEntities() {
		return children;
	}

	public boolean hasComponentType(Component.Type type) {
		return getComponent(type) != null;
	}
	
	public Component getComponent(Component.Type type) {
		for (Component c : components) {
			if (c.getType() == type)
				return c;
		}
		return null;
	}
}
