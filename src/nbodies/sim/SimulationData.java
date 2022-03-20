package nbodies.sim;

import nbodies.Body;
import nbodies.Boundary;

import java.util.ArrayList;

public class SimulationData {
	private final ArrayList<Body> bodies;
	private final Boundary bounds;

	public SimulationData(final ArrayList<Body> bodies, final Boundary bounds) {
		this.bodies = bodies;
		this.bounds = bounds;
	}

	public ArrayList<Body> getBodies() {
		return bodies;
	}

	public Boundary getBounds() {
		return bounds;
	}
}
