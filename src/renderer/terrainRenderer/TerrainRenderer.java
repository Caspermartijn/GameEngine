package renderer.terrainRenderer;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;

import objects.Vao;
import terrains.Terrain;
import terrains.terrainTexture.TerrainTexturePack;
import textures.Texture;

public class TerrainRenderer {

	private TerrainShader shader;

	public TerrainRenderer(TerrainShader shader) {
		this.shader = shader;

		shader.start();
		shader.location_renderDistance.loadFloat(2500);
		shader.stop();
	}

	public void setProjMat(Matrix4f mat) {
		shader.location_projectionMatrix.loadMatrix(mat);
	}

	public void setTileAmount(int back, int red, int green, int blue) {
		shader.start();
		shader.loadTileAmounts(back, red, green, blue);
		shader.stop();
	}

	public void render(Map<TerrainTexturePack, List<Terrain>> terrains, Matrix4f toShadowSpace, Vector4f clipplane) {
		for (TerrainTexturePack pack : terrains.keySet()) {
			bindTextures(pack);
			for (Terrain terrain : terrains.get(pack)) {
				bindBlendMap(terrain.getBlendMapID());
				prepareTerrain(terrain);
				GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
				unbindTexturedModel(terrain.getModel());
			}
		}
	}

	private void bindTextures(TerrainTexturePack pack) {
		shader.location_back.bindTexture(pack.getBack().getTexture());
		shader.location_red.bindTexture(pack.getRed().getTexture());
		shader.location_green.bindTexture(pack.getGreen().getTexture());
		shader.location_blue.bindTexture(pack.getBlue().getTexture());

		shader.location_normalBack.bindTexture(pack.getBack().getNormalMap());
		shader.location_normalRed.bindTexture(pack.getRed().getNormalMap());
		shader.location_normalGreen.bindTexture(pack.getGreen().getNormalMap());
		shader.location_normalBlue.bindTexture(pack.getBlue().getNormalMap());
	}

	private void bindBlendMap(Texture blendMap) {
		shader.location_blend.bindTexture(blendMap);
	}

	public void loadFog(float density, float gradient, float red, float green, float blue) {
		shader.start();
		shader.location_density.loadFloat(density);
		shader.location_gradient.loadFloat(gradient);
		shader.location_skyColor.loadVec3(red, green, blue);
		shader.stop();
	}

	private void prepareTerrain(Terrain terrain) {
		Vao rawModel = terrain.getModel();
		rawModel.bind(0, 1, 2, 3);
		shader.location_shineDamper.loadFloat(1);
		shader.location_reflectivity.loadFloat(0);
		shader.location_numberOfRows.loadFloat(0);
		shader.location_offset.loadVec2(terrain.getTextureXOffset(), terrain.getTextureYOffset());
	}

	private void unbindTexturedModel(Vao vao) {
		vao.unbind(0, 1, 2, 3);
	}

}
