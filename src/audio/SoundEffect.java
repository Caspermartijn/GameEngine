package audio;

public class SoundEffect
{
  private Sound sound;
  private float range;
  
  public SoundEffect(Sound sound, float range)
  {
    this.sound = sound;
    this.range = range;
  }
  
  public Sound getSound()
  {
    return this.sound;
  }
  
  protected float getRange()
  {
    return this.range;
  }
}
