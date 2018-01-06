package music;

import audio.Sound;

public class MusicTrack
{
  private final Sound sound;
  private final String name;
  private boolean locked;
  
  public MusicTrack(Sound sound, String name, boolean locked)
  {
    this.sound = sound;
    this.name = name;
    this.locked = locked;
  }
  
  public Sound getSound()
  {
    return this.sound;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public boolean isLocked()
  {
    return this.locked;
  }
}
