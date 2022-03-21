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

		JPanel topBar = new JPanel();

		JButton startButton = new JButton("Start");
		topBar.add(startButton);
		JButton stopButton = new JButton("Stop");
		topBar.add(stopButton);
		JLabel statusLabel = new JLabel("Status: ");
		topBar.add(statusLabel);

		/*Thread statusUpdaterThread = new Thread(() -> {
			while(true) {
				// TODO avoid this null check
				if(NBodies.getSimulator() != null) {
					if (NBodies.getSimulator().isRunning()) {
						statusLabel.setText("Status: running");
					} else {
						statusLabel.setText("Status: stopped");
					}
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException ignored) {}
			}
		});
		statusUpdaterThread.start();*/

		getContentPane().add(topBar, BorderLayout.NORTH);

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