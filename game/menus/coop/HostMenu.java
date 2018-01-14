package menus.coop;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

import guis.ButtonComponent;
import guis.GUI;
import guis.TextFieldComponent;
import log.Log;

public class HostMenu extends GUI {

	public HostMenu() {
		super.setPosition(0, 0);
		images();
		buttons();
		texts();
	}

	private void images() {

	}

	List<TextFieldComponent> textfields = new ArrayList<TextFieldComponent>();

	private void buttons() {
		float fac = 0.065f;
		float y = 0.4f;
		TextFieldComponent ip4_1 = new TextFieldComponent(this, 0.42f, y + 0.01f, 0.06f, 0.05f, "candara", 1.4f, true);
		TextFieldComponent ip4_2 = new TextFieldComponent(this, 0.42f + fac, y + 0.01f, 0.06f, 0.05f, "candara", 1.4f,
				true);
		TextFieldComponent ip4_3 = new TextFieldComponent(this, 0.42f + fac * 2, y + 0.01f, 0.06f, 0.05f, "candara",
				1.4f, true);
		TextFieldComponent ip4_4 = new TextFieldComponent(this, 0.42f + fac * 3, y + 0.01f, 0.06f, 0.05f, "candara",
				1.4f, true);
		textfields.add(ip4_1);
		textfields.add(ip4_2);
		textfields.add(ip4_3);
		textfields.add(ip4_4);

		TextFieldComponent port = new TextFieldComponent(this, 0.42f, y + 0.01f + 0.05f + 0.03f, 0.08f, 0.05f,
				"candara", 1.4f, false);
		textfields.add(port);
		for (TextFieldComponent comp : textfields) {
			comp.setBackgroundColor(new Vector4f(0.5f, 0.5f, 0.5f, 1));
			comp.setText("ip4");
			comp.setTextPosition(new Vector2f(0f, 0.005f));
		}
		port.setText("port");

		TextFieldComponent server_key_field = new TextFieldComponent(this, 0.42f, y + 0.1f + 0.05f + 0.02f + 0.12f,
				0.4f, 0.05f, "candara", 1.4f, false);
		server_key_field.setBackgroundColor(new Vector4f(0.5f, 0.5f, 0.5f, 1));
		server_key_field.setText("Server key");
		server_key_field.setTextPosition(new Vector2f(0f, 0.005f));

		ButtonComponent createkey_button = new ButtonComponent(this, 0.42f, y + 0.1f + 0.05f + 0.02f, 0.2f, 0.1f);
		createkey_button.setBackgroundColor(new Vector4f(0.3f, 0.3f, 0.3f, 1));
		createkey_button.setText("Create key", "candara", 3f);
		createkey_button.setTextPosition(new Vector2f(0, 0.01f));
		createkey_button.setTextPosition(new Vector2f(0f, 0.005f));
		createkey_button.setHoverEvent(new Runnable() {

			@Override
			public void run() {
				createkey_button.playHover(1.05f);
			}

		});

		createkey_button.setClickEvent(new Runnable() {

			@Override
			public void run() {
				String key = IP_Encryptor.encrypt(
						new String[] { textfields.get(0).getText(), textfields.get(1).getText(),
								textfields.get(2).getText(), textfields.get(3).getText() },
						textfields.get(4).getText());
				Log.append("Server key: " + key, true);
				server_key_field.setText(key);
			}

		});
	}

	private void texts() {

	}
}
