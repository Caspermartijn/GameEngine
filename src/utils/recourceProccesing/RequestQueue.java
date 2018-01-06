package utils.recourceProccesing;

import java.util.ArrayList;
import java.util.List;

public class RequestQueue {
	private List<ResourceRequest> requestQueue = new ArrayList<ResourceRequest>();

	public synchronized void addRequest(ResourceRequest request) {
		this.requestQueue.add(request);
	}

	public synchronized ResourceRequest acceptNextRequest() {
		return (ResourceRequest) this.requestQueue.remove(0);
	}

	public synchronized boolean hasRequests() {
		return !this.requestQueue.isEmpty();
	}
}
