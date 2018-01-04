package panels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

	private static final long serialVersionUID = 567995795606588137L;

	private BufferedImage image;

	private int width;
	private int height;

	public ImagePanel(String path, int widht, int height) {
		width = widht;
		this.height = height;
		try {
			image = ImageIO.read(Class.class.getResourceAsStream(path));
		} catch (IOException ex) {
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, width, height, this);
	}

}