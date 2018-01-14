package main.settings;

public enum Display {
	Size_1080, Size_720, fullscreen, windowed, vSync_true, vSync_false;

	public static String getSettingString(Display dis) {
		String returnS = "";
		switch (dis) {
		case Size_1080:
			returnS = "1920x1080";
			break;
		case Size_720:
			returnS = "1280x720";
			break;
		case fullscreen:
			returnS = "Fullscreen";
			break;
		case vSync_false:
			returnS = "Disabled";
			break;
		case vSync_true:
			returnS = "Enabled";
			break;
		case windowed:
			returnS = "Windowed";
			break;
		}
		return returnS;
	}
}
