package nbodies.sim;

import nbodies.Body;
import nbodies.V2d;
import nbodies.view.SimulationView;

import java.util.List;
import java.util.stream.Stream;

public class MultiThreadSimulator extends AbstractSimulator {

	private final List<Worker> workers;

	protected MultiThreadSimulator(final SimulationView viewer, final SimulationData data, final int nThreads) {
		super(viewer, data);
		workers = Stream.generate(() -> 1)
				.limit(nThreads)
				.map(i -> new Worker(() -> null)) // TODO fix
				.toList();
	}

	public void execute(long nSteps) {
		while (data.getIteration() < nSteps) {
			//System.out.println(iter + " out of " + nSteps); // TODO remove if not needed

			for (Body b : getBodies()) {
				V2d totalForce = computeTotalForceOnBody(b);

				V2d acc = new V2d(totalForce).scalarMul(1.0 / b.getMass());

				b.updateVelocity(acc, data.getDelta());
			}

			for (Body b : getBodies()) {
				b.updatePos(data.getDelta());
			}

			for (Body b : getBodies()) {
				b.checkAndSolveBoundaryCollision(getBounds());
			}

			data.nextIteration();
		}
	}

	public void stop() {}
}
