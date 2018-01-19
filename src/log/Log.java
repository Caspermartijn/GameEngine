package log;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import guis.GUI;
import guis.TextComponent;

public class Log {

	public static final Vector3f DEFAULT_COLOR = new Vector3f(1, 1f, 1);

	public static TextComponent[] logTexts = new TextComponent[11];

	public static List<LogItem> log = new ArrayList<LogItem>();

	private static boolean inited = false;

	public static void init(GUI gui) {
		inited = true;
		TextComponent log_1 = new TextComponent(gui, "", "candara", 0.75f, 200, false);
		TextComponent log_2 = new TextComponent(gui, "", "candara", 0.75f, 200, false);
		TextComponent log_3 = new TextComponent(gui, "", "candara", 0.75f, 200, false);
		TextComponent log_4 = new TextComponent(gui, "", "candara", 0.75f, 200, false);
		TextComponent log_5 = new TextComponent(gui, "", "candara", 0.75f, 200, false);
		TextComponent log_6 = new TextComponent(gui, "", "candara", 0.75f, 200, false);
		TextComponent log_7 = new TextComponent(gui, "", "candara", 0.75f, 200, false);
		TextComponent log_8 = new TextComponent(gui, "", "candara", 0.75f, 200, false);
		TextComponent log_9 = new TextComponent(gui, "", "candara", 0.75f, 200, false);
		TextComponent log_10 = new TextComponent(gui, "", "candara", 0.75f, 200, false);
		TextComponent log_11 = new TextComponent(gui, "", "candara", 0.75f, 200, false);
		logTexts[0] = log_1;
		logTexts[1] = log_2;
		logTexts[2] = log_3;
		logTexts[3] = log_4;
		logTexts[4] = log_5; 
		logTexts[5] = log_6;
		logTexts[6] = log_7;
		logTexts[7] = log_8;
		logTexts[8] = log_9;
		logTexts[9] = log_10;
		logTexts[10] = log_11;

		if (log.size() == 0) {
			initLines();
		}
		float f = 0;
		for (TextComponent text : logTexts) {
			text.setPosition(0.3f, -0.009f + f);
			f += 0.02f;
			text.setColor(0, 0.25f, 1, 1);
		}

	}

	public static void initLines() {
		String s = "";
		for (int i = 0; i < 11; i++) {
			s = s + " ";
			log.add(new LogItem(s));
		}
	}

	public static void error(String s, boolean out) {
		if (out) {
			System.err.println(s);
		}
		if (log.size() == 0) {
			initLines();
		}

		log.remove(0);
		log.add(new LogItem(s, new Vector3f(1, 0.01f, 0.01f)));
		update();
	}

	public static void append(String s, boolean out, Vector3f color) {

		if (out) {
			System.out.println(s);
		}
		if (log.size() == 0) {
			initLines();
		}

		log.remove(0);
		log.add(new LogItem(s, color));

		update();
	}

	public static void append(String s, boolean out) {
		if (out) {
			System.out.println(s);
		}
		if (log.size() == 0) {
			initLines();
		}

		log.remove(0);
		log.add(new LogItem(s));

		update();
	}

	public static void append(boolean out) {
		String s = " ";
		if (out) {
			System.out.println(s);
		}
		if (log.size() == 0) {
			initLines();
		}

		log.remove(0);
		log.add(new LogItem(s));
		update();
	}

	private static void update() {
		if (inited) {
			for (int i = 0; i < 11; i++) {
				logTexts[i].setText(log.get(i).getString());
				Vector3f c = log.get(i).getColor();
				logTexts[i].setColor(c.x, c.y, c.z, 1f);
			}
		}
	}
}
