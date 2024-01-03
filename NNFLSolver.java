import java.util.ArrayList;
import java.util.*;
public class NNFLSolver extends TSPSolver {

//constructors
public NNFLSolver() {
	super();
	this.setName("NNFL");
}
public NNFLSolver(ArrayList<Graph> G) {
    super(G);
    this.init(G);
    this.setName("NNFL");
}
@Override
//Overrides value of nextNode and replaces it. It returns the best node of all the first visited, and the last visited as well as the index to insert at.
public int[] nextNode(Graph G, ArrayList <Integer > visited) {
	int [] nextNode = new int[2];
	int lastVisited;
	int firstVisited;
    int nearestNeighborFirst;
    int nearestNeighborLast;
    lastVisited = visited.get(visited.size() - 1);
    firstVisited = visited.get(0);
    nearestNeighborFirst = nearestNeighbor(G, visited, firstVisited);
	nearestNeighborLast = nearestNeighbor(G, visited, lastVisited);
	if (nearestNeighborFirst !=-1 && nearestNeighborLast != -1) {
	if (G.getCost(firstVisited-1,nearestNeighborFirst-1)<G.getCost(lastVisited-1,nearestNeighborLast-1)) {
		nextNode[0] = nearestNeighborFirst;
		nextNode[1] = 0;
	}else{
		nextNode[0] = nearestNeighborLast;
		nextNode[1] = visited.size();
	}
	}else {
		nextNode[0] = -1;
		nextNode[1] = -1;
	}
	return nextNode;
}
}
