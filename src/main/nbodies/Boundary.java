package nbodies;

/**
 * Boundary of the field where bodies move. 
 *
 */
public class Boundary {

	private final double xMin;
	private final double yMin;
	private final double xMax;
	private final double yMax;

	public Boundary(double x0, double y0, double x1, double y1){
		this.xMin = Math.min(x0, x1);
		this.yMin = Math.min(y0, y1);
		this.xMax = Math.max(x0, x1);
		this.yMax = Math.max(y0, y1);
	}

	public double getXMin(){
		return xMin;
	}

	public double getXMax(){
		return xMax;
	}

	public double getYMin(){
		return yMin;
	}

	public double getYMax(){
		return yMax;
	}

	public boolean isInside(final double x, final double y) {
		return x >= xMin && x <= xMax && y >= yMin && y <= yMax;
	}
}
