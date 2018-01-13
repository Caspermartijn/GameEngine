package gamestates;

import java.util.HashMap;

public abstract class GamePerspective {

	public static HashMap<String,GamePerspective> gameStates = new HashMap<String,GamePerspective>();
	
	public static GamePerspective currentState;
	
	public abstract void render();
	
	public abstract void start();
	public abstract void stop();
	
	private String gamestateName;

	public GamePerspective(String gamestateName) {
		this.gamestateName = gamestateName;
		GamePerspective.gameStates.put(gamestateName, this);
		if(currentState == null) {
			currentState = this;
		}
	}

	public String getGameStateName() {
		return gamestateName;
	}

	
	public void switchState() {
		GamePerspective.currentState.stop();
		GamePerspective.currentState = this;
		start();
	}
	
	public static void renderGameState() {
		GamePerspective.currentState.render();
	}

	public static void switchGameState(String gamestate) {
		GamePerspective.gameStates.get(gamestate).switchState();
	}
}
