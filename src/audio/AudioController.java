package audio;

import org.lwjgl.util.vector.Vector3f;

public class AudioController
{
  private SoundSource source;
  private boolean active = true;
  private boolean fading = false;
  private float finalVolume;
  private float fadeFactor = 1.0F;
  
  protected AudioController(SoundSource source)
  {
    this.source = source;
  }
  
  protected void stop()
  {
    if (this.active) {
      this.source.stop();
    }
  }
  
  protected void fadeOut()
  {
    this.fading = true;
    this.finalVolume = this.source.getVolume();
  }
  
  protected boolean update(float delta)
  {
    if (this.active) {
      updateActiveController(delta);
    }
    return this.active;
  }
  
  protected void setPosition(Vector3f position)
  {
    if (this.active) {
      this.source.setPosition(position);
    }
  }
  
  protected float getVolume()
  {
    return this.source.getVolume();
  }
  
  protected boolean isActive()
  {
    return this.active;
  }
  
  protected void setInactive()
  {
    this.active = false;
  }
  
  private void updateActiveController(float delta)
  {
    if (this.fading) {
      updateFadingOut(delta);
    }
  }
  
  private void updateFadingOut(float delta)
  {
    this.fadeFactor -= delta / 2.0F;
    this.source.setVolume(this.finalVolume * this.fadeFactor);
    if (this.fadeFactor <= 0.0F) {
      this.source.stop();
    }
  }
}
