package nbodies.sim;

import nbodies.sim.data.SimulationData;
import nbodies.utils.barrier.Barrier;
import nbodies.utils.barrier.ReusableBarrier;

import java.util.ArrayList;
import java.util.List;

public class MultiThreadSimulator extends AbstractSimulator {

	private final Barrier endCompute;
	private final Barrier endIteration;
	private List<Worker> workers;

	public MultiThreadSimulator(final SimulationData data) {
		super(data);
		endCompute = new ReusableBarrier(data.getNThreads());
		endIteration = new ReusableBarrier(data.getNThreads());
	}

	public void execute() {
		running = true;
		workers = new ArrayList<>();
		for (int i = 0; i < data.getNThreads(); i++) {
			Worker w = new Worker(i, this.mutex, data, endCompute, endIteration, this::computeTotalForceOnBody);
			workers.add(w);
			w.start();
		}

		Thread waitingThread = new Thread(() -> {
			for (Worker w : workers) {
				try {
					w.join();
				} catch (InterruptedException ignored) {
				}
			}
			running = false;
		});
		waitingThread.start();
	}

	public void start() {
		if (running) return;
		workers.forEach(Worker::wakeUp);
		running = true;
		data.getPause().hitAndWaitAll();
	}

	public void stop() {
		if (!running) return;
		workers.forEach(Worker::pause);
		running = false;
	}

	public boolean isRunning() {
		return running;
	}
}
