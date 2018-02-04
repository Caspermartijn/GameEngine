package animation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import utils.transformations.QuaternionTransform;

public class JointAnimation {

	private int jointID;
	private ArrayList<KeyFrame> keyFrames = new ArrayList<>();
	private ArrayList<KeyFrame> temporaryKeyFrames = new ArrayList<>();

	public JointAnimation(int jointID) {
		super();
		this.jointID = jointID;
	}
	
	public void addKeyFrame(KeyFrame frame) {
		keyFrames.add(frame);
	}

	public QuaternionTransform getTransform(float time) {
		KeyFrame previousFrame = null;
		KeyFrame nextFrame = null;
		for (KeyFrame frame : keyFrames) {
			KeyFrame temporary = getTemporarayKeyFrame(frame.getTimeStamp());

			if (frame.getTimeStamp() > time) {
				nextFrame = frame;
				if (temporary != null) {
					nextFrame = temporary;
				}
				break;
			}
			previousFrame = frame;
			if (temporary != null) {
				previousFrame = temporary;
			}
		}
		if (previousFrame == null) {
			previousFrame = nextFrame;
		} else if (nextFrame == null) {
			nextFrame = previousFrame;
		}

		if (nextFrame == previousFrame) {
			return nextFrame.getJointTransform();
		}

		float timeDifference = nextFrame.getTimeStamp() - previousFrame.getTimeStamp();
		float progression = (time - previousFrame.getTimeStamp()) / timeDifference;
		progression = (float) ((Math.sin((progression - 0.5f) * Math.PI) + 1 ) / 2);

		QuaternionTransform previous = previousFrame.getJointTransform();
		QuaternionTransform next = nextFrame.getJointTransform();
		QuaternionTransform current = QuaternionTransform.interpolate(previous, next, progression);
		return current;
	}

	public boolean hasKeyFrames() {
		return this.keyFrames.size() > 0;
	}
	
	public int getNumberOfKeyFrames() {
		return this.keyFrames.size();
	}
	
	private KeyFrame getTemporarayKeyFrame(float time) {
		for (KeyFrame f : this.temporaryKeyFrames) {
			if (f.getTimeStamp() == time) {
				return f;
			}
		}
		return null;
	}

	private KeyFrame getKeyFrame(float time) {
		for (KeyFrame f : this.keyFrames) {
			if (f.getTimeStamp() == time) {
				return f;
			}
		}
		return null;
	}

	public int getJointID() {
		return jointID;
	}

	public void sortKeyframes() {
		Comparator<KeyFrame> sorter = new Comparator<KeyFrame>() {
			@Override
			public int compare(KeyFrame k1, KeyFrame k2) {
				return ((Float) k1.getTimeStamp()).compareTo(k2.getTimeStamp());
			}
		};
		Collections.sort(this.keyFrames, sorter);
	}

//	public void addTemporaryKeyFrame(JointTransform value, float time) {
//		KeyFrame frame = new KeyFrame(time, value);
//		temporaryKeyFrames.add(frame);
//		if (this.getKeyFrame(frame.getTimeStamp()) == null) {
//			keyFrames.add(frame);
//		}
//		this.sortKeyframes();
//	}
//
//	public void deleteTemporaryKeyFrames() {
//		for (int i = 0; i < this.temporaryKeyFrames.size(); i++) {
//			KeyFrame frame = this.temporaryKeyFrames.get(i);
//			this.temporaryKeyFrames.remove(frame);
//			if (this.keyFrames.contains(frame)) {
//				this.keyFrames.remove(frame);
//			}
//		}
//	}
}
