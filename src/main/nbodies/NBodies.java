package nbodies;

import nbodies.sim.*;
import nbodies.sim.data.SimulationData;
import nbodies.view.SimulationView;

import java.awt.*;

import static java.lang.Integer.min;
import static nbodies.sim.data.SimulationDataBuilder.randomBodyIn;

/**
 * Bodies simulation - legacy code: sequential, unstructured
 * 
 * @author aricci
 */
public class NBodies {

	private static Simulator sim;
	private static final boolean USE_GUI = false;

    public static void main(String[] args) {
		if(USE_GUI) {
			System.out.println("Press Start to start the simulation.\n");
		}

		System.out.println(Runtime.getRuntime().availableProcessors() + " cores available");

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screenSize.getWidth();
		int height = (int) screenSize.getHeight();
		int size = min(width, height) - 100;

		//SimulationData data = SimulationDataFactory.testBodySet4_many_bodies();
		SimulationData data = SimulationData.builder()
				//.threads(1) // uncomment to use serial
				.numBodies(100)
				.bodies(randomBodyIn(-1, 1, -1, 1))
				.bounds(new Boundary(-6, -6, 6, 6))
				.deltaTime(0.01)
				.steps(1000)
				.build();

		System.out.println(data);

		//sim = new SequentialSimulator(data);
		//sim = new ExecutorSimulator(data);
		sim = new MultiThreadSimulator(data);

		if(USE_GUI) {
			new SimulationView(size, size, data);
		} else {
			sim.execute();
		}

		// Printing total time
		while(!data.isFinished()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException ignored) {}
		}

		final double seconds = (double)(data.getTotalTime().toMillis()) / 1000;
		System.out.println("Total time of execution: " + seconds + " seconds");
    }

	public static Simulator getSimulator() {
		return sim;
	}
}
