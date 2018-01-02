package launcher;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;
import javax.swing.JButton;
import javax.swing.JFrame;

public abstract class Launcher extends JFrame implements ILauncher
{

	private static final long serialVersionUID = 1593584962448100240L;

	Configuration config = new Configuration() {
		@Override
		public AppConfigurationEntry[]getAppConfigurationEntry(String name){return null;}};
	public int width = 805;
	public int height = 395;
	public String title;
	public int button_width = 200;
	public int button_height = 60;

	private JButton play, options, quit, credits;

	protected ImagePanel window = new ImagePanel("/launcher/res/spaceBox.png", width, height);

	public Launcher(int widht, int height, String title, int button_width, int button_height) throws HeadlessException {
		this.width = widht;
		this.height = height;
		this.title = title;
		this.button_width = button_width;
		this.button_height = button_height;
	}

	public Launcher(String title, int button_width, int button_height) throws HeadlessException {
		this.title = title;
		this.button_width = button_width;
		this.button_height = button_height;
	}

	public Launcher(String title) throws HeadlessException {
		this.title = title;
	}

	private List<JButton> buttons = new ArrayList<JButton>();

	private void initButtons() {
		play = new JButton("Play");
		options = new JButton("Options");
		quit = new JButton("Quit");
		credits = new JButton("Credits");

		play.setBounds(width - button_width - 5, 50, button_width, button_height);
		options.setBounds(width - button_width - 5, play.getY() + button_height + 5, button_width, button_height);
		credits.setBounds(width - button_width - 5, options.getY() + button_height + 5, button_width, button_height);
		quit.setBounds(width - button_width - 5, credits.getY() + button_height + 5, button_width, button_height);

		buttons.add(play);
		buttons.add(options);
		buttons.add(quit);
		buttons.add(credits);

		if (true) {
			for (JButton button : buttons) {
				button.setFont(new Font("Arial", Font.PLAIN, 40));
				button.setForeground(Color.WHITE);
				button.setOpaque(false);
				button.setContentAreaFilled(false);
				button.setBorderPainted(false);
				window.add(button);
			}
		}

	}

	public void create() {
		setUndecorated(true);
		setTitle(title);
		setSize(width, height);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().add(window);
		setLocationRelativeTo(null);
		setResizable(false);
		window.setLayout(null);
		window.setSize(width, height);
		initButtons();
		setVisible(true);
	}

}