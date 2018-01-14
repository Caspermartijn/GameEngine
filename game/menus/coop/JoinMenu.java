package menus.coop;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

import guis.ButtonComponent;
import guis.GUI;
import guis.TextFieldComponent;

public class JoinMenu extends GUI {

	public JoinMenu() {
		images();
		buttons();
		texts();
	}

	private void images() {

	}

	private void buttons() {
		TextFieldComponent server_key_field = new TextFieldComponent(this, 0.42f, 0.4f, 0.4f, 0.05f, "candara", 1.4f,
				false);
		server_key_field.setBackgroundColor(new Vector4f(0.5f, 0.5f, 0.5f, 1));
		server_key_field.setText("Server key");
		server_key_field.setEditable(false);
		server_key_field.setTextPosition(new Vector2f(0f, 0.005f));
		ButtonComponent join_button = new ButtonComponent(this, 0.42f, 0.4f + 0.05f + 0.02f, 0.2f, 0.1f);
		join_button.setBackgroundColor(new Vector4f(0.3f, 0.3f, 0.3f, 1));
		join_button.setText("Join", "candara", 3f);join_button.setTextPosition(new Vector2f(0, 0.01f));
		join_button.setHoverEvent(new Runnable() {

			@Override
			public void run() {
				join_button.playHover(1.05f);
			}
			
		});
		
	}

	private void texts() {

	}

}
