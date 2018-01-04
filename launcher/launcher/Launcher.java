package launcher;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import files.EngineFileConfig;

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

	private JLabel versionLabel;

	protected ImagePanel window = new ImagePanel("/launcher/res/banner.png", width, height);

	protected LoadingScreenPanel loadingPanel = new LoadingScreenPanel("/launcher/res/bannerLoadingScreen.png", width,
			height);
	private JProgressBar loadingPanelProgressBar;

	protected JPanel gamePanel = new JPanel();

	private EngineFileConfig launcherData = new EngineFileConfig("", "launcherData.cnfg");

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

		if (launcherData.getString("version") == "") {
			launcherData.set("version", "0.0.1");
		}
		try {
			launcherData.saveConfig();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private List<JButton> buttons = new ArrayList<JButton>();

	private void initLoadingPanel() {
		loadingPanelProgressBar = new JProgressBar(0, 100);
		int middle = height / 2;
		loadingPanelProgressBar.setBounds(50, middle + height / 4, width - 100, height / 6);
		loadingPanelProgressBar.setValue(20);
		loadingPanelProgressBar.setVisible(true);
		loadingPanelProgressBar.setStringPainted(false);
		loadingPanelProgressBar.setForeground(Color.gray);
		loadingPanelProgressBar.setOpaque(false);
		loadingPanelProgressBar.setBorderPainted(false);
		loadingPanel.add(loadingPanelProgressBar);
	}

	private void initMain() {
		play = new JButton("Play");
		options = new JButton("Options");
		quit = new JButton("Quit");
		credits = new JButton("Credits");
		versionLabel = new JLabel("Version: " + launcherData.getString("version"));

		play.setBounds(width - button_width + 5, 100, button_width, button_height);
		options.setBounds(width - button_width + 5, play.getY() + button_height + 5, button_width, button_height);
		credits.setBounds(width - button_width + 5, options.getY() + button_height + 5, button_width, button_height);
		quit.setBounds(width - button_width + 5, credits.getY() + button_height + 5, button_width, button_height);

		versionLabel.setBounds(width - button_width + 120, height - button_height + 20, button_width, button_height);

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

		int plain = Font.PLAIN;
		String fonttype = "Akashi";
		int fontSize = 35;

		window.add(versionLabel);

		if (true) {
			for (JButton button : buttons) {

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
		getContentPane().add(loadingPanel);
		setLocationRelativeTo(null);
		setResizable(false);
		window.setLayout(null);
		window.setSize(width, height);
		window.setVisible(false);
		loadingPanel.setLayout(null);
		loadingPanel.setSize(width, height);
		initLoadingPanel();
		initMain();
		setVisible(true);
		startLoading();
	}

	private int progressBarAmount = 0;
	private Thread thread;
	private boolean b = true;

	private void startLoading() {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				while (b) {
					try {
						progressBarAmount++;
						loadingPanelProgressBar.setValue(progressBarAmount);
						Thread.sleep(50);
						if (progressBarAmount >= 100) {
							openMain();
							try {
								thread.join();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		thread = new Thread(runnable);
		thread.start();
		loadDatas();
	}

	private String newVersion = "";

	private void loadDatas() {
		Thread thread = null;
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				try {
					newVersion = Updater.getVersion();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println();
				System.out.println("=====================================");
				System.out.println("Version: " + launcherData.getString("version") + " -> " + newVersion);
				System.out.println("=====================================");
				System.out.println();
			}
		};
		thread = new Thread(runnable);
		thread.start();

		Thread thread2 = null;
		Runnable runnable2 = new Runnable() {
			@Override
			public void run() {
				loadData();
			}
		};
		thread2 = new Thread(runnable2);
		thread2.start();
	}

	private void openMain() {
		b = false;
		loadingPanel.setVisible(false);
		window.setVisible(true);
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
