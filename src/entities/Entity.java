package entities;

import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import objects.Model_3D;
import utils.EulerTransform;

public class Entity implements IEntity {

	private EulerTransform transform;

	private Model_3D model;
	
	private Entity parent;
	
	private List<Entity> children;

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

	public EulerTransform getTransform() {
		return transform;
	}

	public Model_3D getModel() {
		return model;
	}

//	private Matrix4f generateMatrix() {
//			if(parent == null) {
//				Matrix4f matrix = new Matrix4f();
//				matrix.setIdentity();
//				return matrix;
//			}else {
//				return parent.getParentMatrix();
//			}
//	}
	
	public Matrix4f getParentSpaceMatrix() {
		if (parent != null) {
			return Matrix4f.mul(parent.getParentSpaceMatrix(), getLocalTransformationMatrix(), null);
		} else {
			return getLocalTransformationMatrix();
		}
		
//		Matrix4f matrix = generateMatrix();
//		Matrix4f.rotate((float) Math.toRadians(rotation.x), new Vector3f(1, 0, 0), matrix, matrix);
//		Matrix4f.rotate((float) Math.toRadians(rotation.y), new Vector3f(0, 1, 0), matrix, matrix);
//		Matrix4f.rotate((float) Math.toRadians(rotation.z), new Vector3f(0, 0, 1), matrix, matrix);
//		Matrix4f.translate(position, matrix, matrix);
//		return matrix;
	}

	public Matrix4f getLocalTransformationMatrix() {
		return transform.toMatrix();
	}

}
