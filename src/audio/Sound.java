package audio;

import utils.SourceFile;
import utils.glRequestProcessing.GlRequest;
import utils.glRequestProcessing.GlRequestProcessor;
import utils.recourceProccesing.RequestProcessor;
import utils.recourceProccesing.ResourceRequest;

public class Sound
{
  public final String id;
  private int bufferID;
  private SourceFile file;
  private float volume = 1.0F;
  private boolean loaded = false;
  private int totalBytes;
  private int bytesRead;
  
  private Sound(SourceFile soundFile)
  {
    this.file = soundFile;
    this.id = soundFile.getName().split("\\.")[0];
  }
  
  public static Sound loadSoundNow(SourceFile soundFile)
  {
    Sound sound = new Sound(soundFile);
    SoundLoader.doInitialSoundLoad(sound);
    return sound;
  }
  
  public static Sound loadSoundInBackground(SourceFile soundFile)
  {
    Sound sound = new Sound(soundFile);
    RequestProcessor.sendRequest(new ResourceRequest()
    {
      public void doResourceRequest()
      {
        SoundLoader.doInitialSoundLoad(sound);
      }
    });
    return sound;
  }
  
  public void delete()
  {
    if (!this.loaded) {
      return;
    }
    GlRequestProcessor.sendRequest(new GlRequest()
    {
      public void executeGlRequest()
      {
        SoundLoader.deleteBuffer(Integer.valueOf(Sound.this.bufferID));
      }
    });
    this.loaded = false;
  }
  
  public Sound withVolume(float volume)
  {
    this.volume = volume;
    return this;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public float getVolume()
  {
    return this.volume;
  }
  
  public boolean isLoaded()
  {
    return this.loaded;
  }
  
  protected boolean needsStreaming()
  {
    return this.bytesRead < this.totalBytes;
  }
  
  protected int getBytesRead()
  {
    return this.bytesRead;
  }
  
  protected void setTotalBytes(int totalBytes)
  {
    this.totalBytes = totalBytes;
  }
  
  protected void setBuffer(int buffer, int bytesRead)
  {
    this.bufferID = buffer;
    this.bytesRead = bytesRead;
    this.loaded = true;
  }
  
  protected int getBufferID()
  {
    return this.bufferID;
  }
  
  protected SourceFile getSoundFile()
  {
    return this.file;
  }
}
