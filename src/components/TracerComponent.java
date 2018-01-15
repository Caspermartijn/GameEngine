package components;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import objects.Vao;

public class TracerComponent implements Component {

	public static List<TracerComponent> allComponents = new ArrayList<TracerComponent>();

	public Vao vao;

	private Vector3f origen = new Vector3f();
	private Vector3f end = new Vector3f();

	public TracerComponent() {
		vao = Vao.create();
		vao.createAttribute(0, 2, 3);
		vao.setVertexCount(2);
		vao.setVboData(new float[] { origen.x, origen.y, origen.z, end.x, end.y, end.z }, 0);
		allComponents.add(this);
	}

	@Override
	public void update() {
		
	}
	
	public void updateVao() {
		vao.setVboData(new float[] { origen.x, origen.y, origen.z, end.x, end.y, end.z }, 0);
	}

	public void setPoisition(Vector3f origen, Vector3f end) {
		this.origen = origen;
		this.end = end;
	}

	public Vector3f getOrigen() {
		return origen;
	}

	public void setOrigen(Vector3f origen) {
		this.origen = origen;
	}

	public Vector3f getEnd() {
		return end;
	}

	public void setEnd(Vector3f end) {
		this.end = end;
	}

	@Override
	public Type getType() {
		return Type.TRACER;
	}

}
