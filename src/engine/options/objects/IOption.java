package engine.options.objects;

public interface IOption {

	void enable();
	void disable();
	
	boolean isEnabled();
	
	int getValue();
	
}
