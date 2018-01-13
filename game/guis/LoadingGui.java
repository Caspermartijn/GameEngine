package guis;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import utils.SourceFile;
import utils.maths.Maths;

public class LoadingGui extends GUI {

	public LoadingGui() {
		images();
	}

	private void images() {

		ImageComponent background = new ImageComponent(this, new SourceFile("/res/guis/loading/background.png"));
		Vector2f size = Maths.getFrom720toCurrentDisplaySize(new Vector2f(1280, 720));
		background.setSize(new Vector2f(size.x, size.y));
		background.setPosition(0.5f, 0.5f);
		background.show();

		ImageComponent hourglass = new ImageComponent(this, new SourceFile("/res/guis/loading/hourglass.png"));
		Vector2f size_1 = Maths.getFrom720toCurrentDisplaySize(new Vector2f(75, 75));
		hourglass.setSize(new Vector2f(size_1.x, size_1.y));
		hourglass.setPosition(0.90f, 0.85f);
		hourglass.show();

		ImageComponent circle_1 = new ImageComponent(this, new SourceFile("/res/guis/loading/ring_1.png"));
		Vector2f size_2 = Maths.getFrom720toCurrentDisplaySize(new Vector2f(110, 120));
		circle_1.setSize(new Vector2f(size_2.x, size_2.y));
		circle_1.setPosition(0.90f, 0.85f);
		circle_1.show();

	}

	public void renderComps() {
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		super.renderComponents();
	}

}
