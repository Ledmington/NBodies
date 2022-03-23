package nbodies.utils.barrier;

public class ReusableBarrier implements Barrier {

	private int n;
	private final int max;
	
	public ReusableBarrier(int nParticipants) {
		n = nParticipants;
		max = nParticipants;
	}
	
	@Override
	public synchronized void hitAndWaitAll() {
		n--;
		if(n == 0) {
			notifyAll();
			n = max; // TODO: maybe here we need to use two barriers to make sure that all threads are out before going back in
		}
		else {
			try {
				wait();
			} catch (InterruptedException ignored) {}
		}
	}
}
