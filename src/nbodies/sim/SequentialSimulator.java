package nbodies.sim;

import nbodies.Body;
import nbodies.V2d;
import nbodies.view.SimulationView;

public class SequentialSimulator extends AbstractSimulator {

	public SequentialSimulator(final SimulationView viewer, final SimulationData data) {
		super(viewer, data);
	}
	
	public void execute(long nSteps) {
		/* init virtual time */
		/* virtual time */
		double vt = 0;
		double dt = 0.001;

		long iter = 0;

		/* simulation loop */
		while (iter < nSteps) {
			//System.out.println(iter + " out of " + nSteps); // TODO remove if not needed

			/* update bodies velocity */
			for (Body b : getBodies()) {
				/* compute total force on bodies */
				V2d totalForce = computeTotalForceOnBody(b);

				/* compute instant acceleration */
				V2d acc = new V2d(totalForce).scalarMul(1.0 / b.getMass());

				/* update velocity */
				b.updateVelocity(acc, dt);
			}

			/* compute bodies new pos */
			for (Body b : getBodies()) {
				b.updatePos(dt);
			}

			/* check collisions with boundaries */
			for (Body b : getBodies()) {
				b.checkAndSolveBoundaryCollision(getBounds());
			}

			/* update virtual time */
			vt = vt + dt;
			iter++;

			/* display current stage */
			viewer.display(getBodies(), vt, iter, getBounds());
		}
	}
}
