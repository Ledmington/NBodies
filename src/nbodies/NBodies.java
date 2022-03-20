package nbodies;

import nbodies.sim.*;
import nbodies.view.SimulationView;

import java.awt.*;

import static java.lang.Integer.min;

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

		SimulationData data = SimulationDataFactory.testBodySet3_some_bodies();

		// TODO pass simulation data to viewer to avoid the "model-call-graphic" thing

    	SimulationView viewer = new SimulationView(size, size);

    	//Simulator sim = new SequentialSimulator(viewer, data);
		Simulator sim = new ExecutorSimulator(viewer, data);
		//Simulator sim = new MultiThreadSimulator(viewer, data);
        sim.execute(50000);
    }
}
