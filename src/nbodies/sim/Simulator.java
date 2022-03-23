package nbodies.sim;

public interface Simulator {
	void execute(final long nsteps);
	void start();
	void stop();
	boolean isRunning();
}
