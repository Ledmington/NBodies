package nbodies.view;

import nbodies.NBodies;

import javax.swing.*;

public class ControlBar extends JPanel {

	private final JLabel statusLabel;
	private boolean neverStarted = true;

	public ControlBar() {
		JButton startButton = new JButton("Start");
		startButton.addActionListener(e -> {
			if(neverStarted) {
				NBodies.getSimulator().execute();
				neverStarted = false;
			} else {
				NBodies.getSimulator().start();
			}
			checkAndUpdateStatus();
		});
		add(startButton);

		JButton stopButton = new JButton("Stop");
		stopButton.addActionListener(e -> {
			NBodies.getSimulator().stop();
			checkAndUpdateStatus();
		});
		add(stopButton);

		statusLabel = new JLabel("Status: ");
		add(statusLabel);

		SwingUtilities.invokeLater(this::checkAndUpdateStatus);
	}

	private void checkAndUpdateStatus() {
		// TODO avoid this null check
		if(NBodies.getSimulator() == null) return;

		if (NBodies.getSimulator().isRunning()) {
			statusLabel.setText("Status: running");
		} else {
			statusLabel.setText("Status: stopped");
		}
	}
}
