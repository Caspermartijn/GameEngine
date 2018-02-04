package animation;

import java.util.HashMap;
import java.util.Map.Entry;

import org.lwjgl.util.vector.Matrix4f;

import utils.transformations.QuaternionTransform;

public class AnimationPose {

	private HashMap<Integer, QuaternionTransform> transforms = new HashMap<>();

	public AnimationPose(){}
	public AnimationPose(AnimationPose old) {
		fill(old);
	}
	
	public void set(int index, QuaternionTransform transform) {
		transforms.put(index, transform);
	}
	
	public QuaternionTransform get(int index) {
		return transforms.get(index);
	}
	
	public HashMap<Integer, QuaternionTransform> getTransforms() {
		return transforms;
	}
	
	public void fill(AnimationPose pose) {
		for (Entry<Integer, QuaternionTransform> e : pose.getTransforms().entrySet()) {
			if (get(e.getKey()) == null) {
				set(e.getKey(), e.getValue());
			}
		}
	}
	
	public Matrix4f[] toMatrixArray() {
		Matrix4f[] matrices = new Matrix4f[transforms.size()];
		for (Entry<Integer, QuaternionTransform> e : transforms.entrySet()) {
			matrices[e.getKey()] = e.getValue().toMatrix();
		}
		return matrices;
	}
}
