package audio;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import org.lwjgl.BufferUtils;

import utils.SourceFile;

public class WavDataStream {
	final int alFormat;
	final int sampleRate;
	final int totalBytes;
	final int bytesPerFrame;
	private final int chunkSize;
	private final AudioInputStream audioStream;
	private final ByteBuffer buffer;
	private final byte[] data;
	private int totalBytesRead = 0;

	private WavDataStream(AudioInputStream stream, int chunkSize) {
		this.audioStream = stream;
		this.chunkSize = chunkSize;
		AudioFormat format = stream.getFormat();
		this.alFormat = getOpenAlFormat(format.getChannels(), format.getSampleSizeInBits());
		this.buffer = BufferUtils.createByteBuffer(chunkSize);
		this.data = new byte[chunkSize];
		this.sampleRate = ((int) format.getSampleRate());
		this.bytesPerFrame = format.getFrameSize();
		this.totalBytes = ((int) (stream.getFrameLength() * this.bytesPerFrame));
	}

	protected void setStartPoint(int bytesRead) {
		this.totalBytesRead = bytesRead;
		try {
			this.audioStream.read(this.data, 0, bytesRead);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected ByteBuffer loadNextData() {
		try {
			int bytesRead = this.audioStream.read(this.data, 0, this.chunkSize);
			this.totalBytesRead += bytesRead;
			this.buffer.clear();
			this.buffer.put(this.data, 0, bytesRead);
			this.buffer.flip();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Couldn't read more bytes from audio stream!");
		}
		return this.buffer;
	}

	protected boolean hasEnded() {
		return this.totalBytesRead >= this.totalBytes;
	}

	protected int getTotalBytes() {
		return this.totalBytes;
	}

	protected void close() {
		try {
			this.audioStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected static WavDataStream openWavStream(SourceFile wavFile, int chunkSize) throws Exception {
		InputStream bufferedInput = new BufferedInputStream(Class.class.getResourceAsStream(wavFile.getPath()));
		AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedInput);
		WavDataStream wavStream = new WavDataStream(audioStream, chunkSize);
		return wavStream;
	}

	private static int getOpenAlFormat(int channels, int bitsPerSample) {
		if (channels == 1) {
			return bitsPerSample == 8 ? 4352 : 4353;
		}
		return bitsPerSample == 8 ? 4354 : 4355;
	}
}
