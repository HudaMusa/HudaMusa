import java.util.ArrayList;
import java.util.*;
public class NISolver extends TSPSolver {
	
//constructors
public NISolver() {
	super();
	this.setName("NI");
}
public NISolver(ArrayList<Graph> G) {
    super(G);
    this.init(G);
    this.setName("NI");
}
@Override
//Overrides value of nextNode and replaces it. It returns the best node of all the first visited, the last visited and the middle visited as well as the index to insert at.
public int[] nextNode(Graph G, ArrayList <Integer > visited) {
	int [] nextNode = new int[2];
	
	int lastVisited;
	int firstVisited;
	
    int nearestNeighborFirst = -1;
    int nearestNeighborLast = -1;
    int nearestNeighborMiddle = -1; 
    
    int nodePosition = -1;
    
    double firstCost = Double.MAX_VALUE;
    double lastCost = Double.MAX_VALUE;
    double minCost = Double.MAX_VALUE;
    
    firstVisited = visited.get(0);
    lastVisited = visited.get(visited.size() - 1);
    
    nearestNeighborFirst = nearestNeighbor(G, visited, firstVisited);
	nearestNeighborLast = nearestNeighbor(G, visited, lastVisited);
	
	if (nearestNeighborFirst != -1) {
		firstCost = G.getCost(firstVisited-1,nearestNeighborFirst-1);
	}
	if (nearestNeighborLast != -1) {
		lastCost = G.getCost(lastVisited-1,nearestNeighborLast-1);	
	}
	
	for (int i = 1; i <= visited.size()-2; i++) {
		int nearestNeighbor = nearestNeighbor2(G,visited,visited.get(i));
		if (nearestNeighbor != -1) {
		double cost = G.getCost((visited.get(i))-1, nearestNeighbor-1);
			if (cost<minCost) {
				minCost = cost;
				nodePosition = i+1;
				nearestNeighborMiddle = nearestNeighbor;
			}
		}
	}
	if (firstCost == Double.MAX_VALUE && lastCost == Double.MAX_VALUE && minCost == Double.MAX_VALUE) {
		nextNode[0] = -1;
		nextNode[1] = -1;
	} 
	else if (firstCost <= lastCost && firstCost <= minCost) {
		nextNode[0] = nearestNeighborFirst;
		nextNode[1] = 0;
	}
	else if (lastCost <= firstCost && lastCost <= minCost) {
		nextNode[0] = nearestNeighborLast;
		nextNode[1] = visited.size();
	}
	else {
		nextNode[0] = nearestNeighborMiddle;
		nextNode[1] = nodePosition;	
	}

	return nextNode;
}

//check if node k can be inserted at position i
public boolean canBeInserted(Graph G, ArrayList <Integer > visited , int i, int k) {
	return G.existsArc(visited.get(i)-1, k-1);
}
//version of nearestNeighbor but it checks if the node k can be inserted into the path
public int nearestNeighbor2(Graph G, ArrayList<Integer> visited, int k) { // Implementation for finding the nearest unvisited neighbor to the last visited node
	
    int nearestNeighbor = -1;
    double minimumDistance = Double.MAX_VALUE;
    double distance = 0.0;
    for (int i = 1; i <= G.getN(); i++) {
        if ((!visited.contains(i)) && (G.getArc(k-1,i-1)) && canBeInserted(G, visited, visited.indexOf(k)+1, i)) {
        	
        	distance = G.getCost(k-1, i-1);
        	
                if (distance < minimumDistance) {
                    minimumDistance = distance;
                    nearestNeighbor = i;
                }	 
                
        }
        }

    return nearestNeighbor;
}
}
