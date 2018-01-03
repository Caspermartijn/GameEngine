package files;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import files.configData.*;

public class EngineFileConfig {

	protected HashMap<String, ConfigFloat> floats;

	protected HashMap<String, ConfigInt> ints;

	protected HashMap<String, ConfigBoolean> booleans;

	protected HashMap<String, ConfigString> strings;

	protected HashMap<String, ConfigStringList> stringLists;

	protected HashMap<String, ConfigVector2f> vec2s;

	protected HashMap<String, ConfigVector3f> vec3s;

	protected HashMap<String, ConfigVector4f> vec4s;

	private EngineFileReader reader;
	private EngineFileWriter writer;

	public EngineFileConfig(EngineFile engineFileF) {
		reader = new EngineFileReader(engineFileF);
		handleConfigData(reader.configdata);
	}

	public EngineFileConfig(String path, String file) {
		reader = new EngineFileReader(file);
		writer = new EngineFileWriter(path, file);
		handleConfigData(reader.configdata);
	}

	public void saveConfig() throws IOException {
		if (writer != null) {

			writer.start();
			writer.writeStringLists(stringLists.values());
			writer.writeStrings(strings.values());
			writer.writeBooleans(booleans.values());
			writer.writeFloats(floats.values());
			writer.writeInts(ints.values());
			writer.writeVector2fs(vec2s.values());
			writer.writeVector3fs(vec3s.values());
			writer.writeVector4fs(vec4s.values());
			writer.stop();

		} else {
			System.out.println("Can't use file writer with source files!");
			System.out.println("Try creating a new file outside of the project.");
		}
	}

	private void handleConfigData(EngineFileConfigData data) {
		floats = data.floats;
		ints = data.ints;
		booleans = data.booleans;
		strings = data.strings;
		stringLists = data.stringLists;
		vec2s = data.vec2s;
		vec3s = data.vec3s;
		vec4s = data.vec4s;
	}

	public void set(String dat, float f) {
		ConfigFloat cf = new ConfigFloat(dat, f);
		floats.put(dat, cf);
	}

	public void set(String dat, int i) {
		ConfigInt cf = new ConfigInt(dat, i);
		ints.put(dat, cf);
	}

	public void set(String dat, String s) {
		ConfigString cf = new ConfigString(dat, s);
		strings.put(dat, cf);
	}

	public void set(String dat, boolean b) {
		ConfigBoolean cf = new ConfigBoolean(dat, b);
		booleans.put(dat, cf);
	}

	public void set(String dat, List<String> sl) {
		ConfigStringList cf = new ConfigStringList(dat, sl);
		stringLists.put(dat, cf);
	}

	public void set(String dat, Vector2f f) {
		ConfigVector2f cf = new ConfigVector2f(dat, f);
		vec2s.put(dat, cf);
	}

	public void set(String dat, Vector3f f) {
		ConfigVector3f cf = new ConfigVector3f(dat, f);
		vec3s.put(dat, cf);
	}

	public void set(String dat, Vector4f f) {
		ConfigVector4f cf = new ConfigVector4f(dat, f);
		vec4s.put(dat, cf);
	}

	public boolean getBoolean(String dat) {
		return booleans.get(dat).isData();
	}

	@SuppressWarnings("unlikely-arg-type")
	public float getFloat(String dat) {
		float f = 0;
		if (floats.get(0) != null) {
			f = floats.get(dat).getData();
		}
		return f;
	}

	public int getInt(String dat) {
		int i = 0;
		if (ints.get(dat) != null) {
			i = ints.get(dat).getData();
		}
		return i;
	}

	public String getString(String dat) {
		return strings.get(dat).getData();
	}

	public List<String> getStringList(String dat) {
		return stringLists.get(dat).getData();
	}

	public Vector2f getVector2f(String dat) {
		return vec2s.get(dat).getData();
	}

	public Vector3f getVector3f(String dat) {
		return vec3s.get(dat).getData();
	}

	public Vector4f getVector4f(String dat) {
		return vec4s.get(dat).getData();
	}

	protected HashMap<String, ConfigFloat> getFloats() {
		return floats;
	}

	protected HashMap<String, ConfigInt> getInts() {
		return ints;
	}

	protected HashMap<String, ConfigBoolean> getBooleans() {
		return booleans;
	}

	protected HashMap<String, ConfigString> getStrings() {
		return strings;
	}

	protected HashMap<String, ConfigStringList> getStringLists() {
		return stringLists;
	}

	protected HashMap<String, ConfigVector2f> getVec2s() {
		return vec2s;
	}

	protected HashMap<String, ConfigVector3f> getVec3s() {
		return vec3s;
	}

	protected HashMap<String, ConfigVector4f> getVec4s() {
		return vec4s;
	}

}
