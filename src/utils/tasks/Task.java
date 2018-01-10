package utils.tasks;

public abstract class Task implements ITask {

	private int i = 0;

	public Task(float delay_in_milisecconds) {
		Runnable r = new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep((long) delay_in_milisecconds);
					dotask();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		};
		Thread t = new Thread(r);
		t.start();
	}

	public Task() {
		Runnable r = new Runnable() {

			@Override
			public void run() {
				dotask();
			}

		};
		Thread t = new Thread(r);
		t.start();
	}

	public Task(int repeat_amount) {
		Runnable r = new Runnable() {

			@Override
			public void run() {
				while (i < repeat_amount) {
					i++;
					dotask();
				}
			}

		};
		Thread t = new Thread(r);
		t.start();
	}

	public Task(float delay_amount, int repeat_amount) {
		Runnable r = new Runnable() {

			@Override
			public void run() {
				while (i < repeat_amount) {
					i++;
					dotask();
					try {
						Thread.sleep((long) delay_amount);
					} catch (InterruptedException e) {
					}
				}
			}

		};
		Thread t = new Thread(r);
		t.start();
	}

	private void dotask() {
		run();
	}

}
