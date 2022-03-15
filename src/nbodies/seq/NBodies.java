package nbodies.seq;

import nbodies.seq.view.SimulationView;

import java.awt.*;

import static java.lang.Integer.min;

/**
 * Bodies simulation - legacy code: sequential, unstructured
 * 
 * @author aricci
 */
public class NBodies {

    public static void main(String[] args) {

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screenSize.getWidth();
		int height = (int) screenSize.getHeight();
		int size = min(width, height) - 100;
                
    	SimulationView viewer = new SimulationView(size, size);

    	Simulator sim = new SequentialSimulator(viewer);
        sim.execute(50000);
    }
}
