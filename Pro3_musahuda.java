import java.io.BufferedReader;
import java.io.*;
import java.io.IOException;
import java.io.InputStreamReader;
public class Pro3_musahuda {
	public static int numOfCities;
	public static BufferedReader cin = new BufferedReader ( new InputStreamReader ( System . in ) );
	public static double LB , UB;
	public static boolean valid;
	public static void main( String[] args) throws NumberFormatException, IOException {
		Node node = new Node();
		Graph graph  = new Graph(); //MIGHT HAVE TO MOVE ABOVE MAIN
		String choice;
	do {
		
		//Presents user with menu options by calling void function displayMenu
		
		//initializes choice to the value returned by getInteger function
		choice = userChoice("Enter choice: ");
		
		if (graph.getN() == 0 && !(choice.equals("G")||(choice.equals("Q")))) {
			System.out.println("\nERROR: No graph has been loaded!");
		}else {
		if (choice.equals("G")) {
			createGraph(graph);
		}
		if (choice.equals("C")) {
			editCities(graph);
		}
		if (choice.equals("A")) {
			removeArc(graph);
		}
		if (choice.equals("D")) {
			graph.print();
		}
		if (choice.equals("R")) {
			graph.reset();
		}
		if (choice.equals("P")) {
			tryPath(graph);
		}
		}

}while (!(choice.equals("Q")));
	System.out.print("\nCiao!");
	}
public static void displayMenu() {
	System.out.println("   \nJAVA TRAVELING SALESMAN PROBLEM V1");
	System.out.println("G - Create graph");
	System.out.println("C - Edit cities");
	System.out.println("A - Edit arcs");
	System.out.println("D - Display graph info");
	System.out.println("R - Reset graph");
	System.out.println("P - Enter salesman's path");
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
		if (choice.equals("G")) {
			valid = true;
		}else if (choice.equals("C")) {
			valid = true;
		}else if (choice.equals("A")) {
			valid = true;
		}else if (choice.equals("D")) {
			valid = true;
		}else if (choice.equals("R")) {
			valid = true;
		}else if (choice.equals("P")) {
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
public static String userChoiceArcs(String prompt) {
	String choice = "S";
	do {
		valid = true;
		try{
			//System.out.println();
			 System.out.print("\n"+ prompt);
			 choice  = cin.readLine().toUpperCase();
			 
				}
		catch (NumberFormatException e) {
			System.out.println("ERROR: Invalid menu choice!");
			valid  = false;
		}
		catch (IOException e) 
		{
			System.out.println("ERROR: Invalid menu choice!");
			valid  = false;
		}
		if (choice.equals("A")) {
			valid = true;
		}else if (choice.equals("R")) {
			valid = true;
		}else if (choice.equals("Q")) {
			valid = true;
		}else {
			valid = false;
			System.out.println("ERROR: Invalid menu choice!");
		}
	}while (valid==false);
	
	return choice;
	
}
public static boolean createGraph(Graph G) throws IOException {  
	int LB = 0;
    int UB = Integer.MAX_VALUE;
    numOfCities = getInteger("\nEnter number of cities (0 to quit): ", LB, UB);
    if (numOfCities == 0) {
        return false;
    } else {
        G.init(numOfCities);
        for (int i = 1; i <= numOfCities; i++) {
            System.out.print("\nCity " + i + ":");
            System.out.print("\n   Name: ");
            
            // Read the name as a string without checking for errors.
            String name = cin.readLine();
            
            double lat = getDouble("   latitude: ", -90.00, 90.00);
            double lon = getDouble("   longitude: ", -180, 180);

            Node city = new Node(name, lat, lon);

            G.addNode(city);
        }
        System.out.println("\nNow add arcs:");
        addArcs(G);
        return true;
    }
}
public static void editCities(Graph G) //Allow the user to edit city information inside the graph.
{
	int LB = 1;
	System.out.println();
	G.printNodes();
	
	int cityToEdit = getInteger("\nEnter city to edit (0 to quit): ", LB, G.getN());
	if (cityToEdit == 0) {
		displayMenu();
		//userChoice();
	}else {
	Node city = G.getNode(cityToEdit-1);
	city.userEdit();
	for (int i = 0; i < G.getN(); i++) {
		if (G.existsArc(cityToEdit-1, i)) {
			double new_distance = Node.distance(G.getNode(cityToEdit-1), G.getNode(i));
			G.setCost(cityToEdit-1, i, new_distance);
		}
	}
	}
}
public static void displayMenuArcs() {
	System.out.println("EDIT ARCS");
	System.out.println("A - Add arc");
	System.out.println("R - Remove arc");
	System.out.println("Q - Quit");
}
public static void addArcs(Graph G) //Allow the user to add arcs to the graph.
{
	int LB = 0;
	int cityIndex1;
	int cityIndex2;
	System.out.println();
	G.printNodes();
	
	while(true) {
		cityIndex1 = getInteger("\nEnter first city index (0 to quit): ", LB, G.getN());
		if (cityIndex1 == 0) {
			break;
		}
			cityIndex2 = getInteger("\nEnter second city index (0 to quit): ", LB, G.getN());
			if (cityIndex2 == 0) {
				break;
			}
			if (cityIndex1 != cityIndex2) {
				if (G.existsArc(cityIndex1-1, cityIndex2-1)) {
				System.out.println("\nERROR: Arc already exists!");
			}else {
				G.addArc(cityIndex1-1, cityIndex2-1);
				System.out.println("\nArc " + cityIndex1 + "-" + cityIndex2 + " added!");
			}
		}else {
			System.out.print("\nERROR: A city cannot be linked to itself!");
		}
	}
	}
	
public static void removeArc(Graph G) //Allow the user to remove arcs from the graph.
{
	int LB = 0;
	String choice;
	do {
System.out.println();
	G.printArcs();
	System.out.println();
	displayMenuArcs();
	
	choice = userChoiceArcs("Enter choice: ");
	
	if (choice.equals("A")) {
		addArcs(G);
	}
	else if(choice.equals("R")) {
		while(true) {
			System.out.println();;
		G.printArcs();	
	int arcToRemove = getInteger("\nEnter arc to remove (0 to quit): ", LB, G.getM());
	if (arcToRemove == 0) {
		break;	
	}else {
		G.removeArc(arcToRemove);
		System.out.println("\nArc " + arcToRemove + " removed!\n");
	}
		}
	
}
}while (!(choice.equals("Q")));
	return;
}
public static void tryPath(Graph G) //Get a path from the user, check its feasibility, and then print its cost.
{
	System.out.println();
	G.printNodes();
	System.out.print("\nEnter the " + (G.getN()+1) + " cities in the route in order:\n");
	int[] p = new int[(G.getN()+1)];
	for (int i = 0; i < (G.getN()+1); i++) {
		p[i] = getInteger(("City " + (i+1) + ": "), 1, (G.getN()));
	}
	if (G.checkPath(p)) {
		System.out.printf("\nThe total distance traveled is %.2f km.\n", G.pathCost(p));
	}else {
		return;
	}
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

}