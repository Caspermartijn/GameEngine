package renderer.skyboxRenderer;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import objects.Camera;
import objects.Skybox;
import objects.Vao;

public class SkyboxRenderer {
	
	private final int SIZE = 1;
	private final float[] VERTICES = { -SIZE, SIZE, -SIZE, -SIZE, -SIZE, -SIZE, SIZE, -SIZE, -SIZE, SIZE, -SIZE,
			-SIZE, SIZE, SIZE, -SIZE, -SIZE, SIZE, -SIZE, -SIZE, -SIZE, SIZE, -SIZE, -SIZE, -SIZE, -SIZE, SIZE, -SIZE,
			-SIZE, SIZE, -SIZE, -SIZE, SIZE, SIZE, -SIZE, -SIZE, SIZE, SIZE, -SIZE, -SIZE, SIZE, -SIZE, SIZE, SIZE,
			SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, -SIZE, SIZE, -SIZE, -SIZE, -SIZE, -SIZE, SIZE, -SIZE, SIZE, SIZE,
			SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, -SIZE, SIZE, -SIZE, -SIZE, SIZE, -SIZE, SIZE, -SIZE, SIZE, SIZE,
			-SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, SIZE, -SIZE, SIZE, SIZE, -SIZE, SIZE, -SIZE, -SIZE, -SIZE, -SIZE,
			-SIZE, -SIZE, SIZE, SIZE, -SIZE, -SIZE, SIZE, -SIZE, -SIZE, -SIZE, -SIZE, SIZE, SIZE, -SIZE, SIZE };

	private Vao cube;
	private SkyboxShader shader;
	
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
		viewMatrix.rotate((float) Math.toRadians(skybox.getRotation()), 0f, 1f, 0f, viewMatrix);
		viewMatrix.m30(0f);
		viewMatrix.m31(0f);
		viewMatrix.m32(0f);
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

}
