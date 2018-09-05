package renderer.terrainRenderer;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import objects.Vao;
import terrains.Terrain;
import terrains.terrainTexture.TerrainPack;
import utils.tasks.Cleanup;

public class TerrainRenderer extends Cleanup{

	private TerrainShader shader;

	public TerrainRenderer(TerrainShader shader, Matrix4f projectionMatrix) {
		this.shader = shader;
	}

	public void loadProjMatrix(Matrix4f matrix) {
		shader.start();
		shader.location_projectionMatrix.loadMatrix(matrix);
		shader.stop();
	}

	public void render(List<Terrain> terrains) {
		for (Terrain terrain : terrains) {
			prepareTerrain(terrain);
			GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			unbindTexturedModel(terrain);
		}
	}

	private void prepareTerrain(Terrain terrain) {
		Vao rawModel = terrain.getModel();
		rawModel.bind(0, 1, 2);
		bindTextures(terrain);
		shader.location_shineDamper.loadFloat(1);
		shader.location_reflectivity.loadFloat(0);
	}

	private void bindTextures(Terrain terrain) {
		TerrainPack texturePack = terrain.getPack();
		terrains.terrainTexture.TerrainTexturePack textureP = texturePack.getPack();
		shader.location_backgroundTexture.bindTexture(textureP.getBack().getTexture());
		shader.location_rTexture.bindTexture(textureP.getRed().getTexture());
		shader.location_gTexture.bindTexture(textureP.getGreen().getTexture());
		shader.location_bTexture.bindTexture(textureP.getBlue().getTexture());
		shader.location_blendMap.bindTexture(terrain.getBlendMapID());
	}

	private void unbindTexturedModel(Terrain terrain) {
		terrain.getModel().unbind(0, 1, 2);
	}

	@Override
	public void delete() {
		shader.delete();
		System.out.println("- TerrainShader");
	}

}
