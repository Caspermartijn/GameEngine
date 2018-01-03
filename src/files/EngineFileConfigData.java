package files;

import java.util.HashMap;

import files.configData.ConfigBoolean;
import files.configData.ConfigFloat;
import files.configData.ConfigInt;
import files.configData.ConfigString;
import files.configData.ConfigStringList;
import files.configData.ConfigVector2f;
import files.configData.ConfigVector3f;
import files.configData.ConfigVector4f;

public class EngineFileConfigData {

	protected HashMap<String, ConfigFloat> floats = new HashMap<String, ConfigFloat>();

	protected HashMap<String, ConfigInt> ints = new HashMap<String, ConfigInt>();

	protected HashMap<String, ConfigBoolean> booleans = new HashMap<String, ConfigBoolean>();

	protected HashMap<String, ConfigString> strings = new HashMap<String, ConfigString>();

	protected HashMap<String, ConfigStringList> stringLists = new HashMap<String, ConfigStringList>();

	protected HashMap<String, ConfigVector2f> vec2s = new HashMap<String, ConfigVector2f>();

	protected HashMap<String, ConfigVector3f> vec3s = new HashMap<String, ConfigVector3f>();

	protected HashMap<String, ConfigVector4f> vec4s = new HashMap<String, ConfigVector4f>();

	public EngineFileConfigData(HashMap<String, ConfigFloat> floats, HashMap<String, ConfigInt> ints,
			HashMap<String, ConfigBoolean> booleans, HashMap<String, ConfigString> strings,
			HashMap<String, ConfigStringList> stringLists, HashMap<String, ConfigVector2f> vec2s,
			HashMap<String, ConfigVector3f> vec3s, HashMap<String, ConfigVector4f> vec4s) {
		this.floats = floats;
		this.ints = ints;
		this.booleans = booleans;
		this.strings = strings;
		this.stringLists = stringLists;
		this.vec2s = vec2s;
		this.vec3s = vec3s;
		this.vec4s = vec4s;
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
