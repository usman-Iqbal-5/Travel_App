package Travel_App;

import java.util.*;
import java.io.*;
import java.util.zip.DataFormatException;

/**
 * MyController class implements the Controller interface - it implements the 4 functional requirements of the system and private methods which aid in implementing the 4 functional requirements
 *
 * @author usman
 */

public class MyController implements Controller {


    //destinationList is the map that holds all the locations after being read from the CSV file
    private Map<String, Location> destinationList;
    //Graph that holds the locations and distances between each location
    private WeightedGraphADT graph;

    /**
     * Constructor - it initialise the destinationList Map and calls the private method Readfile to read the CSV file
     */
    public MyController(){
        destinationList = new HashMap<>();
        readFile("destinations.csv");

    }

    /**
     * returns all or a specified destination in a user-friendly format
     *
     * @param destination	"all" for listing information about all destination cities;
     *                      specific city name, e.g. "London", for listing information about
     *                      the specified city.
     * @return a string of the destinations from the DestinationList Map in a formatted layout
     */

    @Override
    public String listDestinations(String destination) {
        StringBuilder result = new StringBuilder();
        // adds the titles under which the destinations will be displayed to the result String
        result.append(String.format("%-10s %-85s %-10s %s %6s\n","Name","Description","Latitude","Longitude","Cost" ));

        //loops through all the destinationList Map (if "all" is parameter specified)
        if (destination.equalsIgnoreCase("all")){
            for (Map.Entry<String, Location> entry: destinationList.entrySet()) {
                String key = entry.getKey();

                //adds the location key and values to the results string (in the specified format layout using String.format())
                result.append(String.format("%-10s %-85s %.4f %10.4f %9s\n",key,entry.getValue().getDescription(),entry.getValue().getLatitude(), entry.getValue().getLongitude(),entry.getValue().getCost()));
            }
        } else{
            // if a specified location is passed as parameter - then the key and values for that location are added to the result string instead
            result.append(String.format("%-10s %-85s %.4f %10.4f %9s",destination, destinationList.get(destination).getDescription(),destinationList.get(destination).getLatitude(), destinationList.get(destination).getLongitude(), destinationList.get(destination).getCost() ));
        }
        return result.toString();
    }


    /**
     * produces a string of nearby locations (within a specified distance) ordered cost and distance (in a user-friendly formatted layout)
     *
     * @param latitude1	the latitude coordinate of the user-specified location
     * @param longitude1	the longitude coordinate of the user-specified location
     * @param distance	the distance relative to the given location
     * @return a String of locations ordered by cost and distance in a formatted layout
     */
    @Override
    public String listNearbyDestinations(double latitude1, double longitude1, int distance) {

        //priority queue that prioritises locations initially by cost and then by distance
        PriorityQueue<Location> nearbyDestinations = new PriorityQueue<>(new LocationComparator(latitude1, longitude1));
        StringBuilder results = new StringBuilder();

        //enhanced for loop that iterates through all the locations within destinationList (via entrySet.())
        for (Map.Entry<String, Location> entry: destinationList.entrySet()) {
            //retrieves latitude of location (in destinationList)
            double latitude2 = entry.getValue().getLatitude();
            //retrieves latitude of location (in destinationList)
            double longitude2 = entry.getValue().getLongitude();
            //calculates the distance of the destination (in destinationList) from the current location
            double calculatedDistance = Utility.calculateDistance(latitude2, longitude2, latitude1, longitude1);


            // adds locations (from destinationList) to the priority queue if they are within the specified distance
           if(calculatedDistance <= distance){
               nearbyDestinations.add(entry.getValue());
           }

        }

        // adds the current location and titles of the column (in a user-friendly layout) to the results String
        results.append(String.format("Current Location: (Lat) %.2f (Lon) %.2f\n",latitude1,longitude1));
        results.append(String.format("%-15s %-10s %s", "Name","Cost", "Distance to current Location (KM)\n"));

        //loops through the priority queue and adds the correctly ordered locations to the results String (in a user-friendly formatted layout)
        while (!nearbyDestinations.isEmpty()) {
            Location location = nearbyDestinations.poll();
            results.append(String.format("%-15s %-10s %.1f\n", location.getName(),location.getCost(), Utility.calculateDistance(location.getLatitude(), location.getLongitude(), latitude1, longitude1)));
        }


        return results.toString();
    }

    /**
     * Shows all the locations and their distances to each location in the destinationList Map
     * @return String of all the locations and their distance from all the other locations (in a user-firendly formatted layout)
     */

    @Override
    public String listDistances() {
        // if the weighted graph is null - creates a new graph (otherwise this step is skipped)
        if(graph == null){
            createWeightedGraph();
        }
        String result = graph.printGraph();

        //writes the content of the printGraph function to a new text file and prints the return string to show the file was created successfully
        System.out.println(writeNewFile(result));

        //returns the results of the printGraph function
        return result;

    }

    /**
     *
     * @param cities2visit an array storing the cities a traveller would like to visit
     * @return a string of all the locations in order of the shortest path a traveller would take (in a user-friendly formatted layout)
     */

   @Override
    public String listShortestPath(String[] cities2visit) {

       // if the weighted graph is null - creates a new graph (otherwise this step is skipped)
        if(graph == null){
            createWeightedGraph();
        }

        StringBuilder result = new StringBuilder();

        // the order number of the location paths to travel
        int orderNum = 0;

        //stores the total distance that needs to be travelled to visit all the specified cities.
        double totalDistance = 0;


        // map that stores the locations and the shortest distance of those location from the start location
        Map<String, Double> shortestPath = graph.dijkstraAlgorithm(cities2visit[0]);

        //priority queue that order locations via the shortest distances
        PriorityQueue<Node> shortDistOrder = new PriorityQueue<>(Comparator.comparingDouble(Node::getDistance));

       // iterate through the cities to visit array to add specified locations and distance to priority queue
        for (String city : cities2visit) {
            double distance = shortestPath.get(city);
            shortDistOrder.add(new Node(city, distance));

        }



       // add a title to the result string to indicate the order of the shortest travel path
        result.append("Oder of the shortest travel path: \n");


        // Iterate through the priority queue and add the shortest travel path in the correct order
        while(!shortDistOrder.isEmpty()){
            String location1 = shortDistOrder.poll().getName();
            //update the order number
            orderNum++;


            // Check if there are more locations in the queue to prevent error
            if(!shortDistOrder.isEmpty()) {
                String location2 = shortDistOrder.peek().getName();

                // Calculate the shortest distance between location1 and location2 using Dijkstra's algorithm
                double shortestDistance = graph.dijkstraAlgorithm(location1).get(location2);

                totalDistance += shortestDistance;

                // add the order number, locations, and the shortest distance to the result string (in a user-friendly formatted layout)
                result.append(orderNum).append(") ").append("(").append(location1).append(") ").append("--(").append(String.format("%.1f KM",shortestDistance)).append(")--> ").append("(").append(location2).append(") \n");
            }

        }
        // adds the total distance of travel to the result string
       result.append(String.format("Total distance of travel: %.1f KM \n", totalDistance));


        return result.toString();
    }

    /**
     *private method that reads the CSV file and creates a location object for each destination and adds it to the destinationList Map
     * @param fileName
     */
    private void readFile(String fileName) {
        // try-catch block to catch any file not found or wrong format error
        try {
            //Scanner object ot read file
            Scanner scanner = new Scanner(new File(fileName));

            // reads the headers of the CSV file and ignores them
            String line = scanner.nextLine();

            //loop through the CSV file as long as there is another line
            while (scanner.hasNextLine()) {
                // reads a line of the CVS file
                line = scanner.nextLine();
                // splits the line into tokens (separated by commas) and stores the tokensS into a string array
                String[] fields = line.split(",");


                // checks if there are 5 tokens or throws an error
                if (fields.length == 5) {
                    //uses the tokens to create a new location object for each of the destinations and store them int the destinationList Map
                    destinationList.put(fields[0], new Location(fields[0], fields[1], Double.parseDouble(fields[2]), Double.parseDouble(fields[3]), fields[4]));
                } else {
                    throw new DataFormatException("Incompatible data format");
                }
            }

        } catch (FileNotFoundException | DataFormatException e) {
            //prints error to the screen
            System.out.print(e.toString());
        }


    }

    /**
     * private method that creates a weighted graph from the destinationList Map
     */
    private void createWeightedGraph(){

        // creates new weighted graph object
        graph = new WeightedGraph();



        // iterates through destinationList map and add a vertex for every location (key) and for each key iterates through the destination list again to add associated edges
        for(Map.Entry<String, Location> entry: destinationList.entrySet()){ // creates the initial vertices
            String key = entry.getKey();
            Location value = entry.getValue();
            //adds vertex to weighted graph
            graph.addVertex(key);

            for(Map.Entry<String, Location> secondEntry: destinationList.entrySet()){ //inner loop iterates through destination list map again to add edges
                Location secondValue = secondEntry.getValue();
                //adds edges to weighted graph
                graph.addEdge(value,secondValue, Utility.calculateDistance(value.getLatitude(),value.getLongitude(),secondValue.getLatitude(),secondValue.getLongitude()));

            }

        }
    }

    /**
     * private method - writes and saves a new text file
     * @param fileContent
     * @return string message to display file was created successfully
     */
    private String writeNewFile(String fileContent){

        // try and catch block to catch input/output error
        try {
            File file = new File("listDistances.txt");
            //creates new file writer object
            FileWriter fileWriter = new FileWriter(file);
            //create new buffered writer obeject
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Writes the content to the file
            bufferedWriter.write(fileContent);

            //close the buffered writer
            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "File created successfully!";

    }

}