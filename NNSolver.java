import java.util.ArrayList;
import java.util.*;
public class NNSolver extends TSPSolver {

//constructors
public NNSolver() {
	super();
	this.setName("NN");
}
public NNSolver(ArrayList<Graph> G) {
    super(G);
    this.init(G);
    this.setName("NN");
}
@Override
//Overrides value of nextNode and replaces it. It returns the best node based on the last visited as well as the index to insert at.
public int[] nextNode(Graph G, ArrayList <Integer > visited) {
	int [] nextNode = new int[2];
	int lastVisited;
    int nearestNeighbor;
    lastVisited = visited.get(visited.size() - 1);
	nearestNeighbor = nearestNeighbor(G, visited, lastVisited);
	//System.out.print("Visited: " + visited + " lastVisited: " + lastVisited);
	nextNode[0] = nearestNeighbor;
	nextNode[1] = visited.size();
	return nextNode;
}
}
