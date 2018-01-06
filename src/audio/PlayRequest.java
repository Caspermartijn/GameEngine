package audio;

import org.lwjgl.util.vector.Vector3f;

public class PlayRequest
{
  private Vector3f position = new Vector3f(0.0F, 0.0F, 0.0F);
  private float innerRange = 1.0F;
  private float outerRange = 1.0F;
  private boolean systemSound = true;
  private boolean loop = false;
  private float volume = 1.0F;
  private Sound sound;
  
  private PlayRequest(Sound sound, float volume)
  {
    this.sound = sound;
    this.volume = (volume * SoundMaestro.SOUND_VOLUME);
  }
  
  protected Vector3f getPosition()
  {
    return this.position;
  }
  
  protected float getInnerRange()
  {
    return this.innerRange;
  }
  
  protected float getOuterRange()
  {
    return this.outerRange;
  }
  
  protected boolean isSystemSound()
  {
    return this.systemSound;
  }
  
  protected float getVolume()
  {
    return this.volume;
  }
  
  protected Sound getSound()
  {
    return this.sound;
  }
  
  protected void setLooping(boolean loop)
  {
    this.loop = loop;
  }
  
  protected boolean isLooping()
  {
    return this.loop;
  }
  
  protected static PlayRequest newSystemPlayRequest(Sound systemSound)
  {
    return new PlayRequest(systemSound, 1.0F);
  }
  
  protected static PlayRequest new3dSoundPlayRequest(Sound sound, float volume, Vector3f position, float innerRange, float outerRange)
  {
    PlayRequest request = new PlayRequest(sound, volume);
    request.systemSound = false;
    request.innerRange = (innerRange < 1.0F ? 1.0F : innerRange);
    request.outerRange = outerRange;
    request.position = position;
    return request;
  }
}
