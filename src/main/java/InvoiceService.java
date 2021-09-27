import java.util.ArrayList;
import java.util.HashMap;

public class InvoiceService {
    RideRepository rideRepository;

    public void setRideRepository(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    public double calculateFare(Ride ride) {
        double totalFare = ride.getDistance().getDistanceInKm() * ride.getType().getRatePerKm() + ride.getTime().getTimeInMin() * ride.getType().getRatePerMin();
        return Math.max(totalFare, ride.getType().getMinRate());
    }

    public double getTotalAggregateFare(String userId) {
        double totalAggregateFare = 0;
        for (Ride ride : rideRepository.getUserRidesMap().get(userId)) {
            totalAggregateFare += calculateFare(ride);
        }
        return totalAggregateFare;
    }

    public String getInvoiceSummary(String userId) {
        double totalLength = rideRepository.getUserRidesMap().get(userId).size();
        return "Total number of rides: " + totalLength + "\nTotal fare amount: " + getTotalAggregateFare(userId) + "\nAverage Fare per ride: " + (getTotalAggregateFare(userId) / totalLength);
    }

    public void addRides(String userId, ArrayList<Ride> rideList) {
        for(Ride ride : rideList){
            rideRepository.addRides(userId,ride);
        }
    }

    public String showRideList(String userId) {
        return rideRepository.showRideList(userId);
    }

    public HashMap<String, ArrayList<Ride>> getUserRidesMap(){
        return rideRepository.getUserRidesMap();
    }
}
