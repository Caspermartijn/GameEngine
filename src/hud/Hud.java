package hud;

import java.util.Collection;
import java.util.HashMap;

import org.lwjgl.util.vector.Vector2f;

import texts.Text;
import utils.maths.Maths;

public abstract class Hud {

	private HashMap<Text, Vector2f> allTexts = new HashMap<Text, Vector2f>();
	private HashMap<HudTexture, Vector2f> allHuds = new HashMap<HudTexture, Vector2f>();

	private float scale;
	private Vector2f position;

	public Hud(float scale_in_factor, Vector2f position) {
		this.scale = scale_in_factor;
		this.position = position;
		init();
	}
	
	public abstract void init();

	public float getScale() {
		return scale;
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
		for (Text text : allTexts.keySet()) {
			Vector2f offset = allTexts.get(text);
			Vector2f toTextPos = Maths.openGLtoText(position);
			text.setPosition((toTextPos.x + ((offset.x - 0.5f) * scale)), (toTextPos.y + ((offset.y - 0.5f) * scale)));
			text.setFontSize(text.getStartFontSize() * scale);
		}
		for (HudTexture hud : allHuds.keySet()) {
			Vector2f offset = allHuds.get(hud);
			hud.setPosition(position.x + (offset.x * scale), position.y + (offset.y * scale));
		}
	}

	
	public void addText(Text text) {
		allTexts.put(text, text.getPosition());
		Vector2f offset = text.getPosition();
		Vector2f toTextPos = Maths.openGLtoText(position);
		text.setFontSize(text.getStartFontSize() * scale);
		text.setPosition((toTextPos.x + ((offset.x - 0.5f) * scale)), (toTextPos.y + ((offset.y - 0.5f) * scale)));
	}

	public void removeText(Text text) {
		allTexts.remove(text);
	}

	public void addHud(HudTexture hud) {
		allHuds.put(hud, hud.getPosition());
		Vector2f offset = hud.getPosition();
		hud.setPosition(position.x + (offset.x * scale), position.y + (offset.y * scale));
		hud.setScale(new Vector2f(hud.getScale().x * scale, hud.getScale().y * scale));
	}

	public void removeHud(HudTexture hud) {
		allHuds.remove(hud);
	}

	public Collection<Text> getAllTexts() {
		return allTexts.keySet();
	}

	public Collection<HudTexture> getAllHuds() {
		return allHuds.keySet();
	}

}
