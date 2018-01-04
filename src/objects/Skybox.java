package objects;

import engine.EngineDisplay;
import textures.CubeMapTexture;
import utils.SourceFile;

public class Skybox {

	private final CubeMapTexture texture;
	private float size = 1;
	private float rotation;
	private float rotationSpeed;

	public Skybox(SourceFile[] textures, int textureSize) {
		texture = CubeMapTexture.createCubeMap(textures, textureSize);
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}

	public CubeMapTexture getTexture() {
		return texture;
	}

	public float getRotationSpeed() {
		return rotationSpeed;
	}

	public void setRotationSpeed(float rotationSpeed) {
		this.rotationSpeed = rotationSpeed;
	}

	public void updateRotation() {
		rotation += EngineDisplay.getFrameTime() * rotationSpeed;
		rotation %= rotation;
	}

	public void delete() {
		texture.delete();
	}

	public float getRotation() {
		return rotation;
	}

	public void increaseRotation(float f) {
		rotation += f;
	}

}
