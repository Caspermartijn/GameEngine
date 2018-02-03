package components;

import java.util.ArrayList;

import org.lwjgl.util.vector.Matrix4f;

import animation.Joint;

public class AnimationComponent implements Component {

	private int numJoints;
	private Joint root;
	private ArrayList<Joint> joints;
	
	private Matrix4f[] transforms;
	
	public AnimationComponent(int numJoints, Joint root, ArrayList<Joint> joints) {
		super();
		this.numJoints = numJoints;
		this.root = root;
		this.joints = joints;
		transforms = new Matrix4f[numJoints];
		root.calcInverseBindTransform((Matrix4f) new Matrix4f().setIdentity());
	}

	public Joint getJointByID(int id) {
		for (Joint j : joints) {
			if (j.getID() == id) { 
				return j;
			}
		}
		return null;
	}
	
	public Matrix4f[] getMatrices() {
		return transforms;
	}
	
	@Override
	public void update() {
		root.calculateTotalTransform(transforms, null);
	}

	@Override
	public Type getType() {
		return Component.Type.ANIMATION;
	}
	
	public int getNumberJoints() {
		return numJoints;
	}

}
