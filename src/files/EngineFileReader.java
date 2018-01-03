package files;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import files.configData.*;

public class EngineFileReader {

	protected EngineFileConfigData configdata;

	public EngineFileReader(EngineFile engineFileF) {
		generateEmpty();
		handle(getLines(engineFileF));
	}

	public EngineFileReader(String path) {
		File f = new File(path);
		generateEmpty();
		if (f.exists()) {
			handle(getLines(f));
		}
	}

	protected void generateEmpty() {
		configdata = new EngineFileConfigData(new HashMap<String, ConfigFloat>(), new HashMap<String, ConfigInt>(),
				new HashMap<String, ConfigBoolean>(), new HashMap<String, ConfigString>(),
				new HashMap<String, ConfigStringList>(), new HashMap<String, ConfigVector2f>(),
				new HashMap<String, ConfigVector3f>(), new HashMap<String, ConfigVector4f>());
	}

	protected EngineFileConfigData getConfigdata() {
		return configdata;
	}

	private List<String> getLines(File f) {
		Scanner sc = null;
		try {
			sc = new Scanner(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ArrayList<String> list = new ArrayList<String>();
		while (sc.hasNext()) {
			list.add(sc.next());
		}
		sc.close();
		return list;
	}

	private List<String> getLines(EngineFile f) {
		Scanner sc = new Scanner(Class.class.getResourceAsStream(f.getPath()));
		ArrayList<String> list = new ArrayList<String>();
		while (sc.hasNext()) {
			list.add(sc.next());
		}
		sc.close();
		return list;
	}

	private boolean list = false;
	private ConfigStringList currentStringList = null;

	private void handle(List<String> lines) {
		for (String s : lines) {
			if (list) {
				if (s.startsWith("-")) {
					String ss = s.replaceAll("-", "");
					currentStringList.getData().add(ss);
				} else {
					configdata.stringLists.put(currentStringList.getDat(), currentStringList);
					list = false;
					handleLine(s);
				}
			} else {
				handleLine(s);
			}
		}
	}

	private void handleLine(String s) {
		String[] line = s.split(":");
		String prefix = line[0] + ":";
		String dat = line[1];
		String data = line[2];

		if (prefix.equalsIgnoreCase("f:")) {
			ConfigFloat f = new ConfigFloat(dat, Float.parseFloat(data));
			configdata.floats.put(dat, f);
		}
		if (prefix.equalsIgnoreCase("i:")) {
			ConfigInt f = new ConfigInt(dat, Integer.parseInt(data));
			configdata.ints.put(dat, f);
		}
		if (prefix.equalsIgnoreCase("b:")) {
			ConfigBoolean f = new ConfigBoolean(dat, Boolean.parseBoolean(data));
			configdata.booleans.put(dat, f);
		}
		if (prefix.equalsIgnoreCase("s:")) {
			ConfigString f = new ConfigString(dat, data);
			configdata.strings.put(dat, f);
		}
		if (prefix.equalsIgnoreCase("sl:")) {// TODO handle list lines
			list = true;
			currentStringList = new ConfigStringList(dat, new ArrayList<String>());
		}
		if (prefix.equalsIgnoreCase("v2:")) {
			float[] rf = getFF(2, data);
			ConfigVector2f f = new ConfigVector2f(dat, new Vector2f(rf[0], rf[1]));
			configdata.vec2s.put(dat, f);
		}
		if (prefix.equalsIgnoreCase("v3:")) {
			float[] rf = getFF(3, data);
			ConfigVector3f f = new ConfigVector3f(dat, new Vector3f(rf[0], rf[1], rf[2]));
			configdata.vec3s.put(dat, f);
		}
		if (prefix.equalsIgnoreCase("v4:")) {
			float[] rf = getFF(4, data);
			ConfigVector4f f = new ConfigVector4f(dat, new Vector4f(rf[0], rf[1], rf[2], rf[3]));
			configdata.vec4s.put(dat, f);
		}
	}

	private float[] getFF(int am, String dat) {
		float[] rf = new float[am];
		String[] con = dat.split("=");
		if (true) {
			for (int i = 0; i < am; i++) {
				rf[i] = Float.parseFloat(con[i]);
			}
		}
		return rf;
	}

}
