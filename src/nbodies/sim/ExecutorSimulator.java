package nbodies.sim;

import nbodies.Body;
import nbodies.V2d;

import java.util.*;
import java.util.concurrent.*;

public class ExecutorSimulator extends AbstractSimulator {

	private final ExecutorService executor;

	public ExecutorSimulator(final SimulationData data) {
		super(data);
		executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	}

	public void execute(long nSteps) {
		while (data.getIteration() < nSteps) {
			//System.out.println(iter + " out of " + nSteps); // TODO remove if not needed

			Map<Body, Future<V2d>> totalForces = new HashMap<>();
			for (Body b : getBodies()) {
				Future<V2d> tempResult = executor.submit(() -> computeTotalForceOnBody(b));
				totalForces.put(b, tempResult);
			}

			for (Body b : totalForces.keySet()) {
				try {
					V2d totalForce = totalForces.get(b).get();
					V2d acc = new V2d(totalForce).scalarMul(1.0 / b.getMass());
					b.updateVelocity(acc, data.getDelta());
				} catch (InterruptedException | ExecutionException ignored) {}
			}

			List<Future<Void>> tempResult = new LinkedList<>();
			for (Body b : getBodies()) {
				tempResult.add(executor.submit(() -> {
					b.updatePos(data.getDelta());
					b.checkAndSolveBoundaryCollision(getBounds());
					return null;
				}));
			}

			for (Future<Void> f : tempResult) {
				try {
					f.get();
				} catch (InterruptedException | ExecutionException ignored) {}
			}

			data.nextIteration();
		}
	}

	public void stop() {
		executor.shutdown();
		try {
			boolean b = executor.awaitTermination(1000_000_000, TimeUnit.SECONDS);
		} catch (InterruptedException ignored) {}
	}
}
