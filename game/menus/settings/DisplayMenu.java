package menus.settings;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

import guis.ButtonComponent;
import guis.GUI;
import guis.TextComponent;
import main.Settings;
import main.settings.Display;

public class DisplayMenu extends GUI {

	public DisplayMenu() {
		super.setPosition(0.0f, 0.0f);
		images();
		buttons();
		texts();
	}

	private void images() {

	}

	private float left_1_x = 0.41f + 0.0025f;
	private float left_1_y = 0.2f + 0.15f + 0.005f;

	private void texts() {
		TextComponent displayModeText = new TextComponent(this, "Display mode", "candara", 1f, 200, false);
		displayModeText.setPosition(left_1_x, left_1_y + 0.005f);

		TextComponent windowSizeText = new TextComponent(this, "Window size", "candara", 1f, 200, false);
		windowSizeText.setPosition(left_1_x, left_1_y + 0.0365f + 0.005f);

		TextComponent vSyncText = new TextComponent(this, "vSync", "candara", 1f, 200, false);
		vSyncText.setPosition(left_1_x, left_1_y + 0.0365f + 0.0365f + 0.005f);
	}

	ButtonComponent displayMode;
	ButtonComponent windowSize;
	ButtonComponent vSync;

	private void buttons() {
		displayMode = new ButtonComponent(this, left_1_x + 0.08f, left_1_y, 0.08f, 0.035f);
		displayMode.setOutlineWidth(0.001f);
		displayMode.setOutlineColor(new Vector4f(0.3f, 0.3f, 0.3f, 0.7f));
		displayMode.setBackgroundColor(new Vector4f(0.6f, 0.6f, 0.6f, 1));
		displayMode.setTextPosition(new Vector2f(0, 0.005f));

		windowSize = new ButtonComponent(this, left_1_x + 0.08f, left_1_y + 0.0365f, 0.08f, 0.035f);
		windowSize.setOutlineWidth(0.001f);
		windowSize.setOutlineColor(new Vector4f(0.3f, 0.3f, 0.3f, 0.7f));
		windowSize.setBackgroundColor(new Vector4f(0.6f, 0.6f, 0.6f, 1));
		windowSize.setTextPosition(new Vector2f(0, 0.005f));

		vSync = new ButtonComponent(this, left_1_x + 0.08f, left_1_y + 0.0365f + 0.0365f + 0.0005f, 0.08f, 0.035f);
		vSync.setOutlineWidth(0.001f);
		vSync.setOutlineColor(new Vector4f(0.3f, 0.3f, 0.3f, 0.7f));
		vSync.setBackgroundColor(new Vector4f(0.6f, 0.6f, 0.6f, 1));
		vSync.setTextPosition(new Vector2f(0, 0.005f));

		displayMode.setClickEvent(new Runnable() {

			@Override
			public void run() {
				if (Settings.displayMode == Display.fullscreen) {
					Settings.displayMode = Display.windowed;
				} else {
					Settings.displayMode = Display.fullscreen;
				}
			}

		});

		windowSize.setClickEvent(new Runnable() {

			@Override
			public void run() {
				if (Settings.windowSize == Display.Size_1080) {
					Settings.windowSize = Display.Size_720;
				} else {
					Settings.windowSize = Display.Size_1080;
				}
			}

		});

		vSync.setClickEvent(new Runnable() {

			@Override
			public void run() {
				if (Settings.vsync == Display.vSync_true) {
					Settings.vsync = Display.vSync_false;
				} else {
					Settings.vsync = Display.vSync_true;
				}
			}

		});
	}

	public void updateText() {
		displayMode.setText(Display.getSettingString(Settings.displayMode), "candara", 1f);
		windowSize.setText(Display.getSettingString(Settings.windowSize), "candara", 1f);
		vSync.setText(Display.getSettingString(Settings.vsync), "candara", 1f);
	}

	@Override
	public void renderComponents() {
		updateText();
		super.renderComponents();
	}

}
