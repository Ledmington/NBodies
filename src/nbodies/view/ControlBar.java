package nbodies.view;

import nbodies.NBodies;

import javax.swing.*;

public class ControlBar extends JPanel {

	private final JLabel statusLabel;

	public ControlBar() {
		JButton startButton = new JButton("Start");
		startButton.addActionListener(e -> {
			NBodies.getSimulator().start();
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
	}

	private void checkAndUpdateStatus() {
		// TODO avoid this null check
		if(NBodies.getSimulator() != null) {
			if (NBodies.getSimulator().isRunning()) {
				statusLabel.setText("Status: running");
			} else {
				statusLabel.setText("Status: stopped");
			}
		}
	}
}
