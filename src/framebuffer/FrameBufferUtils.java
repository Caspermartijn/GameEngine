package framebuffer;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;

import engine.Display;

public class FrameBufferUtils {
	
	public static final int DEPTH_ATTACHMENT = -1;
	
	public static BufferedImage getScreenShot(int attachment) {
		if (FrameBuffer.active == null)
			return screenshot(Display.getWidth(), Display.getHeight(), attachment);
		else
			return screenshot(FrameBuffer.active.getWidth(), FrameBuffer.active.getHeight(), attachment);
	}

	private static BufferedImage screenshot(int width, int height, int attachment) {
		FrameBuffer target = FrameBuffer.active;
		if (target != null)
			target.getReadBuffer().bind();

		int[] pixels = new int[width * height];
		int bindex;
		ByteBuffer fb;
		if (attachment == DEPTH_ATTACHMENT) {
			fb = ByteBuffer.allocateDirect(width * height);
		} else {
			fb = ByteBuffer.allocateDirect(width * height * 3);
		}
		
		if (attachment == DEPTH_ATTACHMENT) {
			GL11.glReadPixels(0, 0, width, height, GL11.GL_DEPTH_COMPONENT, GL11.GL_UNSIGNED_BYTE, fb);
		} else {
			GL11.glReadBuffer(GL30.GL_COLOR_ATTACHMENT0+attachment);
			GL11.glReadPixels(0, 0, width, height, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, fb);
			GL11.glReadBuffer(target.readBuffer);
		}
		
		BufferedImage imageIn = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < pixels.length; i++) {
			if (attachment == DEPTH_ATTACHMENT) {
				bindex = i;
				pixels[i] = ((fb.get(bindex) << 16)) + ((fb.get(bindex) << 8)) + ((fb.get(bindex) << 0));
			} else {
				bindex = i * 3;
				pixels[i] = ((fb.get(bindex) << 16)) + ((fb.get(bindex + 1) << 8)) + ((fb.get(bindex + 2) << 0));
			}
		}
		imageIn.setRGB(0, 0, width, height, pixels, 0, width);

		AffineTransform at = AffineTransform.getScaleInstance(1, -1);
		at.translate(0, -imageIn.getHeight(null));

		AffineTransformOp opRotated = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		BufferedImage imageOut = opRotated.filter(imageIn, null);
		if (target != null)
			target.bind();
		return imageOut;
	}
	
	public static void clearColorBuffer() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
	}

	public static void clearDepthBuffer() {
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	public static void copy(FrameBuffer source, FrameBuffer destination) {
		FrameBuffer active = FrameBuffer.active;
		if (source == null)
			return;
		GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, (destination == null) ? 0 : destination.getDrawBuffer().id);
		GL30.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, source.getReadBuffer().id);
		int width = (destination == null) ? Display.getWidth() : destination.getWidth();
		int height = (destination == null) ? Display.getHeight() : destination.getHeight();
		GL30.glBlitFramebuffer(0, 0, source.getWidth(), source.getHeight(), 0, 0, width, height,
				GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT, GL11.GL_NEAREST);
		source.unbind();
		if (active != null)
			active.bind();
	}
	
	protected static int createColorBufferAttachment(int attachment, FrameBufferObject object) {
		int colorBuffer = GL30.glGenRenderbuffers();
		GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, colorBuffer);
		if (!object.isMultisampled())
			GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL11.GL_RGBA8, object.getWidth(), object.getHeight());
		else
			GL30.glRenderbufferStorageMultisample(GL30.GL_RENDERBUFFER, object.getSamples(), GL11.GL_RGBA8, object.getWidth(), object.getHeight());
		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0+attachment, GL30.GL_RENDERBUFFER,
				colorBuffer);
		return colorBuffer;
	}

	protected static int createColorTextureAttachment(int attachment, FrameBufferObject object) {
		int texture = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, object.getWidth(), object.getHeight(), 0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE,
				(ByteBuffer) null);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0+attachment, GL11.GL_TEXTURE_2D, texture, 0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		return texture;
	}

	protected static int createDepthTextureAttachment(FrameBufferObject object) {
		int texture = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL14.GL_DEPTH_COMPONENT32, object.getWidth(), object.getHeight(), 0, GL11.GL_DEPTH_COMPONENT,
				GL11.GL_FLOAT, (ByteBuffer) null);
		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL11.GL_TEXTURE_2D, texture, 0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		return texture;
	}

	protected static int createDepthBufferAttachment(FrameBufferObject object) {
		int depthBuffer = GL30.glGenRenderbuffers();
		GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, depthBuffer);
		if (!object.isMultisampled())
			GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL11.GL_DEPTH_COMPONENT, object.getWidth(), object.getHeight());
		else
			GL30.glRenderbufferStorageMultisample(GL30.GL_RENDERBUFFER, object.getSamples(), GL11.GL_DEPTH_COMPONENT, object.getWidth(), object.getHeight());
		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL30.GL_RENDERBUFFER,
				depthBuffer);
		return depthBuffer;
	}
	
}
