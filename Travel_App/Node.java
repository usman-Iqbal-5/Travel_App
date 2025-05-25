package Travel_App;

public class Node {


    /**
     * Node is a helper class which stores information of the location name and location distance
     * @author usman
     */
    //stores the name of the location
    private String name;
    //store the distance of the location from the start location
    private double distance;

    /**
     * Constructor - initialise the name and distance of a location
     * @param name
     * @param distance
     */

    public Node(String name, double distance){
        this.name = name;
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public double getDistance() {
        return distance;
    }


}
