package objects;

import java.util.ArrayList;
import java.util.List;

public class AutoPilotProgram {

	private List<AutoPilotCheckPoint> check = new ArrayList<AutoPilotCheckPoint>();

	private int stage = 0;

	public void addCheckPoint(AutoPilotCheckPoint checkp) {
		check.add(checkp);
	}

	public void complete() {
		stage++;
	}

	public AutoPilotCheckPoint getCurrentCheckPoint() {
		if (stage >= check.size()-1) {
			return check.get(check.size() - 1);
		} else {
			return check.get(stage);
		}
	}

	public boolean completed() {
		return stage >= check.size()-1;
	}

}
