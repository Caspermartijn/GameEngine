package main;

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

	//Audio settings
	public static int master_gain = 50;
	public static int music_gain = 50;
	public static int effects_gain = 50;
	//Audio settings
}
