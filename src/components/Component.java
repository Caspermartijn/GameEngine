package components;

public interface Component {

	public static enum Type {
		ANIMATION, AUDIO_LISTENER, HITBOX, SOUND_SOURCE, TRACER;
	}
	
	public abstract void update();
	public Type getType();
	
}
