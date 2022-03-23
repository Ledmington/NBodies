package nbodies.sim;

import nbodies.Body;
import nbodies.V2d;
import nbodies.sim.data.SimulationData;
import nbodies.utils.barrier.Barrier;
import nbodies.utils.barrier.ReusableBarrier;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MultiThreadSimulator extends AbstractSimulator {
	
	private final int nth;
	private List<Worker> workers;
	private final Barrier endCompute;
	private final Barrier endIteration;

	public MultiThreadSimulator(final SimulationData data, final int nThreads) {
		super(data);
		nth = nThreads;
		endCompute = new ReusableBarrier(nth);
		endIteration = new ReusableBarrier(nth);
	}

	public MultiThreadSimulator(final SimulationData data) {
		this(data, Runtime.getRuntime().availableProcessors());
	}

	public void execute(long nSteps) {
		workers = new ArrayList<>();
		for (int i=0; i<nth; i++) {
			final int id = i;
			Worker w = new Worker(i, () -> {
				// partition indices
				final int start = getBodies().size() * id / nth;
				final int end = getBodies().size() * (id+1) / nth;

				final List<Body> myBodies = getBodies().subList(start, end);
				final List<V2d> tmpForces = Stream.generate(() -> new V2d(0,0))
						.limit(myBodies.size())
						.collect(Collectors.toList());

				while (data.getIteration() < nSteps) {
					//System.out.println(iter + " out of " + nSteps); // TODO remove if not needed

					for (Body b : myBodies) {
						tmpForces.set(myBodies.indexOf(b), computeTotalForceOnBody(b));
					}
					endCompute.hitAndWaitAll();

					for (Body b : myBodies) {
						V2d tmpForceOnBody = tmpForces.get(myBodies.indexOf(b));
						V2d acc = new V2d(tmpForceOnBody).scalarMul(1.0 / b.getMass());

						b.updateVelocity(acc, data.getDelta());

						b.updatePos(data.getDelta());

						b.checkAndSolveBoundaryCollision(getBounds());
					}

					if(id == 0) {
						data.nextIteration();
					}
					endIteration.hitAndWaitAll();
				}
			});
			workers.add(w);
			w.start();
		}
	}

	public void stop() {
		workers.forEach(t -> {
			try {
				t.join();
			} catch (InterruptedException ignored) {}
		});
		workers.clear();
	}
}
