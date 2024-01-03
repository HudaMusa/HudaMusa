import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.io.*;
public class Pro5_musahuda {
	public static BufferedReader cin = new BufferedReader ( new InputStreamReader ( System . in ) );
	public static boolean valid;
	public static int graphNum;
	public static void main( String[] args) throws NumberFormatException, IOException {
		Graph g = new Graph();
		Node node = new Node();
		ArrayList<Graph> G = new ArrayList<>();
		TSPSolver solver = new TSPSolver();
		NNSolver NN = new NNSolver();
		NNFLSolver NNFL = new NNFLSolver();
		NISolver NI = new NISolver();
		String choice;
	do {
		
		//Presents user with menu options by calling void function displayMenu
		
		//initializes choice to the value returned by getInteger function
		choice = userChoice("Enter choice: ");
		if (G.size() == 0 && !(choice.equals("L")||(choice.equals("Q")))) {
			if (choice.equals("R") || choice.equals("I") || choice.equals("C")) {
			System.out.println("\nERROR: No graphs have been loaded!");
			}else {
				System.out.println("\nERROR: Results do not exist for all algorithms!");
			}
		}else {
			if (choice.equals("L")) {
				loadFile(G);
			}
		if (choice.equals("I")) {
			System.out.println();
			displayGraphs(G);
		}
		if (choice.equals("C")) {
			G = new ArrayList<>(0);
			resetAll(NN,NNFL,NI);
			solver.reset();
			g.reset();
			
			
			System.out.println(""
					+ "\nAll graphs cleared.");
		}
		
		if (choice.equals("R")) {
				NN.init(G);
				NNFL.init(G);
				NI.init(G);
				runAll(G,NN,NNFL,NI);
			
		}
		if (choice.equals("D")) {
			printAll(NN, NNFL, NI);
		}
		if (choice.equals("X")) {
			compare(NN,NNFL,NI);
		}
		}
		//}

}while (!(choice.equals("Q")));
	System.out.print("\nCiao!");
	}
	public static void displayMenu() { //Display the menu.
		System.out.println("\n   JAVA TRAVELING SALESMAN PROBLEM V3");
		System.out.println("L - Load graphs from file");
		System.out.println("I - Display graph info");
		System.out.println("C - Clear all graphs");
		System.out.println("R - Run all algorithms");
		System.out.println("D - Display algorithm performance");
		System.out.println("X - Compare average algorithm performance");
		System.out.println("Q - Quit");
	}

	public static String userChoice(String prompt) {
		String choice = "S";
		do {
			valid = true;
			try{
				
				displayMenu();
				 System.out.print("\n"+ prompt);
				 choice  = cin.readLine().toUpperCase();
				 
					}
			catch (NumberFormatException e) {
				System.out.print("\nERROR: Invalid menu choice!\n");
				valid  = false;
			}
			catch (IOException e) 
			{
				System.out.print("\nERROR: Invalid menu choice!\n");
				valid  = false;
			}
			if (choice.equals("L")) {
				valid = true;
			}else if (choice.equals("I")) {
				valid = true;
			}else if (choice.equals("C")) {
				valid = true;
			}else if (choice.equals("R")) {
				valid = true;
			}else if (choice.equals("D")) {
				valid = true;
			}else if (choice.equals("Q")) {
				valid = true;
			}else if (choice.equals("X")) {
				valid = true;
			}else {
				valid = false;
				System.out.print("\nERROR: Invalid menu choice!");
				System.out.println();
			}
		}while (valid==false);
		
		return choice;
		
	}
	
	 //Read in graphs from a user-specified file.
	@SuppressWarnings("resource")
	public static boolean loadFile(ArrayList<Graph> G) throws IOException {
	    String filename;
	    int counter = 0;

	    try {
	        System.out.print("\nEnter file name (0 to cancel): ");
	        filename = cin.readLine();

	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	    if (filename.equals("0")) {
            System.out.print("\nFile loading process canceled.\n");
            return false;
        }
	    File TSPFile = new File(filename);
        if (!(TSPFile.exists())){
        	System.out.print("\nERROR: File not found!\n");
        	return false;
        }
	    
	    BufferedReader TSPBuff = new BufferedReader(new FileReader(TSPFile));

	    String line1;
	    int loadedGraphs = 0;
	    while ((line1 = TSPBuff.readLine()) != null) {
	        boolean line1isNum;

	        try {
	            int num = Integer.parseInt(line1);
	            line1isNum = true;
	        } catch (NumberFormatException e) {
	            line1isNum = false;
	        } 

	        if (line1isNum) {
	            // # of cities or an arc connected to itself
	            String line2 = TSPBuff.readLine();
	            String[] contents = line2.split(",");
	            boolean line2isNum;

	            try {
	                int num2 = Integer.parseInt(contents[0]);
	                line2isNum = true;
	            } catch (NumberFormatException e) {
	                line2isNum = false;
	            }

	            if (!line2isNum) {
	                int numNodes = Integer.parseInt(line1);
	                Graph graph = new Graph(numNodes);
	                graph.init(numNodes);
	                counter = 0;

	                String[] firstCityInfo = line2.split(",");
	                String cityName = firstCityInfo[0].trim();
	                double lat = Double.parseDouble(firstCityInfo[1]);
	                double lon = Double.parseDouble(firstCityInfo[2]);
	                if (!isValidLatitude(lat) || !isValidLongitude(lon)) {
	                    return false;
	                }
	                graph.addNode(new Node(cityName, lat, lon));

	                for (int i = 1; i < (1 + (numNodes) + (numNodes - 1) - 1); i++) {
	                    String currentLine = TSPBuff.readLine();
	                    String[] lineInfo = currentLine.split(",");
	                    boolean isCityInfo;

	                    try {
	                        int num_ = Integer.parseInt(lineInfo[0]);
	                        isCityInfo = false;
	                    } catch (NumberFormatException e) {
	                        isCityInfo = true;
	                    }

	                    if (isCityInfo) {
	                        String name = lineInfo[0];
	                        double latitude = Double.parseDouble(lineInfo[1]);
	                        double longitude = Double.parseDouble(lineInfo[2]);
	                        
	                        Node node = new Node();
	                        node.setName(name);
	                        node.setLat(latitude);
	                        node.setLon(longitude);
	                        if (!isValidLatitude(lat) || !isValidLongitude(lon) || graph.existsNode(node)) {
	                            return false;
	                        }
	          
	                        
	                        graph.addNode((node));
	                        
	                        
	                    }					
	                    else {
							int [] arcs = new int[lineInfo.length];
							for (int j = 0; j < lineInfo.length; j++) {
								arcs[j] = Integer.parseInt(lineInfo[j]);
							}
							//add the arc(s) to the graph 
								for (int k = 0; k < arcs.length; k++) {
									if (!(graph.existsArc(counter, arcs[k]-1))) {
									graph.addArc(counter, arcs[k]-1);
									
								}
				
							}
								counter++;
	             
	                }
	                }

	                G.add(graph);
	                loadedGraphs++;
	            }
	        }
	    }

	    TSPBuff.close();
	    System.out.println();
	    System.out.println(loadedGraphs + " of " + loadedGraphs + " graphs loaded!");

	    return true;
	}

	//Checks if latitude is within bounds
	public static boolean isValidLatitude(double lat) {
	    return lat >= -90 && lat <= 90;
	}

	// Checks if longitude is within bounds
	public static boolean isValidLongitude(double lon) {
	    return lon >= -180 && lon <= 180;
	}
	
	public static void displayGraphs(ArrayList<Graph> G) { //Display summary info for each graph, and allow user to select graphs to see detailed info.
	    TSPSolver solver = new TSPSolver(G);
	    int LB = 0;
	    int UB = G.size();
	    solver.printGraphSummary(G);
	    System.out.println();
	    int graphNum;
	    do {
	        graphNum = getInteger("Enter graph to see details (0 to quit): ", LB, UB);
	        //System.out.println();
	        // Get the selected graph from the list
	        if (graphNum == 0) {
	        	return;
	        }
	        System.out.println();
	        if (graphNum != 0) {
	            G.get(graphNum - 1).print();
	        }
	        System.out.println();
	        solver.printGraphSummary(G);
	        System.out.println();
	    } while (graphNum != 0);
	}
	
	public static void resetAll(NNSolver NN, NNFLSolver FL, NISolver NI) { //Reset all solvers.
		NN.reset();
		FL.reset();
		NI.reset();
	}
	public static void runAll(ArrayList<Graph> G, NNSolver NN, NNFLSolver FL, NISolver NI) {
	        for (int i = 0; i < G.size(); i++) {
	            // Run NN
	            NN.run(G, i, false);
	            
	        }
	        System.out.println("\nNearest neighbor algorithm done.");
	        
	        for (int i = 0; i < G.size(); i++) {
	            // Run NNFL
	            FL.run(G, i, false);
	            
	        }
	        System.out.println("\nNearest neighbor first-last algorithm done.");
	       
	        for (int i = 0; i < G.size(); i++) {
	            // Run NI
	            NI.run(G, i, false);
	            
	        }
	        System.out.println("\nNode insertion algorithm done.");
	}
	
	public static void printAll(NNSolver NN, NNFLSolver FL, NISolver NI) { //Print the detailed results and statistics summaries for all algorithms.
		System.out.println();
		System.out.println("Detailed results for nearest neighbor:");
		NN.printAll();
		System.out.println();
		System.out.println("Statistical summary for nearest neighbor:");
		NN.printStats();
		System.out.println();
		System.out.println();
		System.out.println("Detailed results for nearest neighbor first-last:");
		FL.printAll();
		System.out.println();
		System.out.println("Statistical summary for nearest neighbor first-last:");
		FL.printStats();
	    System.out.println();
		System.out.println();
	    System.out.println("Detailed results for node insertion:");
	    NI.printAll();
	    System.out.println();
	    System.out.println("Statistical summary for node insertion:");
	    NI.printStats();
	}
	public static void compare(NNSolver NN, NNFLSolver FL, NISolver NI) {
		System.out.println();
	    double minCost = Double.MAX_VALUE;
	    double minTime = Double.MAX_VALUE;
	    double maxSuccessRate = Double.MIN_VALUE;

	    TSPSolver[] algorithms = {NN, FL, NI};

	    String[] algorithmNames = {"NN", "NN-FL", "NI"};
	    
	    String bestCostAlg = "";
	    String bestTimeAlg = "";
	    String bestSuccessAlg = "";
	    
	    String overallWinner;
	    
	    int counterCost = 0;
	    int counterTime = 0;
	    int counterSuccess = 0;

	    System.out.println("------------------------------------------------------------\n"
	            + "           Cost (km)     Comp time (ms)     Success rate (%)\n"
	            + "------------------------------------------------------------");

	    System.out.printf("NN%18.2f%19.3f%21.1f\n", NN.avgCost(), NN.avgTime(), NN.successRate());
	    System.out.printf("NN-FL%15.2f%19.3f%21.1f\n", FL.avgCost(), FL.avgTime(), FL.successRate());
	    System.out.printf("NI%18.2f%19.3f%21.1f\n", NI.avgCost(), NI.avgTime(), NI.successRate());
	    
	    for (int i = 0; i < algorithms.length; i++) {
	        TSPSolver algorithm = algorithms[i];
	        double avgCost = algorithm.avgCost();
	        double avgTime = algorithm.avgTime();
	        double successRate = algorithm.successRate();

	        // Update minimum values for cost and time, and maximum for success rate
	        if (avgCost < minCost) {
	            minCost = avgCost;
	            bestCostAlg = algorithmNames[i];
	        }

	        if (avgTime < minTime) {
	            minTime = avgTime;
	            bestTimeAlg = algorithmNames[i];
	        }

	        if (successRate > maxSuccessRate) {
	            maxSuccessRate = successRate;
	            bestSuccessAlg = algorithmNames[i];
	        }
	    }
	    
	    System.out.println("------------------------------------------------------------");

	    System.out.printf("Winner%14s%19s%21s\n", bestCostAlg, bestTimeAlg, bestSuccessAlg);
	    
	    System.out.println("------------------------------------------------------------");
	    
	    if ((NN.avgCost() < FL.avgCost() && NN.avgCost() < NI.avgCost()) && (NN.avgTime() < FL.avgTime() && NN.avgTime() < NI.avgTime()) && (NN.successRate() > FL.successRate() && NN.successRate() > NI.successRate())) {
	    	overallWinner = "NN";
	    }else if ((FL.avgCost() < NN.avgCost() && FL.avgCost() < NI.avgCost()) && (FL.avgTime() < NN.avgTime() && FL.avgTime() < NI.avgTime()) && (FL.successRate() > NN.successRate() && FL.successRate() > NI.successRate())) {
	    	overallWinner = "FL";
	    }else if ((NI.avgCost() < NN.avgCost() && NI.avgCost() < FL.avgCost()) && (NI.avgTime() < NN.avgTime() && NI.avgTime() < FL.avgTime()) && (NI.successRate() > NN.successRate() && NI.successRate() > FL.successRate())){
	    	overallWinner = "NI";
	    }else {
	    	overallWinner = "Unclear";
	    }
	    
	    System.out.println("Overall winner: " + overallWinner);
	    
	}

	//Get an integer in the range [LB, UB] from the user. Prompt the user repeatedly until a valid value is entered.
	public static int getInteger(String prompt, int LB, int UB) {
		 //declares and initializes choice and valid
		 int choice = 0;
		 boolean valid = true;
		 do
		{
			//resets the boolean value of valid to be true
			valid = true;
			//takes user input
			try{
		 System.out.print(prompt);
		 choice  = Integer.parseInt(cin.readLine());
			}
			//catch for inputs that are not of integer type
			catch (NumberFormatException e)
			{
				if (UB == Integer.MAX_VALUE) {
					System.out.println("ERROR: Input must be a real number in [" + LB + ", infinity" + "]!");
					valid  = false;
				}else {
				System.out.println("ERROR: Input must be a real number in [" + LB + ", " + UB + "]!");
				System.out.println();
				valid  = false;
				}
			}
			//catch for unexpected program crashes
			catch (IOException e) 
			{
				if (UB == Integer.MAX_VALUE) {
					System.out.println("ERROR: Input must be a real number in [" + LB + ", infinity" + "]!");
					valid  = false;
				}else {
				System.out.println("ERROR: Input must be a real number in [" + LB + ", " + UB + "]!");
				System.out.println();
				valid  = false;
				}
			}
			//if statement for if the user's input is out of bounds
			if(valid && (LB>choice || choice>UB))
		 {
				if (UB == Integer.MAX_VALUE) {
					System.out.println("ERROR: Input must be a real number in [" + LB + ", infinity" + "]!");
					valid  = false;
				}else {
				System.out.println("ERROR: Input must be a real number in [" + LB + ", " + UB + "]!");
				System.out.println();
				valid  = false;
				}
		 }
			//while condition is false it will keep looping until the user inputs a valid response
		}while(valid!=true);
		 //returns valid user choice
		 return choice;
	}
	
	//Get a real number in the range [LB, UB] from the user. Prompt the user repeatedly until a valid value is entered.

	public static double getDouble(String prompt, double LB, double UB) {
		//declares and initializes value and valid
		double value = 0;
		boolean valid = true;
		do
		{
			//resets the boolean value of valid to be true
			valid = true;
			//takes user input
			try{
		System.out.print(prompt);
		value = Double.parseDouble(cin.readLine());
		}
			//catch for inputs that are not of integer type
			catch (NumberFormatException e)
			{
				System.out.format("ERROR: Input must be a real number in [%.2f" + ", %.2f]!\n", LB, UB);
				System.out.println();
				valid  = false;
			}
			//catch for unexpected program crashes
			catch (IOException e) 
			{
				System.out.format("ERROR: Input must be a real number in [%.2f" + ", %.2f]!\n", LB, UB);
				System.out.println();
				valid  = false;
			}
			//if statement for if the user's input is out of bounds
			if(valid && (value<LB || value>UB))
		 {
				System.out.format("ERROR: Input must be a real number in [%.2f" + ", %.2f]!\n", LB, UB);
			System.out.println();
			valid = false;
		 }
			//while condition is false it will keep looping until the user inputs a valid response
		}while(valid!=true);
		//returns valid user input for value
		 return value;
	 }

	}

