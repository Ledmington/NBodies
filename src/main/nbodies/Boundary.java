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
		this.xMin = x0;
		this.yMin = y0;
		this.xMax = x1;
		this.yMax = y1;
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
}
