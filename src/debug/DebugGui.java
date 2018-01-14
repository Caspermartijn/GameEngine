package debug;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import engine.Display;
import engine.Keyboard;
import engine.Mouse;
import guis.GUI;
import guis.ImageComponent;
import guis.TextComponent;
import guis.TextFieldComponent;
import log.Log;
import objects.Camera;
import objects.Vao;
import objects.Vbo;
import renderer.MasterRenderer;
import shaders.uniforms.ShaderProgram;
import textures.Texture;
import utils.RenderItem;
import utils.SourceFile;
import utils.maths.Maths;

public class DebugGui extends GUI {

	private TextComponent fps;
	private TextComponent delta;
	private TextComponent ping;

	private TextComponent campos;
	private TextComponent vaos;
	private TextComponent vbos;
	private TextComponent textures;
	private TextComponent shaders;

	private TextComponent entities;
	private TextComponent terrains;
	private TextComponent lights;

	private TextComponent scenes;

	private List<TextComponent> texts = new ArrayList<TextComponent>();

	private boolean hidden = true;

	public DebugGui() {
		new RenderItem() {
			boolean b = false;

			@Override
			public void render() {
				if (Keyboard.isKeyDown(GLFW.GLFW_KEY_F1)) {
					if (!b) {
						b = true;
						if (hidden) {
							show();
							Log.append("Debug menu enabled", false, new Vector3f(0.1f, 1, 0.1f));
						} else {
							hide();
						}
					}
				} else {
					b = false;
				}
			}
		};

		super.setPosition(0f, 0.0f);
		images();
		buttons();
		texts();
		texts2();
		this.hideAll();
	}

	private void buttons() {
		TextFieldComponent command_field = new TextFieldComponent(this, 0.3f, 0.211f, 0.4f, 0.03f, "candara", 1f,
				false);
		command_field.setBackgroundColor(new Vector4f(0.7f, 0.7f, 0.7f, 1));

		new RenderItem() {
			@Override
			public void render() {
				if (!hidden) {
					if (command_field.isActive()) {
						String text = command_field.getText();
						if (text != "") {
							if (Keyboard.isKeyDown(GLFW.GLFW_KEY_ENTER)) {
								if (text.startsWith("/") || text.startsWith("-")) {
									boolean accepted = CommandHandler.runCommand(command_field.getText());
									if(!accepted) {
										Log.append("That command doesn't exist!", false, new Vector3f(0.98f, 0.019607f, 1));
									}else {
										Log.append(command_field.getText(), false, new Vector3f(0.98f, 0.019607f, 1));
									}
									
									command_field.setText("");
								} else {
									Log.append("Not an command specified (Use / for commands)", false, new Vector3f(0.98f, 0.019607f, 1));
									command_field.setText("");
								}
							}
						}
					}
				}
			}

		};
	}

	private void images() {
		ImageComponent background = new ImageComponent(this, new SourceFile("/res/guis/hud/hud_side.png"));
		background.setScale(1f);
		Vector2f vec = Maths.getFrom720toCurrentDisplaySize(new Vector2f(1280, 200));
		background.setSize(vec.x, vec.y);
		background.setPosition(0.5f, 0.11f);
		background.setRotation(180);
		background.show();
	}

	private void texts2() {
		campos = new TextComponent(this, "Campos: ", "candara", 0.775f, 200, false);
		vaos = new TextComponent(this, "Vao: ", "candara", 0.775f, 200, false);
		vbos = new TextComponent(this, "Vbo: ", "candara", 0.775f, 200, false);
		textures = new TextComponent(this, "Textures: ", "candara", 0.775f, 200, false);
		shaders = new TextComponent(this, "Shaders: ", "candara", 0.775f, 200, false);

		entities = new TextComponent(this, "Entities: ", "candara", 0.775f, 200, false);
		terrains = new TextComponent(this, "Terrains: ", "candara", 0.775f, 200, false);
		lights = new TextComponent(this, "Lights: ", "candara", 0.775f, 200, false);

		scenes = new TextComponent(this, "Scenes: ", "candara", 0.775f, 200, false);

		texts.add(campos);
		texts.add(vaos);
		texts.add(vbos);
		texts.add(textures);
		texts.add(shaders);

		texts.add(entities);
		texts.add(terrains);
		texts.add(lights);

		texts.add(scenes);

		float f = 0;

		float f2 = 0.1f;

		int i = 0;

		for (TextComponent component : texts) {
			component.setPosition(0.001f + f2, 0f + f);
			f += 0.017f;
			component.setColor(1, 1, 1, 1);
			i++;
			if (i == 5 || i == 8 || i == 1) {
				f += 0.017f;
			}
		}
	}

	private void texts() {
		fps = new TextComponent(this, "fps: ", "candara", 0.8f, 200, false);
		delta = new TextComponent(this, "delta:", "candara", 0.8f, 200, false);
		ping = new TextComponent(this, "ping: ", "candara", 0.8f, 200, false);
		fps.setPosition(0.001f, 0f);
		delta.setPosition(0.001f, 0.02f);
		ping.setPosition(0.001f, 0.04f);
		fps.setColor(1f, 1f, 1f, 1f);
		delta.setColor(1f, 1f, 1f, 1f);
		ping.setColor(1f, 1f, 1f, 1f);
	}

	public void update(Camera camera) {
		fps.setText("FPS: " + Display.getFPS());
		delta.setText("DELTA: " + (Display.getDeltaSeccond() * 100));
		ping.setText("ping: " + 0);

		campos.setText("campos: " + (short) camera.x + " " + (short) camera.y + " " + (short) camera.z);
		vaos.setText("vao: " + Vao.vaosCreated);
		vbos.setText("vbo: " + Vbo.vbosCreated);
		textures.setText("textures: " + Texture.created);
		shaders.setText("shaders: " + ShaderProgram.created);

		entities.setText("entities: " + MasterRenderer.getEntitiesAmount());
		terrains.setText("terrains: " + MasterRenderer.getTerrainsAmount());
		lights.setText("lights: " + MasterRenderer.getLightsAmount());
	}

	public boolean isHidden() {
		return hidden;
	}

	private boolean wasMousEnabled = false;

	public void hide() {
		Mouse.setMouseEnabled(wasMousEnabled);
		hidden = true;
		this.hideAll();
	}

	public void show() {
		wasMousEnabled = Mouse.isCursorActivated();
		Mouse.setMouseEnabled(true);
		hidden = false;
		this.showAll();
	}
}
