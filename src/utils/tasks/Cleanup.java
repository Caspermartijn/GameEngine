package utils.tasks;

import java.util.ArrayList;
import java.util.List;

public abstract class Cleanup {

	public static List<Cleanup> list = new ArrayList<Cleanup>();
	
	public static void cleanAll() {
		for(Cleanup cleanup : list) {
			cleanup.run();
		}
	}
	
	public Cleanup() {
		Cleanup.list.add(this);
	}
	
	public abstract void run();
	
}
