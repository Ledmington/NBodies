package nbodies.sim;

import nbodies.Body;
import nbodies.Boundary;
import nbodies.P2d;
import nbodies.V2d;

import java.util.ArrayList;
import java.util.Random;

public abstract class SimulationDataFactory {
	public static SimulationData testBodySet1_two_bodies() {
		Boundary bounds = new Boundary(-4.0, -4.0, 4.0, 4.0);
		ArrayList<Body> bodies = new ArrayList<>();
		bodies.add(new Body(0, new P2d(-0.1, 0), new V2d(0,0), 1));
		bodies.add(new Body(1, new P2d(0.1, 0), new V2d(0,0), 2));
		return new SimulationData(bodies, bounds);
	}

	public static SimulationData testBodySet2_three_bodies() {
		Boundary bounds = new Boundary(-1.0, -1.0, 1.0, 1.0);
		ArrayList<Body> bodies = new ArrayList<>();
		bodies.add(new Body(0, new P2d(0, 0), new V2d(0,0), 10));
		bodies.add(new Body(1, new P2d(0.2, 0), new V2d(0,0), 1));
		bodies.add(new Body(2, new P2d(-0.2, 0), new V2d(0,0), 1));
		return new SimulationData(bodies, bounds);
	}

	public static SimulationData testBodySet3_some_bodies() {
		Boundary bounds = new Boundary(-4.0, -4.0, 4.0, 4.0);
		int nBodies = 100;
		Random rand = new Random(System.currentTimeMillis());
		ArrayList<Body> bodies = new ArrayList<>();
		for (int i = 0; i < nBodies; i++) {
			double x = bounds.getXMin()*0.25 + rand.nextDouble() * (bounds.getXMax() - bounds.getXMin()) * 0.25;
			double y = bounds.getYMin()*0.25 + rand.nextDouble() * (bounds.getYMax() - bounds.getYMin()) * 0.25;
			Body b = new Body(i, new P2d(x, y), new V2d(0, 0), 10);
			bodies.add(b);
		}
		return new SimulationData(bodies, bounds);
	}

	public static SimulationData testBodySet4_many_bodies() {
		Boundary bounds = new Boundary(-6.0, -6.0, 6.0, 6.0);
		int nBodies = 1000;
		Random rand = new Random(System.currentTimeMillis());
		ArrayList<Body> bodies = new ArrayList<>();
		for (int i = 0; i < nBodies; i++) {
			double x = bounds.getXMin()*0.25 + rand.nextDouble() * (bounds.getXMax() - bounds.getXMin()) * 0.25;
			double y = bounds.getYMin()*0.25 + rand.nextDouble() * (bounds.getYMax() - bounds.getYMin()) * 0.25;
			Body b = new Body(i, new P2d(x, y), new V2d(0, 0), 10);
			bodies.add(b);
		}
		return new SimulationData(bodies, bounds);
	}
}
