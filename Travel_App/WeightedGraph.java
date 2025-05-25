package Travel_App;

import java.util.*;

/**
 * WeightedGraph object is a adjacent lit implementation of the WeightedGraphADT
 * It has locations names mapped to a list of edges (which has locations with distances (weights))
 *
 * @author usman
 */

public class WeightedGraph implements WeightedGraphADT{

// Map that holds the start locations keys and maps them to a list of locations and their associated distances
    private Map<String, List<Edges>> adjacentList;


    /**
     *Constructor - this initializes the Adjacent List
     */

    public WeightedGraph(){
        adjacentList = new HashMap<>();

    }

    /**
     * adds a new list of edges to the start vertex (location which acts as the key)
     * @param vertex
     */
    @Override
    public void addVertex(String vertex){
        adjacentList.put(vertex, new ArrayList<Edges>());
    }

    /**
     * Adds a location (with its distance (weight)) from the start location
     * @param startLocation
     * @param endLocation
     * @param weight
     */
    @Override
    public void addEdge(Location startLocation, Location endLocation, double weight) {
        // adds a new edge to the list of edge using the start location key
        adjacentList.get(startLocation.getName()).add(new Edges(weight, endLocation));
    }

    /**
     * This method creates a string representation of the weighted graph
     * @return String of the locations and all the connected locations and their respective distances
     */
    @Override
    public String printGraph() {

        StringBuilder result = new StringBuilder();

        // loops through the adjacentList
        for (Map.Entry<String, List<Edges>> entry : adjacentList.entrySet()) {
            //access the ket and edges
            String vertex = entry.getKey();
            List<Edges> edges = entry.getValue();

            //adds the start location to the results string (in a user-friendly formatted layout)
            result.append("Location: ").append(vertex).append(" is connected to: \n");

            //loops through the edges and add the names of the edges and their distances to the start location to the results string in a user-friendly formatted layout
            for (Edges edge : edges) {
                result.append(" - ").append(edge.getLocation().getName()).append(" by a distance of ").append(String.format("%.1f KM.\n",edge.getDistance()));
            }
        }
        return result.toString();
    }

    /**
     * This method implements Dijkstra's algorithm using a priority queue - uses the start location to find the shortest distances to all the other locations
     * @param startLocation
     * @return a map of the shortest distances of all the locations from the starting location
     */

    @Override
    public Map<String, Double> dijkstraAlgorithm(String startLocation){
        //creates priority queue to nodes and their distances (ordered by shortest distance first)
        PriorityQueue<Node> shortDist = new PriorityQueue<>(Comparator.comparingDouble(Node -> Node.getDistance()));

        // create map which stores distance (weight) of each location (vertex)
        Map<String, Double> distances = new HashMap<>();

        // Initialize weights with maximum values (infinity) for all vertices
        for (String vertex: adjacentList.keySet()){
            distances.put(vertex, Double.MAX_VALUE);
        }
        // Set the distance (weight) of the start location to 0 (as the distance from itself is 0)
        distances.put(startLocation, 0.0);


        // Add the start location to the priority queue with a distance of 0
        shortDist.add(new Node(startLocation, 0));

        //Dijkstra's Algorithm

        // Continue the algorithm until all vertices are processed
        while(!shortDist.isEmpty()){

            // remove the node with the smallest distance (from the priority queue)
            Node currentNode = shortDist.poll();
            String currentVertex = currentNode.getName();



            // traverse neighbors of the current vertex
            for (Edges neighbour: adjacentList.get(currentVertex)){

                // Calculate the new distance (weights) from the start locations to the neighbour
                double newDistance = distances.get(currentVertex) + neighbour.getDistance();

                // Update the distance (weight) if the new distance (weight) is smaller than the current weight
                if( newDistance <distances.get(neighbour.getLocation().getName())){
                    distances.put(neighbour.getLocation().getName(), newDistance);

                    // Add the neighbor to the priority queue with the updated distance
                    shortDist.add(new Node(neighbour.getLocation().getName(), newDistance));
                    shortDist.add(new Node(neighbour.getLocation().getName(), newDistance));
                }
            }


        }
        return distances;
    }

}
