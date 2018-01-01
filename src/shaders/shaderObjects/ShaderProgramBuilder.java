package shaders.shaderObjects;

import java.util.HashMap;

import utils.SourceFile;

public class ShaderProgramBuilder {

	protected SourceFile vertexFile, fragmentFile;
	protected HashMap<Integer, String> inputs = new HashMap<>();
	protected HashMap<Integer, String> outputs = new HashMap<>();
	
	protected ShaderProgramBuilder(SourceFile vertexFile, SourceFile fragmentFile) {
		this.vertexFile = vertexFile;
		this.fragmentFile = fragmentFile;
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
