package nbodies.sim.data;

import nbodies.Body;
import nbodies.Boundary;
import nbodies.utils.barrier.Barrier;
import nbodies.utils.barrier.ReusableBarrier;
import nbodies.utils.stats.Statistics;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class SimulationData {
	private final int nThreads;
	private final ArrayList<Body> bodies;
	private final Boundary bounds;
	private final double dt;
	private final long steps;
	private final Barrier pause;
	private final Statistics FPSstats;
	private double vt = 0;
	private long iter = 0;
	private Instant beginning;
	private Instant lastIteration = null;
	private Duration totalTime = null;

	public SimulationData(final ArrayList<Body> bodies, final Boundary bounds, final double dt, final long nsteps, final int nThreads) {
		this.nThreads = nThreads;
		this.bodies = bodies;
		this.bounds = bounds;
		this.dt = dt;
		this.steps = nsteps;
		this.FPSstats = new Statistics();
		pause = new ReusableBarrier(nThreads + 1);
	}

	public SimulationData(final SimulationData data) {
		this.nThreads = data.nThreads;
		this.bodies = data.bodies.stream().map(Body::new).collect(Collectors.toCollection(ArrayList::new));
		this.bounds = new Boundary(data.bounds);
		this.dt = data.dt;
		this.steps = data.steps;
		this.pause = data.pause; // No need to deep copy the barrier
		this.vt = data.vt;
		this.iter = data.iter;
		this.FPSstats = data.FPSstats;
		this.beginning = data.beginning;
		this.lastIteration = data.lastIteration;
		this.totalTime = data.totalTime;
	}

	public SimulationData(final ArrayList<Body> bodies, final Boundary bounds) {
		this(bodies, bounds, 0.001, 50000, Runtime.getRuntime().availableProcessors());
	}

	public static SimulationDataBuilder builder() {
		return new SimulationDataBuilder();
	}

	public void nextIteration() {
		if (isFinished()) return;

		vt += dt;
		iter++;

		if (lastIteration == null) {
			beginning = Instant.now();
			lastIteration = Instant.now();
		} else {
			Instant newIteration = Instant.now();
			Duration timeElapsed = Duration.between(lastIteration, newIteration);
			lastIteration = newIteration;
			FPSstats.add((double) (timeElapsed.toMillis()));
		}

		if (isFinished()) {
			totalTime = Duration.between(beginning, Instant.now());
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

	public Barrier getPause() {
		return pause;
	}

	public boolean isFinished() {
		return iter >= steps;
	}

	public Statistics getFPSStats() {
		return FPSstats;
	}

	public String getETA() {
		if (beginning == null) return "";
		Duration elapsed = Duration.between(beginning, Instant.now());
		double msForEachIteration = (double) elapsed.toMillis() / (double) iter;
		long remainingMillis = (long) (msForEachIteration * (steps - iter));
		long minutes = remainingMillis / 60_000;
		remainingMillis %= 60_000;
		long seconds = remainingMillis / 1000;
		remainingMillis %= 1000;
		return String.format("%2d:%02d:%03d", minutes, seconds, remainingMillis);
	}

	public Duration getTotalTime() {
		return totalTime;
	}

	public String toString() {
		return "Simulating " + bodies.size() + " bodies\n" +
				"Steps: " + steps + "\n" +
				"Boundaries:\n" +
				"\tx: [" + bounds.getXMin() + ", " + bounds.getXMax() + "]\n" +
				"\ty: [" + bounds.getYMin() + ", " + bounds.getYMax() + "]\n" +
				"delta time: " + dt;
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SimulationData that = (SimulationData) o;
		if (beginning != null && !beginning.equals(that.beginning)) return false;
		if (lastIteration != null && !lastIteration.equals(that.lastIteration)) return false;
		if (totalTime != null && !totalTime.equals(that.totalTime)) return false;
		return nThreads == that.nThreads &&
				Double.compare(that.dt, dt) == 0 &&
				steps == that.steps &&
				Double.compare(that.vt, vt) == 0 &&
				iter == that.iter &&
				bodies.equals(that.bodies) &&
				bounds.equals(that.bounds) &&
				pause.equals(that.pause) &&
				FPSstats.equals(that.FPSstats);
	}

	public int hashCode() {
		return Objects.hash(nThreads, bodies, bounds, dt, steps, pause, FPSstats, vt, iter, beginning, lastIteration, totalTime);
	}
}
