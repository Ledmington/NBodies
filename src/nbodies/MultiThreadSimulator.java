package nbodies;

import nbodies.view.SimulationView;

import java.util.List;
import java.util.stream.Stream;

public class MultiThreadSimulator extends AbstractSimulator {

	private final List<Worker> workers;

	protected MultiThreadSimulator(SimulationView viewer, int nThreads) {
		super(viewer);
		workers = Stream.generate(() -> 1)
				.limit(nThreads)
				.map(i -> new Worker())
				.toList();
	}

	public void execute(long nSteps) {
		double vt = 0;
		double dt = 0.001;

		long iter = 0;

		while (iter < nSteps) {
			System.out.println(iter + " out of " + nSteps);

			for (Body b : bodies) {
				V2d totalForce = computeTotalForceOnBody(b);

				V2d acc = new V2d(totalForce).scalarMul(1.0 / b.getMass());

				b.updateVelocity(acc, dt);
			}

			for (Body b : bodies) {
				b.updatePos(dt);
			}

			for (Body b : bodies) {
				b.checkAndSolveBoundaryCollision(bounds);
			}

			vt = vt + dt;
			iter++;

			//viewer.display(bodies, vt, iter, bounds);
		}
	}

	public void stop() {}
}
