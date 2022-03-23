package nbodies.sim;

import nbodies.sim.data.SimulationData;
import nbodies.utils.barrier.Barrier;
import nbodies.utils.barrier.ReusableBarrier;

import java.util.ArrayList;
import java.util.List;

public class MultiThreadSimulator extends AbstractSimulator {
	
	private List<Worker> workers;
	private final Barrier endCompute;
	private final Barrier endIteration;

	public MultiThreadSimulator(final SimulationData data) {
		super(data);
		endCompute = new ReusableBarrier(data.getNThreads());
		endIteration = new ReusableBarrier(data.getNThreads());
	}

	public void execute(long nSteps) {
		workers = new ArrayList<>();
		for (int i=0; i<data.getNThreads(); i++) {
			Worker w = new Worker(i, data, endCompute, endIteration, this::computeTotalForceOnBody);
			workers.add(w);
			w.start();
		}
		workers.forEach(t -> {
			try {
				t.join();
			} catch (InterruptedException ignored) {}
		});
	}

	public void stop() {
		workers.clear();
	}
}
