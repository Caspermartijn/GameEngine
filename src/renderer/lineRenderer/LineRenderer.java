package renderer.lineRenderer;

import java.util.Collection;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import components.TracerComponent;

import static org.lwjgl.opengl.GL11.*;
import hitbox.HBox;
import objects.Camera;
import objects.Vao;
import shaders.uniforms.ShaderProgram;
import utils.maths.Matrix;
import utils.tasks.Cleanup;

public class LineRenderer extends Cleanup {

	private LineShader shader = new LineShader();

	public LineRenderer() {
		super();
	}

	public void setProjectionMatrix(Matrix4f matrix) {
		shader.start();
		shader.location_projectionViewMatrix.loadMatrix(matrix);
		shader.stop();
	}

	public void renderHitBoxes(Camera camera, Collection<HBox> collection) {
		shader.start();
		for (HBox box : collection) {
			if (box.getPosition() != null) {
				prepareHitBox(box);
				shader.location_transformationMatrix.loadMatrix(Matrix.createTransformationMatrix(box.getPosition(),
						box.getRotation().x, box.getRotation().y, box.getRotation().z, box.getScale()));
				shader.location__viewMatrix.loadMatrix(camera.getViewMatrix());
				shader.color.loadVec4(0, 1, 0, 1);// 100% green
				glDrawArrays(GL_LINE_STRIP, 0, box.getRawModel().getVertexCount());
				unbind(box);
			}
		}
		shader.stop();
	}

	public void renderTracers(Camera camera, Collection<TracerComponent> tracers) {
		shader.start();
		for (TracerComponent tracer : tracers) {
			tracer.setOrigen(new Vector3f(camera.x, camera.y, camera.z));
			tracer.updateVao();
			tracer.vao.bind(0);

			shader.location_transformationMatrix.loadMatrix(
					Matrix.createTransformationMatrix(new Vector3f(camera.x, camera.y, camera.z), 0f, 0f, 0f, 1f));
			shader.location__viewMatrix.loadMatrix(camera.getViewMatrix());
			shader.color.loadVec4(1, 0, 0, 1);// 100% red
			glDrawArrays(GL_LINE_STRIP, 0, tracer.vao.getVertexCount());
			tracer.vao.unbind(0);
		}
		shader.start();
	}

	private void prepareHitBox(HBox box) {
		Vao model = box.getRawModel();
		model.bind(0);
	}

	private void unbind(HBox box) {
		box.getRawModel().unbind(0);
	}

	public ShaderProgram getShader() {
		return shader;
	}

	@Override
	public void delete() {
		shader.delete();
	}

}
