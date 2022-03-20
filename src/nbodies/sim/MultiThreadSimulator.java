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
		double vt = 0;
		double dt = 0.001;

		long iter = 0;

		while (iter < nSteps) {
			//System.out.println(iter + " out of " + nSteps); // TODO remove if not needed

			for (Body b : getBodies()) {
				V2d totalForce = computeTotalForceOnBody(b);

				V2d acc = new V2d(totalForce).scalarMul(1.0 / b.getMass());

				b.updateVelocity(acc, dt);
			}

			for (Body b : getBodies()) {
				b.updatePos(dt);
			}

			for (Body b : getBodies()) {
				b.checkAndSolveBoundaryCollision(getBounds());
			}

			vt = vt + dt;
			iter++;

			//viewer.display(bodies, vt, iter, bounds);
		}
	}

	public void stop() {}
}
