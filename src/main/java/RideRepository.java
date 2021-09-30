import java.util.ArrayList;
import java.util.HashMap;

public class RideRepository {
    private static ArrayList<Ride> ridesList;
    private static HashMap<String, ArrayList<Ride>> userRidesMap;

    RideRepository(){
        ridesList = new ArrayList<>();
        userRidesMap = new HashMap<>();
    }

    public double calculateFare(RideType type, double distanceInKm, double timeInMin) {
        double totalFare = distanceInKm * type.ratePerKm + timeInMin * type.ratePerMin;
        return Math.max(totalFare, type.minRate);
    }

    public double getTotalAggregateFare(String userId) {
        double totalAggregateFare = 0;
        for (Ride ride : this.getUserRides(userId)) {
            totalAggregateFare += calculateFare(ride.getType(), ride.distanceInKm, ride.timeInMin);
        }
        return totalAggregateFare;
    }

    public ArrayList<Ride> addRides(String userId, ArrayList<Ride> rideArrayList) {
        for(Ride ride : rideArrayList) {
            if (userRidesMap.containsKey(userId)) {
                ArrayList<Ride> rideList = userRidesMap.get(userId);
                rideList.add(ride);
            } else {
                ridesList.add(ride);
                userRidesMap.put(userId, ridesList);
            }
        }
        return ridesList;
    }

    public String getInvoiceSummary(String userId) {
        double totalLength = this.getUserRides(userId).size();
        return "Total number of rides: " + totalLength + "\nTotal fare amount: " + this.getTotalAggregateFare(userId) + "\nAverage Fare per ride: " + (this.getTotalAggregateFare(userId) / totalLength);
    }

    public String showRideList(String userId) {
        StringBuilder showRideDetails = new StringBuilder();
        showRideDetails.append("User Id:").append(userId);
        for (Ride ride : userRidesMap.get(userId)) {
            showRideDetails.append("\nRide Type: ");
            showRideDetails.append(ride.getType());
            showRideDetails.append("\nDistance: ");
            showRideDetails.append(ride.distanceInKm);
            showRideDetails.append("\tTime: ");
            showRideDetails.append(ride.timeInMin);
        }
        return showRideDetails.toString();
    }

    public ArrayList<Ride> getUserRides(String userId) {
        return userRidesMap.get(userId);
    }
}


