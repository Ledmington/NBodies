package nbodies.seq;

import nbodies.seq.view.SimulationView;

/**
 * Bodies simulation - legacy code: sequential, unstructured
 * 
 * @author aricci
 */
public class SequentialBodySimulationMain {

    public static void main(String[] args) {
                
    	SimulationView viewer = new SimulationView(800, 800);

    	Simulator sim = new Simulator(viewer);
        sim.execute(50000);
    }
}
