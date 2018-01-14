package main.settings;

public enum Graphics {
	Graphics_Ultra, Graphics_High, Graphics_Medium, Graphics_Potatoe;

	public static int getGraphicsImageSize(Graphics dis) {
		int f = 0;
		switch(dis) {
		case Graphics_High:
			f = 1024;
			break;
		case Graphics_Medium:
			f = 512;
			break;
		case Graphics_Potatoe:
			f = 256;
			break;
		case Graphics_Ultra:
			f = 2048;
			break;
		}
		return (int) f;
	}
	
	public static String getCnofigSettingString(Graphics dis) {
		String returnS = "";
		switch (dis) {
		case Graphics_High:
			returnS = "high";
			break;
		case Graphics_Medium:
			returnS = "medium";
			break;
		case Graphics_Potatoe:
			returnS = "potatoe";
			break;
		case Graphics_Ultra:
			returnS = "ultra";
			break;
		}
		return returnS;
	}

	public static Graphics getCofigSettingEnum(String s) {
		Graphics returnEnum = Graphics.Graphics_Potatoe;
		String check = s.toLowerCase();
		switch (check) {
		case "potatoe":
			returnEnum = Graphics.Graphics_Potatoe;
			break;
		case "medium":
			returnEnum = Graphics.Graphics_Medium;
			break;
		case "high":
			returnEnum = Graphics.Graphics_High;
			break;
		case "ultra":
			returnEnum = Graphics.Graphics_Ultra;
			break;
		}
		return returnEnum;
	}
}
