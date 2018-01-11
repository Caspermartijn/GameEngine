package main;

import static renderer.textRendering.TextMaster.addText;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import texts.Text;

public class Log {

	public static Text[] logTexts = new Text[11];

	public static List<String> log = new ArrayList<String>();

	private static boolean inited = false;

	public static void init() {
		inited = true;
		float a = 0.005f;
		Text log_1 = new Text("", 0.75f, "candara", new Vector2f(0.2f, 0.00f + a), 200, false);
		Text log_2 = new Text("", 0.75f, "candara", new Vector2f(0.2f, 0.02f + a), 200, false);
		Text log_3 = new Text("", 0.75f, "candara", new Vector2f(0.2f, 0.04f + a), 200, false);
		Text log_4 = new Text("", 0.75f, "candara", new Vector2f(0.2f, 0.06f + a), 200, false);
		Text log_5 = new Text("", 0.75f, "candara", new Vector2f(0.2f, 0.08f + a), 200, false);
		Text log_6 = new Text("", 0.75f, "candara", new Vector2f(0.2f, 0.1f + a), 200, false);
		Text log_7 = new Text("", 0.75f, "candara", new Vector2f(0.2f, 0.12f + a), 200, false);
		Text log_8 = new Text("", 0.75f, "candara", new Vector2f(0.2f, 0.14f + a), 200, false);
		Text log_9 = new Text("", 0.75f, "candara", new Vector2f(0.2f, 0.16f + a), 200, false);
		Text log_10 = new Text("", 0.75f, "candara", new Vector2f(0.2f, 0.18f + a), 200, false);
		Text log_11 = new Text("", 0.75f, "candara", new Vector2f(0.2f, 0.20f + a), 200, false);
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

		for (Text text : logTexts) {
			text.setColor(0, 0.25f, 1);
			addText(text);
		}

	}

	public static void initLines() {
		String s = "";
		for (int i = 0; i < 11; i++) {
			s = s + " ";
			log.add(s);
		}
	}

	public static void append(String s) {

		System.out.println(s);
		if (log.size() == 0) {
			initLines();
		}

		log.remove(0);
		log.add(s);

		if (inited) {

			for (int i = 0; i < 11; i++) {
				logTexts[i].setText(log.get(10 - i));
				logTexts[i].applyChanges();
			}
		}
	}

	public static void append() {
		String s = "                                                                           ";
		System.out.println(s);
		if (log.size() == 0) {
			initLines();
		}

		log.remove(0);
		log.add(s);
		if (inited) {
			for (int i = 0; i < 11; i++) {
				logTexts[i].setText(log.get(10 - i));
				logTexts[i].applyChanges();
			}
		}
	}

}
