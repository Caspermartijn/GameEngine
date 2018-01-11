package hud;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import textures.Texture;
import utils.SourceFile;
import utils.maths.Maths;

public class Hud {

	private Texture cornerTex = Texture.getTextureBuilder(new SourceFile("/res/guis/hud/hud_corner.png")).create();
	private Texture midTex = Texture.getTextureBuilder(new SourceFile("/res/guis/hud/hud_mid.png")).create();
	private Texture sideTex = Texture.getTextureBuilder(new SourceFile("/res/guis/hud/hud_side.png")).create();
	
	private Vector2f position = new Vector2f(0,0);
	private float scale = 1;

	private List<HudTexture> textures = new ArrayList<HudTexture>();
	
	public Hud(Vector2f position, float scale) {
		this.position = position;
		this.scale = scale;
		generate();
	}
	
	private void generate() {
		Vector2f scale = Maths.getNormalizedSize(100*this.scale, 100*this.scale);
		
	}
 	
}
