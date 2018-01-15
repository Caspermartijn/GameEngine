package framebuffer;

public interface FrameBufferObject {

	public int getWidth();
	public int getHeight();
	public boolean isMultisampled();
	public int getSamples();
	public void delete();
	
}
