package nbodies.sim.data;

import nbodies.Body;
import nbodies.Boundary;
import nbodies.P2d;
import nbodies.V2d;

import java.util.ArrayList;
import java.util.function.Supplier;

public class SimulationDataBuilder {

	private Supplier<Body> bodySupplier;
	private long nBodies = 1000;
	private Boundary bounds;
	private double dt = 0.001;
	private long steps = 50000;

	public SimulationDataBuilder bodies(final Supplier<Body> bodyCreator) {
		bodySupplier = bodyCreator;
		return this;
	}

	public SimulationDataBuilder numBodies(final long n) {
		nBodies = n;
		return this;
	}

	public SimulationDataBuilder bounds(final Boundary boundaries) {
		this.bounds = boundaries;
		return this;
	}

	public SimulationDataBuilder deltaTime(final double dt) {
		this.dt = dt;
		return this;
	}

	public SimulationDataBuilder steps(final long steps) {
		this.steps = steps;
		return this;
	}

	public static Supplier<Body> randomBodyIn(double xmin, double xmax, double ymin, double ymax) {
		return () -> new Body(
				new P2d(randomDouble(xmin, xmax), randomDouble(ymin, ymax)),
				new V2d(0,0),
				10);
	}

	private static double randomDouble(double a, double b) {
		return Math.random() * (b - a) + a;
	}

	public SimulationData build() {
		if(bodySupplier == null) {
			throw new IllegalStateException("Cannot build SimulationData without bodies");
		}
		if(bounds == null) {
			throw new IllegalStateException("Cannot build SimulationData without boundaries");
		}

		ArrayList<Body> bodies = new ArrayList<>();
		for (int i=0; i<nBodies; i++) {
			bodies.add(bodySupplier.get());
		}

		return new SimulationData(bodies, bounds, dt, steps);
	}
}
