package audio;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SourcePoolManager {
	private List<SoundSource> sourcePool = new ArrayList<SoundSource>();
	private List<SoundSource> usedSources = new ArrayList<SoundSource>();

	protected SourcePoolManager() {
		for (int i = 0; i < 20; i++) {
			this.sourcePool.add(new SoundSource());
		}
	}

	protected AudioController play(PlayRequest playRequest) {
		if (!this.sourcePool.isEmpty()) {
			SoundSource source = (SoundSource) this.sourcePool.remove(0);
			this.usedSources.add(source);
			source.setPosition(playRequest.getPosition());
			source.loop(playRequest.isLooping());
			if (!playRequest.isSystemSound()) {
				source.setRanges(playRequest.getInnerRange(), playRequest.getOuterRange());
			} else {
				source.setUndiminishing();
			}
			Sound sound = playRequest.getSound();
			source.setVolume(playRequest.getVolume() * sound.getVolume());
			AudioController controller = source.playSound(sound);
			return controller;
		}
		return null;
	}

	protected void update() {
		Iterator<SoundSource> iterator = this.usedSources.iterator();
		while (iterator.hasNext()) {
			SoundSource source = (SoundSource) iterator.next();
			if (!source.isPlaying()) {
				iterator.remove();
				source.setInactive();
				this.sourcePool.add(source);
			}
		}
	}

	protected void cleanUp() {
		for (SoundSource source : this.sourcePool) {
			source.delete();
		}
	}
}
