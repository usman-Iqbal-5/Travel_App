package Travel_App;

import java.util.Map;

/**
 * The Weighted Graph ADT interface defines the functions that an implementing subclass graph should be able to do
 * It defines 4 abstract methods that are required to meet the needs of the system
 *
 * @author usman
 */

public interface WeightedGraphADT {
    /**
     * add an edge to the graph based on start location, end location and the distance (weight)
     * @param startLocation
     * @param endLocation
     * @param weight
     */
    public void addEdge(Location startLocation, Location endLocation, double weight);

    /**
     * add a vertex (or location) to the graph
     * @param vertex
     */
    public void addVertex(String vertex);

    /**
     * Produces a string that displays the graph (including its vertices (locations) and weights (distances)
     * @return a string representation of the graph
     */
    public String printGraph();

    /**
     * An algorithm that finds the shorts paths from a start vertex to all the other vertices
     * @param startLocation
     * @return a map of the shortest paths to each of the vertices (from the start vertex)
     */
    public Map<String, Double>  dijkstraAlgorithm(String startLocation);

}
