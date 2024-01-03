import java.io.IOException;
public class Node {
	//public static double LB , UB;
	private String name; //node name
	private double lat; //lat coord
	private double lon; //lon coord
	
	//constructors
	public Node() {
		
	}
	
	public Node(String name, double lat, double lon) {
		setName(name);
		setLat(lat);
		setLon(lon);
	}
		
	//setters
	public void setName(String name) {
		this.name = name;
	}
	
	public void setLat(double lat) {
		this.lat = lat;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	
	//getters
	public String getName() {
		return this.name;
	}
	public double getLat() {
		return this.lat;
	}
	public double getLon() {
		return this.lon;
	}
	
	public void userEdit() //get user info and edit node
	{
		//
		//name = this.getName();
		try {
			System.out.print("   Name: ");
			name = Pro4_musahuda.cin.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //do not need to check validity + call getString function
		lat = this.getLat();
		lat = Pro4_musahuda.getDouble("   latitude: ",-90,90);
		lon = this.getLon();
		lon = Pro4_musahuda.getDouble("   longitude: ", -180,180);
		
	}
	public void print() // print node info as a table row
	{
		
		String temp = String.format("%s\n","(" + this.getLat() +","+ this.getLon()+")");
		System.out.printf("%19s%20s", this.getName(), temp);
	}
		// should only have this System.out.print(Graph.getName() + "        " + "(" + this.getLat() + ","  + this.getLon() + ")");
	
	public static double distance(Node i, Node j) //calc distance btwn two nodes 
	{
		double lat1;
		double lon1;
		double lat2;
		double lon2;
		double deltaX;
		double deltaY;
		double R;
		double a;
		double b;
		double distance; 
		lat1 = Math.toRadians(i.lat);
		lon1 = Math.toRadians(i.lon);
		lat2 = Math.toRadians(j.lat);
		lon2 = Math.toRadians(j.lon);
		deltaX = lat1 - lat2; 
		deltaY = lon1 - lon2;
		R = 6371; 
		a = Math.pow(Math.sin(deltaX / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(deltaY / 2), 2); 
		b = 2* Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		distance = R * b;
		return distance;
	}

	
}
