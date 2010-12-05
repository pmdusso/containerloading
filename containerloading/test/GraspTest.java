import org.junit.Before;
import org.junit.Test;

import Model.GRASP;
import Model.Solution;
import containerloading.InputReader;

public class GraspTest {

    InputReader reader;

    GRASP grasp;

    @Before
    public void setUp() {

    }

    @Test
    public void test() {
	this.reader = new InputReader("C:\\Users\\Matheus Abegg\\Desktop\\br10.txt");
	reader.readInput(1);
	this.grasp = new GRASP(reader.getContainer(), reader.getBoxes(), reader.getBoxTypes(), reader.getSeed());

	Solution solution = grasp.solve();

	System.out.println(solution.getValue());

	System.out.println(solution.getContainer().getVolume());

	System.out.println(String.format("Razão = %s", (float) solution.getValue() / (float) solution.getContainer().getVolume()));
    }
}
