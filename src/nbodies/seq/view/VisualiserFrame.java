package nbodies.seq.view;

import nbodies.seq.Body;
import nbodies.seq.Boundary;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class VisualiserFrame extends JFrame {

	private final VisualiserPanel panel;

	public VisualiserFrame(int w, int h){
		setTitle("N-Bodies Simulation");
		setSize(w, h);
		setMinimumSize(new Dimension(w, h));
		setResizable(false);
		panel = new VisualiserPanel(w, h);
		getContentPane().add(panel);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		this.pack();
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