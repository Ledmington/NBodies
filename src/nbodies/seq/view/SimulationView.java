package nbodies.seq.view;

import nbodies.seq.Body;
import nbodies.seq.Boundary;
import nbodies.seq.P2d;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.RenderingHints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.*;

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
     * 
     * @param w
     * @param h
     */
    public SimulationView(int w, int h){
    	frame = new VisualiserFrame(w,h);
    }
        
    public void display(ArrayList<Body> bodies, double vt, long iter, Boundary bounds){
 	   frame.display(bodies, vt, iter, bounds); 
    }
}
