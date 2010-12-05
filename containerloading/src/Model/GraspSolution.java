package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GraspSolution implements Solution {

    private List<Box> boxesInContainer;

    private List<Solution> neighbours;

    private Container container;

    private List<Box> boxesOutside;

    public GraspSolution(Container container) {
	boxesInContainer = new ArrayList<Box>();
	this.container = container;
    }

    public GraspSolution(List<Box> boxesInside, List<Box> boxesOutside, Container container) {
	this.boxesInContainer = boxesInside;
	this.container = container;
	this.boxesOutside = boxesOutside;
    }

    @Override
    public List<Solution> getNeighbours() {
	if (neighbours == null) {
	    generateNeighbours();
	}
	return neighbours;
    }

    @Override
    public Long getValue() {
	Long value = 0L;

	for (Box box : getBoxes()) {
	    value += box.getVolume();
	}

	return value;
    }

    private void generateNeighbours() {
	neighbours = new ArrayList<Solution>();
	for (int i = 0; i < boxesInContainer.size() - 1; i++) {
	    for (int j = 0; j < boxesOutside.size() - 1; j++) {
		if (neighbours.size() < 100) {
		    if (!boxesInContainer.get(i).equals(boxesOutside.get(j))) {
			List<Box> boxesIn = new ArrayList<Box>(boxesInContainer);
			List<Box> boxesOut = new ArrayList<Box>(boxesOutside);

			Box boxRemoved = boxesIn.remove(i);
			Box boxToBeInserted = boxesOut.remove(j);

			boxesIn.add(i, boxToBeInserted);
			boxesOut.add(j, boxRemoved);

			container.clear();
			if (isSolutionVaid(boxesIn)) {
			    neighbours.add(new GraspSolution(boxesIn, boxesOut, container));
			}
		    }
		}
	    }
	}
    }

    private Boolean isSolutionVaid(List<Box> solution) {

	for (Box box : solution) {
	    if (container.fitsIn(box) == null)
		return false;

	    container.insertBox(box);
	}

	return true;

    }

    @Override
    public List<Box> getBoxes() {
	return boxesInContainer;
    }

    @Override
    public Container getContainer() {
	return container;
    }
}