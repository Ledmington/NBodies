package nbodies.sim;

import nbodies.Body;
import nbodies.Boundary;

import java.util.ArrayList;

public class SimulationData {
	private final ArrayList<Body> bodies;
	private final Boundary bounds;
	private double vt = 0;
	private final double dt;
	private long iter = 0;

	public SimulationData(final ArrayList<Body> bodies, final Boundary bounds, final double dt) {
		this.bodies = bodies;
		this.bounds = bounds;
		this.dt = dt;
	}

	public SimulationData(final ArrayList<Body> bodies, final Boundary bounds) {
		this(bodies, bounds, 0.001);
	}

	public void nextIteration() {
		vt += dt;
		iter++;
	}

	public ArrayList<Body> getBodies() {
		return bodies;
	}

	public Boundary getBounds() {
		return bounds;
	}

	public double getTime() {
		return vt;
	}

	public double getDelta() {
		return dt;
	}

	public long getIteration() {
		return iter;
	}
}
