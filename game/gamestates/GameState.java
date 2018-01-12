package gamestates;

import java.util.HashMap;

public abstract class GameState {

	public static HashMap<String,GameState> gameStates = new HashMap<String,GameState>();
	
	public static GameState currentState;
	
	public abstract void render();
	
	public abstract void start();
	
	private String gamestateName;

	public GameState(String gamestateName) {
		this.gamestateName = gamestateName;
		GameState.gameStates.put(gamestateName, this);
		if(currentState == null) {
			currentState = this;
		}
	}

	public String getGameStateName() {
		return gamestateName;
	}

	
	public void setCurrentState() {
		GameState.currentState = this;
		start();
	}
}
