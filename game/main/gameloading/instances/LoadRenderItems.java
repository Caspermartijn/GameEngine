package main.gameloading.instances;

import org.lwjgl.glfw.GLFW;

import engine.Keyboard;
import gamestates.GamePerspective;
import main.gameloading.LoadItemMedium;
import utils.RenderItem;

public class LoadRenderItems extends LoadItemMedium {

	@Override
	public void load() {
		// ================================================================
		// ===================ESCAPE BUTTON IN MENUS=======================
		new RenderItem() {
			boolean button = false;
			@Override
			public void render() {
				if (!(GamePerspective.currentState.getGameStateName().equalsIgnoreCase("ingame"))) {
					if (!(GamePerspective.currentState.getGameStateName().equalsIgnoreCase("loading"))) {
						if (Keyboard.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
							if (!button) {
								GamePerspective.switchGameState("main_menu");
								button = true;
							}
						} else {
							button = false;
						}
					}
				}
			}
		};
		// ================================================================

	}

}
