package Travel_App;

/**
 * Edge object is used to model the edge in the adjacent list implementation of the Weighted Graph class
 * It stores the vertex location and distance to the start vertex
 *
 * @author usman
 */

public class Edges {
    //the distance between the two vertices
    private double distance;
    //the end vertex (location)
    private Location location;

    /**
     * constructor - intalises the distance and location
     *
     * @param distance
     * @param location
     */

    public Edges(double distance, Location location) {
        this.distance = distance;
        this.location = location;

    }

    /**
     * gets the distance
     *
     * @return distance
     */

    public double getDistance() {
        return distance;
    }

    /**
     * gets the location
     * @return location
     */

    public Location getLocation() {
        return location;
    }
}
