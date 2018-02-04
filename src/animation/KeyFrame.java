package animation;

import utils.transformations.QuaternionTransform;

public class KeyFrame {
	
	private final float timeStamp;
	private final QuaternionTransform transform;
	
	public KeyFrame(float timeStamp, QuaternionTransform transform) {
		this.timeStamp = timeStamp;
		this.transform = transform;
	}

	protected float getTimeStamp() {
		return timeStamp;
	}

	protected QuaternionTransform getJointTransform() {
		return transform;
	}
}
