package nbodies.sim;

public interface Simulator {
	void execute();

	void start();

	void stop();

	boolean isRunning();
}
