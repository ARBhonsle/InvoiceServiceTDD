import java.util.ArrayList;
import java.util.HashMap;

public class RideRepository {
    private static ArrayList<Ride> ridesList;
    private static HashMap<String, ArrayList<Ride>> userRidesMap;

    RideRepository(){
        ridesList = new ArrayList<>();
        userRidesMap = new HashMap<>();
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
