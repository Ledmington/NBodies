package nbodies.view;

import nbodies.Body;
import nbodies.Boundary;
import nbodies.sim.data.SimulationData;

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

		Thread displayerThread = new Thread(() -> {
			while(true) {
				display(data);
				try {
					// TODO remove comment
					Thread.sleep(50); // apparently 100 is too much and 50ms is just enough to make things look smooth
				} catch (InterruptedException ignored) {}
			}
		});
		displayerThread.start();
    }
        
    public void display(final SimulationData data){
 	   frame.display(data);
    }
}