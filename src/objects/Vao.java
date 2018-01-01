package objects;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Vao {

	public final int id;
	private List<Vbo> dataVbos = new ArrayList<Vbo>();
	private Vbo indexVbo;
	private int indexCount;
	private int vertexCount;
	private static Vao bound;

	public static Vao create() {
		int id = GL30.glGenVertexArrays();
		return new Vao(id);
	}

	private Vao(int id) {
		this.id = id;
	}

	public int getVertexCount() {
		return vertexCount;
	}

	public void setVertexCount(int vertexCount) {
		this.vertexCount = vertexCount;
	}

	public int getIndexCount() {
		return indexCount;
	}

	public void bind(int... attributes) {
		bind();
		for (int i : attributes) {
			GL20.glEnableVertexAttribArray(i);
		}
	}

	public void unbind(int... attributes) {
		for (int i : attributes) {
			GL20.glDisableVertexAttribArray(i);
		}
		unbind();
	}

	public void createStaticIndexBuffer(int[] indices) {
		this.indexVbo = Vbo.createStaticVbo(GL15.GL_ELEMENT_ARRAY_BUFFER);
		indexVbo.bind();
		indexVbo.storeStaticData(indices);
		this.indexCount = indices.length;
	}

	public void createStaticAttribute(int attribute, float[] data, int attrSize) {
		Vbo dataVbo = Vbo.createStaticVbo(GL15.GL_ARRAY_BUFFER);
		dataVbo.bind();
		dataVbo.storeStaticData(data);
		GL20.glVertexAttribPointer(attribute, attrSize, GL11.GL_FLOAT, false, 0, 0);
		dataVbo.setAttribute(attribute);
		dataVbo.unbind();
		dataVbos.add(dataVbo);
	}

	public void createStaticIntAttribute(int attribute, int[] data, int attrSize) {
		Vbo dataVbo = Vbo.createStaticVbo(GL15.GL_ARRAY_BUFFER);
		dataVbo.bind();
		dataVbo.storeStaticData(data);
		GL30.glVertexAttribIPointer(attribute, attrSize, GL11.GL_INT, 0, 0);
		dataVbo.setAttribute(attribute);
		dataVbo.unbind();
		dataVbos.add(dataVbo);
	}

	public void createIndexBuffer(int maxLength) {
		this.indexVbo = Vbo.createVbo(GL15.GL_ELEMENT_ARRAY_BUFFER);
		indexVbo.bind();
		indexVbo.storeData(maxLength * 4);
		this.indexCount = 0;
	}

	public void createAttribute(int attribute, int maxLength, int attrSize) {
		Vbo dataVbo = Vbo.createStaticVbo(GL15.GL_ARRAY_BUFFER);
		dataVbo.bind();
		dataVbo.storeData(maxLength * 4);
		GL20.glVertexAttribPointer(attribute, attrSize, GL11.GL_FLOAT, false, attrSize * 4, 0);
		dataVbo.setAttribute(attribute);
		dataVbo.unbind();
		dataVbos.add(dataVbo);
	}

	public void createIntAttribute(int attribute, int maxLength, int attrSize) {
		Vbo dataVbo = Vbo.createStaticVbo(GL15.GL_ARRAY_BUFFER);
		dataVbo.bind();
		dataVbo.storeData(maxLength * 4);
		GL30.glVertexAttribIPointer(attribute, attrSize, GL11.GL_INT, attrSize * 4, 0);
		dataVbo.setAttribute(attribute);
		dataVbo.unbind();
		dataVbos.add(dataVbo);
	}

	public void setIndexBuffer(int[] data) {
		if (indexVbo.isStatic())
			return;

		indexVbo.setIntData(data);
	}

	public void setVboData(int[] data, int attribute) {
		if (getVboByAttribute(attribute) == null)
			return;

		Vbo target = getVboByAttribute(attribute);
		target.bind();
		target.storeData(data.length * 4);
		target.setIntData(data);
		target.unbind();
	}

	public void setVboData(float[] data, int attribute) {
		if (getVboByAttribute(attribute) == null)
			return;

		Vbo target = getVboByAttribute(attribute);
		target.bind();
		target.storeData(data.length * 4);
		target.setFloatData(data);
		target.unbind();
	}

	private Vbo getVboByAttribute(int attribute) {
		for (Vbo vbo : dataVbos) {
			if (vbo.getAttribute() == attribute) {
				return vbo;
			}
		}
		return null;
	}

	public void delete() {
		GL30.glDeleteVertexArrays(id);
		for (Vbo vbo : dataVbos) {
			vbo.delete();
		}
		if (indexVbo != null)
			indexVbo.delete();
	}

	private void bind() {
		if (bound == this)
			return;
		bound = this;
		GL30.glBindVertexArray(id);
	}

	private void unbind() {
		bound = null;
		GL30.glBindVertexArray(0);
	}

	public boolean isBound() {
		return bound == this;
	}

	public static Vao getBoundVao() {
		return bound;
	}

	public Vao loadToVAO(float[] verticesArray, float[] texturesArray, float[] normalsArray, int[] indicesArray,
			float[] tangentsArray) {
		bind();
		createIndexBuffer(indicesArray.length);
		createStaticAttribute(0, verticesArray, 3);
		createStaticAttribute(1, texturesArray, 2);
		createStaticAttribute(2, normalsArray, 3);
		createStaticAttribute(3, tangentsArray, 3);
		unbind();
		return this;
	}

	public Vao loadToVAO(float[] verticesArray, float[] texturesArray, float[] normalsArray, int[] indicesArray) {
		bind();
		createIndexBuffer(indicesArray.length);
		createStaticAttribute(0, verticesArray, 3);
		createStaticAttribute(1, texturesArray, 2);
		createStaticAttribute(2, normalsArray, 3);
		unbind();
		return this;
	}
}
