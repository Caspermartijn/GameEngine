package animation;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Matrix4f;

public class Joint {
	
	public static final int MAX_WEIGHTS = 3;
	public static final int MAX_JOINTS = 50;
	
	public final int index;
	public final String name;
	public final String parentName;
	public final List<Joint> children = new ArrayList<Joint>();

	private Matrix4f transform;
	
	public final Matrix4f localBindTransform;
	private Matrix4f inverseBindTransform = new Matrix4f();

	public Joint(int index, String name, String parentName, Matrix4f localBindTransform) {
		this.index = index;
		this.name = name;
		this.parentName = parentName;
		this.localBindTransform = localBindTransform;
		transform = localBindTransform;
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
		Matrix4f currentTransform = new Matrix4f(transform);
		if (parentTransform != null) {
			Matrix4f.mul(parentTransform, currentTransform, currentTransform);
		} 
		for (Joint j : children) {
			j.calculateTotalTransform(matrices, currentTransform);
		}
		matrices[index] = Matrix4f.mul(currentTransform, inverseBindTransform, null);
	}

	public Matrix4f getTransform() {
		return transform;
	}

	public void setTransform(Matrix4f transform) {
		Matrix4f.mul(localBindTransform, transform, this.transform);
	}

	public int getID() {
		return index;
	}

	public String getName() {
		return name;
	}

	public void load(AnimationPose transforms) {
		this.transform = transforms.get(index).toMatrix();
		for (Joint child : children) {
			child.load(transforms);
		}
	}
	
	
}
