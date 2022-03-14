package nbodies.seq.view;

import nbodies.seq.Body;
import nbodies.seq.Boundary;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class VisualiserFrame extends JFrame {

	private final VisualiserPanel panel;

	public VisualiserFrame(int w, int h){
		setTitle("N-Bodies Simulation");
		setSize(w,h);
		setResizable(false);
		panel = new VisualiserPanel(w,h);
		getContentPane().add(panel);
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent ev){
				System.exit(-1);
			}
			public void windowClosed(WindowEvent ev){
				System.exit(-1);
			}
		});
		this.setVisible(true);
	}

	public void display(ArrayList<Body> bodies, double vt, long iter, Boundary bounds){
		try {
			SwingUtilities.invokeAndWait(() -> {
				panel.display(bodies, vt, iter, bounds);
				repaint();
			});
		} catch (Exception ignored) {}
	}
}