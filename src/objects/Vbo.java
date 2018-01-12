package objects;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;

import utils.tasks.Cleanup;

public class Vbo extends Cleanup {

	public static int vbosCreated;
	private final int vboId;
	private final int type;
	private final boolean isStatic;
	private int attribute = -1;

	private Vbo(int vboId, int type, boolean isStatic) {
		this.isStatic = isStatic;
		this.vboId = vboId;
		this.type = type;
	}

	public static Vbo createStaticVbo(int type) {
		vbosCreated++;
		int id = GL15.glGenBuffers();
		return new Vbo(id, type, true);
	}

	public static Vbo createVbo(int type) {
		vbosCreated++;
		int id = GL15.glGenBuffers();
		return new Vbo(id, type, false);
	}

	public void bind() {
		GL15.glBindBuffer(type, vboId);
	}

	public void unbind() {
		GL15.glBindBuffer(type, 0);
	}

	public void storeStaticData(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		storeStaticData(buffer);
	}

	public void storeStaticData(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		storeStaticData(buffer);
	}

	public void setAttribute(int attribute) {
		this.attribute = attribute;
	}

	public void storeStaticData(IntBuffer data) {
		GL15.glBufferData(type, data, GL15.GL_STATIC_DRAW);
	}

	public void storeStaticData(FloatBuffer data) {
		GL15.glBufferData(type, data, GL15.GL_STATIC_DRAW);
	}

	public void storeData(int size) {
		GL15.glBufferData(type, size, GL15.GL_STREAM_DRAW);
	}

	public void setIntData(int[] data) {
		if (isStatic)
			return;
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		setIntData(buffer);
	}

	public void setFloatData(float[] data) {
		if (isStatic)
			return;
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		setFloatData(buffer);
	}

	public void setIntData(IntBuffer data) {
		GL15.glBufferSubData(type, 0, data);
	}

	public void setFloatData(FloatBuffer data) {
		GL15.glBufferSubData(type, 0, data);
	}

	public void delete() {
		vbosCreated--;
		GL15.glDeleteBuffers(vboId);
	}

	public boolean isStatic() {
		return isStatic;
	}

	public int getAttribute() {
		return attribute;
	}
}
