package nbodies.view;

import nbodies.Body;
import nbodies.Boundary;

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
    public SimulationView(int w, int h){
    	frame = new VisualiserFrame(w, h);
    }
        
    public void display(ArrayList<Body> bodies, double vt, long iter, Boundary bounds){
 	   frame.display(bodies, vt, iter, bounds); 
    }
}
