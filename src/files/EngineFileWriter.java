package files;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import files.configData.*;

public class EngineFileWriter {

	private File file;
	private FileWriter writer;

	public EngineFileWriter(String path, String file) {
		File f = new File(path + file);
		File pathF = new File(path);
		if (!f.exists()) {
			try {
				pathF.mkdirs();
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.file = f;
	}

	public void start() throws IOException {
		writer = new FileWriter(file);
	}

	public void writeBooleans(Collection<ConfigBoolean> b) throws IOException {
		for (ConfigBoolean bool : b) {
			writer.write("b" + ":" + bool.getDat() + ":" + Boolean.toString(bool.isData()));
			writer.write(System.lineSeparator());
		}
	}

	public void writeFloats(Collection<ConfigFloat> b) throws IOException {
		for (ConfigFloat bool : b) {
			writer.write("f" + ":" + bool.getDat() + ":" + Float.toString(bool.getData()));
			writer.write(System.lineSeparator());
		}
	}

	public void writeStrings(Collection<ConfigString> b) throws IOException {
		for (ConfigString bool : b) {
			writer.write("s" + ":" + bool.getDat() + ":" + bool.getData());
			writer.write(System.lineSeparator());
		}
	}

	public void writeInts(Collection<ConfigInt> b) throws IOException {
		for (ConfigInt bool : b) {
			writer.write("i" + ":" + bool.getDat() + ":" + Integer.toString(bool.getData()));
			writer.write(System.lineSeparator());
		}
	}

	public void writeStringLists(Collection<ConfigStringList> b) throws IOException {
		for (ConfigStringList bool : b) {
			writer.write("sl:" + bool.getDat());
			writer.write(System.lineSeparator());
			for (String s : bool.getData()) {
				writer.write("-" + s);
				writer.write(System.lineSeparator());
			}
		}
	}

	public void writeVector2fs(Collection<ConfigVector2f> b) throws IOException {
		for (ConfigVector2f bool : b) {
			Vector2f v = bool.getData();
			writer.write("v2" + ":" + bool.getDat() + ":" + v.x + "=" + v.y);
			writer.write(System.lineSeparator());
		}
	}

	public void writeVector3fs(Collection<ConfigVector3f> b) throws IOException {
		for (ConfigVector3f bool : b) {
			Vector3f v = bool.getData();
			writer.write("v3" + ":" + bool.getDat() + ":" + v.x + "=" + v.y + "=" + v.z);
			writer.write(System.lineSeparator());
		}
	}

	public void writeVector4fs(Collection<ConfigVector4f> b) throws IOException {
		for (ConfigVector4f bool : b) {
			Vector4f v = bool.getData();
			writer.write("v4" + ":" + bool.getDat() + ":" + v.x + "=" + v.y + "=" + v.z + "=" + v.w);
			writer.write(System.lineSeparator());
		}
	}

	public void stop() throws IOException {
		writer.flush();
		writer.close();
	}

}
