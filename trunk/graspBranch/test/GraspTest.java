import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import br.ufrgs.inf.containerloading.entities.Solution;
import br.ufrgs.inf.containerloading.grasp.GRASP;
import br.ufrgs.inf.containerloading.reader.InputReader;

public class GraspTest {

    InputReader reader;
    GRASP grasp;

    @Before
    public void setUp() {

    }

    @Test
    public void test() throws Exception {
	Integer instanceNumber = 4;
	this.reader = new InputReader("C:\\Users\\Matheus Abegg\\Desktop\\otimização\\ins\\br" + instanceNumber + ".txt");
	reader.readInput(50);
	this.grasp = new GRASP(reader.getContainer(), reader.getBoxes(), reader.getBoxTypes(), reader.getSeed());
	Long initialTime = System.currentTimeMillis();
	Solution solution = grasp.solve();
	Long endTime = System.currentTimeMillis();

	String total = new SimpleDateFormat("mm:ss").format(new Date(endTime - initialTime));

	System.out.println("Intancia: br" + instanceNumber);

	System.out.println("Tempo gasto (mm:ss): " + total);

	System.out.println(solution.getValue());

	System.out.println(solution.getContainer().getVolume());

	System.out.println(String.format("Razão = %s", (float) solution.getValue() / (float) solution.getContainer().getVolume()));
    }
}
