package nbodies;

public interface Simulator {
	void execute(final long nsteps);
	void stop();
	boolean isRunning();
}
