package nbodies.sim;

public class Worker extends Thread {

	private final int id;
	private final Runnable task;

	public Worker(final int id, final Runnable task) {
		this.id = id;
		this.task = task;
	}

	public void run() {
		task.run();
	}
}
