package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import components.Component;
import hitbox.HBox;
import objects.Model_3D;
import renderer.MasterRenderer;
import selector.SelectorManager;
import utils.transformations.EulerTransform;

public class Entity implements IEntity {

	private EulerTransform transform;

	private Model_3D model;

	private Entity parent;

	private List<Entity> children = new ArrayList<Entity>();

	private List<HBox> hitboxes = new ArrayList<HBox>();

	private List<Component> components = new ArrayList<Component>();

	private UUID uuid;
	private UUID parent_uuid;

	private Type entityType = Type.Normal;

	private Vector3f colorMapOffsetColor = new Vector3f();

	private Vector3f hoverColor;
	private float hoverCheckDistance = 0;
	private Runnable hoverEvent;

	public Entity(Model_3D model, Vector3f position, Vector3f rotation, float scale) {
		transform = new EulerTransform();
		transform.setPosition(position);
		transform.setRotation(rotation);
		transform.setScale(scale);
		this.model = model;
		uuid = UUID.randomUUID();
	}

	public void addHoverEvent(float maxDistance, Runnable runnable) {
		this.hoverEvent = runnable;
		this.hoverCheckDistance = maxDistance;
		this.hoverColor = SelectorManager.getNewColor();
	}

	public void updateHover(Vector3f startPos) {
		float distance = (float) Math.sqrt(((startPos.x - transform.posX) * (startPos.x - transform.posX))
				+ ((startPos.y - transform.posY) * (startPos.y - transform.posY))
				+ ((startPos.z - transform.posZ) * (startPos.z - transform.posZ)));
		if (hoverEvent != null) {
			if (distance <= hoverCheckDistance) {
				if (red() && green() && blue()) {
					hoverEvent.run();
				}
			}
		}
	}

	private boolean blue() {
		if (MasterRenderer.selectedColor.z == hoverColor.z) {
			return true;
		} else {
			return false;
		}
	}

	private boolean green() {
		if (MasterRenderer.selectedColor.y == hoverColor.y) {
			return true;
		} else {
			return false;
		}
	}

	private boolean red() {
		if (MasterRenderer.selectedColor.x == hoverColor.x) {
			return true;
		} else {
			return false;
		}
	}

	public void setType(Type type) {
		this.entityType = type;
	}

	public Type getType() {
		return entityType;
	}

	public List<Entity> getChildren() {
		return children;
	}

	public UUID getParent_uuid() {
		return parent_uuid;
	}

	public void setParent_uuid(UUID parent_uuid) {
		this.parent_uuid = parent_uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void addComponent(Component comp) {
		components.add(comp);
	}

	public List<Component> getComponents() {
		return components;
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

	public static enum Type {
		Normal, Camera;
	}

	public void setColorMapOffsetColor(Vector3f colorMapOffsetColor) {
		this.colorMapOffsetColor = colorMapOffsetColor;
	}

	public Vector3f getColorMapOffsetColor() {
		return colorMapOffsetColor;
	}

	public boolean hasHoverEvent() {
		return hoverEvent != null;
	}

}
