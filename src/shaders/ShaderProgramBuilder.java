package shaders;

import java.util.HashMap;

public class ShaderProgramBuilder {

	protected HashMap<Integer, String> inputs = new HashMap<>();
	protected HashMap<Integer, String> outputs = new HashMap<>();
	
	protected ShaderProgramBuilder() {}
	
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
