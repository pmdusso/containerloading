package br.ufrgs.inf.containerloading.grasp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import br.ufrgs.inf.containerloading.entities.Box;
import br.ufrgs.inf.containerloading.entities.Container;
import br.ufrgs.inf.containerloading.entities.Solution;
import br.ufrgs.inf.containerloading.entities.Vector3d;

/**
 * 
 * @author Matheus Abegg
 */
public class GRASP {

    private List<Box> boxes;
    private Container container;

    private Random randomizer;
    private Float alpha;

    public GRASP(Container container, List<Box> boxes, Integer boxTypes, Long seed) {
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

	Solution localMaximum = null;
	for (int i = 0; i < GraspParameters.NUMBER_OS_STARTS; i++) {
	    System.out.println("Iniciando loop " + i);
	    if (solution.getValue() != container.getVolume()) {
		container.clear();
		Solution initalSolution = greedyRandomized(alpha);

		localMaximum = localSearch(initalSolution);

		if (localMaximum.getValue() > solution.getValue())
		    solution = localMaximum;
	    }
	    System.out.println("Fim do loop " + i);
	    System.out.println("Solução corrente = " + (float) ((float) localMaximum.getValue() / (float) container.getVolume()));
	    System.out.println("Melhor = " + (float) ((float) solution.getValue() / (float) container.getVolume()));

	}
	return solution;
    }

    private Boolean isSolutionValid(List<Box> boxes, Container container) {
	container.clear();
	Vector3d lastPosition = new Vector3d(0, 0, 0);
	for (Box box : boxes) {
	    Box fitsIn = null;
	    if (GraspParameters.FULL_SEARCH)
		fitsIn = container.fitsIn(box);
	    else
		fitsIn = container.fitsIn(box, lastPosition);
	    if (fitsIn == null)
		return false;

	    container.insertBox(fitsIn);
	    lastPosition = fitsIn.getRelativeCoordenates();
	}

	return true;
    }

    private Solution localSearch(Solution solution) {
	Solution bestSolution = solution;
	Boolean hasChanged = true;
	while (hasChanged) {
	    hasChanged = false;
	    List<Solution> neighbours = bestSolution.getNeighbours();
	    for (Solution neighbour : neighbours) {
		if (neighbour.getValue() > bestSolution.getValue() && isSolutionValid(new ArrayList<Box>(neighbour.getBoxes()), this.container)) {
		    bestSolution = neighbour;
		    hasChanged = true;
		}
	    }
	}

	return bestSolution;
    }

    private Solution greedyRandomized(Float alpha) {
	Solution solution = new GraspSolution(new ArrayList<Box>(), new ArrayList<Box>(boxes), container);
	List<Box> iteratableBoxList = new ArrayList<Box>(boxes);
	Vector3d lastBoxInserted = new Vector3d(0, 0, 0);
	Box previousCandidate = null;

	while (!iteratableBoxList.isEmpty()) {
	    List<Box> candidates = getCadidatesList(iteratableBoxList, alpha);
	    Box candidateBox = candidates.get(0);
	    if (!candidateBox.equals(previousCandidate)) {
		Box boxInContainer = null;
		if (GraspParameters.FULL_SEARCH)
		    boxInContainer = container.fitsIn(candidateBox);
		else
		    boxInContainer = container.fitsIn(candidateBox, lastBoxInserted);
		if (boxInContainer != null) {
		    solution.addBox(boxInContainer);
		    container.insertBox(boxInContainer);
		    lastBoxInserted = boxInContainer.getRelativeCoordenates();
		    previousCandidate = null;
		} else {
		    previousCandidate = candidateBox;
		}
	    }

	    iteratableBoxList.remove(candidateBox);
	}
	return solution;
    }

    private List<Box> getCadidatesList(List<Box> boxes, Float alpha) {
	Collections.sort(boxes);
	Integer limit = (int) Math.floor(boxes.size() * alpha);

	if (limit > 0) {
	    List<Box> subList = boxes.subList(0, limit > 0 ? limit : 0);
	    Collections.shuffle(subList, randomizer);

	    return subList;
	} else
	    return boxes;
    }
}