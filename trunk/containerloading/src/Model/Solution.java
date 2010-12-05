package Model;

import java.util.List;

public interface Solution {

    public List<Solution> getNeighbours();

    public Long getValue();

    public List<Box> getBoxes();

    public Container getContainer();

}
