package nbodies.sim;

import nbodies.Body;
import nbodies.Boundary;
import nbodies.P2d;
import nbodies.V2d;
import nbodies.view.SimulationView;

import java.util.ArrayList;
import java.util.Random;

public abstract class AbstractSimulator implements Simulator {

	protected final SimulationView viewer;

	/* bodies in the field */
	protected ArrayList<Body> bodies;

	/* boundary of the field */
	protected Boundary bounds;

	protected AbstractSimulator(SimulationView viewer) {
		this.viewer = viewer;
	}

	protected V2d computeTotalForceOnBody(Body b) {

		V2d totalForce = new V2d(0, 0);

		/* compute total repulsive force */

		for (Body otherBody : bodies) {
			if (!b.equals(otherBody)) {
				try {
					V2d forceByOtherBody = b.computeRepulsiveForceBy(otherBody);
					totalForce.sum(forceByOtherBody);
				} catch (Exception ignored) {
				}
			}
		}

		/* add friction force */
		totalForce.sum(b.getCurrentFrictionForce());

		return totalForce;
	}

	protected void testBodySet1_two_bodies() {
		bounds = new Boundary(-4.0, -4.0, 4.0, 4.0);
		bodies = new ArrayList<>();
		bodies.add(new Body(0, new P2d(-0.1, 0), new V2d(0,0), 1));
		bodies.add(new Body(1, new P2d(0.1, 0), new V2d(0,0), 2));
	}

	protected void testBodySet2_three_bodies() {
		bounds = new Boundary(-1.0, -1.0, 1.0, 1.0);
		bodies = new ArrayList<>();
		bodies.add(new Body(0, new P2d(0, 0), new V2d(0,0), 10));
		bodies.add(new Body(1, new P2d(0.2, 0), new V2d(0,0), 1));
		bodies.add(new Body(2, new P2d(-0.2, 0), new V2d(0,0), 1));
	}

	protected void testBodySet3_some_bodies() {
		bounds = new Boundary(-4.0, -4.0, 4.0, 4.0);
		int nBodies = 100;
		Random rand = new Random(System.currentTimeMillis());
		bodies = new ArrayList<>();
		for (int i = 0; i < nBodies; i++) {
			double x = bounds.getXMin()*0.25 + rand.nextDouble() * (bounds.getXMax() - bounds.getXMin()) * 0.25;
			double y = bounds.getYMin()*0.25 + rand.nextDouble() * (bounds.getYMax() - bounds.getYMin()) * 0.25;
			Body b = new Body(i, new P2d(x, y), new V2d(0, 0), 10);
			bodies.add(b);
		}
	}

	protected void testBodySet4_many_bodies() {
		bounds = new Boundary(-6.0, -6.0, 6.0, 6.0);
		int nBodies = 1000;
		Random rand = new Random(System.currentTimeMillis());
		bodies = new ArrayList<>();
		for (int i = 0; i < nBodies; i++) {
			double x = bounds.getXMin()*0.25 + rand.nextDouble() * (bounds.getXMax() - bounds.getXMin()) * 0.25;
			double y = bounds.getYMin()*0.25 + rand.nextDouble() * (bounds.getYMax() - bounds.getYMin()) * 0.25;
			Body b = new Body(i, new P2d(x, y), new V2d(0, 0), 10);
			bodies.add(b);
		}
	}
}
