package Travel_App;

/**
 * A location object is used to model a location that is read from the CSV file.
 * A location has a name, description, latitude, longitude and living cost which is associatd with a location object.
 *
 * @author usman
 */


public class Location {
    //name of the location
    private String name;
    //a description of the location
    private String description;
    //the latitude of the location
    private double latitude;
    //the longitude of the location
    private double longitude;
    //the cost of the city
    private String cost;


    /**
     * Constructor - initialises the name, description, latitude, longitude and cost of the location
     * @param name
     * @param description
     * @param latitude
     * @param longitude
     * @param cost
     */
    public Location (String name, String description, Double latitude, Double longitude, String cost){
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.cost = cost;
    }

    /**
     * Get method for name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Get method for description
     * @return description
     */
    public String getDescription() {
        return description;
    }


    /**
     * Get method for latitude
     * @return latitude
     */

    public Double getLatitude() {
        return latitude;
    }

    /**
     * Get method for longitude
     * @return longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * Get method for cost
     * @return cost
     */

    public String getCost() {
        return cost;
    }

}
