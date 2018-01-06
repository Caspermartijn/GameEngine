package audio;

import org.lwjgl.openal.AL10;
import org.lwjgl.util.vector.Vector3f;

public class SoundSource
{
  private int sourceID;
  private float volume = 1.0F;
  private boolean active = false;
  private AudioController currentController;
  
  public SoundSource()
  {
    this.sourceID = createSource();
    AL10.alSource3f(this.sourceID, 4100, 0.0F, 0.0F, 0.0F);
    AL10.alSource3f(this.sourceID, 4102, 1.0F, 0.0F, 0.0F);
    AL10.alSourcef(this.sourceID, 4129, 0.0F);
    AL10.alSourcef(this.sourceID, 4106, this.volume);
  }
  
  protected void setRange(float radius)
  {
    AL10.alSourcef(this.sourceID, 4128, 1.0F);
    AL10.alSourcef(this.sourceID, 4129, 1.0F);
    AL10.alSourcef(this.sourceID, 4131, radius);
  }
  
  public void setUndiminishing()
  {
    AL10.alSourcef(this.sourceID, 4129, 0.0F);
  }
  
  protected void setRanges(float primaryRadius, float secondaryRadius)
  {
    if (primaryRadius < 1.0F) {
      primaryRadius = 1.0F;
    }
    AL10.alSourcef(this.sourceID, 4128, primaryRadius);
    AL10.alSourcef(this.sourceID, 4129, 1.0F);
    AL10.alSourcef(this.sourceID, 4131, secondaryRadius);
  }
  
  public void setVolume(float newVolume)
  {
    if (newVolume != this.volume)
    {
      AL10.alSourcef(this.sourceID, 4106, newVolume);
      this.volume = newVolume;
    }
  }
  
  public float getVolume()
  {
    return this.volume;
  }
  
  protected void setPosition(Vector3f position)
  {
    AL10.alSource3f(this.sourceID, 4100, position.x, position.y, position.z);
  }
  
  public void loop(boolean loop)
  {
    AL10.alSourcei(this.sourceID, 4103, loop ? 1 : 0);
  }
  
  public AudioController playSound(Sound sound)
  {
    if (!sound.isLoaded()) {
      return null;
    }
    stop();
    this.active = true;
    this.currentController = new AudioController(this);
    if (sound.needsStreaming())
    {
      queue(sound.getBufferID());
      AL10.alSourcei(this.sourceID, 4103, 0);
      StreamManager.STREAMER.stream(sound, this, this.currentController);
    }
    else
    {
      AL10.alSourcei(this.sourceID, 4103, 0);
      AL10.alSourcei(this.sourceID, 4105, sound.getBufferID());
    }
    AL10.alSourcePlay(this.sourceID);
    return this.currentController;
  }
  
  public void stop()
  {
    if (isPlaying()) {
      AL10.alSourceStop(this.sourceID);
    }
    setInactive();
  }
  
  public void setInactive()
  {
    if (this.active)
    {
      AL10.alSourcei(this.sourceID, 4105, 0);
      this.currentController.setInactive();
      for (int i = 0; i < getFinishedBuffersCount(); i++) {
        unqueue();
      }
      this.active = false;
    }
  }
  
  public boolean isPlaying()
  {
    return AL10.alGetSourcei(this.sourceID, 4112) == 4114;
  }
  
  public void delete()
  {
    stop();
    AL10.alDeleteSources(this.sourceID);
  }
  
  protected void queue(int buffer)
  {
    AL10.alSourceQueueBuffers(this.sourceID, buffer);
  }
  
  protected void unqueue()
  {
    AL10.alSourceUnqueueBuffers(this.sourceID);
  }
  
  protected int getFinishedBuffersCount()
  {
    return AL10.alGetSourcei(this.sourceID, 4118);
  }
  
  private static int createSource()
  {
    int sourceID = AL10.alGenSources();
    if (AL10.alGetError() != 0) {
      System.err.println("Problem creating source!");
    }
    return sourceID;
  }
}
