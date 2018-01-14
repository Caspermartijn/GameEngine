package menus.settings;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

import guis.ButtonComponent;
import guis.GUI;
import guis.TextComponent;
import main.Settings;
import main.settings.Graphics;

public class GraphicsMenu extends GUI {

	public GraphicsMenu() {
		super.setPosition(0.0f, 0.0f);
		images();
		buttons();
		texts();
	}

	private void images() {

	}

	private float left_1_x = 0.41f + 0.0025f;
	private float left_1_y = 0.2f + 0.15f + 0.005f;
	private float factor = 0.0365f + 0.005f;

	private void texts() {
		TextComponent textures = new TextComponent(this, "Texture quality", "candara", 1f, 200, false);
		textures.setPosition(left_1_x, left_1_y + 0.005f);

		TextComponent terrain = new TextComponent(this, "Terrain quality", "candara", 1f, 200, false);
		terrain.setPosition(left_1_x, left_1_y + factor + 0.005f);

		TextComponent shadows = new TextComponent(this, "Shadow quality", "candara", 1f, 200, false);
		shadows.setPosition(left_1_x, left_1_y + factor * 2 + 0.005f);

		TextComponent postprocesing = new TextComponent(this, "Postprocesing", "candara", 1f, 200, false);
		postprocesing.setPosition(left_1_x, left_1_y + factor * 3);

		TextComponent water = new TextComponent(this, "Water quality", "candara", 1f, 200, false);
		water.setPosition(left_1_x, left_1_y + factor * 4);
	}

	private void buttons() {
		potatoeButtons();
		mediumButtons();
		highButtons();
		ultraButtons();
	}

	private Vector4f defaultBackgroundColor = new Vector4f(0.6f, 0.6f, 0.6f, 1f);
	private Vector4f backgroundSelectedColor = new Vector4f(0.4f, 0.4f, 0.4f, 0.7f);

	public void updateButtons() {
		for (ButtonComponent button : all_Buttons) {
			button.setBackgroundColor(defaultBackgroundColor);
		}
		highlightSelected();
	}

	@Override
	public void renderComponents() {
		updateButtons();
		super.renderComponents();
	}

	private List<ButtonComponent> all_Buttons = new ArrayList<ButtonComponent>();

	private List<ButtonComponent> buttonsPotatoe = new ArrayList<ButtonComponent>();
	private List<ButtonComponent> buttonsMedium = new ArrayList<ButtonComponent>();
	private List<ButtonComponent> buttonsHigh = new ArrayList<ButtonComponent>();
	private List<ButtonComponent> buttonsUltra = new ArrayList<ButtonComponent>();

	private void highlightSelected() {
		switch (Settings.textures) {
		case Graphics_High:
			buttonsHigh.get(0).setBackgroundColor(backgroundSelectedColor);
			break;
		case Graphics_Medium:
			buttonsMedium.get(0).setBackgroundColor(backgroundSelectedColor);
			break;
		case Graphics_Potatoe:
			buttonsPotatoe.get(0).setBackgroundColor(backgroundSelectedColor);
			break;
		case Graphics_Ultra:
			buttonsUltra.get(0).setBackgroundColor(backgroundSelectedColor);
			break;
		}

		switch (Settings.terrain) {
		case Graphics_High:
			buttonsHigh.get(1).setBackgroundColor(backgroundSelectedColor);
			break;
		case Graphics_Medium:
			buttonsMedium.get(1).setBackgroundColor(backgroundSelectedColor);
			break;
		case Graphics_Potatoe:
			buttonsPotatoe.get(1).setBackgroundColor(backgroundSelectedColor);
			break;
		case Graphics_Ultra:
			buttonsUltra.get(1).setBackgroundColor(backgroundSelectedColor);
			break;
		}

		switch (Settings.shadows) {
		case Graphics_High:
			buttonsHigh.get(2).setBackgroundColor(backgroundSelectedColor);
			break;
		case Graphics_Medium:
			buttonsMedium.get(2).setBackgroundColor(backgroundSelectedColor);
			break;
		case Graphics_Potatoe:
			buttonsPotatoe.get(2).setBackgroundColor(backgroundSelectedColor);
			break;
		case Graphics_Ultra:
			buttonsUltra.get(2).setBackgroundColor(backgroundSelectedColor);
			break;
		}

		switch (Settings.postproccesing) {
		case Graphics_High:
			buttonsHigh.get(3).setBackgroundColor(backgroundSelectedColor);
			break;
		case Graphics_Medium:
			buttonsMedium.get(3).setBackgroundColor(backgroundSelectedColor);
			break;
		case Graphics_Potatoe:
			buttonsPotatoe.get(3).setBackgroundColor(backgroundSelectedColor);
			break;
		case Graphics_Ultra:
			buttonsUltra.get(3).setBackgroundColor(backgroundSelectedColor);
			break;
		}

		switch (Settings.water) {
		case Graphics_High:
			buttonsHigh.get(4).setBackgroundColor(backgroundSelectedColor);
			break;
		case Graphics_Medium:
			buttonsMedium.get(4).setBackgroundColor(backgroundSelectedColor);
			break;
		case Graphics_Potatoe:
			buttonsPotatoe.get(4).setBackgroundColor(backgroundSelectedColor);
			break;
		case Graphics_Ultra:
			buttonsUltra.get(4).setBackgroundColor(backgroundSelectedColor);
			break;
		}
	}

	private void potatoeButtons() {
		float x = left_1_x + 0.1f;

		ButtonComponent textures = new ButtonComponent(this, x, left_1_y, 0.08f, 0.035f);
		buttonsPotatoe.add(textures);
		textures.setClickEvent(new Runnable() {

			@Override
			public void run() {
				Settings.textures = Graphics.Graphics_Potatoe;
			}

		});

		ButtonComponent terrain = new ButtonComponent(this, x, left_1_y + factor, 0.08f, 0.035f);
		buttonsPotatoe.add(terrain);
		terrain.setClickEvent(new Runnable() {

			@Override
			public void run() {
				Settings.terrain = Graphics.Graphics_Potatoe;
			}

		});

		ButtonComponent shadows = new ButtonComponent(this, x, left_1_y + factor * 2, 0.08f, 0.035f);
		buttonsPotatoe.add(shadows);
		shadows.setClickEvent(new Runnable() {

			@Override
			public void run() {
				Settings.shadows = Graphics.Graphics_Potatoe;
			}

		});

		ButtonComponent postprocesing = new ButtonComponent(this, x, left_1_y + factor * 3, 0.08f, 0.035f);
		buttonsPotatoe.add(postprocesing);
		postprocesing.setClickEvent(new Runnable() {

			@Override
			public void run() {
				Settings.postproccesing = Graphics.Graphics_Potatoe;
			}

		});

		ButtonComponent water = new ButtonComponent(this, x, left_1_y + factor * 4, 0.08f, 0.035f);
		buttonsPotatoe.add(water);
		water.setClickEvent(new Runnable() {

			@Override
			public void run() {
				Settings.water = Graphics.Graphics_Potatoe;
			}

		});

		for (ButtonComponent button : buttonsPotatoe) {
			button.setOutlineWidth(0.001f);
			button.setOutlineColor(new Vector4f(0.3f, 0.3f, 0.3f, 0.7f));
			button.setBackgroundColor(defaultBackgroundColor);
			button.setText("Potato", "candara", 1f);
			button.setTextPosition(new Vector2f(0, 0.005f));
		}
		all_Buttons.addAll(buttonsPotatoe);
	}

	private void mediumButtons() {
		float x = left_1_x + 0.1f + 0.082f;

		ButtonComponent textures = new ButtonComponent(this, x, left_1_y, 0.08f, 0.035f);
		buttonsMedium.add(textures);
		textures.setClickEvent(new Runnable() {

			@Override
			public void run() {
				Settings.textures = Graphics.Graphics_Medium;
			}

		});

		ButtonComponent terrain = new ButtonComponent(this, x, left_1_y + factor, 0.08f, 0.035f);
		buttonsMedium.add(terrain);
		terrain.setClickEvent(new Runnable() {

			@Override
			public void run() {
				Settings.terrain = Graphics.Graphics_Medium;
			}

		});

		ButtonComponent shadows = new ButtonComponent(this, x, left_1_y + factor * 2, 0.08f, 0.035f);
		buttonsMedium.add(shadows);
		shadows.setClickEvent(new Runnable() {

			@Override
			public void run() {
				Settings.shadows = Graphics.Graphics_Medium;
			}

		});

		ButtonComponent postprocesing = new ButtonComponent(this, x, left_1_y + factor * 3, 0.08f, 0.035f);
		buttonsMedium.add(postprocesing);
		postprocesing.setClickEvent(new Runnable() {

			@Override
			public void run() {
				Settings.postproccesing = Graphics.Graphics_Medium;
			}

		});

		ButtonComponent water = new ButtonComponent(this, x, left_1_y + factor * 4, 0.08f, 0.035f);
		buttonsMedium.add(water);
		water.setClickEvent(new Runnable() {

			@Override
			public void run() {
				Settings.water = Graphics.Graphics_Medium;
			}

		});

		for (ButtonComponent button : buttonsMedium) {
			button.setOutlineWidth(0.001f);
			button.setOutlineColor(new Vector4f(0.3f, 0.3f, 0.3f, 0.7f));
			button.setBackgroundColor(defaultBackgroundColor);
			button.setText("Medium", "candara", 1f);
			button.setTextPosition(new Vector2f(0, 0.005f));
		}
		all_Buttons.addAll(buttonsMedium);
	}

	private void highButtons() {
		float x = left_1_x + 0.1f + 0.082f + 0.082f;

		ButtonComponent textures = new ButtonComponent(this, x, left_1_y, 0.08f, 0.035f);
		buttonsHigh.add(textures);
		textures.setClickEvent(new Runnable() {

			@Override
			public void run() {
				Settings.textures = Graphics.Graphics_High;
			}

		});

		ButtonComponent terrain = new ButtonComponent(this, x, left_1_y + factor, 0.08f, 0.035f);
		buttonsHigh.add(terrain);
		terrain.setClickEvent(new Runnable() {

			@Override
			public void run() {
				Settings.terrain = Graphics.Graphics_High;
			}

		});

		ButtonComponent shadows = new ButtonComponent(this, x, left_1_y + factor * 2, 0.08f, 0.035f);
		buttonsHigh.add(shadows);
		shadows.setClickEvent(new Runnable() {

			@Override
			public void run() {
				Settings.shadows = Graphics.Graphics_High;
			}

		});

		ButtonComponent postprocesing = new ButtonComponent(this, x, left_1_y + factor * 3, 0.08f, 0.035f);
		buttonsHigh.add(postprocesing);
		postprocesing.setClickEvent(new Runnable() {

			@Override
			public void run() {
				Settings.postproccesing = Graphics.Graphics_High;
			}

		});

		ButtonComponent water = new ButtonComponent(this, x, left_1_y + factor * 4, 0.08f, 0.035f);
		buttonsHigh.add(water);
		water.setClickEvent(new Runnable() {

			@Override
			public void run() {
				Settings.water = Graphics.Graphics_High;
			}

		});

		for (ButtonComponent button : buttonsHigh) {
			button.setOutlineWidth(0.001f);
			button.setOutlineColor(new Vector4f(0.3f, 0.3f, 0.3f, 0.7f));
			button.setBackgroundColor(defaultBackgroundColor);
			button.setText("High", "candara", 1f);
			button.setTextPosition(new Vector2f(0, 0.005f));
		}
		all_Buttons.addAll(buttonsHigh);
	}

	private void ultraButtons() {
		float x = left_1_x + 0.1f + 0.082f + 0.082f + 0.082f;

		ButtonComponent textures = new ButtonComponent(this, x, left_1_y, 0.08f, 0.035f);
		buttonsUltra.add(textures);
		textures.setClickEvent(new Runnable() {

			@Override
			public void run() {
				Settings.textures = Graphics.Graphics_Ultra;
			}

		});

		ButtonComponent terrain = new ButtonComponent(this, x, left_1_y + factor, 0.08f, 0.035f);
		buttonsUltra.add(terrain);
		terrain.setClickEvent(new Runnable() {

			@Override
			public void run() {
				Settings.terrain = Graphics.Graphics_Ultra;
			}

		});

		ButtonComponent shadows = new ButtonComponent(this, x, left_1_y + factor * 2, 0.08f, 0.035f);
		buttonsUltra.add(shadows);
		shadows.setClickEvent(new Runnable() {

			@Override
			public void run() {
				Settings.shadows = Graphics.Graphics_Ultra;
			}

		});

		ButtonComponent postprocesing = new ButtonComponent(this, x, left_1_y + factor * 3, 0.08f, 0.035f);
		buttonsUltra.add(postprocesing);
		postprocesing.setClickEvent(new Runnable() {

			@Override
			public void run() {
				Settings.postproccesing = Graphics.Graphics_Ultra;
			}

		});

		ButtonComponent water = new ButtonComponent(this, x, left_1_y + factor * 4, 0.08f, 0.035f);
		buttonsUltra.add(water);
		water.setClickEvent(new Runnable() {

			@Override
			public void run() {
				Settings.water = Graphics.Graphics_Ultra;
			}

		});

		for (ButtonComponent button : buttonsUltra) {
			button.setOutlineWidth(0.001f);
			button.setOutlineColor(new Vector4f(0.3f, 0.3f, 0.3f, 0.7f));
			button.setBackgroundColor(defaultBackgroundColor);
			button.setText("Ultra", "candara", 1f);
			button.setTextPosition(new Vector2f(0, 0.005f));
		}
		all_Buttons.addAll(buttonsUltra);
	}

}
