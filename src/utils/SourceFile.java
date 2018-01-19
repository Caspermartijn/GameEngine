package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class SourceFile {

	private String path;
	private String name;
	private static final String FILE_SEPERATOR = "/";

	public SourceFile(String file) {
		this.path = (file);
		String[] dirs = path.split("/");
		this.name = dirs[(dirs.length - 1)];
	}

	public SourceFile(SourceFile file, String subfile) {
		path = file.getPath() + FILE_SEPERATOR + subfile;
	}

	public SourceFile(SourceFile file) {
		path = file.getPath();
	}

	public String getPath() {
		return path;
	}

	public BufferedReader openFileReader() throws IOException {
		try {
			return new BufferedReader(new InputStreamReader(SourceFile.class.getResourceAsStream(path)));
		} catch (NullPointerException e) {
			throw new FileNotFoundException("File not found: " + path);
		}
	}

	public BufferedWriter openFileWriter() throws IOException {
		return new BufferedWriter(new FileWriter(path));
	}

	public SourceFile getSubFile(String subFile) {
		return new SourceFile(this, subFile);
	}

	public SourceFile getParentFile() {
		try {
			return new SourceFile(path.substring(0, path.lastIndexOf(FILE_SEPERATOR) + 1));
		} catch (Exception e) {
			return this;
		}
	}

	public String getName() {
		return name; 
	}

	public BufferedReader getReader() throws Exception {
		try {
			InputStreamReader isr = new InputStreamReader(Class.class.getResourceAsStream(path));
			BufferedReader reader = new BufferedReader(isr);
			return reader;
		} catch (Exception e) {
			System.err.println("Couldn't get reader for " + path);
			throw e;
		}
	}
}
