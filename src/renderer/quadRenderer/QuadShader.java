package renderer.quadRenderer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import shaders.uniforms.ShaderProgram;
import shaders.uniforms.Uniform;
import shaders.uniforms.UniformMat4;
import shaders.uniforms.UniformVec4;
import utils.SourceFile;

public class QuadShader extends ShaderProgram {

	private static final SourceFile FRAGMENT_FILE = new SourceFile("/shaders/quad/shader.fsh");
	private static final SourceFile VERTEX_FILE = new SourceFile("/shaders/quad/shader.vsh");
	
	public UniformMat4 transform = new UniformMat4("transform");
	public UniformVec4 dimensions = new UniformVec4("dimensions");
	public UniformVec4 color = new UniformVec4("color");
	public UniformVec4 outlineColor = new UniformVec4("outlineColor");

	protected QuadShader() {
		super.init(ShaderProgram.newShaderProgram().addInput(0, "inPosition")
				.addOutput(0, "outColor"));
	}

	@Override
	protected SourceFile getVertexFile() {
		return VERTEX_FILE;
	}

	@Override
	protected SourceFile getFragmentFile() {
		return FRAGMENT_FILE;
	}

	@Override
	protected Uniform[] getAllUniforms() {
		return new Uniform[] {transform, color, outlineColor, dimensions};
	}
	
	@Override
	protected Collection<Uniform> getAllUnis() {
		List<Uniform> uniforms = new ArrayList<Uniform>();
		uniforms.add(transform);
		uniforms.add(color);
		uniforms.add(outlineColor);
		uniforms.add(dimensions);
		return uniforms;
	}

}
