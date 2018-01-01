package texts;

import objects.Vao;

public class TextMeshData {
	
	private float[] vertexPositions;
	private float[] textureCoords;
	
	protected TextMeshData(float[] vertexPositions, float[] textureCoords){
		this.vertexPositions = vertexPositions;
		this.textureCoords = textureCoords;
	}

	public float[] getVertexPositions() {
		return vertexPositions;
	}

	public float[] getTextureCoords() {
		return textureCoords;
	}

	public int getVertexCount() {
		return vertexPositions.length/2;
	}
	
	public Vao toVao() {
		Vao vao = Vao.create();
		vao.bind();
		vao.createStaticAttribute(0, vertexPositions, 2);
		vao.createStaticAttribute(1, textureCoords, 2);
		vao.unbind();
		return vao;
	}

}
