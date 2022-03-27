package nbodies.sim;

import nbodies.Body;
import nbodies.V2d;
import nbodies.sim.data.SimulationData;

public class SequentialSimulator extends AbstractSimulator {

	public SequentialSimulator(final SimulationData data) {
		super(data);
	}
	
	public void execute() {
		while (!data.isFinished()) {
			//System.out.println(iter + " out of " + nSteps); // TODO remove if not needed

			/* update bodies velocity */
			for (Body b : getBodies()) {
				/* compute total force on bodies */
				V2d totalForce = computeTotalForceOnBody(b);

				/* compute instant acceleration */
				V2d acc = new V2d(totalForce).scalarMul(1.0 / b.getMass());

				/* update velocity */
				b.updateVelocity(acc, data.getDelta());
			}

			/* compute bodies new pos */
			for (Body b : getBodies()) {
				b.updatePos(data.getDelta());
			}

			/* check collisions with boundaries */
			for (Body b : getBodies()) {
				b.checkAndSolveBoundaryCollision(getBounds());
			}

			data.nextIteration();
		}
	}

	public void start() {
		// TODO
	}

	public void stop() {
		// TODO
	}

	public boolean isRunning() {
		return false; // TODO
	}
}