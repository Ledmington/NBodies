package nbodies.sim;

import nbodies.Body;
import nbodies.Boundary;
import nbodies.V2d;

import java.util.ArrayList;

public abstract class AbstractSimulator implements Simulator {

	protected final SimulationData data;

	protected AbstractSimulator(final SimulationData data) {
		this.data = data;
	}

	protected ArrayList<Body> getBodies() {
		return data.getBodies();
	}

	protected Boundary getBounds() {
		return data.getBounds();
	}

	protected V2d computeTotalForceOnBody(final Body b) {

		V2d totalForce = new V2d(0, 0);

		/* compute total repulsive force */

		for (Body otherBody : getBodies()) {
			if (!b.equals(otherBody)) {
				try {
					V2d forceByOtherBody = b.computeRepulsiveForceBy(otherBody);
					totalForce.sum(forceByOtherBody);
				} catch (Exception ignored) {}
			}
		}

		/* add friction force */
		totalForce.sum(b.getCurrentFrictionForce());

		return totalForce;
	}
}
