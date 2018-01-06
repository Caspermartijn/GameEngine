package utils.recourceProccesing;

public class RequestProcessor
  extends Thread
{
  private static RequestProcessor PROCESSOR = new RequestProcessor();
  private RequestQueue requestQueue = new RequestQueue();
  private boolean running = true;
  
  public static void sendRequest(ResourceRequest request)
  {
    PROCESSOR.addRequestToQueue(request);
  }
  
  public static void cleanUp()
  {
    PROCESSOR.kill();
  }
  
  public synchronized void run()
  {
    while ((this.running) || (this.requestQueue.hasRequests())) {
      if (this.requestQueue.hasRequests()) {
        this.requestQueue.acceptNextRequest().doResourceRequest();
      } else {
        try
        {
          wait();
        }
        catch (InterruptedException e)
        {
          e.printStackTrace();
        }
      }
    }
  }
  
  private void kill()
  {
    this.running = false;
    indicateNewRequests();
  }
  
  private synchronized void indicateNewRequests()
  {
    notify();
  }
  
  private RequestProcessor()
  {
    start();
  }
  
  private void addRequestToQueue(ResourceRequest request)
  {
    boolean isPaused = !this.requestQueue.hasRequests();
    this.requestQueue.addRequest(request);
    if (isPaused) {
      indicateNewRequests();
    }
  }
}
