package shaders;

import java.io.BufferedReader;
import java.util.Map.Entry;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import log.Log;
import shaders.uniforms.Uniform;
import utils.SourceFile;

public class ShaderProgram {

	private int programID;

	private static int created, deleted;

	protected ShaderProgram(ShaderProgramBuilder builder) {
		int vertexShaderID = 0;
		int fragmentShaderID = 0;

		if (builder.vertexFile != null) {
			vertexShaderID = loadShader(builder.vertexFile, GL20.GL_VERTEX_SHADER);
		} else {
			vertexShaderID = loadShader(builder.vertexString, GL20.GL_VERTEX_SHADER);
		}

		if (builder.fragmentFile != null) {
			fragmentShaderID = loadShader(builder.fragmentFile, GL20.GL_FRAGMENT_SHADER);
		} else {
			fragmentShaderID = loadShader(builder.fragmentString, GL20.GL_FRAGMENT_SHADER);
		}

		created++;

		programID = GL20.glCreateProgram();
		GL20.glAttachShader(programID, vertexShaderID); 
		GL20.glAttachShader(programID, fragmentShaderID);
		for (Entry<Integer, String> e : builder.inputs.entrySet()) {
			bindAttribute(e.getKey(), e.getValue());
		}
		for (Entry<Integer, String> e : builder.outputs.entrySet()) {
			bindFragData(e.getKey(), e.getValue());
		}
		GL20.glLinkProgram(programID);
		GL20.glDetachShader(programID, vertexShaderID);
		GL20.glDetachShader(programID, fragmentShaderID);
		GL20.glDeleteShader(vertexShaderID);
		GL20.glDeleteShader(fragmentShaderID);

	}

	protected void storeAllUniformLocations(Uniform... uniforms) {
		for (Uniform uniform : uniforms) {
			uniform.storeUniformLocation(programID);
		}
		GL20.glValidateProgram(programID);
	}

	public void start() {
		GL20.glUseProgram(programID);
	}

	public void stop() {
		GL20.glUseProgram(0);
	}

	public void delete() {
		deleted++;
		stop();
		GL20.glDeleteProgram(programID);
	}

	private void bindAttribute(int index, String name) {
		GL20.glBindAttribLocation(programID, index, name);
	}

	private void bindFragData(int index, String name) {
		GL30.glBindFragDataLocation(programID, index, name);
	}

	private int loadShader(SourceFile file, int type) {
		StringBuilder shaderSource = new StringBuilder();
		try {
			BufferedReader reader = file.openFileReader();
			String line;
			while ((line = reader.readLine()) != null) {
				shaderSource.append(line).append("//\n");
			}
			reader.close();
		} catch (Exception e) {
			System.err.println("Could not read file.");
			e.printStackTrace();
			System.exit(-1);
		}
		int shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, shaderSource);
		GL20.glCompileShader(shaderID);
		if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.err.println("Could not compile shader " + file.getPath());
			System.err.println(GL20.glGetShaderInfoLog(shaderID, 500));
			System.exit(-1);
		}
		return shaderID;
	}

	private int loadShader(String[] strings, int type) {
		StringBuilder shaderSource = new StringBuilder();
		if (true) {
			for (String s : strings) {
				shaderSource.append(s).append("//\n");
			}
		}
		int shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, shaderSource);
		GL20.glCompileShader(shaderID);
		if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.err.println(GL20.glGetShaderInfoLog(shaderID, 500));
			System.exit(-1);
		}
		return shaderID;
	}

	public static ShaderProgramBuilder newShaderProgram(SourceFile vertexFile, SourceFile fragmentFile) {
		return new ShaderProgramBuilder(vertexFile, fragmentFile);
	}

	public static ShaderProgramBuilder newShaderProgram(SourceFile vertexFile, String[] fragmentFile) {
		return new ShaderProgramBuilder(vertexFile, fragmentFile);
	}

	public static ShaderProgramBuilder newShaderProgram(String[] vertexShader, SourceFile fragmentFile) {
		return new ShaderProgramBuilder(vertexShader, fragmentFile);
	}

	public static ShaderProgramBuilder newShaderProgram(String[] vertexShader, String[] fragmentFile) {
		return new ShaderProgramBuilder(vertexShader, fragmentFile);
	}

	public static void printLog() {
		Log.append("Shader_Log[created: " + created + " , deleted: " + deleted + "]");
	}

}
