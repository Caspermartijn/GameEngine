package utils;

public class SmoothFloat
{
  private final float agility;
  private float target;
  private float actual;
  
  public SmoothFloat(float initialValue, float agility)
  {
    this.target = initialValue;
    this.actual = initialValue;
    this.agility = agility;
  }
  
  public void update(float delta)
  {
    float offset = this.target - this.actual;
    float factor = delta * this.agility;
    if (factor > 1.0F) {
      this.actual = this.target;
    } else {
      this.actual += offset * factor;
    }
  }
  
  public void setTarget(float target)
  {
    this.target = target;
  }
  
  public void increaseTarget(float increase)
  {
    this.target += increase;
  }
  
  public void force(float newValue)
  {
    this.actual = newValue;
    this.target = newValue;
  }
  
  public void instantIncrease(float increase)
  {
    this.actual += increase;
  }
  
  public void increaseAll(float increase)
  {
    this.actual += increase;
    this.target += increase;
  }
  
  public float get()
  {
    return this.actual;
  }
}
