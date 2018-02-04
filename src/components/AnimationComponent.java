package components;

import java.util.ArrayList;

import org.lwjgl.util.vector.Matrix4f;

import animation.Animation;
import animation.Joint;

public class AnimationComponent implements Component {

	private int numJoints;
	private Joint root;
	private ArrayList<Joint> joints;

	private Matrix4f[] transforms;
	private ArrayList<Animation> animations = new ArrayList<>();
	private Animation currentAnimation;

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
		if (currentAnimation != null) {
			currentAnimation.update();
			root.load(currentAnimation.getTransforms());
			
			if (!currentAnimation.isPlaying())
				currentAnimation = null;
		}

		root.calculateTotalTransform(transforms, null);
	}

	@Override
	public Type getType() {
		return Component.Type.ANIMATION;
	}

	public int getNumberJoints() {
		return numJoints;
	}

	public void addAnimation(Animation a) {
		this.animations.add(a);
	}

	public Animation getByName(String name) {
		for (Animation a : animations) {
			if (a.getName().equals(name))
				return a;
		}
		return null;
	}

	public void stopAnimation() {
		if (currentAnimation == null)
			return;

		currentAnimation.stop();
		currentAnimation = null;
	}

	public void startAnimation(String animation) {
		if (currentAnimation != null)
			currentAnimation.stop();

		currentAnimation = getByName(animation);
		currentAnimation.play();
	}

}
