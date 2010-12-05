package containerloading;

import Model.Box;
import Model.Container;
import Model.Vector3d;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Matheus Abegg
 */
public class InputReader {

    private Container container;
    private List<Box> boxes = new ArrayList<Box>();
    private Long seed;
    private String filePath;
    private Integer boxTypes;

    public InputReader(String filePath) {
        this.filePath = filePath;
    }

    public void readInput(Integer instanceNumber) {
        File input = new File(filePath);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(input));

            String instancesNumber = reader.readLine().trim();
            Integer numberOfInstances = Integer.parseInt(instancesNumber);

            for (int i = 0; i < numberOfInstances; i++) {
                String instanceData = reader.readLine().trim();
                String[] idSeed = instanceData.split(" ");
                Integer id = Integer.parseInt(idSeed[0]);

                seed = Long.parseLong(idSeed[1]);
                String containerData = reader.readLine().trim();
                String[] containerDataArray = containerData.split(" ");

                Integer conatinerWidth = Integer.parseInt(containerDataArray[0]);
                Integer conatinerlength = Integer.parseInt(containerDataArray[1]);
                Integer conatinerHeight = Integer.parseInt(containerDataArray[2]);

                String boxTypesNumberStr = reader.readLine().trim();
                boxTypes = Integer.parseInt(boxTypesNumberStr);

                for (int j = 0; j < boxTypes; j++) {
                    String boxData = reader.readLine().trim();
                    String[] boxDataArray = boxData.split(" ");

                    Integer boxWidth = Integer.parseInt(boxDataArray[1]);
                    Boolean widthVertical = !Boolean.parseBoolean(boxDataArray[2]);

                    Integer boxLength = Integer.parseInt(boxDataArray[3]);
                    Boolean lengthVertical = !Boolean.parseBoolean(boxDataArray[4]);

                    Integer boxHeigth = Integer.parseInt(boxDataArray[5]);
                    Boolean heigthVertical = !Boolean.parseBoolean(boxDataArray[6]);

                    Integer numberOfBoxes = Integer.parseInt(boxDataArray[7]);

                    if (id == instanceNumber) {
                        addBox(boxWidth, widthVertical, boxLength, lengthVertical, boxHeigth, heigthVertical, numberOfBoxes,j+1);
                    }
                }

                if (id == instanceNumber) {
                    this.container = new Container(new Vector3d(conatinerWidth, conatinerlength, conatinerHeight));
                }
            }
        } catch (IOException e) {
            System.out.println("Houve um erro na hora de ler o arquivo. \n" + e.getMessage());
        }
    }

    private void addBox(Integer boxWidth, Boolean widthVertical,
                        Integer boxLength, Boolean lengthVertical,
                        Integer boxHeigth, Boolean heigthVertical,
                        Integer numberOfBoxes,Integer boxType) {
        Box box = new Box(new Vector3d(boxWidth, boxLength, boxHeigth),
                            widthVertical, lengthVertical, heigthVertical,boxType);

        for (int i = 0; i < numberOfBoxes; i++) {
            boxes.add(box);
        }
    }

    public List<Box> getBoxes() {
        return boxes;
    }

    public Container getContainer() {
        return container;
    }

    public Long getSeed() {
        return seed;
    }

    public Integer getBoxTypes() {
        return boxTypes;
    }
}
