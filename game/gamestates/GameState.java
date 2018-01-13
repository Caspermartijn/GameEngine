package gamestates;

import java.util.HashMap;

public abstract class GameState {

	public static HashMap<String,GameState> gameStates = new HashMap<String,GameState>();
	
	public static GameState currentState;
	
	public abstract void render();
	
	public abstract void start();
	public abstract void stop();
	
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

	
	public void switchState() {
		GameState.currentState.stop();
		GameState.currentState = this;
		start();
	}
	
	public static void renderGameState() {
		GameState.currentState.render();
	}

	public static void switchGameState(String gamestate) {
		GameState.gameStates.get(gamestate).switchState();
	}
}
