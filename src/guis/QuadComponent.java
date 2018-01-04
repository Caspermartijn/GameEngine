package guis;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import engine.EngineDisplay;

public class QuadComponent extends GUIComponent {

	private Vector4f backgroundColor = new Vector4f(0, 0, 0, 1);
	private Vector4f outlineColor = new Vector4f(0, 0, 0, 1);
	private float x, y, width, height;
	private float outlineWidth;
	private GUI container;
	
	public QuadComponent(GUI container, float x, float y, float width, float height) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.container = container;
		container.addComponent(this);
	}
	
	public float getX() {
		return x + container.getPosition().x;
	}
	
	public float getY() {
		return y + container.getPosition().y;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}

	public Vector4f getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Vector4f backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public Vector4f getOutlineColor() {
		return outlineColor;
	}

	public void setOutlineColor(Vector4f outlineColor) {
		this.outlineColor = outlineColor;
	}

	public float getOutlineWidth() {
		return outlineWidth;
	}

	public void setOutlineWidth(float outlineWidth) {
		this.outlineWidth = outlineWidth;
	}	
	
	public Vector4f getDimensions() {
		return new Vector4f(getX(), getY(), width, height);
	}
	
	public Vector4f getInnerDimensions() {
		return new Vector4f(getX() + outlineWidth, getY() + outlineWidth*EngineDisplay.getAspectRatio(), width - outlineWidth*2, height - outlineWidth*2*EngineDisplay.getAspectRatio());
	}
	
	public Matrix4f getBackgroundTransform() {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		
		float posX = (getX()-0.5f)*2.0f + getWidth();
		float posY = (getY()-0.5f)*-2.0f - getHeight();
		Matrix4f.translate(new Vector3f(posX, posY, 0),matrix,matrix);
		Matrix4f.scale(new Vector3f(width, height, 1),matrix,matrix);	
		return matrix;
	}
	
	@Override
	public void render() {
		GUIMaster.getQuadRenderer().renderQuad(this);
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	@Override
	public void delete() {
		
	}
}
