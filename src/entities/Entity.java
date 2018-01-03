package entities;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import objects.Model_3D;
import utils.EulerTransform;

public class Entity implements IEntity {

	private EulerTransform transform;

	private Model_3D model;
	
	private Entity parent;
	
	private List<Entity> children = new ArrayList<Entity>();

	public Entity(Model_3D model, Vector3f position, Vector3f rotation, float scale) {
		transform = new EulerTransform();
		transform.setPosition(position);
		transform.setRotation(rotation);
		transform.setScale(scale);
		this.model = model;
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

	}

	public void delete() {
		model.delete();
	}
	
	public EulerTransform getTransform() {
		return transform;
	}

	public Model_3D getModel() {
		return model;
	}
	
	public Matrix4f getParentSpaceMatrix() {
		if (parent != null) {
			return Matrix4f.mul(parent.getParentSpaceMatrix(), getLocalTransformationMatrix(), null);
		} else {
			return getLocalTransformationMatrix();
		}
		
	}

	public Matrix4f getLocalTransformationMatrix() {
		return transform.toMatrix();
	}
	
	public Matrix4f getTransformationMatrix() {
		if(parent == null) {
			return getLocalTransformationMatrix();
		}else {
			return getParentSpaceMatrix();
		}
	}

}
