package nbodies;

import nbodies.view.SimulationView;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorSimulator extends AbstractSimulator {

	private final ExecutorService executor;

	public ExecutorSimulator(SimulationView viewer) {
		super(viewer);
		executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	}

	public void execute(long nSteps) {
		/* init virtual time */
		double vt = 0;
		double dt = 0.001;

		long iter = 0;

		/* simulation loop */
		while (iter < nSteps) {

			Map<Body, Future<V2d>> totalForces = new HashMap<>();
			for (Body b : bodies) {
				Future<V2d> tempResult = executor.submit(() -> computeTotalForceOnBody(b));
				totalForces.put(b, tempResult);
			}

			for (Body b : totalForces.keySet()) {
				try {
					V2d totalForce = totalForces.get(b).get();
					V2d acc = new V2d(totalForce).scalarMul(1.0 / b.getMass());
					b.updateVelocity(acc, dt);
				} catch (InterruptedException | ExecutionException ignored) {}
			}

			/* compute bodies new pos */
			bodies.stream()
					.map(b -> executor.submit(() -> b.updatePos(dt)))
					.forEach(f -> {
						try {
							f.get();
						} catch (InterruptedException | ExecutionException ignored) {}
					});

			/* check collisions with boundaries */
			bodies.stream()
					.map(b -> executor.submit(() -> b.checkAndSolveBoundaryCollision(bounds)))
					.forEach(f -> {
						try {
							f.get();
						} catch (InterruptedException | ExecutionException ignored) {}
					});

			/* update virtual time */
			vt = vt + dt;
			iter++;

			/* display current stage */
			viewer.display(bodies, vt, iter, bounds);
		}
	}

	public void stop() {}
}
