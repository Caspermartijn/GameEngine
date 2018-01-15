package framebuffer;

public class CubeMapFrameBufferBuilder {

	protected int size;
	protected int depthAttachment;
	
	public static final int NONE = -1, BUFFER = 0, TEXTURE = 1;
	
	protected CubeMapFrameBufferBuilder(int size) {
		this.size = size;
	}
	
	public CubeMapFrameBufferBuilder setDepthAttachment(int type) {
		this.depthAttachment = Math.max(0, type);
		return this;
	}
	
	public CubeMapFrameBuffer create() {
		return new CubeMapFrameBuffer(this);
	}
	
}
