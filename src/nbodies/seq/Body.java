package nbodies.seq;


/*
 * This class represents a body
 * 
 */
public class Body {
    
	private static final double REPULSIVE_CONST = 0.01;
	private static final double FRICTION_CONST = 10;
	
    private final P2d pos;
    private final V2d vel;
    private final double mass;
    private final int id;
    
    public Body(int id, P2d pos, V2d vel, double mass){
    	this.id = id;
        this.pos = pos;
        this.vel = vel;
        this.mass = mass;
    }
    
    public double getMass() {
    	return mass;
    }
    
    public P2d getPos(){
        return pos;
    }

    public V2d getVel(){
        return vel;
    }
    
    public int getId() {
    	return id;
    }
    
    public boolean equals(Object b) {
		if(b.getClass() != Body.class) return false;
    	return ((Body)b).id == id;
    }
    
    
    /**
     * Update the position, according to current velocity
     * 
     * @param dt time elapsed 
     */
    public void updatePos(double dt){    	
    	pos.sum(new V2d(vel).scalarMul(dt));
    }

    /**
     * Update the velocity, given the instant acceleration
     * @param acc instant acceleration
     * @param dt time elapsed
     */
    public void updateVelocity(V2d acc, double dt){
    	vel.sum(new V2d(acc).scalarMul(dt));
    }
    
    /**
     * Change the velocity
     * 
     * @param vx
     * @param vy
     */
    public void changeVel(double vx, double vy){
    	vel.change(vx, vy);
    }
  	
    /**
     * Computes the distance from the specified body
     * 
     * @param b
     * @return
     */
    public double getDistanceFrom(Body b) {
    	double dx = pos.getX() - b.getPos().getX();
    	double dy = pos.getY() - b.getPos().getY();
    	return Math.sqrt(dx*dx + dy*dy);
    }
    
    /**
     * 
     * Compute the repulsive force exerted by another body
     * 
     * @param b
     * @return
     * @throws InfiniteForceException
     */
    public V2d computeRepulsiveForceBy(Body b) throws InfiniteForceException {
		double dist = getDistanceFrom(b);
		if (dist > 0) {
			try {
				return new V2d(b.getPos(), pos)
					.normalize()
					.scalarMul(b.getMass()*REPULSIVE_CONST/(dist*dist));
			} catch (Exception ex) {
				throw new InfiniteForceException();
			}
		} else {
			throw new InfiniteForceException();
		}
    }
    
    /**
     * 
     * Compute current friction force, given the current velocity
     */
    public V2d getCurrentFrictionForce() {
        return new V2d(vel).scalarMul(-FRICTION_CONST);
    }
    
    /**
     * Check if there are collisions with the boundary and update the
     * position and velocity accordingly
     * 
     * @param bounds
     */
    public void checkAndSolveBoundaryCollision(Boundary bounds){
    	double x = pos.getX();
    	double y = pos.getY();
        if (x > bounds.getXMax()){
            pos.change(bounds.getXMax(), pos.getY());
            vel.change(-vel.getX(), vel.getY());
        } else if (x < bounds.getXMin()){
            pos.change(bounds.getXMin(), pos.getY());
            vel.change(-vel.getX(), vel.getY());
        } else if (y > bounds.getYMax()){
            pos.change(pos.getX(), bounds.getYMax());
            vel.change(vel.getX(), -vel.getY());
        } else if (y < bounds.getYMin()){
            pos.change(pos.getX(), bounds.getYMin());
            vel.change(vel.getX(), -vel.getY());
        }
    }
}
