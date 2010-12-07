
import containerloading.InputReader;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Matheus Abegg
 */
public class InputReaderTest {

    InputReader inputReader;

    String filePath = "C:\\Users\\Matheus Abegg\\Desktop\\br1.txt";

    @Before
    public void setUp(){
        inputReader = new InputReader(filePath);
    }

    @Test
    public void testReader(){
        inputReader.readInput(50);
        System.out.println(inputReader);
    }
}
