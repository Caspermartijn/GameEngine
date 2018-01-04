package terrains;

import org.lwjgl.util.vector.Vector2f;

public class BlendMapData {
	private Vector2f row;
	private int rows;

	public BlendMapData(Vector2f row, int rows) {
		this.row = row;
		this.rows = rows;
	}

	public Vector2f getRow() {
		return row;
	}

	public int getRows() {
		return rows;
	}

}
