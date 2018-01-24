package guis;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import entities.Entity;
import objects.Camera;
import objects.Model_3D;
import renderer.MasterRenderer;

public class EntityComponent extends GUIComponent {

	public EntityComponent(GUI container, Model_3D model, Vector2f position, Vector3f rotation, float scale) {
		super(container);
		entity = new Entity(model, new Vector3f(position.x, position.y, 0), rotation, scale);
		entity.setType(Entity.Type.Camera);
		ori_Position = new Vector3f(position.x, position.y, 1f);
	}

	private Entity entity;
	private Vector3f ori_Position;
	private static Matrix4f camEntPos = new Matrix4f();

	public void updateEntity(Camera cam) {
		float distance = ori_Position.z;
		float offsetX = ori_Position.x;
		float offsetY = ori_Position.y;
		camEntPos.setZero();
		camEntPos.setIdentity();
		Matrix4f.rotate((float) Math.toRadians(-cam.yaw), new Vector3f(0, 1, 0), camEntPos, camEntPos);
		Matrix4f.rotate((float) Math.toRadians(-cam.pitch), new Vector3f(1, 0, 0), camEntPos, camEntPos);
		Vector4f result = new Vector4f();
		Matrix4f.transform(camEntPos, new Vector4f(offsetX * distance, offsetY * distance, -distance, 1), result);
		entity.getTransform().setPosition(result.x + cam.x, result.y + cam.y, result.z + cam.z);
	}

	@Override
	public void render() {
		MasterRenderer.addEntityComponents(this);
	}

	@Override
	public void delete() {

	}

	public Entity getEntity() {
		return entity;
	}
}
