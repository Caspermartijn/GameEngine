package audio;

import utils.SmoothFloat;
import utils.maths.Maths;

import java.util.List;
import org.lwjgl.util.vector.Vector3f;

public class AmbientNode {
	@SuppressWarnings("unused")
	private static final float RADIUS_CHANGE_AGIL = 0.5F;
	@SuppressWarnings("unused")
	private static final float RANGE_THRESHOLD = 1.2F;
	private Vector3f position;
	private SmoothFloat innerRadius;
	private SmoothFloat fadeOutRadius;
	private List<Sound> sounds;
	private float volume = 1.0F;
	private boolean active = false;
	private AudioController controller = null;
	private Sound lastPlayed = null;

	public AmbientNode(Vector3f position, float innerRange, float fadeOutRange, List<Sound> sounds) {
		this.position = position;
		this.innerRadius = new SmoothFloat(innerRange, 0.5F);
		this.fadeOutRadius = new SmoothFloat(fadeOutRange, 0.5F);
		this.sounds = sounds;
	}

	public void update(float delta) {
		updateValues(delta);
		float distance = getDistanceFromListener();
		if ((!this.active) && (distance <= getRange()) && (!this.sounds.isEmpty())) {
			playNewSound();
		} else if (this.active) {
			updateActiveNode(distance, delta);
		}
	}

	public void setVolume(float targetVolume) {
		this.volume = targetVolume;
	}

	public void setRanges(float innerRange, float fadeOutRange) {
		this.innerRadius.setTarget(innerRange);
		this.fadeOutRadius.setTarget(fadeOutRange);
	}

	public float getRange() {
		return this.innerRadius.get() + this.fadeOutRadius.get();
	}

	public void setSoundList(List<Sound> sounds) {
		this.sounds = sounds;
		if (this.controller != null) {
			this.controller.fadeOut();
		}
	}

	public void setSound(Sound sound) {
		this.sounds.clear();
		this.sounds.add(sound);
		if (this.controller != null) {
			this.controller.fadeOut();
		}
	}

	public void clear() {
		this.sounds.clear();
		if (this.controller != null) {
			System.out.println("fading");
			this.controller.fadeOut();
		}
	}

	public void addSound(Sound sound) {
		this.sounds.add(sound);
	}

	public void removeSound(Sound sound) {
		this.sounds.remove(sound);
	}

	public void setPosition(float x, float y, float z) {
		this.position.set(x, y, z);
		if (this.controller != null) {
			this.controller.setPosition(this.position);
		}
	}

	private void updateActiveNode(float distance, float delta) {
		if (distance >= getRange() * 1.2F) {
			this.controller.stop();
			this.active = false;
		} else {
			boolean stillPlaying = this.controller.update(delta);
			if ((!stillPlaying) && (!this.sounds.isEmpty())) {
				playNewSound();
			}
		}
	}

	private float getDistanceFromListener() {
		Vector3f listenerPos = SoundMaestro.getListener().getPosition();
		return Vector3f.sub(listenerPos, this.position, null).length();
	}

	private void playNewSound() {
		Sound sound = chooseNextSound();
		PlayRequest request = PlayRequest.new3dSoundPlayRequest(sound, this.volume, this.position,
				this.innerRadius.get(), getRange());
		this.controller = SoundMaestro.play3DSound(request);
		this.active = (this.controller != null);
	}

	private Sound chooseNextSound() {
		Sound sound = null;
		int index = Maths.RANDOM.nextInt(this.sounds.size());
		if ((this.sounds.size() > 1) && (sound == this.lastPlayed)) {
			index = (index + 1) % this.sounds.size();
		}
		sound = (Sound) this.sounds.get(index);
		this.lastPlayed = sound;
		return sound;
	}

	private void updateValues(float delta) {
		this.innerRadius.update(delta);
		this.fadeOutRadius.update(delta);
	}
}
