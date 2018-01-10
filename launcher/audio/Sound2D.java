package audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound2D {

	private Clip clip;

	private float dB = 0;
	private float volume = 100;

	public Sound2D(String path) throws Exception {
		clip = AudioSystem.getClip();
		AudioInputStream ais = AudioSystem.getAudioInputStream(Class.class.getResourceAsStream(path));
		clip.open(ais);
	}

	public void play() {
		clip.setFramePosition(0);
		clip.start();
	}

	public void stop() {
		clip.stop();
	}

	public void setVolume(float volume_gain) {
		this.volume = volume_gain;
		FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

		double gain = (volume_gain / 100); // number between 0 and 1 (loudest)
		dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
		volume.setValue(dB);
	}

	public float getVolume() {
		return volume;
	}

	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

}
