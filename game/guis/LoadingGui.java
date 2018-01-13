package guis;

import static engine.Mouse.setMouseEnabled;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import engine.Display;
import gamestates.GamePerspective;
import utils.SourceFile;
import utils.maths.Maths;
import utils.tasks.Task;

public abstract class LoadingGui extends GUI {

	public LoadingGui(float timeInMilisecconds) {
		images();
		GamePerspective loading = new GamePerspective("loading") {

			@Override
			public void render() {
				renderComps();
			}

			@Override
			public void start() {
				setMouseEnabled(true);
			}

			@Override
			public void stop() {

			}
		};
		new Task(timeInMilisecconds/2) {

			@Override
			public void run() {
				midLoad();
				new Task(timeInMilisecconds/2) {

					@Override
					public void run() {
						afterLoad();
					}
					
				};
			}
			
		};
		loading.switchState();
	}

	private ImageComponent hourglass;

	private void images() {
		ImageComponent background = new ImageComponent(this, new SourceFile("/res/guis/loading/background_2.png"));
		Vector2f size = Maths.getFrom720toCurrentDisplaySize(new Vector2f(1280, 720));
		background.setSize(new Vector2f(size.x, size.y));
		background.setPosition(0.5f, 0.5f);

		hourglass = new ImageComponent(this, new SourceFile("/res/guis/loading/hourglass.png"));
		Vector2f size_1 = Maths.getFrom720toCurrentDisplaySize(new Vector2f(37.5f, 37.5f));
		hourglass.setSize(new Vector2f(size_1.x, size_1.y));
		hourglass.setPosition(0.90f, 0.85f);

		ImageComponent circle = new ImageComponent(this, new SourceFile("/res/guis/loading/ring_1.png"));
		Vector2f size_3 = Maths.getFrom720toCurrentDisplaySize(new Vector2f(95, 95));
		circle.setSize(new Vector2f(size_3.x, size_3.y));
		circle.setPosition(0.90f, 0.85f);
	}

	public void renderComps() {
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		hourglass.setRotation((float) (hourglass.getRotation() + 180 * Display.getFrameTime()));
		super.renderComponents();
	}

	
	@Override
	public void showAll() {
		super.showAll();
	}

	public abstract void afterLoad();
	public abstract void midLoad();
}
