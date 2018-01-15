package framebuffer;

import java.util.ArrayList;

public class FrameBufferBuilder {

	protected ArrayList<Integer> attachments = new ArrayList<>();

	protected boolean createBuffers;

	protected int depthAttachment;

	protected int samples;

	protected int width, height;

	public static final int NONE = -1, BUFFER = 0, TEXTURE = 1;

	public FrameBufferBuilder(int x, int y) {
		this.width = x;
		this.height = y;
	}

	public FrameBufferBuilder addTexture(int attachment) {
		if (attachment >= 0 && attachment < 16)
			attachments.add(attachment);
		return this;
	}

	public FrameBufferBuilder setDepthTesting(int depthTesting) {
		if (depthTesting >= -1 && depthTesting <= 1)
			depthAttachment = depthTesting;
		return this;
	}

	public FrameBuffer createFrameBuffer() {
		return new FrameBuffer(this);
	}
	
	public FrameBuffer createFrameBuffer(int samples) {
		this.samples = Math.max(0, samples);
		if (samples == 0)
				return new FrameBuffer(this);
		return new MultisampledFrameBuffer(this);
	}

	protected FrameBufferBuilder getMultisampledVersion() {
		FrameBufferBuilder fb = new FrameBufferBuilder(this.width, this.height);
		if (this.depthAttachment >= 0)
			fb.setDepthTesting(FrameBufferBuilder.BUFFER);
		fb.attachments = this.attachments;
		fb.createBuffers = true;
		fb.samples = this.samples;
		return fb;
	}

	protected FrameBufferBuilder getResolveVersion() {
		FrameBufferBuilder fb = new FrameBufferBuilder(this.width, this.height);
		if (this.depthAttachment == FrameBufferBuilder.TEXTURE)
			fb.depthAttachment = this.depthAttachment;
		fb.attachments = this.attachments;
		return fb;
	}
}
