package animation;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Matrix4f;

import utils.transformations.QuaternionTransform;

public class Joint {
	
	public final int index;
	public final String name;
	public final String parentName;
	public final List<Joint> children = new ArrayList<Joint>();

	private QuaternionTransform transform;
	
	public final Matrix4f localBindTransform;
	private Matrix4f inverseBindTransform = new Matrix4f();

	public Joint(int index, String name, String parentName, Matrix4f localBindTransform) {
		this.index = index;
		this.name = name;
		this.parentName = parentName;
		this.localBindTransform = localBindTransform;
		transform = new QuaternionTransform();
		System.out.println(index + ":" + name);
	}

	public void addChild(Joint child) {
		this.children.add(child);
	}

	public void calcInverseBindTransform(Matrix4f parentBindTransform) {
		Matrix4f bindTransform = Matrix4f.mul(parentBindTransform, localBindTransform, null);
		Matrix4f.invert(bindTransform, inverseBindTransform);
		for (Joint child : children) {
			child.calcInverseBindTransform(bindTransform);
		}
	}
	
	public void calculateTotalTransform(Matrix4f[] matrices, Matrix4f parentTransform) {
		Matrix4f currentTransform = new Matrix4f();
		Matrix4f.mul(localBindTransform, transform.toMatrix(), currentTransform);
		if (parentTransform != null) {
			Matrix4f.mul(parentTransform, currentTransform, currentTransform);
		} 
		for (Joint j : children) {
			j.calculateTotalTransform(matrices, currentTransform);
		}
		matrices[index] = Matrix4f.mul(currentTransform, inverseBindTransform, null);
	}

	public QuaternionTransform getTransform() {
		return transform;
	}

	public void setTransform(QuaternionTransform transform) {
		this.transform = transform;
	}

	public int getID() {
		return index;
	}

	public String getName() {
		return name;
	}
	
	
}
