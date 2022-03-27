package nbodies;

import java.util.Objects;

public class P2d {

    private double x, y;

    public P2d(double x,double y){
        this.x = x;
        this.y = y;
    }

    public void sum(V2d v) {
    	x += v.x;
    	y += v.y;
	}
     
    public void change(double x, double y){
    	this.x = x;
    	this.y = y;
    }

	public void setX(final double x) {
		this.x = x;
	}

	public void setY(final double y) {
		this.y = y;
	}

	public double getX() {
    	return x;
    }

	public double getY() {
    	return y;
    }

	public String toString() {
		return "P2d{" +
				"x=" + x +
				", y=" + y +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		P2d p2d = (P2d) o;
		return Double.compare(p2d.x, x) == 0 && Double.compare(p2d.y, y) == 0;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
}
