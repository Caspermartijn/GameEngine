package renderer.skyboxRenderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import objects.Camera;
import objects.Skybox;
import objects.Vao;
import shaders.ShaderProgram;

public class SkyboxRenderer {

	private final int SIZE = 1;
	private final float[] VERTICES = { -SIZE, SIZE, -SIZE, -SIZE, -SIZE, -SIZE, SIZE, -SIZE, -SIZE, SIZE, -SIZE, -SIZE,
			SIZE, SIZE, -SIZE, -SIZE, SIZE, -SIZE, -SIZE, -SIZE, SIZE, -SIZE, -SIZE, -SIZE, -SIZE, SIZE, -SIZE, -SIZE,
			SIZE, -SIZE, -SIZE, SIZE, SIZE, -SIZE, -SIZE, SIZE, SIZE, -SIZE, -SIZE, SIZE, -SIZE, SIZE, SIZE, SIZE, SIZE,
			SIZE, SIZE, SIZE, SIZE, SIZE, -SIZE, SIZE, -SIZE, -SIZE, -SIZE, -SIZE, SIZE, -SIZE, SIZE, SIZE, SIZE, SIZE,
			SIZE, SIZE, SIZE, SIZE, SIZE, -SIZE, SIZE, -SIZE, -SIZE, SIZE, -SIZE, SIZE, -SIZE, SIZE, SIZE, -SIZE, SIZE,
			SIZE, SIZE, SIZE, SIZE, SIZE, -SIZE, SIZE, SIZE, -SIZE, SIZE, -SIZE, -SIZE, -SIZE, -SIZE, -SIZE, -SIZE,
			SIZE, SIZE, -SIZE, -SIZE, SIZE, -SIZE, -SIZE, -SIZE, -SIZE, SIZE, SIZE, -SIZE, SIZE };

	private Vao cube;
	private SkyboxShader shader;
	
	public SkyboxRenderer() {
		init();
	}

	public void init() {
		cube = Vao.create();
		cube.bind();
		cube.createStaticAttribute(0, VERTICES, 3);
		cube.unbind();

		shader = new SkyboxShader();
	}

	public void render(Skybox skybox, Camera camera) {
		shader.start();
		cube.bind(0);

		shader.cubeMap.bindTexture(skybox.getTexture());
		shader.projectionMatrix.loadMatrix(camera.getProjectionMatrix());
		Matrix4f viewMatrix = new Matrix4f(camera.getViewMatrix());

		Matrix4f.rotate((float) Math.toRadians(skybox.getRotation()), new Vector3f(0, 1, 0), viewMatrix, viewMatrix);
		viewMatrix.m30 = 0f;
		viewMatrix.m31 = 0f;
		viewMatrix.m32 = 0f;
		shader.viewMatrix.loadMatrix(viewMatrix);
		shader.size.loadFloat(skybox.getSize());

		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, VERTICES.length);

		cube.unbind(0);
		shader.stop();
	}

	public void delete() {
		cube.delete();
		shader.delete();
	}
	
	public ShaderProgram getShader() {
		return shader;
	}

}
