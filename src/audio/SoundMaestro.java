package audio;

import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;

import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.openal.AL.*;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.util.vector.Vector3f;

import music.MusicPlayer;
import utils.SourceFile;

public class SoundMaestro {
	public static SourceFile RES_FOLDER = new SourceFile("res");

	private static long device;
	private static long context;

	public static final SourceFile SOUND_FOLDER = new SourceFile(RES_FOLDER, "sounds");
	public static float SOUND_VOLUME = 1.0F;
	private static SourcePoolManager sourcePool;
	private static AudioListener listener;
	private static MusicPlayer musicPlayer;

	public static void init(AudioListener theListener) {
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
		AL10.alGetError();
		AL10.alDistanceModel(53252);
		StreamManager.STREAMER.start();
		sourcePool = new SourcePoolManager();
		listener = theListener;
		musicPlayer = new MusicPlayer();
	}

	public static void init() throws Exception {
		SoundMaestro.device = alcOpenDevice((ByteBuffer) null);
		if (device == 0) {
			throw new IllegalStateException("Failed to open the default OpenAL device.");
		}
		ALCCapabilities deviceCaps = ALC.createCapabilities(device);
		SoundMaestro.context = alcCreateContext(device, (IntBuffer) null);
		if (context == 0) {
			throw new IllegalStateException("Failed to create OpenAL context.");
		}
		alcMakeContextCurrent(context);
		createCapabilities(deviceCaps);
	}

	public static AudioController playSystemSound(Sound sound) {
		if (!sound.isLoaded()) {
			return null;
		}
		return sourcePool.play(PlayRequest.newSystemPlayRequest(sound));
	}

	public static AudioListener getListener() {
		return listener;
	}

	public static MusicPlayer getMusicPlayer() {
		return musicPlayer;
	}

	public static void update(float delta) {
		Vector3f position = listener.getPosition();
		AL10.alListener3f(4100, position.x, position.y, position.z);
		musicPlayer.update(delta);
		sourcePool.update();
	}

	public static void cleanUp() {
		StreamManager.STREAMER.kill();
		sourcePool.cleanUp();
		musicPlayer.cleanUp();
		SoundLoader.cleanUp();
	}

	protected static AudioController play3DSound(PlayRequest playRequest) {
		if (!playRequest.getSound().isLoaded()) {
			return null;
		}
		return sourcePool.play(playRequest);
	}
}
