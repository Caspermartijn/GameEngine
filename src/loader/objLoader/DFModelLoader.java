package loader.objLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import animation.Joint;
import components.ArmatureComponent;
import jdk.nashorn.internal.runtime.GlobalConstants;
import objects.Vao;
import scene.Model;
import utils.SourceFile;

public class DFModelLoader {

	public static Model loadDFMeshModel(SourceFile file, boolean loadJointData) {
		try {
			BufferedReader br = file.openFileReader();

			ArrayList<Vertex> vertices = new ArrayList<>();
			Vertex lastVertex = null;

			ArrayList<Integer> indicesList = new ArrayList<>();

			// read data
			try {
				String line = br.readLine();
				while (line != null) {
					if (line.contains("vertex:")) {
						if (lastVertex != null) {
							vertices.add(lastVertex);
						}

						String valuesLine = line.split(":")[1];
						String[] values = valuesLine.split(";");

						String[] positionString = values[0].split(" ");
						Vector3f position = new Vector3f(Float.parseFloat(positionString[0]),
								Float.parseFloat(positionString[1]), Float.parseFloat(positionString[2]));

						String[] textureCoordsString = values[1].split(" ");
						Vector2f textureCoords = new Vector2f(Float.parseFloat(textureCoordsString[0]),
								Float.parseFloat(textureCoordsString[1]));

						String[] normalString = values[2].split(" ");
						Vector3f normal = new Vector3f(Float.parseFloat(normalString[0]),
								Float.parseFloat(normalString[1]), Float.parseFloat(normalString[2]));

						lastVertex = new Vertex(position, normal, textureCoords);
					} else if ((line.contains("weight:") && lastVertex != null) && loadJointData) {
						String valuesLine = line.split(":")[1];
						String[] values = valuesLine.split(";");
						int id = Integer.valueOf(values[0]);
						float weight = Float.parseFloat(values[1]);
						if (id >= 0)
							lastVertex.addWeight(new Weight(id, weight));
					} else if ((line.contains("triangle:"))) {
						String valuesLine = line.split(":")[1];
						String[] values = valuesLine.split(" ");
						indicesList.add(Integer.valueOf(values[0]));
						indicesList.add(Integer.valueOf(values[1]));
						indicesList.add(Integer.valueOf(values[2]));
					}
					line = br.readLine();
				}

				if (lastVertex != null) {
					vertices.add(lastVertex);
				}
				br.close();
			} catch (Exception e) {
				System.err.println("Error Reading file: " + file.getPath());
				e.printStackTrace();
			}

			if (vertices.isEmpty() || indicesList.isEmpty()) {
				throw new IllegalArgumentException("No valid model data found in: " + file.getPath());
			}

			// convertToArray
			float[] positions = new float[vertices.size() * 3];
			float[] normals = new float[vertices.size() * 3];
			float[] textures = new float[vertices.size() * 2];

			int[] indices = new int[indicesList.size()];

			for (int i = 0; i < vertices.size(); i++) {
				Vertex v = vertices.get(i);
				if (v.getWeights().size() == 0) 
					System.out.println(v.getPos());
				positions[i * 3] = v.getPos().getX();
				positions[i * 3 + 1] = v.getPos().getY();
				positions[i * 3 + 2] = v.getPos().getZ();
				normals[i * 3] = v.getNormal().getX();
				normals[i * 3 + 1] = v.getNormal().getY();
				normals[i * 3 + 2] = v.getNormal().getZ();
				textures[i * 2] = v.getTex().getX();
				textures[i * 2 + 1] = v.getTex().getY();
			}

			for (int i = 0; i < indicesList.size(); i++) {
				indices[i] = indicesList.get(i);
			}

			// bind VAO

			Vao vao = Vao.create();
			vao.bind();
			vao.createStaticIndexBuffer(indices);
			vao.createStaticAttribute(0, positions, 3);
			vao.createStaticAttribute(1, textures, 2);
			vao.createStaticAttribute(2, normals, 3);

			if (loadJointData) {
				int[] jointIDs = new int[vertices.size() * GlobalConstants.MAX_WEIGHTS];
				float[] weightValues = new float[vertices.size() * GlobalConstants.MAX_WEIGHTS];
				for (int i = 0; i < vertices.size(); i++) {
					Vertex v = vertices.get(i);
					List<Weight> weights = v.getWeights();
					for (int j = 0; j < GlobalConstants.MAX_WEIGHTS; j++) {
						if (weights.size() < j + 1) {
							jointIDs[i * GlobalConstants.MAX_WEIGHTS + j] = 0;
							weightValues[i * GlobalConstants.MAX_WEIGHTS + j] = 0f;
						} else {
							jointIDs[i * GlobalConstants.MAX_WEIGHTS + j] = weights.get(j).getJoint();
							weightValues[i * GlobalConstants.MAX_WEIGHTS + j] = weights.get(j).getValue();
						}
					}
				}
				vao.createStaticIntAttribute(3, jointIDs, GlobalConstants.MAX_WEIGHTS);
				vao.createStaticAttribute(4, weightValues, GlobalConstants.MAX_WEIGHTS);
			}

			vao.unbind();
			Model m = new Model(vao, indices.length);
			return m;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void addArmatureComponent(SourceFile file, Model m) {
		try {
			BufferedReader br = file.openFileReader();
			int numJoints = 0;
			ArrayList<Joint> joints = new ArrayList<>();
			String line = br.readLine();
			while (line != null) {
				if (line.contains("numJoints:")) {
					numJoints = Integer.valueOf(line.split(":")[1]);
				} else if (line.contains("joint:")) {
					String valuesLine = line.split(":")[1];
					String[] values = valuesLine.split(";");
					int index = Integer.valueOf(values[0]);
					String name = values[1];
					String parentName = values[2];
					String matrix = values[3];
					Matrix4f bindTransform = convertToMatrix(matrix, " ");
					Joint joint = new Joint(index, name, parentName, bindTransform);
					joints.add(joint);
				} else if (line.contains("vertex:")) {
					break;
				}
				line = br.readLine();
			}
			br.close();
			
			if (joints.isEmpty()) {
				System.err.println("No skeleton data found in: " + file.getPath());
				return;
			}

			Joint root = null;
			for (Joint j : joints) {
				if (j.parentName.equals("*")) {
					root = j;
				} else {
					for (Joint joint : joints) {
						if (joint.name.equals(j.parentName)) {
							joint.addChild(j);
						}
					}
				}
			}
			m.addComponent(new ArmatureComponent(numJoints, root, joints));
		} catch (IOException e) {
			System.err.println("Error Reading file: " + file.getPath());
			e.printStackTrace();
		}
	}
	
	private static Matrix4f convertToMatrix(String matrixString, String regex) {
		String[] stringValues = matrixString.split(regex);
		float[] floatValues = new float[16];
		for (int i = 0; i < 16; i++) {
			floatValues[i] = Float.valueOf(stringValues[i]);
		}
		Matrix4f matrix = new Matrix4f();
		matrix.m00 = floatValues[0];
		matrix.m01 = floatValues[1];
		matrix.m02 = floatValues[2];
		matrix.m03 = floatValues[3];
		matrix.m10 = floatValues[4];
		matrix.m11 = floatValues[5];
		matrix.m12 = floatValues[6];
		matrix.m13 = floatValues[7];
		matrix.m20 = floatValues[8];
		matrix.m21 = floatValues[9];
		matrix.m22 = floatValues[10];
		matrix.m23 = floatValues[11];
		matrix.m30 = floatValues[12];
		matrix.m31 = floatValues[13];
		matrix.m32 = floatValues[14];
		matrix.m33 = floatValues[15];
		return matrix;
	}

}

class Vertex {

	private Vector3f pos;
	private Vector3f normal;
	private Vector2f tex;

	private ArrayList<Weight> weights = new ArrayList<>();

	public Vertex(Vector3f pos, Vector3f normal, Vector2f tex) {
		super();
		this.pos = pos;
		this.normal = normal;
		this.tex = tex;
	}

	public void addWeight(Weight weight) {
		weights.add(weight);
	}

	private Comparator<Weight> comp = new Comparator<Weight>() {
		@Override
		public int compare(Weight w1, Weight w2) {
			if (w1.getValue() == w2.getValue()) {
				return 0;
			} else if (w1.getValue() < w2.getValue()) {
				return 1;
			} else {
				return -1;
			}
		}
	};

	public List<Weight> getWeights() {
		if (weights.size() > GlobalConstants.MAX_WEIGHTS) {
			Collections.sort(weights, comp);
			List<Weight> allWeights = weights.subList(0, GlobalConstants.MAX_WEIGHTS);
			return weights;
		} else {
			return weights;
		}
	}

	public Vector3f getPos() {
		return pos;
	}

	public Vector3f getNormal() {
		return normal;
	}

	public Vector2f getTex() {
		return tex;
	}
}

class Weight {

	private int joint;
	private float value;

	public Weight(int joint, float value) {
		super();
		this.joint = joint;
		this.value = value;
	}

	public int getJoint() {
		return joint;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}
}
