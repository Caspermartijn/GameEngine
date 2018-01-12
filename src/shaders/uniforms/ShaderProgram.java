package shaders.uniforms;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map.Entry;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import utils.SourceFile;

public abstract class ShaderProgram {

	private int programID;

	public static int created, deleted;

	protected void init(ShaderProgramBuilder builder) {
		int vertexShaderID = loadShader(getVertexFile(), GL20.GL_VERTEX_SHADER);
		int fragmentShaderID = loadShader(getFragmentFile(), GL20.GL_FRAGMENT_SHADER);

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
		start();
		for (Uniform u : getAllUniforms()) {
			u.storeUniformLocation(programID);
		}
		stop();
		GL20.glValidateProgram(programID);

		ArrayList<Uniform> sampler = new ArrayList<Uniform>();
		for (Uniform u : getAllUniforms()) {
			if (u instanceof UniformSampler || u instanceof UniformCubeMap) {
				sampler.add(u);
			}
		}
		if (sampler.isEmpty())
			return;

		int i = 0;
		start();
		for (Uniform u : sampler) {
			if (u instanceof UniformSampler)
				((UniformSampler) u).loadTexUnit(i);
			else
				((UniformCubeMap) u).loadTexUnit(i);
			i++;
		}
		stop();
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

	protected abstract SourceFile getVertexFile();

	protected abstract SourceFile getFragmentFile();

	protected abstract Uniform[] getAllUniforms();

	protected abstract Collection<Uniform> getAllUnis();

	public static ShaderProgramBuilder newShaderProgram() {
		return new ShaderProgramBuilder();
	}

	public static void printLog() {
		System.out.println("Shader_Log[created: " + created + " , deleted: " + deleted + "]");
	}
}
