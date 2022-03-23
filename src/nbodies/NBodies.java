package nbodies;

import nbodies.sim.*;
import nbodies.sim.data.SimulationData;
import nbodies.sim.data.SimulationDataBuilder;
import nbodies.sim.data.SimulationDataFactory;
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

    public static void main(String[] args) {

		System.out.println(Runtime.getRuntime().availableProcessors() + " cores available");

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screenSize.getWidth();
		int height = (int) screenSize.getHeight();
		int size = min(width, height) - 100;

		//SimulationData data = SimulationDataFactory.testBodySet4_many_bodies();
		SimulationData data = new SimulationDataBuilder()
				.numBodies(1000)
				.bodies(randomBodyIn(-1, 1, -1, 1))
				.bounds(new Boundary(-6, -6, 6, 6))
				.deltaTime(0.01)
				.steps(1000)
				.build();

		System.out.println(data);

    	SimulationView viewer = new SimulationView(size, size, data);

    	//Simulator sim = new SequentialSimulator(data);
		//Simulator sim = new ExecutorSimulator(data);
		Simulator sim = new MultiThreadSimulator(data);
        sim.execute(50000);
    }
}
