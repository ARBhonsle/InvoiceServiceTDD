import java.util.ArrayList;
import java.util.HashMap;

public class InvoiceService {
    private static final double DISTANCE_KM_FARE_RATE = 10, TIME_MIN_FARE_RATE = 1, MINIMUM_FARE = 5;
    private static ArrayList<Ride> ridesList;
    private static HashMap<String, ArrayList<Ride>> userRidesMap;

    InvoiceService(){
        ridesList = new ArrayList<>();
        userRidesMap = new HashMap<>();
    }

    public double calculateFare(Ride ride) {
        double totalFare = ride.getDistance().getDistanceInKm() * DISTANCE_KM_FARE_RATE + ride.getTime().getTimeInMin() * TIME_MIN_FARE_RATE;
        return Math.max(totalFare, MINIMUM_FARE);
    }


    public double getTotalAggregateFare(String userId) {
        double totalAggregateFare = 0;
        for (Ride ride : userRidesMap.get(userId)) {
            totalAggregateFare += calculateFare(ride);
        }
        return totalAggregateFare;
    }

    public String getSummary(String userId) {

        double totalLength = userRidesMap.get(userId).size();
        return "Total number of rides: " + totalLength + "\nTotal fare amount: " + getTotalAggregateFare(userId) + "\nAverage Fare per ride: " + (getTotalAggregateFare(userId) / totalLength);
    }

    public void addRides(String userId, Ride ride) {
        if (userRidesMap.containsKey(userId)) {
            ArrayList<Ride> rideList = userRidesMap.get(userId);
            rideList.add(ride);
        } else {
            ridesList.add(ride);
            userRidesMap.put(userId, ridesList);
        }
    }

    public String showRideList(String userId) {
        StringBuilder showRideDetails = new StringBuilder();
        showRideDetails.append("User Id:").append(userId);
        for (Ride ride : userRidesMap.get(userId)) {
            showRideDetails.append("\nDistance: ");
            showRideDetails.append(ride.getDistance().getDistanceInKm());
            showRideDetails.append("\tTime: ");
            showRideDetails.append(ride.getTime().getTimeInMin());
        }
        return showRideDetails.toString();
    }

    public HashMap<String, ArrayList<Ride>> getUserRidesMap() {
        return userRidesMap;
    }
}
