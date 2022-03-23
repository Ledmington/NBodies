package nbodies.view;

import nbodies.*;

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
		setFocusable(true);

		MovingArrowsListener listener = new MovingArrowsListener(w, h);
		addKeyListener(listener);

		setLayout(new BorderLayout());

		panel = new VisualiserPanel(w, h, listener);
		getContentPane().add(panel, BorderLayout.CENTER);

		getContentPane().add(new ControlBar(), BorderLayout.NORTH);

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		this.pack();
	}

	public void display(ArrayList<Body> bodies, double vt, long iter, Boundary bounds, String eta){
		try {
			SwingUtilities.invokeAndWait(() -> {
				panel.display(bodies, vt, iter, bounds, eta);
				repaint();
			});
		} catch (Exception ignored) {}
	}
}