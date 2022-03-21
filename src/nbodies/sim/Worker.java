package nbodies.sim;

import java.util.function.Supplier;

public class Worker extends Thread {

	private boolean stop = false;
	private final Supplier<Runnable> taskSupplier;

	public Worker(Supplier<Runnable> taskSupplier) {
		this.taskSupplier = taskSupplier;
	}

	public void run() {
		while(!stop) {
			taskSupplier.get().run();
		}
	}

	public void die() {
		stop = true;
	}
}
