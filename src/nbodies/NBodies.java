package nbodies;

import nbodies.view.SimulationView;

import java.awt.*;

import static java.lang.Integer.min;

/**
 * Bodies simulation - legacy code: sequential, unstructured
 * 
 * @author aricci
 */
public class NBodies {

	private static Simulator sim;

    public static void main(String[] args) {

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screenSize.getWidth();
		int height = (int) screenSize.getHeight();
		int size = min(width, height) - 100;
                
    	SimulationView viewer = new SimulationView(size, size);

    	sim = new SequentialSimulator(viewer);
        sim.execute(50000);
    }

	public static Simulator getSimulator() {
		return sim;
	}
}
