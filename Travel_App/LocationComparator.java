package Travel_App;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * A locationComparator object is usd to create a customer comparator so that location is prioritised initially on cost and then by distance
 *
 * @author usman
 */


class LocationComparator implements Comparator<Location> {

    // maps the cost categories to the cost vaues
    private final Map<String, Integer> costValues;

    //current location latitude
    private double currentLatitude;
    //current location longitude
    private double currentLongitude;

    /**
     * initalises the current latitude and current longitude
     * @param currentLatitude
     * @param currentLongitude
     */

    public LocationComparator(double currentLatitude, double currentLongitude) {
        // Assigning numeric values to cost categories
        //initialises a hashmap with a know capacity of 3
        costValues = new HashMap<>(3);
        costValues.put("Low", 1);
        costValues.put("Medium", 2);
        costValues.put("High", 3);
        this.currentLatitude = currentLatitude;
        this.currentLongitude = currentLongitude;
    }

    /**
     * Overrides the compare method with a customer comparator for the priority queue to first prioritise by cost and then distance
     * @param loc1 the first object to be compared.
     * @param loc2 the second object to be compared.
     * @return zero, positive or negative value depending on the location within priority queue
     */
    @Override
    public int compare(Location loc1, Location loc2) {

        // Getting numeric values for cost categories
        int costValue1 = costValues.get(loc1.getCost());
        int costValue2 = costValues.get(loc2.getCost());


        //working out the distances between the locations in the priority queue
        double distance1 = Utility.calculateDistance(loc1.getLatitude(), loc1.getLongitude(), currentLatitude, currentLongitude);
        double distance2 = Utility.calculateDistance(loc2.getLatitude(), loc2.getLongitude(), currentLatitude, currentLongitude);


        // Comparing based on cost and distance
        if (costValue1 != costValue2) {
            //if cost are not equal - uses the Integer Wrapper class compare the values
            return Integer.compare(costValue1, costValue2);

        } else {
            // If costs are equal, prioritize based on distance (uses the Double wrapper class compare function to compare the values)
            return Double.compare(distance1,distance2);
        }
    }
}
