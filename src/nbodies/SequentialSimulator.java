package nbodies;

import nbodies.view.SimulationView;

public class SequentialSimulator extends AbstractSimulator {

	/* virtual time */
	private double vt;

	/* virtual time step */
	double dt;

	public SequentialSimulator(SimulationView viewer) {
		super(viewer);

		/* initializing boundary and bodies */

		//testBodySet1_two_bodies();
		//testBodySet2_three_bodies();
		//testBodySet3_some_bodies();
		testBodySet4_many_bodies();
	}
	
	public void execute(long nSteps) {
		/* init virtual time */
		vt = 0;
		dt = 0.001;

		long iter = 0;

		/* simulation loop */
		while (iter < nSteps) {
			System.out.println(iter + " out of " + nSteps);

			/* update bodies velocity */
			for (Body b : bodies) {
				/* compute total force on bodies */
				V2d totalForce = computeTotalForceOnBody(b);

				/* compute instant acceleration */
				V2d acc = new V2d(totalForce).scalarMul(1.0 / b.getMass());

				/* update velocity */
				b.updateVelocity(acc, dt);
			}

			/* compute bodies new pos */
			for (Body b : bodies) {
				b.updatePos(dt);
			}

			/* check collisions with boundaries */
			for (Body b : bodies) {
				b.checkAndSolveBoundaryCollision(bounds);
			}

			/* update virtual time */
			vt = vt + dt;
			iter++;

			/* display current stage */
			//viewer.display(bodies, vt, iter, bounds);
		}
	}

	public void stop() {
		bodies.clear();
	}
}
