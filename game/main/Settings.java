package main;

import java.io.File;
import java.io.IOException;

import files.EngineFileConfig;
import main.settings.Display;
import main.settings.Graphics;

public class Settings {

	// Display settings
	public static Display displayMode = Display.windowed;
	public static Display windowSize = Display.Size_720;
	public static Display vsync = Display.vSync_true;
	// Display settings

	// Graphics settings
	public static Graphics textures = Graphics.Graphics_Medium;
	public static Graphics terrain = Graphics.Graphics_Medium;
	public static Graphics shadows = Graphics.Graphics_Medium;
	public static Graphics postproccesing = Graphics.Graphics_Medium;
	public static Graphics water = Graphics.Graphics_Medium;
	// Graphics settings

	// Audio settings
	public static int master_gain = 50;
	public static int music_gain = 50;
	public static int effects_gain = 50;
	// Audio settings

	private static EngineFileConfig config;

	public static int[] windowSizeValues;
	public static boolean fullscreenValue;
	public static boolean vsyncValue;
	
	public static void load() {
		File f = new File("settings.cnfg");
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			config = new EngineFileConfig("", "settings.cnfg");
		} else {
			config = new EngineFileConfig("", "settings.cnfg");

			displayMode = Display.getCofigSettingEnum(config.getString("displaymode"));
			windowSize = Display.getCofigSettingEnum(config.getString("windowsize"));
			vsync = Display.getCofigSettingEnum(config.getString("vsync"));

			textures = Graphics.getCofigSettingEnum(config.getString("textures"));
			terrain = Graphics.getCofigSettingEnum(config.getString("terrain"));
			shadows = Graphics.getCofigSettingEnum(config.getString("shadows"));
			postproccesing = Graphics.getCofigSettingEnum(config.getString("postproccesing"));
			water = Graphics.getCofigSettingEnum(config.getString("water"));
		}
		
		if (Settings.windowSize == main.settings.Display.Size_720) {
			windowSizeValues = new int[] { 1280, 720 };
		} else {
			windowSizeValues = new int[] { 1920, 1080 };
		}

		if (Settings.displayMode == main.settings.Display.fullscreen) {
			fullscreenValue = true;
			windowSizeValues = new int[] { 1920, 1080 };
		} else {
			fullscreenValue = false;
		}

		if (Settings.vsync == main.settings.Display.vSync_true) {
			vsyncValue = true;
		} else {
			vsyncValue = false;
		}
	}

	public static void save() {
		config.set("displaymode", Display.getCnofigSettingString(displayMode));
		config.set("windowsize", Display.getCnofigSettingString(windowSize));
		config.set("vsync", Display.getCnofigSettingString(vsync));

		config.set("textures", Graphics.getCnofigSettingString(textures));
		config.set("terrain", Graphics.getCnofigSettingString(terrain));
		config.set("shadows", Graphics.getCnofigSettingString(shadows));
		config.set("postproccesing", Graphics.getCnofigSettingString(postproccesing));
		config.set("water", Graphics.getCnofigSettingString(water));

		try {
			config.saveConfig();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
