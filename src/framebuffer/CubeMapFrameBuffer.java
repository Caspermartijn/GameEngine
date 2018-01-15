package framebuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import engine.Display;
import objects.Camera;
import textures.CubeMapTexture;

public class CubeMapFrameBuffer implements FrameBufferObject {

	private int id;
	private int size;

	private int depthBuffer;
	private CubeMapTexture depthTexture;
	private CubeMapTexture texture;
	
	private CubeMapCamera camera = new CubeMapCamera();
	
	protected CubeMapFrameBuffer(CubeMapFrameBufferBuilder builder) {
		this.size = builder.size;

		id = GL30.glGenFramebuffers();
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, id);
		GL11.glDrawBuffer(GL30.GL_COLOR_ATTACHMENT0);

		if (builder.depthAttachment == CubeMapFrameBufferBuilder.BUFFER) {
			depthBuffer = FrameBufferUtils.createDepthBufferAttachment(this);
		} else if (builder.depthAttachment == CubeMapFrameBufferBuilder.TEXTURE) {
			depthTexture = CubeMapTexture.createCubeMapTextureObject(FrameBufferUtils.createDepthTextureAttachment(this), size);
		}
		
		texture = CubeMapTexture.newCubeMapTexture(size);
		
		unbind();
	}

	public Camera bind(int face) {
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, id);
		GL11.glViewport(0, 0, size, size);
		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0,
                 GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X + face, texture.getID(), 0);
		
		camera.switchToFace(face);
		return camera;
	}
	
	public void unbind() {
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
		GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
	}
	
	@Override
	public int getWidth() {
		return size;
	}

	@Override
	public int getHeight() {
		return size;
	}

	@Override
	public boolean isMultisampled() {
		return false;
	}

	@Override
	public int getSamples() {
		return 0;
	}

	public static CubeMapFrameBufferBuilder getNewFrameBuffer(int size) {
		return new CubeMapFrameBufferBuilder(size);
	}

	@Override
	public void delete() {
		if (depthBuffer != 0)
			GL30.glDeleteRenderbuffers(depthBuffer);
		if (depthTexture != null)
			depthTexture.delete();
		texture.delete();
	}

	public CubeMapTexture getTexture() {
		return texture;
	}

}

class CubeMapCamera extends Camera {
	
	private static final float aspectRatio = 1;
	private static final float FOV = 90;
	private static final float NEAR_PLANE = 0.2f;
	private static final float FAR_PLANE = 200;
	
	@Override protected void setProjectionMatrix(Matrix4f projection) {
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))));
		float x_scale = y_scale / aspectRatio;
		float frustum_length = FAR_PLANE - NEAR_PLANE;
		
		projection.m00 = x_scale;
		projection.m11 = y_scale;
		projection.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
		projection.m23 = -1;
		projection.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
		projection.m33 = 0;
	}
	
	 protected void switchToFace(int faceIndex) {
	        switch (faceIndex) {
	        case 0:
	            pitch = 0;
	            yaw = 90;
	            break;
	        case 1:
	            pitch = 0;
	            yaw = -90;
	            break;
	        case 2:
	            pitch = -90;
	            yaw = 180;
	            break;
	        case 3:
	            pitch = 90;
	            yaw = 180;
	            break;
	        case 4:
	            pitch = 0;
	            yaw = 180;
	            break;
	        case 5:
	            pitch = 0;
	            yaw = 0;
	            break;
	        }
	        super.updateMatrix();
	    }
	
}
