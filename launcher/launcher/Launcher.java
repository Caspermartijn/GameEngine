package launcher;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public abstract class Launcher extends JFrame implements ILauncher {

	private static final long serialVersionUID = 1593584962448100240L;

	Configuration config = new Configuration() {
		@Override
		public AppConfigurationEntry[] getAppConfigurationEntry(String name) {
			return null;
		}
	};
	public int width = 805;
	public int height = 395;
	public String title;
	public int button_width = 200;
	public int button_height = 60;

	private JButton play, options, quit, credits;

	protected ImagePanel window = new ImagePanel("/launcher/res/banner.png", width, height);
	protected JPanel gamePanel = new JPanel();

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

		play.setBounds(width - button_width - 10, 100, button_width, button_height);
		options.setBounds(width - button_width - 10, play.getY() + button_height + 5, button_width, button_height);
		credits.setBounds(width - button_width - 10, options.getY() + button_height + 5, button_width, button_height);
		quit.setBounds(width - button_width - 10, credits.getY() + button_height + 5, button_width, button_height);

		buttons.add(play);
		buttons.add(options);
		buttons.add(quit);
		buttons.add(credits);

		play.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Runnable r = new Runnable() {

					@Override
					public void run() {
						play();
					}
				};
				Thread t = new Thread(r);
				t.start();
				hideApp();
			}
		});

		options.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				options();
			}
		});

		quit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				quit();
			}
		});

		credits.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				credits();
			}
		});

		if (true) {
			for (JButton button : buttons) {
				int plain = Font.PLAIN;
				String fonttype = "Akashi";
				int fontSize = 35;

				Font font = new Font(fonttype, plain, fontSize);
				button.setFont(font);
				button.setForeground(Color.WHITE);
				button.setOpaque(false);
				button.setContentAreaFilled(false);
				button.setBorderPainted(false);
				button.setFocusPainted(false);
				button.setHorizontalAlignment(SwingConstants.RIGHT);

				button.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseEntered(java.awt.event.MouseEvent evt) {
						button.setFont(new Font(fonttype, Font.PLAIN, 37));
					}

					public void mouseExited(java.awt.event.MouseEvent evt) {
						button.setFont(font);
					}
				});

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

	public void hideApp() {
		try {
			this.setVisible(false);
		} catch (Exception e) {
			System.out.println("Error while hiding launcher.");
		}
	}

	public void closeApp() {
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
}
