package audio;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class Streamer
{
  private SoundSource source;
  private AudioController controller;
  private WavDataStream stream;
  private boolean initialBufferPlaying = true;
  private List<Integer> unusedBuffers = new ArrayList<Integer>();
  private List<Integer> bufferQueue = new ArrayList<Integer>();
  
  protected Streamer(Sound sound, SoundSource source, AudioController controller)
    throws Exception
  {
    System.out.println("Streaming " + sound.getSoundFile());
    this.source = source;
    this.controller = controller;
    this.stream = WavDataStream.openWavStream(sound.getSoundFile(), 100000);
    this.stream.setStartPoint(sound.getBytesRead());
    for (int i = 0; i < 2; i++) {
      this.unusedBuffers.add(Integer.valueOf(SoundLoader.generateBuffer()));
    }
  }
  
  protected void delete()
  {
    this.stream.close();
    for (Integer buffer : this.bufferQueue) {
      SoundLoader.deleteBuffer(buffer);
    }
    for (Integer buffer : this.unusedBuffers) {
      SoundLoader.deleteBuffer(buffer);
    }
  }
  
  protected boolean update()
  {
    if (!this.controller.isActive()) {
      return false;
    }
    if ((!this.stream.hasEnded()) && (this.source.isPlaying())) {
      if (!this.unusedBuffers.isEmpty()) {
        queueUnusedBuffer();
      } else if (isTopBufferFinished()) {
        refillTopBuffer();
      }
    }
    return this.controller.isActive();
  }
  
  private void queueUnusedBuffer()
  {
    int buffer = ((Integer)this.unusedBuffers.remove(0)).intValue();
    loadNextDataIntoBuffer(buffer);
    queueBuffer(buffer);
  }
  
  private void refillTopBuffer()
  {
    int topBuffer = unqueueTopBuffer();
    loadNextDataIntoBuffer(topBuffer);
    queueBuffer(topBuffer);
  }
  
  private void loadNextDataIntoBuffer(int buffer)
  {
    ByteBuffer data = this.stream.loadNextData();
    SoundLoader.loadSoundDataIntoBuffer(buffer, data, this.stream.alFormat, this.stream.sampleRate);
  }
  
  private boolean isTopBufferFinished()
  {
    int finishedBufferCount = this.source.getFinishedBuffersCount();
    if ((finishedBufferCount > 0) && (this.initialBufferPlaying))
    {
      finishedBufferCount--;
      this.source.unqueue();
      this.initialBufferPlaying = false;
    }
    return finishedBufferCount > 0;
  }
  
  private int unqueueTopBuffer()
  {
    int topBuffer = ((Integer)this.bufferQueue.remove(0)).intValue();
    this.source.unqueue();
    return topBuffer;
  }
  
  private void queueBuffer(int buffer)
  {
    if (this.source.isPlaying())
    {
      this.source.queue(buffer);
      this.bufferQueue.add(Integer.valueOf(buffer));
    }
  }
}
