package nbodies.seq.view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MovingArrowsListener implements KeyListener {

	private double scale = 1;

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 38){  		/* KEY UP */
			scale *= 1.1;
		} else if (e.getKeyCode() == 40){  	/* KEY DOWN */
			scale *= 0.9;
		}
	}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}

	public double getScale() {
		return scale;
	}
}
