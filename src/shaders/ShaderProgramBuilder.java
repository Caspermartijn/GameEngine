package shaders;

import java.util.HashMap;

import utils.SourceFile;

public class ShaderProgramBuilder {

	protected SourceFile vertexFile, fragmentFile;
	protected String[] fragmentString, vertexString;
	protected HashMap<Integer, String> inputs = new HashMap<>();
	protected HashMap<Integer, String> outputs = new HashMap<>();

	protected ShaderProgramBuilder(SourceFile vertexFile, SourceFile fragmentFile) {
		this.vertexFile = vertexFile;
		this.fragmentFile = fragmentFile;
	}

	public ShaderProgramBuilder(SourceFile vertexFile, String[] fragment) {
		this.vertexFile = vertexFile;
		this.fragmentString = fragment;
	}

	public ShaderProgramBuilder(String[] vertex, SourceFile fragmentFile) {
		this.vertexString = vertex;
		this.fragmentFile = fragmentFile;
	}

	public ShaderProgramBuilder(String[] vertex, String[] fragment) {
		this.vertexString = vertex;
		this.fragmentString = fragment;
	}

	public ShaderProgramBuilder addInput(int inputID, String inputName) {
		if (inputID >= 0 && inputID < 16)
			inputs.put(inputID, inputName);
		return this;
	}

	public ShaderProgramBuilder addOutput(int outputID, String outputName) {
		if (outputID >= 0 && outputID < 16) {
			outputs.put(outputID, outputName);
		}
		return this;
	}
}
