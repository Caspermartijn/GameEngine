package animation;

import java.util.HashMap;
import java.util.Map.Entry;

import engine.Display;

public class Animation {

	private final float length;
	private HashMap<Integer, JointAnimation> jointAnimations = new HashMap<>();

	private float time;
	private boolean playing;
	private boolean looping;
	private String name;
	private float speedFactor = 1f;

	public Animation(float lengthInSeconds, String name) {
		this.length = lengthInSeconds;
		this.name = name;
	}

	public void update() {
		if (!playing) {
			return;
		}

		// System.out.println(time);

		time += Display.getFrameTime() * speedFactor;
		if (time > length) {
			for (JointAnimation anim : this.jointAnimations.values()) {
//				anim.deleteTemporaryKeyFrames();
			}
			if (looping) {
				time = 0;
			} else {
				stop();
			}
		}
	}

	public void setSpeedFactor(float newFactor) {
		this.speedFactor = newFactor;
	}

	public String getName() {
		return name;
	}

	public void play() {
		playing = true;
		time = 0;
	}

	public void stop() {
		playing = false;
		time = 0;
//		for (JointAnimation anim : this.jointAnimations.values()) {
//			anim.deleteTemporaryKeyFrames();
//		}
	}

	public boolean isPlaying() {
		return playing;
	}

	public void setLooping(boolean looping) {
		this.looping = looping;
	}

	public float getLength() {
		return length;
	}

	public float getTime() {
		return time;
	}

	public void setTime(float time) {
		this.time = time;
	}

	public void addJointAnimation(JointAnimation anim) {
		this.jointAnimations.put(anim.getJointID(), anim);
		anim.sortKeyframes();
	}

	public AnimationPose getTransforms() {
		AnimationPose pose = new AnimationPose();
		for (Entry<Integer, JointAnimation> jointAnimation : this.jointAnimations.entrySet()) {
			pose.set(jointAnimation.getKey(), jointAnimation.getValue().getTransform(this.time));
		}
		return pose;
	}

//	public void addTemporaryKeyFrames(int index, JointTransform value, float time) {
//		if (jointAnimations.get(index) != null)
//			if (-time >= 0) {
//				jointAnimations.get(index).addTemporaryKeyFrame(value, time);
//			} else if (jointAnimations.get(index).getNumberOfKeyFrames() > 1) {
//				jointAnimations.get(index).addTemporaryKeyFrame(value, time);
//			}
//	}
}
