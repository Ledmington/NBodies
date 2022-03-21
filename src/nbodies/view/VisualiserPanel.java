package nbodies.view;

import nbodies.Body;
import nbodies.Boundary;
import nbodies.P2d;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class VisualiserPanel extends JPanel {

	private ArrayList<Body> bodies;
	private Boundary bounds;
	private final MovingArrowsListener listener;

	private long nIter;
	private double vt;
	private String eta;

	private final int dx;
	private final int dy;

	public VisualiserPanel(int w, int h, MovingArrowsListener listener){
		setSize(w, h);
		dx = w/2 - 20;
		dy = h/2 - 20;
		this.listener = listener;
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		requestFocusInWindow();
	}
	
	private double getScale() {
		return listener.getScale();
	}

	private int getXCenter() {
		return listener.getXCenter();
	}

	private int getYCenter() {
		return listener.getYCenter();
	}

	public void paint(Graphics g){
		if (bodies != null) {
			Graphics2D g2 = (Graphics2D) g;

			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g2.clearRect(0, 0, this.getWidth(), this.getHeight());

			int x0 = getXCoord(bounds.getXMin());
			int y0 = getYCoord(bounds.getYMin());

			int wd = getXCoord(bounds.getXMax()) - x0;
			int ht = y0 - getYCoord(bounds.getYMax());

			g2.drawRect(x0, y0 - ht, wd, ht);

			bodies.forEach( b -> {
				P2d p = b.getPos();
				int radius = (int) (10 * getScale());
				if (radius < 1) {
					radius = 1;
				}
				g2.drawOval(getXCoord(p.getX()), getYCoord(p.getY()), radius, radius);
			});

			double totalMod = bodies.stream().mapToDouble(b -> b.getVel().mod()).sum();

			String time = String.format("%.2f", vt);
			g2.drawString("Bodies: " + bodies.size() + " - vt: " + time + " - nIter: " + nIter, 2, 10);
			g2.drawString(String.format("Total velocity: %.3e", totalMod), 2, 25);
			g2.drawString("Remaining time: "+this.eta, 2, 40);
			g2.drawString("(+/- to zoom, arrows to move around)", 2, 55);
		}
	}

	private int getXCoord(double x) {
		return (int)(getXCenter() + x*dx*getScale());
	}

	private int getYCoord(double y) {
		return (int)(getYCenter() - y*dy*getScale());
	}

	public void display(ArrayList<Body> bodies, double vt, long iter, Boundary bounds, String eta) {
		this.bodies = bodies;
		this.bounds = bounds;
		this.vt = vt;
		this.nIter = iter;
		this.eta = eta;
	}
}