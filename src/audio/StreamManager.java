package audio;

import java.util.ArrayList;
import java.util.List;

public class StreamManager
  extends Thread
{
  protected static final StreamManager STREAMER = new StreamManager();
  public static final int SOUND_CHUNK_MAX_SIZE = 100000;
  private List<Streamer> streamers = new ArrayList<Streamer>();
  private List<Streamer> toRemove = new ArrayList<Streamer>();
  private boolean alive = true;
  
  public void run()
  {
    while (this.alive)
    {
      List<Streamer> safeCopy = new ArrayList<Streamer>(this.streamers);
      for (Streamer streamer : safeCopy) {
        updateStreamer(streamer);
      }
      removeFinishedStreamers();
      pause();
    }
  }
  
  protected void kill()
  {
    this.alive = false;
  }
  
  protected synchronized void stream(Sound sound, SoundSource source, AudioController controller)
  {
    try
    {
      this.streamers.add(new Streamer(sound, source, controller));
    }
    catch (Exception e)
    {
      e.printStackTrace();
      System.err.println("Couldn't open stream for sound " + sound.getSoundFile());
    }
  }
  
  private synchronized void removeFinishedStreamers()
  {
    for (Streamer streamer : this.toRemove)
    {
      this.streamers.remove(streamer);
      streamer.delete();
    }
    this.toRemove.clear();
  }
  
  private void pause()
  {
    try
    {
      Thread.sleep(100L);
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
    }
  }
  
  private void updateStreamer(Streamer streamer)
  {
    boolean stillAlive = streamer.update();
    if (!stillAlive) {
      this.toRemove.add(streamer);
    }
  }
}
