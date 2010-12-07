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

    public static final int MAX = 10;

    private List<Box> boxes;
    private Container container;

    private Random randomizer;

    private Float alpha;

    public GRASP(Container container, List<Box> boxes, Integer boxTypes, Long seed, Float alpha) {
	this.container = container;
	this.boxes = boxes;
	this.randomizer = new Random(seed);
	Float random = 2F / boxTypes;
	if (random >= 0.2F)
	    this.alpha = random;
	else
	    this.alpha = 0.2F;
    }

    public Solution solve() {
	Solution solution = new GraspSolution(container);

	for (int i = 0; i < MAX; i++) {
	    if (solution.getValue() != container.getVolume()) {
		// System.out.println("Itreação corrente do grasp = " + i);
		container.clear();
		Solution initalSolution = greedyRandomized(alpha);

		Solution localMaximum = localSearch(initalSolution);

		// System.out.println(String.format("GRASP: Melhor:%s Atual:%s",
		// solution.getValue(), localMaximum.getValue()));
		if (localMaximum.getValue() > solution.getValue())
		    solution = localMaximum;
	    }
	}
	return solution;
    }

    public static Boolean isSolutionValid(List<Box> boxes, Container container) {
	// System.out.println("isValidCalled -------------------");
	container.clear();
	Vector3d lastPosition = new Vector3d(0, 0, 0);
	for (Box box : boxes) {
	    Box fitsIn = container.fitsIn(box, lastPosition);
	    if (fitsIn == null)
		return false;

	    container.insertBox(fitsIn);
	    lastPosition = fitsIn.relativeCoordenates;
	}

	return true;
    }

    private Solution localSearch(Solution solution) {
	Solution bestSolution = solution;
	boolean hasChanged = true;
	while (hasChanged) {
	    hasChanged = false;
	    List<Solution> neighbours = bestSolution.getNeighbours();
	    for (Solution neighbour : neighbours) {
		// System.out.println(String.format("LocaSearch: Melhor:%s Atual:%s",
		// bestSolution.getValue(), neighbour.getValue()));
		if (neighbour.getValue() > bestSolution.getValue() && GRASP.isSolutionValid(new ArrayList<Box>(neighbour.getBoxes()), this.container)) {
		    bestSolution = neighbour;
		    hasChanged = true;
		}
	    }
	}

	return bestSolution;
    }

    private Solution greedyRandomized(Float alpha) {
	// System.out.println("Gerando Greedy");
	Solution solution = new GraspSolution(new ArrayList<Box>(), new ArrayList<Box>(boxes), container);
	List<Box> iteratableBoxList = new ArrayList<Box>(boxes);
	Vector3d lastBoxInserted = new Vector3d(0, 0, 0);
	Box previousCandidate = null;

	while (!iteratableBoxList.isEmpty()) {
	    List<Box> candidates = getCadidatesList(iteratableBoxList, alpha);
	    Box candidateBox = candidates.get(0);
	    if (!candidateBox.equals(previousCandidate)) {
		Box boxInContainer = container.fitsIn(candidateBox, lastBoxInserted);
		if (boxInContainer != null) {
		    solution.addBox(boxInContainer);
		    container.insertBox(boxInContainer);
		    lastBoxInserted = boxInContainer.relativeCoordenates;
		    previousCandidate = null;
		} else {
		    previousCandidate = candidateBox;
		}
	    }

	    iteratableBoxList.remove(candidateBox);
	}
	// System.out.println("GreedyGerado");
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
	    return o1.getVolume().compareTo(o2.getVolume());
	}
    };
}