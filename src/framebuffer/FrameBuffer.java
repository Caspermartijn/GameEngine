package framebuffer;

import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map.Entry;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import engine.Display;
import objects.Vao;
import textures.Texture;

public class FrameBuffer implements FrameBufferObject {

	protected int id;
	private int width, height;

	protected HashMap<Integer, Texture> colorTextures;
	protected boolean useBuffers;
	// private HashMap<TextureAttachment, Integer> colorBuffers;

	private Texture depthAttachment;
	private int depthBuffer;
	private int samples;

	protected int readBuffer;

	protected static FrameBuffer active;

	protected FrameBuffer(FrameBufferBuilder builder) {
		width = builder.width;
		height = builder.height;
		samples = builder.samples;
		useBuffers = builder.createBuffers;

		id = GL30.glGenFramebuffers();
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, id);

		colorTextures = new HashMap<>();

		for (int attachment : builder.attachments) {
			colorTextures.put(attachment,
					Texture.createTextureObject(FrameBufferUtils.createColorTextureAttachment(attachment, this)));
		}

		if (builder.depthAttachment == FrameBufferBuilder.BUFFER)
			depthBuffer = FrameBufferUtils.createDepthBufferAttachment(this);
		if (builder.depthAttachment == FrameBufferBuilder.TEXTURE) {
//			depthBuffer = FrameBufferUtils.createDepthBufferAttachment(this);
			depthAttachment = Texture.createTextureObject(FrameBufferUtils.createDepthTextureAttachment(this));
		}
		
		readBuffer = builder.attachments.size() > 0 ? GL30.GL_COLOR_ATTACHMENT0 + (int) builder.attachments.toArray()[0]
				: GL11.GL_NONE;
		GL11.glReadBuffer(readBuffer);

		if (builder.attachments.size() > 1) {
			IntBuffer drawBuffers = BufferUtils.createIntBuffer(builder.attachments.size());
			for (int attachment : builder.attachments) {
				drawBuffers.put(GL30.GL_COLOR_ATTACHMENT0 + attachment);
			}
			drawBuffers.flip();
			GL20.glDrawBuffers(drawBuffers);
		} else if (builder.attachments.size() > 0) {
			GL11.glDrawBuffer(GL30.GL_COLOR_ATTACHMENT0 + (int) builder.attachments.toArray()[0]);
		} else {
			GL11.glDrawBuffer(GL11.GL_NONE);
		}
		
//		System.out.println("FBO creation log: \n" + "  Samples: " + builder.samples + "\n" + "  Color_Buffers: "
//				+ builder.createBuffers + "\n" + "  Color_Textures: " + builder.attachments.size() + "\n"
//				+ "  Depth_Attachment: " + builder.depthAttachment + " _ " + (depthAttachment != null ? depthAttachment.id : 0) + "\n" + "  FBO_Status: "
//				+ (GL30.glCheckFramebufferStatus(GL30.GL_DRAW_FRAMEBUFFER) == GL30.GL_FRAMEBUFFER_COMPLETE) + "\n");

		if (GL30.glCheckFramebufferStatus(GL30.GL_DRAW_FRAMEBUFFER) != GL30.GL_FRAMEBUFFER_COMPLETE) {
			System.err.println("Failed to create FrameBuffer");
		}

		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
	}

	public void bind() {
		if (active == this)
			return;
		active = this;
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, id);
		GL11.glViewport(0, 0, width, height);
	}

	public void unbind() {
		active = null;
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
		GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
	}
	
	public static void unbindAll() {
		active = null;
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
		GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
	}

	@Override
	public void delete() {
		GL30.glDeleteFramebuffers(id);
		for (Texture texture : this.colorTextures.values()) {
			texture.delete();
		}
		colorTextures.clear();
		if (depthAttachment != null)
			depthAttachment.delete();
		if (depthBuffer != 0)
			GL30.glDeleteRenderbuffers(depthBuffer);
	}

	public void setReadColorAttachment(int attachment) {
		if (attachment >= 0 && attachment < 16
				&& (this.colorTextures.containsKey(attachment))) {
			GL11.glReadBuffer(GL30.GL_COLOR_ATTACHMENT0 + attachment);
			readBuffer = GL30.GL_COLOR_ATTACHMENT0 + attachment;
		}
	}

	public Texture getColorAttachment(int attachmentID) {
		if (useBuffers)
			return null;
		for (Entry<Integer, Texture> e : colorTextures.entrySet()) {
			if (e.getKey() == attachmentID)
				return e.getValue();
		}
		return null;
	}

	public static void renderVaoUsingIndicesBuffer(Vao vao) {
		if (!vao.isBound()) {
			System.out.println("VAO not bound! Failed to render!");
			return;
		}
		GL11.glDrawElements(GL11.GL_TRIANGLES, vao.getIndexCount(), GL11.GL_UNSIGNED_INT, 0);
	}

	public Texture getDepthAttachment() {
		return depthAttachment;
	}

	public static FrameBufferBuilder getFrameBufferBuilder(int x, int y) {
		return new FrameBufferBuilder(x, y);
	}

	public static FrameBuffer getBoundFrameBuffer() {
		return active;
	}

	protected FrameBuffer getDrawBuffer() {
		return this;
	}

	protected FrameBuffer getReadBuffer() {
		return this;
	}

	/**
	 * Textures from multisampled FrameBuffers must be must be updated using
	 * resolve(). If the FrameBuffer is not multisampled, resolve() won't have
	 * any effect.
	 */
	public void resolve() {
	}

	@Override
	public boolean isMultisampled() {
		return samples > 0;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public int getSamples() {
		return samples;
	}
}
