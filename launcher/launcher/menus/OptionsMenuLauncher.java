package launcher.menus;

import javax.swing.JFrame;

public class OptionsMenuLauncher extends JFrame{

	/**
	 * 
	 * By Casper
	 * This class is for the options menu within the launcher
	 * I havent desided if it wil be an intergrated or an external menu 
	 * Probebly will go with an external menu cuz thats way easyer
	 * File created at 5-9-2018
	 * 
	 */
	private static final long serialVersionUID = -2322767087690172375L;
	private JFrame mainFrame;
	
	@SuppressWarnings("deprecation")
	public void init(JFrame parant_Frame) {
		this.mainFrame = parant_Frame;
		super.hide();
		super.setTitle("Options");
	}
	
	@SuppressWarnings("deprecation")
	public void open() {
		mainFrame.hide();	
		super.show();
	}
	
	public void close() {
		
	}
	
	public void loadSettings() {
		
	}
	
	public void closeSettings() {
		
	}
	
	
	
}
