import java.util.ArrayList;
import java.util.*;
public class TSPSolver {
private ArrayList <int[]> solnPath;
private double [] solnCost;
private double [] compTime;
private boolean [] solnFound;
private boolean resultsExist; //for display menu display algorithm performance
Graph graph = new Graph();
// constructors
public TSPSolver () {
	this.init(null);
	
}
public TSPSolver (ArrayList <Graph> G) {
	this.init(G);
	
}

//getters
public int[] getSolnPath (int i) {
	return solnPath.get(i);
}
public double getSolnCost (int i) {
	return solnCost[i];
}
public double getCompTime (int i) {
	return compTime[i];
}
public boolean getSolnFound (int i) {
	return solnFound[i];
}
public boolean hasresults() {
	return resultsExist;
}

//setters
public void setSolnPath (int i, int [] solnPath) {
	this.solnPath.set(i, solnPath);
}
public void setSolnCost (int i, double solnCost) {
	this.solnCost[i] = solnCost;
}
public void setCompTime (int i, double compTime) {
	this.compTime[i] = compTime;
}
public void setSolnFound (int i, boolean solnFound) {
	this.solnFound[i] = solnFound;
}
public void setHasResults (boolean b) {
	this.resultsExist = b;
}
public void init(ArrayList<Graph> G) { // Implementation for initialization
solnPath = new ArrayList <>(G.size());
solnCost = new double[G.size()];
compTime = new double[G.size()];
solnFound = new boolean[G.size()];
for (int i = 0; i < G.size(); i++) {
    solnPath.add(null); // Initialize each element to null initially
    solnCost[i] = 0.0;   // Initialize solnCost to 0.0
    compTime[i] = 0.0;   // Initialize compTime to 0.0
    solnFound[i] = false; // Initialize solnFound to false
}
}
public void printGraphSummary(ArrayList<Graph> G) {
    System.out.println("GRAPH SUMMARY");
    System.out.println("No.    # nodes    # arcs");
    System.out.println("------------------------");

    for (int i = 0; i < G.size(); i++) {
        Graph graph = G.get(i);

        if (graph != null) {
            System.out.printf("%3d%11d%11d%n", i + 1, graph.getN(), graph.getM());
        }
    }
}

public void reset() { // Implementation for resetting variables and arrays
	solnPath = null;
	solnCost = null;
	compTime = null;
	solnFound = null;
	resultsExist = false;
	graph = null;
}
public void run(ArrayList<Graph> G, int i) {
    Graph graph = G.get(i);
    ArrayList<Integer> visited = new ArrayList<>();
    visited.add(1); // Start from node 1
    int[] path = new int[graph.getN()+1];
    path[0] = 1;
    long start = System.currentTimeMillis();
    double elapsedTime;
    int nearestNeighbor=0;
    int j = 0;

    do {
        nearestNeighbor = nearestNeighbor(graph, visited);
        if (nearestNeighbor != -1) {
        visited.add(nearestNeighbor);
        path[j+1] = nearestNeighbor;
        j++;
        }
    }while(nearestNeighbor != -1 && j!=graph.getN()-1);
            // No unvisited neighbors, or no arc back to the starting node
    if (nearestNeighbor == -1) {
            System.out.println("ERROR: No TSP route found for Graph " + i + "!");
            return;
    }
    // Check if arc exists between the first and last node
    if (!(graph.existsArc(path[0], path[path.length - 1]))) {
    	System.out.println("ERROR: No TSP route found for Graph " + i + "!");
        return;
    } else {

    path[path.length - 1] = path[0];

    elapsedTime = (System.currentTimeMillis() - start); // Convert to seconds

    // Calculate the cost of the solution path
    solnCost[i] = graph.pathCost(path);

    // Set the results for this graph
    setSolnPath(i, path);
    setSolnCost(i, solnCost[i]);
    setCompTime(i, elapsedTime);
    setSolnFound(i, true);

    // Mark that results exist
    setHasResults(true);
    int graphNum = i+1;
    System.out.println("Graph " + graphNum + " done in " + elapsedTime + "ms.");
}
}

public int nearestNeighbor(Graph G, ArrayList<Integer> visited) { // Implementation for finding the nearest unvisited neighbor to the last visited node
	
	    int nearestNeighbor = -1;
	    double minimumDistance = Double.MAX_VALUE;
	    double distance = 0.0;
	    int lastVisited;
	    
	    lastVisited = visited.get(visited.size() - 1);

	    for (int i = 1; i <= G.getN(); i++) {
	        if ((!visited.contains(i)) && (G.getArc(lastVisited-1,i-1))) {
	        	
	        	distance = G.getCost(lastVisited-1, i-1);
	        	
	                if (distance < minimumDistance) {
	                    minimumDistance = distance;
	                    nearestNeighbor = i;
	                }	 
	                
	        }
	        }

	    return nearestNeighbor;
	}

public void printSingleResult(int i, boolean rowOnly) {
    if (rowOnly) {
        System.out.print("Detailed results:\n"
                + "----------------------------------------\n"
                + "No.   Cost (km)   Comp time (ms)   Route   \n"
                + "----------------------------------------\n"
                + "");
    }

    if (!solnFound[i]) {
        System.out.printf("%3d           -                -   -    ", i + 1); //change formatting for space
        System.out.println();
    } else {
        int[] path = solnPath.get(i);
        System.out.printf("%3d%12.2f%17.3f", i + 1, solnCost[i], compTime[i]);
        printPathWithDashes(path); // Call the modified method without passing 'graph'
        System.out.println();
    }
}
public void printPathWithDashes(int[] path) {
    System.out.printf("   ");
    int lastIndex = path.length - 1;
    
    for (int i = 0; i < path.length; i++) {
        System.out.print(path[i]);
        
        if (i < lastIndex) {
            System.out.print("-");
        }
    }
}

public void printAll() { // Implementation for printing results for all graphs
	boolean rowOnly = true;
	if(!hasresults()){
		System.out.println("ERROR: No results exist!");
        return;
	}
	for (int i = 0; i < solnPath.size(); i++) {
		printSingleResult(i,rowOnly);
	
			rowOnly = false;
	}
}

public void printStats() { // Implementation for printing statistics
    if (!hasresults()) {
        return;
    }

    double averageCost = 0.0;
    double averageComp = 0.0;
    double stDevCost = 0.0;
    double stDevComp = 0.0;
    double minCost = Double.MAX_VALUE;
    double minComp = Double.MAX_VALUE;
    double maxCost = 0.0;
    double maxComp = 0.0;
    double sumCost = 0.0;
    double sumComp = 0.0;
    double sum2Cost = 0.0;
    double sum2Comp = 0.0;
    double counter = 0.0;

    for (int i = 0; i < solnPath.size(); i++) {
        if (solnFound[i]) {
            sumCost += solnCost[i];
            sumComp += compTime[i];
            counter++;
            // Update min and max values
            minCost = Math.min(minCost, solnCost[i]);
            minComp = Math.min(minComp, compTime[i]);
            maxCost = Math.max(maxCost, solnCost[i]);
            maxComp = Math.max(maxComp, compTime[i]);
        }
    }

    if (counter > 1) { // Check if there's more than one valid result for standard deviation
        averageCost = sumCost / counter;
        averageComp = sumComp / counter;

        for (int i = 0; i < solnPath.size(); i++) {
            if (solnFound[i]) {
                double deviationCost = solnCost[i] - averageCost;
                double deviationComp = compTime[i] - averageComp;

                sum2Cost += Math.pow(deviationCost, 2);
                sum2Comp += Math.pow(deviationComp, 2);
            }
        }

        stDevCost = Math.sqrt(sum2Cost / (counter - 1));
        stDevComp = Math.sqrt(sum2Comp / (counter - 1));
    }
    //fix formatting for entire print statement
    
    System.out.print("Statistical summary:\n"
            + "---------------------------------------\n"
            + "           Cost (km)     Comp time (ms)\n"
            + "---------------------------------------\n"
            + "Average   " + String.format("%.2f", averageCost) + "              " + String.format("%.3f", averageComp) + "\n"
            + "St Dev     " + (Double.isNaN(stDevCost) ? "NaN" : String.format("%.2f", stDevCost)) + "              "
            + (Double.isNaN(stDevComp) ? "NaN" : String.format("%.3f", stDevComp)) + "\n"
            + "Min       " + (Double.isNaN(minCost) ? "NaN" : String.format("%.2f", minCost)) + "              "
            + (Double.isNaN(minComp) ? "NaN" : String.format("%.3f", minComp)) + "\n"
            + "Max       " + (Double.isNaN(maxCost) ? "NaN" : String.format("%.2f", maxCost)) + "              "
            + (Double.isNaN(maxComp) ? "NaN" : String.format("%.3f", maxComp)) + "\n");
    System.out.println("\nSuccess rate: " + String.format("%.2f", successRate()) + "%");
}
public double successRate() { // Implementation for calculating success rate
	double counter = 0.0;
	double successRate;
	for (int i = 0; i<solnPath.size();i++) {
		if (solnFound[i]==true) {
		counter++;

		}
	}
	successRate = (counter/solnPath.size())*100;
	return successRate;
}

}
