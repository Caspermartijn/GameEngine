package guis;

import org.lwjgl.util.vector.Vector4f;

import utils.maths.Maths;

public class ProgressBarComponent extends QuadComponent {

	private QuadComponent progressBar;
	private float progress;
	private Direction direction = Direction.RIGHT;

	public enum Direction {
		UP, DOWN, LEFT, RIGHT;
	}

	public ProgressBarComponent(GUI container, float x, float y, float width, float height) {
		super(container, x, y, width, height);
		progressBar = new QuadComponent(container, 0, 0, 0, 0);
		progressBar.hide();
	}

	@Override
	public void render() {
		super.render();

		if (progress != 0) {
			if (direction == Direction.RIGHT || direction == Direction.DOWN) {
				progressBar.setX(super.getX());
				progressBar.setY(super.getY());
				if (direction == Direction.RIGHT) {
					progressBar.setHeight(super.getHeight());
					progressBar.setWidth(super.getWidth() * progress);
				} else if (direction == Direction.DOWN) {
					progressBar.setWidth(super.getWidth());
					progressBar.setHeight(super.getHeight() * progress);
				}
			} else if (direction == Direction.UP) {
				progressBar.setX(super.getX());
				progressBar.setWidth(super.getWidth());
				progressBar.setY(super.getY() + super.getHeight() - (1 - progress) * super.getHeight());
				progressBar.setHeight(super.getHeight() * progress);
			} else if (direction == Direction.LEFT) {
				progressBar.setY(super.getY());
				progressBar.setHeight(super.getHeight());
				progressBar.setX(super.getX() + super.getWidth() - (1 - progress) * super.getWidth());
				progressBar.setWidth(super.getWidth() * progress);
			}
			progressBar.show();
		} else {
			progressBar.hide();
		}

	}

	@Override
	public void delete() {
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public float getProgress() {
		return progress;
	}

	public void setProgress(float progress) {
		this.progress = Maths.clamp(0, 1, progress);
	}

	@Override
	public void setOutlineWidth(float width) {
		super.setOutlineWidth(width);
		progressBar.setOutlineWidth(width);
	}

	@Override
	public void setOutlineColor(Vector4f color) {
		super.setOutlineColor(color);
		progressBar.setBackgroundColor(color);
	}

	public void setProgressBarColor(Vector4f color) {
		progressBar.setBackgroundColor(color);
	}
}
