package framebuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import textures.Texture;

public class MultisampledFrameBuffer extends FrameBuffer {

	private int drawID;

	protected MultisampledFrameBuffer(FrameBufferBuilder builder) {
		super(builder.getResolveVersion());
		drawID = GL30.glGenFramebuffers();
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, drawID);
		
		
		
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
	}

	@Override
	public void resolve() {
		FrameBuffer activeFrameBuffer = FrameBuffer.active;
		GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, super.id);
		GL30.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, drawID);
		for (int e : super.colorTextures.keySet()) {
			GL11.glDrawBuffer(GL30.GL_COLOR_ATTACHMENT0+e);
			GL11.glReadBuffer(GL30.GL_COLOR_ATTACHMENT0+e);
			GL30.glBlitFramebuffer(0, 0, getWidth(), getHeight(), 0, 0, getWidth(), getHeight(),
					GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT, GL11.GL_NEAREST);
		}
		super.unbind();
		if (activeFrameBuffer != null)
			activeFrameBuffer.bind();
		GL11.glReadBuffer(readBuffer);
	}

	@Override public void bind() {
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, drawID);
		GL11.glViewport(0, 0, getWidth(), getHeight());
	}
	
	@Override
	public void unbind() {
		super.unbind();
	}

//	@Override
//	public Texture getColorAttachment(int attachment) {
//		if (getReadBuffer() != this)
//		return getReadBuffer().getColorAttachment(attachment);
//		else
//			return 
//	}

	@Override
	public Texture getDepthAttachment() {
		return getReadBuffer().getDepthAttachment();
	}

	@Override
	public void delete() {
		super.delete();
		GL30.glDeleteFramebuffers(drawID);
	}
}
