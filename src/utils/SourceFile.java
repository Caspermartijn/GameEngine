package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class SourceFile {
	
	private String path;
	private static final String FILE_SEPERATOR = "/";
	
	public SourceFile(String file) {
		path = file;
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
			return new SourceFile(path.substring(0, path.lastIndexOf(FILE_SEPERATOR)+1));
		} catch (Exception e) {
			return this;
		}
	}
}
