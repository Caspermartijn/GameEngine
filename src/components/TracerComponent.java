package components;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import objects.Vao;

public class TracerComponent implements Component {

	public static List<TracerComponent> allComponents = new ArrayList<TracerComponent>();

	public Vao vao;

	private Vector3f origen = new Vector3f();

	public TracerComponent() {
		vao = Vao.create();
		vao.createStaticAttribute(0, new float[] { origen.x, origen.y, origen.z, }, 3);
		vao.setVertexCount(2);
		allComponents.add(this);
	}

	@Override
	public void update() {

	}

	public void updateVao() {
		Vao.vaosCreated--;
		Vao.vaosDeleted--;
		vao.delete();
		vao = Vao.create();
		vao.createStaticAttribute(0, new float[] { origen.x, origen.y, origen.z, }, 3);
		vao.setVertexCount(2);
	}

	public Vector3f getOrigen() {
		return origen;
	}

	public void setOrigen(Vector3f origen) {
		this.origen = origen;
	}

	@Override
	public Type getType() {
		return Type.TRACER;
	}

}
