package audio;

import java.util.HashMap;

public class Sound2DMaster {

	public static HashMap<String, Sound2D> songs = new HashMap<String, Sound2D>();

	private static float volume = 50;

	public static void loadSound(String name, String path) throws Exception {
		songs.put(name, new Sound2D(path));
	}

	public static Sound2D getSong(String name) {
		return songs.get(name);
	}

	public static void play(String string) {
		songs.get(string).play();
	}

	public static void setVolume(String name, float volume) {
		songs.get(name).setVolume(volume);
	}

	public static void setGeneralVolume(float volume) {
		Sound2DMaster.volume = volume;
		for (Sound2D song : songs.values()) {
			song.setVolume(Sound2DMaster.volume);
		}
	}

}
