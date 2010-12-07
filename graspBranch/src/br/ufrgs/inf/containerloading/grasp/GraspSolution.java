package br.ufrgs.inf.containerloading.grasp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.ufrgs.inf.containerloading.entities.Box;
import br.ufrgs.inf.containerloading.entities.Container;
import br.ufrgs.inf.containerloading.entities.Solution;

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
	List<Box> boxesIn = new ArrayList<Box>(boxesInContainer);
	List<Box> boxesOut = new ArrayList<Box>(boxesOutside);
	neighbours = new ArrayList<Solution>();
	for (int i = 0; i < boxesIn.size() - 1; i++) {
	    for (int j = 0; j < boxesOut.size() - 1; j++) {
		boxesIn = new ArrayList<Box>(boxesInContainer);
		boxesOut = new ArrayList<Box>(boxesOutside);
		if (!boxesIn.get(i).equals(boxesOut.get(j))) {
		    Box boxRemoved = boxesIn.remove(i);
		    Box boxToBeInserted = boxesOut.remove(j);

		    boxesIn.add(i, boxToBeInserted);
		    boxesOut.add(j, boxRemoved);

		    GraspSolution newSolution = new GraspSolution(boxesIn, boxesOut, container);

		    if (newSolution.getValue() >= this.getValue() && neighbours.size() < GraspParameters.MAX_NEIGHBOURS_NUMBER)
			if (!neighbours.contains(newSolution)) {
			    neighbours.add(newSolution);
			}
		}
	    }
	}
	Collections.sort(neighbours);
    }

    @Override
    public List<Box> getBoxes() {
	return boxesInContainer;
    }

    @Override
    public Container getContainer() {
	return container;
    }

    @Override
    public boolean equals(Object obj) {
	if (obj instanceof GraspSolution) {
	    Solution ob = (Solution) obj;
	    return ob.getBoxes().equals(this.boxesInContainer);
	} else
	    return false;
    }

    @Override
    public void addBox(Box boxInContainer) {
	boxesInContainer.add(boxInContainer);
	boxesOutside.remove(boxInContainer);

    }

    @Override
    public int compareTo(Solution o) {
	return this.getValue().compareTo(o.getValue());
    }
}