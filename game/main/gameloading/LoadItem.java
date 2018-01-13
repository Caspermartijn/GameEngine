package main.gameloading;

public abstract class LoadItem {

	public abstract void load();

	public Importance IMPORTANCE;

	public LoadItem(Importance importance) {
		IMPORTANCE = importance;
		load();
	}

	public Importance getImportance() {
		return IMPORTANCE;
	}

}
