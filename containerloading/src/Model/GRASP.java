package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * 
 * @author Matheus Abegg
 */
public class GRASP {

    private static final int MAX_ITERACOES = 50;

    private List<Box> boxes;
    private Container container;

    private Random randomizer;

    private Float ALPHA;

    public GRASP(Container container, List<Box> boxes, Integer boxTypes, Long seed) {
	this.container = container;
	this.boxes = boxes;
	this.randomizer = new Random(seed);

	if (boxTypes > 1) {
	    ALPHA = 1.5F / (float) boxTypes;
	} else
	    ALPHA = 1F;
    }

    public Solution solve() {
	Solution solution = new GraspSolution(container);

	for (int i = 0; i < MAX_ITERACOES; i++) {
	    container.clear();
	    Solution initalSolution = greedyRandomized(ALPHA);

	    Solution localMaximum = localSearch(initalSolution);

	    System.out.println(String.format("GRASP: Melhor:%s Atual:%s", solution.getValue(), localMaximum.getValue()));
	    if (localMaximum.getValue() > solution.getValue())
		solution = localMaximum;
	}
	return solution;
    }

    private Solution localSearch(Solution solution) {
	Solution bestSolution = solution;
	List<Solution> neighbours = solution.getNeighbours();
	for (Solution neighbour : neighbours) {
	    System.out.println(String.format("LocaSearch: Melhor:%s Atual:%s", bestSolution.getValue(), neighbour.getValue()));
	    if (neighbour.getValue() > bestSolution.getValue()) {
		bestSolution = neighbour;
	    }
	}

	return bestSolution;
    }

    private Solution greedyRandomized(Float alpha) {
	Solution solution = new GraspSolution(new ArrayList<Box>(), new ArrayList<Box>(boxes), container);
	List<Box> iteratableBoxList = new ArrayList<Box>(boxes);

	while (!iteratableBoxList.isEmpty()) {
	    List<Box> candidates = getCadidatesList(iteratableBoxList, alpha);
	    Box cadidateBox = candidates.get(0);
	    Box boxInContainer = container.fitsIn(cadidateBox);
	    if (boxInContainer != null) {
		solution.getBoxes().add(boxInContainer);
		container.insertBox(boxInContainer);
	    }

	    iteratableBoxList.remove(cadidateBox);
	}

	return solution;
    }

    private List<Box> getCadidatesList(List<Box> boxes, Float alpha) {
	Collections.sort(boxes, boxComparator);
	Integer limit = (int) Math.floor(boxes.size() * alpha);

	if (limit > 0) {
	    List<Box> subList = boxes.subList(0, limit > 0 ? limit : 0);
	    Collections.shuffle(subList, randomizer);

	    return subList;
	} else
	    return boxes;
    }

    private Comparator<Box> boxComparator = new Comparator<Box>() {
	@Override
	public int compare(Box o1, Box o2) {
	    if (o1.getVolume() > o2.getVolume())
		return 1;
	    else if (o1.getVolume() < o2.getVolume())
		return -1;
	    else
		return 0;
	}
    };
}