package utils.glRequestProcessing;

public class GlRequestProcessor
{
  @SuppressWarnings("unused")
private static final float MAX_TIME_MILLIS = 8.0F;
  private static GlRequestQueue requestQueue = new GlRequestQueue();
  
  public static void sendRequest(GlRequest request)
  {
    requestQueue.addRequest(request);
  }
  
  public static void dealWithTopRequests()
  {
    float remainingTime = 8000000.0F;
    long start = System.nanoTime();
    while (requestQueue.hasRequests())
    {
      requestQueue.acceptNextRequest().executeGlRequest();
      long end = System.nanoTime();
      long timeTaken = end - start;
      remainingTime -= (float)timeTaken;
      start = end;
      if (remainingTime < 0.0F) {
        break;
      }
    }
  }
  
  public static void completeAllRequests()
  {
    while (requestQueue.hasRequests()) {
      requestQueue.acceptNextRequest().executeGlRequest();
    }
  }
}
