package renderer.lineRenderer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import shaders.uniforms.ShaderProgram;
import shaders.uniforms.Uniform;
import shaders.uniforms.UniformMat4;
import shaders.uniforms.UniformVec4;
import utils.SourceFile;

public class LineShader extends ShaderProgram {

	private static final SourceFile VERTEX_FILE = new SourceFile("/shaders/line/shader.vsh");
	private static final SourceFile FRAGMENT_FILE = new SourceFile("/shaders/line/shader.fsh");

	public UniformMat4 location_transformationMatrix = new UniformMat4("transformationMatrix");
	public UniformMat4 location_projectionViewMatrix = new UniformMat4("projectionViewMatrix");
	public UniformMat4 location__viewMatrix = new UniformMat4("viewMatrix");

	public UniformVec4 color = new UniformVec4("color");

	public LineShader() {
		super.init(ShaderProgram.newShaderProgram().addInput(0, "in_position").addOutput(0, "out_Color"));
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
		return new Uniform[] { location__viewMatrix, location_projectionViewMatrix, location_transformationMatrix,
				color };
	}

	@Override
	protected Collection<Uniform> getAllUnis() {
		List<Uniform> uniforms = new ArrayList<Uniform>();
		uniforms.add(location_transformationMatrix);
		uniforms.add(location_projectionViewMatrix);
		uniforms.add(location__viewMatrix);
		uniforms.add(color);
		return uniforms;
	}

}
