package utils.transformations;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class EulerTransform {

	private static final Vector3f X_AXIS = new Vector3f(1, 0, 0);
	private static final Vector3f Y_AXIS = new Vector3f(0, 1, 0);
	private static final Vector3f Z_AXIS = new Vector3f(0, 0, 1);

	public enum RotationType {
		XYZ(1, 2, 3), XZY(1, 3, 2), YXZ(2, 1, 3), YZX(2, 3, 1), ZXY(3, 1, 2), ZYX(3, 2, 1);

		private int[] order;

		RotationType(int first, int second, int third) {
			order = new int[] { first, second, third };
		}

		protected static float getAngle(int index, EulerTransform transform) {
			int o = transform.type.order[index-1];
			switch (o) {
			case 1:
				return transform.rotX;
			case 2:
				return transform.rotY;
			case 3:
				return transform.rotZ;
			}
			return 0;
		}

		protected static Vector3f getAxis(int index, EulerTransform transform) {
			int o = transform.type.order[index-1];
			switch (o) {
			case 1:
				return X_AXIS;
			case 2:
				return Y_AXIS;
			case 3:
				return Z_AXIS;
			}
			return new Vector3f();
		}
	}

	public float posX, posY, posZ, rotX, rotY, rotZ, scaleX = 1, scaleY = 1, scaleZ = 1;

	private RotationType type = RotationType.XYZ;

	public EulerTransform() {
	}

	public EulerTransform(float posX, float posY, float posZ, float rotX, float rotY, float rotZ, float scaleX,
			float scaleY, float scaleZ) {
		super();
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		this.scaleZ = scaleZ;
	}

	public Matrix4f toMatrix() {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(new Vector3f(posX, posY, posZ), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(RotationType.getAngle(1, this)), RotationType.getAxis(1, this), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(RotationType.getAngle(2, this)), RotationType.getAxis(2, this), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(RotationType.getAngle(3, this)), RotationType.getAxis(3, this), matrix, matrix);
		Matrix4f.scale(new Vector3f(scaleX, scaleY, scaleZ), matrix, matrix);
		return matrix;
	}

	public void setPosition(Vector3f pos) {
		posX = pos.x;
		posY = pos.y;
		posZ = pos.z;
	}

	public void setRotation(Vector3f rot) {
		this.rotX = rot.x;
		this.rotY = rot.y;
		this.rotZ = rot.z;
	}

	public void setScale(Vector3f scale) {
		this.scaleX = scale.x;
		this.scaleY = scale.y;
		this.scaleZ = scale.z;
	}

	public void setScale(float scale) {
		this.scaleX = scale;
		this.scaleY = scale;
		this.scaleZ = scale;
	}

	public Vector3f getPosition() {
		return new Vector3f(posX, posY, posZ);
	}

	public Vector3f geRotation() {
		return new Vector3f(rotX, rotY, rotZ);
	}

	public Vector3f getScale() {
		return new Vector3f(scaleX, scaleY, scaleZ);
	}

	public RotationType getType() {
		return type;
	}

	public void setType(RotationType type) {
		this.type = type;
	}

}
