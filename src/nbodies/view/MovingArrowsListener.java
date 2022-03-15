package nbodies.view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MovingArrowsListener implements KeyListener {

	private double scale = 1;

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_UP -> scale *= 1.1;
			case KeyEvent.VK_DOWN -> scale *= 0.9;
		}
	}

	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}

	public double getScale() {
		return scale;
	}
}
