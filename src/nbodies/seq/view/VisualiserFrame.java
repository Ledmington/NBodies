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

		setLayout(new BorderLayout());

		panel = new VisualiserPanel(w, h);
		getContentPane().add(panel, BorderLayout.CENTER);

		JPanel topBar = new JPanel();

		JButton startButton = new JButton("Start");
		topBar.add(startButton);
		JButton stopButton = new JButton("Stop");
		topBar.add(stopButton);

		getContentPane().add(topBar, BorderLayout.NORTH);

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