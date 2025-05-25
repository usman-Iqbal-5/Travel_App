package Travel_App;

/**
 * A utility class for providing a method to calculate distance between two
 * locations as specified in longitude and latitude.
 *
 * @version 30/11/2023
 */
public class Utility {

	/**
	 * Calculates the distance between two locations using the Haversine formula.
	 *
	 * @param lat1 Latitude of the first location.
	 * @param lon1 Longitude of the first location.
	 * @param lat2 Latitude of the second location.
	 * @param lon2 Longitude of the second location.
	 * @return The distance in kilometers between the two locations.
	 */
	public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
		// Radius of the Earth in kilometers
		final double R = 6371.0;

		// Convert latitude and longitude from degrees to radians
		double lat1Rad = Math.toRadians(lat1);
		double lon1Rad = Math.toRadians(lon1);
		double lat2Rad = Math.toRadians(lat2);
		double lon2Rad = Math.toRadians(lon2);

		// Haversine formula for calculating distance
		double dlon = lon2Rad - lon1Rad;
		double dlat = lat2Rad - lat1Rad;
		double a = Math.pow(Math.sin(dlat / 2), 2)
				+ Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.pow(Math.sin(dlon / 2), 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distance = R * c;

		return distance;
	}

}
