package audio;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.lwjgl.openal.AL10;

public class SoundLoader {
	private static List<Integer> buffers = new ArrayList<Integer>();

	protected static void doInitialSoundLoad(Sound sound) {
		try {
			System.out.println("Loading " + sound.getSoundFile());
			WavDataStream stream = WavDataStream.openWavStream(sound.getSoundFile(), 100000);
			sound.setTotalBytes(stream.getTotalBytes());
			ByteBuffer byteBuffer = stream.loadNextData();
			int bufferID = generateBuffer();
			loadSoundDataIntoBuffer(bufferID, byteBuffer, stream.alFormat, stream.sampleRate);
			sound.setBuffer(bufferID, byteBuffer.limit());
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Couldn't load sound file " + sound.getSoundFile());
		}
	}

	protected static int generateBuffer() {
		int bufferID = AL10.alGenBuffers();
		buffers.add(Integer.valueOf(bufferID));
		return bufferID;
	}

	protected static void deleteBuffer(Integer bufferID) {
		buffers.remove(bufferID);
		AL10.alDeleteBuffers(bufferID.intValue());
		if (AL10.alGetError() != 0) {
			System.err.println("Problem deleting sound buffer.");
		}
	}

	@SuppressWarnings("rawtypes")
	protected static void cleanUp() {
		for (Iterator localIterator = buffers.iterator(); localIterator.hasNext();) {
			int buffer = ((Integer) localIterator.next()).intValue();
			AL10.alDeleteBuffers(buffer);
		}
		if (AL10.alGetError() != 0) {
			System.err.println("Problem deleting sound buffers.");
		}
	}

	protected static void loadSoundDataIntoBuffer(int bufferID, ByteBuffer data, int format, int sampleRate) {
		AL10.alBufferData(bufferID, format, data, sampleRate);
		int error = AL10.alGetError();
		if (error != 0) {
			System.err.println("Problem loading sound data into buffer. " + error);
		}
	}
}
