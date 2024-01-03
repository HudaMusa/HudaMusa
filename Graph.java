import java.util.ArrayList;
import java.util.*;
import java.io.*;
public class Graph {
	private int n; //# of nodes
	private int m; //# of arcs
	private ArrayList<Node> node; //ArrayList/ array of nodes
	private boolean[][] A; //adjaceny matrix
	private double[][] C; //cost matrix
	Node content = new Node();
	
	//constructors
	public Graph () {
		this.init(0);
	}
	
	public Graph(int n) {
		this.init(n);
	}
	
	//setters
	public void setN(int n) {
		this.n = n;
	}
	public void setM(int m) {
		this.m = m;
	}
	public void setArc (int i, int j, boolean b) {
		this.A[i][j] = b;
		
	}
	public void setCost(int i, int j, double c) {
		this.C[i][j] = c;
		this.C[j][i] = c;
	}
	
	//getters
	public int getN() {
		return n;
	}
	public int getM() {
		return m;
	}
	public boolean getArc(int i, int j) {
		return A[i][j];
	}
	public double getCost(int i, int j) {
		return C[i][j];
	}
	public Node getNode(int i) {
		return node.get(i);
	}
	
	public void init(int n) //initialize values and arrays 
	{
		setN(n);
		setM(0);
		node = new ArrayList<Node>(n);
		A = new boolean[n][n];
		C = new double[n][n];
		
	}
	public void reset() //reset the graph
	{
		this.init(0);
	}
	 //check if arc exists
	public boolean existsArc(int i, int j) //check if arc exists
	{
		boolean exists = false;
		if (this.getArc(i,j)) { //A[i][J]
			exists = true;
		}return exists;
	}
	public boolean existsNode(Node t) // check if node exists
	{
		Boolean exists = false;
		if (node.contains(t)) {
			exists = true;
		}
		return exists;
	}
	public boolean addArc(int i, int j) // add an arc, return T/F if successful
	{
		
		A[i][j] = false;
		C[i][j] = 0.0;
		A[i][j] = true;
		A[j][i] = true;
		C[i][j] = Node.distance(node.get(i), node.get(j));
		C[j][i] = Node.distance(node.get(i), node.get(j));
		m++;
		return true;
	    }
		//add a one in two place
	public void removeArc(int k) {
	    int counter = 0;
	    
	    for (int i = 0; i < node.size(); i++) {
	        for (int j = i + 1; j < node.size(); j++) {
	            if (A[i][j] == true) {
	                counter += 1;
	                if (counter == k) {
	                    A[i][j] = false;
	                    A[j][i] = false;
	                    C[i][j] = 0;
	                    C[j][i] = 0; // You should reset C[j][i] as well if needed
	                    m--;
	                    return; // Exit the loop since you found and removed the arc
	                }
	            }
	        }
	    }
	}
	public boolean addNode(Node t) {
	    // Check for duplicate city names or coordinates
	    for (int i = 0; i < node.size(); i++) {
	        while (node.get(i).getName().equals(t.getName()) || (node.get(i).getLat() == t.getLat() && node.get(i).getLon() == t.getLon())) {
	            System.out.println("ERROR: City name and/or coordinates already exist!");
	            System.out.print("\nCity " + Pro3_musahuda.numOfCities + ":");
	            System.out.print("\n   Name: ");
	            try {
	                String name = Pro3_musahuda.cin.readLine();
		            double lat = Pro3_musahuda.getDouble("   latitude: ", -90, 90);
		            double lon = Pro3_musahuda.getDouble("   longitude: ", -180, 180);
		            t = new Node(name, lat, lon);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }

	            // Exit the loop when a duplicate name is found
	            // Exit the loop when duplicate coordinates are found
	        }
	    }
	    node.add(t);
	    return true;
	}
	public void print() //print all graph info
	{
		System.out.println("Number of nodes: " + node.size()); 
		System.out.println("Number of arcs: " + m);
		System.out.println();
		printNodes();
		System.out.print("\n");
		printArcs();
	}
	public void printNodes() //print node list
	{
		System.out.print("NODE LIST\n"
				+ "No.               Name        Coordinates\n");
		System.out.print("-----------------------------------------\n");
		for (int i = 0; i < node.size(); i++) {
			System.out.printf("%3d",i+1);
			node.get(i).print();
	    }
	}
	public void printArcs() // print arc list
	{
	    System.out.print("ARC LIST\n" +
	            "No.    Cities       Distance\n");
	    System.out.print("----------------------------\n");

	    int counter = 0;

	    for (int i = 0; i < node.size(); i++) {
	        for (int j = i + 1; j < node.size(); j++) {
	            if (A[i][j] == true) {
	                counter += 1;
	                System.out.printf("%3d", counter);
	                String cities = String.format("%d-%d", (i + 1), (j + 1));
	                String distance = String.format("%.2f", C[i][j]);
	                System.out.printf("%10s%15s%n", cities, distance);
	                //System.out.println();
	            }
	        }
	    }
	}
	public boolean checkPath(int[] P) // check feasibility
	{
	    boolean[] visited = new boolean[n];

	    if (P[0] != P[P.length - 1]) {
	        System.out.print("\nERROR: Start and end cities must be the same!\n");
	        return false; // Exit the method if there's an error
	    }

	    for (int i = 0; i < P.length - 1; i++) {
	        int from = P[i] - 1;
	        int to = P[i + 1] - 1;

	        if (P[i] == P[i + 1]) {
	            System.out.print("\nERROR: City " + P[i] + " is visited more than once!\n");
	            return false; // Exit the method if there's an error
	        }

	        if (!existsArc(from, to)) {
	            System.out.print("\nERROR: Arc <" + P[i] + "-" + P[i + 1] + "> does not exist!\n");
	            return false; // Exit the method if there's an error
	        }

	        visited[from] = true;
	    }

	    for (int i = 0; i < n; i++) {
	        if (!visited[i]) {
	            System.out.print("\nERROR: City " + (i + 1) + " is not visited!\n");
	            return false; // Exit the method if there's an error
	        }
	    }

	    return true;
	}
	public double pathCost(int[] P) //calculate cost getter
	{
		double cost = 0.0;
	    for (int i = 0; i < P.length - 1; i++) {
	        int cityIndex1 = P[i] - 1;
	        int cityIndex2 = P[i + 1] - 1;    
	        cost += C[cityIndex1][cityIndex2];
	    }
	    return cost;
}
}