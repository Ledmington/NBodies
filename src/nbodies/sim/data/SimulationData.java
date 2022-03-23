package nbodies.sim.data;

import nbodies.Body;
import nbodies.Boundary;

import java.time.*;
import java.util.ArrayList;

public class SimulationData {
	private final int nThreads;
	private final ArrayList<Body> bodies;
	private final Boundary bounds;
	private double vt = 0;
	private final double dt;
	private long iter = 0;
	private final long steps;
	private Instant beginning;
	private Instant lastIteration = null;

	public SimulationData(final ArrayList<Body> bodies, final Boundary bounds, final double dt, final long nsteps, final int nThreads) {
		this.nThreads = nThreads;
		this.bodies = bodies;
		this.bounds = bounds;
		this.dt = dt;
		this.steps = nsteps;
	}

	public SimulationData(final ArrayList<Body> bodies, final Boundary bounds) {
		this(bodies, bounds, 0.001, 50000, Runtime.getRuntime().availableProcessors());
	}

	public static SimulationDataBuilder builder() {
		return new SimulationDataBuilder();
	}

	public void nextIteration() {
		vt += dt;
		iter++;

		if (lastIteration == null) {
			beginning = Instant.now();
			lastIteration = Instant.now();
		} else {
			Instant newIteration = Instant.now();
			Duration timeElapsed = Duration.between(lastIteration, newIteration);
			lastIteration = newIteration;
		}
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

	public int getNThreads() {
		return nThreads;
	}

	public boolean isFinished() {
		return iter >= steps;
	}

	public String getETA() {
		if(beginning == null) return "";
		Duration elapsed = Duration.between(beginning, Instant.now());
		double msForEachIteration = (double)elapsed.toMillis() / (double)iter;
		long remainingMillis = (long) (msForEachIteration * (steps - iter));
		long minutes = remainingMillis / 60_000;
		remainingMillis %= 60_000;
		long seconds = remainingMillis / 1000;
		remainingMillis %= 1000;
		return String.format("%2d:%02d:%03d", minutes, seconds, remainingMillis);
	}

	public String toString() {
		return "Simulating " + bodies.size() + " bodies\n" +
				"Steps: " + steps + "\n" +
				"Boundaries:\n" +
				"\tx: [" + bounds.getXMin() + ", " + bounds.getXMax() + "]\n" +
				"\ty: [" + bounds.getYMin() + ", " + bounds.getYMax() + "]\n" +
				"delta time: " + dt +
				"\n";
	}
}
