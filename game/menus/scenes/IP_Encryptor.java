package menus.scenes;

public class IP_Encryptor {

	public static String encrypt(String[] ip4, String poort) {
		String[] strings = ip4;
		String returnS = "";
		int intI = 0;
		if (true) {
			for (String s : strings) {
				String a = s.replaceAll("1", "a");
				String b = a.replaceAll("2", "b");
				String c = b.replaceAll("3", "c");
				String d = c.replaceAll("4", "d");
				String e = d.replaceAll("5", "e");
				String f = e.replaceAll("6", "f");
				String g = f.replaceAll("7", "g");
				String h = g.replaceAll("8", "h");
				String i = h.replaceAll("9", "i");
				String k = i.replaceAll("0", "k");
				intI++;
				if (intI < strings.length) {
					k = k + "x";
				}
				returnS = returnS + k;
			}
		}
		String a = poort.replaceAll("1", "a");
		String b = a.replaceAll("2", "b");
		String c = b.replaceAll("3", "c");
		String d = c.replaceAll("4", "d");
		String e = d.replaceAll("5", "e");
		String f = e.replaceAll("6", "f");
		String g = f.replaceAll("7", "g");
		String h = g.replaceAll("8", "h");
		String i = h.replaceAll("9", "i");
		String k = i.replaceAll("0", "k");
		returnS = returnS + "y" + k;
		return returnS;
	}

	public static String[] decrypt(String key) {
		String[] ss = new String[2];
		key = key.replaceAll("a", "1");
		key = key.replaceAll("b", "2");
		key = key.replaceAll("c", "3");
		key = key.replaceAll("d", "4");
		key = key.replaceAll("e", "5");
		key = key.replaceAll("f", "6");
		key = key.replaceAll("g", "7");
		key = key.replaceAll("h", "8");
		key = key.replaceAll("i", "9");
		key = key.replaceAll("k", "0");
		ss = key.split(":");
		return ss;
	}

}
