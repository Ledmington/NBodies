package nbodies.sim;

import nbodies.Body;
import nbodies.V2d;
import nbodies.sim.data.SimulationData;
import nbodies.utils.barrier.Barrier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.function.Function;

public class Worker extends Thread {

	private final int id;
	private final SimulationData data;
	private final Barrier endCompute;
	private final Barrier endIteration;
	private final Function<Body, V2d> totalForceComputer;
	private boolean paused = false;
	private final Lock mutex;

	public Worker(final int id, final Lock mutex, final SimulationData data, final Barrier endCompute, final Barrier endIteration, final Function<Body, V2d> totalForceComputer) {
		this.id = id;
		this.mutex = mutex;
		this.data = data;
		this.endCompute = endCompute;
		this.endIteration = endIteration;
		this.totalForceComputer = totalForceComputer;
	}

	public void run() {
		// The master thread locks the mutex
		if(id == 0) {
			mutex.lock();
		}

		final ArrayList<Body> bodies = data.getBodies();

		// partition indices
		final int start = bodies.size() * id / data.getNThreads();
		final int end = bodies.size() * (id + 1) / data.getNThreads();

		final List<Body> myBodies = bodies.subList(start, end);
		final List<V2d> tmpForces = new ArrayList<>(myBodies.size());
		for(int i=0; i<myBodies.size(); i++) {
			tmpForces.add(new V2d(0, 0));
		}

		while (!data.isFinished()) {
			while (paused) {
				data.getPause().hitAndWaitAll();
			}

			for (Body b : myBodies) {
				tmpForces.set(myBodies.indexOf(b), totalForceComputer.apply(b));
			}
			endCompute.hitAndWaitAll();

			for (Body b : myBodies) {
				V2d tmpForceOnBody = tmpForces.get(myBodies.indexOf(b));
				V2d acc = new V2d(tmpForceOnBody).scalarMul(1.0 / b.getMass());

				b.updateVelocity(acc, data.getDelta());
				b.updatePos(data.getDelta());
				b.checkAndSolveBoundaryCollision(data.getBounds());
			}

			if (id == 0) {
				data.nextIteration();
				mutex.unlock();
				mutex.lock();
			}
			endIteration.hitAndWaitAll();
		}
	}

	public void pause() {
		paused = true;
	}

	public void wakeUp() {
		paused = false;
	}
}
