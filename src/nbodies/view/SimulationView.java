package nbodies.view;

import nbodies.Body;
import nbodies.Boundary;
import nbodies.sim.SimulationData;

import java.util.ArrayList;

/**
 * Simulation view
 *
 * @author aricci
 *
 */
public class SimulationView {
        
	private final VisualiserFrame frame;

	/**
     * Creates a view of the specified size (in pixels)
     */
    public SimulationView(final int w, final int h, final SimulationData data){
    	frame = new VisualiserFrame(w, h);

		Thread t = new Thread(() -> {
			while(true) {
				display(data.getBodies(), data.getTime(), data.getIteration(), data.getBounds(), data.getETA());
				try {
					// TODO remove comment
					Thread.sleep(50); // apparently 100 is too much and 50ms is just enough to make things look smooth
				} catch (InterruptedException ignored) {}
			}
		});
		t.start();
    }
        
    public void display(ArrayList<Body> bodies, double vt, long iter, Boundary bounds, String eta){
 	   frame.display(bodies, vt, iter, bounds, eta);
    }
}
