package hud;

import textures.Texture;

public class HudTexture {

	private Texture tex;

	private boolean seetrough;

	public HudTexture(Texture tex, boolean seetrough) {
		super();
		this.tex = tex;
		this.seetrough = seetrough;
	}

	public Texture getTex() {
		return tex;
	}

	public void setTex(Texture tex) {
		this.tex = tex;
	}

	public boolean isSeetrough() {
		return seetrough;
	}

	public void setSeetrough(boolean seetrough) {
		this.seetrough = seetrough;
	}

}
