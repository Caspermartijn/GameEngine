package utils.glRequestProcessing;

import java.util.ArrayList;
import java.util.List;

public class GlRequestQueue
{
private List<GlRequest> requestQueue = new ArrayList<GlRequest>();
  
  public synchronized void addRequest(GlRequest request)
  {
    this.requestQueue.add(request);
  }
  
  public synchronized GlRequest acceptNextRequest()
  {
    return (GlRequest)this.requestQueue.remove(0);
  }
  
  public synchronized boolean hasRequests()
  {
    return !this.requestQueue.isEmpty();
  }
}
