import java.io.BufferedReader;
import java.io.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.FileReader;
import java.io.FileNotFoundException;
public class Pro4_musahuda {
	public static BufferedReader cin = new BufferedReader ( new InputStreamReader ( System . in ) );
	//public static B
	public static boolean valid;
	public static int graphNum;
	public static void main( String[] args) throws NumberFormatException, IOException {
		Graph g = new Graph();
		Node node = new Node();
		ArrayList<Graph> G = new ArrayList<>();
		TSPSolver solver = new TSPSolver(G);
		String choice;
	do {
		
		//Presents user with menu options by calling void function displayMenu
		
		//initializes choice to the value returned by getInteger function
		choice = userChoice("Enter choice: ");
		if (G.size() == 0 && !(choice.equals("L")||(choice.equals("Q")))) {
			System.out.println("\nERROR: No graph has been loaded!");
		}else {
		
			if (choice.equals("L")) {
				boolean fileLoaded = loadFile(G);
				//check if file is loaded
				solver.init(G);
			    if (!fileLoaded) {
			    	System.out.println("\nERROR: File not found!");
			    }
			}
		if (choice.equals("I")) {
			displayGraphs(G);
			
		}
		if (choice.equals("C")) {
			G = new ArrayList<>(0);
			solver.reset();
			System.out.println("\nAll graphs cleared.");
		}
		if (choice.equals("R")) {
			System.out.println();
			for (int i = 0; i<G.size(); i++) {
				solver.run(G,i);
			}
			System.out.println("\nAll graphs done.");
		}
		if (choice.equals("D")) {
			System.out.println();
			solver.printAll();
			System.out.println();
			solver.printStats();
		}
		}
		//}

}while (!(choice.equals("Q")));

	}

public static void displayMenu() {
	System.out.println("\n   JAVA TRAVELING SALESMAN PROBLEM V2");
	System.out.println("L - Load graphs from file");
	System.out.println("I - Display graph info");
	System.out.println("C - Clear all graphs");
	System.out.println("R - Run nearest neighbor algorithm");
	System.out.println("D - Display algorithm performance");
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
		}else {
			valid = false;
			System.out.print("\nERROR: Invalid menu choice!");
			System.out.println();
		}
	}while (valid==false);
	
	return choice;
	
}
//create a function that checks if file exists
@SuppressWarnings("resource")
public static boolean loadFile(ArrayList<Graph> G) throws IOException {
    String filename;
    BufferedReader fin;
    int counter = 0;

    try {
        System.out.print("\nEnter file name (0 to cancel): ");
        filename = cin.readLine();
        fin = new BufferedReader(new FileReader(filename));

        if (filename.equals("0")) {
            System.out.println("File loading process canceled.");
            return false;
        }

    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }

    String line1;
    int loadedGraphs = 0;
    while ((line1 = fin.readLine()) != null) {
        boolean line1isNum;

        try {
            int num = Integer.parseInt(line1);
            line1isNum = true;
        } catch (NumberFormatException e) {
            line1isNum = false;
        }

        if (line1isNum) {
            // # of cities or an arc connected to itself
            String line2 = fin.readLine();
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
                    String currentLine = fin.readLine();
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

    fin.close();
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
public static void displayGraphs(ArrayList<Graph> G) {
    TSPSolver solver = new TSPSolver(G);
    int LB = 0;
    int UB = G.size();
    solver.printGraphSummary(G);
    System.out.println();
    int graphNum;
    do {
        graphNum = getInteger("Enter graph to see details (0 to quit): ", LB, UB);
        System.out.println();
        // Get the selected graph from the list
        if (graphNum != 0) {
            G.get(graphNum - 1).print();
        }
        System.out.println();
        solver.printGraphSummary(G);
        System.out.println();
    } while (graphNum != 0);
}
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

