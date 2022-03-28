package nbodies.sim;

import nbodies.Boundary;
import nbodies.sim.data.SimulationData;
import org.junit.jupiter.api.Test;

import static nbodies.sim.data.SimulationDataBuilder.randomBodyIn;
import static org.junit.jupiter.api.Assertions.*;

public class TestData {
	@Test
	public void copyMustBeEqualButNotTheSame() {
		SimulationData data = SimulationData.builder()
				.bodies(randomBodyIn(-1,-1,1,1))
				.bounds(new Boundary(-6,-6,6,6))
				.build();

		// TODO
		/*SimulationData data2 = (SimulationData) data.clone();

		assertNotSame(data, data2);
		assertEquals(data, data2);
		assertEquals(data2, data);*/
	}
}
