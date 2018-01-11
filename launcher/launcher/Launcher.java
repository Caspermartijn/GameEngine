package launcher;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import org.lwjgl.util.vector.Vector3f;

import audio.Sound2DMaster;
import files.EngineFileConfig;
import log.Log;
import panels.ImagePanel;
import panels.LoadingScreenPanel;
import utils.tasks.Task;

public abstract class Launcher extends JFrame implements ILauncher {

	private static final long serialVersionUID = 1593584962448100240L;

	private List<JButton> buttons = new ArrayList<JButton>();

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

	// ================MAIN================
	protected ImagePanel window = new ImagePanel("/launcher/res/banner.png", width, height);
	private JButton play, options, quit, credits;
	private JLabel versionLabel;
	// ================MAIN================

	// ================Loading================
	protected LoadingScreenPanel loadingPanel = new LoadingScreenPanel("/launcher/res/bannerLoadingScreen.png", width,
			height);
	private JProgressBar loadingPanelProgressBar;
	// ================Loading================

	// ================Error/Warning================
	protected ImagePanel errorPanel = new ImagePanel("/launcher/res/bannerLoadingScreen.png", width, height);
	private JLabel titleError = new JLabel("ERROR/WARNING");
	private JTextArea errorText = new JTextArea();
	// ================Error/Warning================

	// ================LauncherData================
	private EngineFileConfig launcherData = new EngineFileConfig("", "launcherData.cnfg");
	// ================LauncherData================

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

	private void initErrorLabel() {
		int plain = Font.PLAIN;
		String fonttype = "Akashi";
		int fontSize = 35;
		Font font = new Font(fonttype, plain, fontSize);

		titleError.setBounds(0, 20, width, 30);
		titleError.setHorizontalAlignment(SwingConstants.CENTER);
		titleError.setForeground(Color.red);
		titleError.setFont(font);

		int errorX = (int) (width / 1.3);
		int errorY = (int) (height / 1.3);

		errorText.setSize(errorX, errorY);
		errorText.setLocation((int) ((width - (width / 1.3)) / 2), (int) (20 + (height - (height / 1.3)) / 2));
		errorText.setEditable(false);

		errorPanel.add(titleError);
		errorPanel.add(errorText);
	}

	private void initLoadingPanel() {
		loadingPanelProgressBar = new JProgressBar(0, 100);
		int middle = height / 2;
		loadingPanelProgressBar.setBounds(50, middle + height / 4, width - 100, height / 6);
		loadingPanelProgressBar.setValue(20);
		loadingPanelProgressBar.setVisible(true);
		loadingPanelProgressBar.setStringPainted(false);
		loadingPanelProgressBar.setForeground(new Color(40, 40, 40, 64));
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

		play.setBounds(width - button_width + 5, 120, button_width, button_height);
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
				new Task() {

					@Override
					public void run() {
						new Task(100, (int) Sound2DMaster.getSong("track2").getVolume()) {

							@Override
							public void run() {
								Sound2DMaster.getSong("track2")
										.setVolume(Sound2DMaster.getSong("track2").getVolume() - 1);
							}

						};

						new Task(200) {

							@Override
							public void run() {
								Sound2DMaster.playSound("track1");
								Sound2DMaster.setVolume("track1", 5);
							}

						};

						new Task(1000) {

							@Override
							public void run() {
								play();
							}

						};
						Sound2DMaster.playSound("play_click");
					}
				};
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
		Font font = new Font(fonttype, plain, fontSize);

		window.add(versionLabel);

		if (true) {
			for (JButton button : buttons) {

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
						Sound2DMaster.playSound("hoverpop");
					}

					public void mouseExited(java.awt.event.MouseEvent evt) {
						button.setFont(font);
						Sound2DMaster.getSong("hoverpop").stop();
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
		getContentPane().add(errorPanel);
		setLocationRelativeTo(null);
		setResizable(false);
		window.setLayout(null);
		window.setSize(width, height);
		window.setVisible(false);
		loadingPanel.setLayout(null);
		loadingPanel.setSize(width, height);
		loadingPanel.setVisible(true);
		errorPanel.setLayout(null);
		errorPanel.setSize(width, height);
		errorPanel.setVisible(false);
		initLoadingPanel();
		initMain();
		initErrorLabel();
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
						if (progressBarAmount == 20) {
						}
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

	private void writerError(String error) {
		errorText.setText(error);
		errorText.updateUI();
	}

	private void loadDatas() {
		Thread thread = null;
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				launcherLoad();
				// TODO add oldversion check add updater panel
				/*
				 * try { Updater.downloadUpdate(); } catch (IOException e1) {
				 * writerError(e1.getMessage()); }
				 */
				try {
					newVersion = Updater.getVersion();
				} catch (IOException e) {
					writerError(e.getMessage());
				}
				Vector3f green = new Vector3f(0.1f, 0.9f, 0.1f);
				Log.append();
				Log.append("=====================================", green);
				Log.append("Version: " + launcherData.getString("version") + " -> " + newVersion, green);
				Log.append("=====================================", green);
				Log.append();
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
