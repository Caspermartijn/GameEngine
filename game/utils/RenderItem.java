package utils;

import java.util.ArrayList;
import java.util.List;

public abstract class RenderItem implements IRenderItem{

	public static List<RenderItem> renderItems = new ArrayList<RenderItem>();
	
	public RenderItem() {
		renderItems.add(this);
	}
	
	public static void renderItems() {
		for(RenderItem item : renderItems) {
			item.render();
		}
	} 
	
}
