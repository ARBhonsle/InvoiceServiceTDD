import java.util.ArrayList;
import java.util.HashMap;

public class InvoiceService {
    private static ArrayList<Ride> ridesList;
    private static HashMap<String, ArrayList<Ride>> userRidesMap;

    InvoiceService() {
        ridesList = new ArrayList<>();
        userRidesMap = new HashMap<>();
    }

    public double calculateFare(Ride ride) {
        double totalFare = ride.getDistance().getDistanceInKm() * ride.getType().getRatePerKm() + ride.getTime().getTimeInMin() * ride.getType().getRatePerMin();
        return Math.max(totalFare, ride.getType().getMinRate());
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
            showRideDetails.append("\nRide Type: ");
            showRideDetails.append(ride.getType());
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
