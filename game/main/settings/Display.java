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

	public static String getCnofigSettingString(Display dis) {
		String returnS = "";
		switch (dis) {
		case Size_1080:
			returnS = "1920x1080";
			break;
		case Size_720:
			returnS = "1280x720";
			break;
		case fullscreen:
			returnS = "fullscreen";
			break;
		case vSync_false:
			returnS = "vsyncdisabled";
			break;
		case vSync_true:
			returnS = "vsyncenabled";
			break;
		case windowed:
			returnS = "windowed";
			break;
		}
		return returnS;
	}

	public static Display getCofigSettingEnum(String s) {
		Display returnEnum = Display.fullscreen;
		String check = s.toLowerCase();
		switch (check) {
		case "1920x1080":
			returnEnum = Size_1080;
			break;
		case "1280x720":
			returnEnum = Display.Size_720;

			break;
		case "fullscreen":
			returnEnum = Display.fullscreen;

			break;
		case "vsyncdisabled":
			returnEnum = vSync_false;

			break;
		case "vsyncenabled":
			returnEnum = vSync_true;

			break;
		case "windowed":
			returnEnum = Display.windowed;
			break;
		}
		return returnEnum;
	}
}
