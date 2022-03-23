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

    public static void main(String[] args) {
		System.out.println(Runtime.getRuntime().availableProcessors() + " cores available");

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screenSize.getWidth();
		int height = (int) screenSize.getHeight();
		int size = min(width, height) - 100;

		//SimulationData data = SimulationDataFactory.testBodySet4_many_bodies();
		SimulationData data = SimulationData.builder()
				.numBodies(1000)
				.bodies(randomBodyIn(-1, 1, -1, 1))
				.bounds(new Boundary(-6, -6, 6, 6))
				.deltaTime(0.01)
				.steps(10000)
				.build();

		System.out.println(data);

    	new SimulationView(size, size, data);

    	//sim = new SequentialSimulator(data);
		//sim = new ExecutorSimulator(data);
		sim = new MultiThreadSimulator(data);
        sim.execute(50000);
    }

	public static Simulator getSimulator() {
		return sim;
	}
}
