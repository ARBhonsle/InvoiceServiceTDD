package cab.invoice.system;

import java.util.ArrayList;

public class InvoiceService {
    RideRepository rideRepository;

    public void setRideRepository(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    public double calculateFare(RideType type, double distanceInKm, double timeInMin) {
        return rideRepository.calculateFare(type,distanceInKm,timeInMin);
    }

    public double getTotalAggregateFare(String userId) {
        return rideRepository.getTotalAggregateFare(userId);
    }

    public String getInvoiceSummary(String userId) {
        return rideRepository.getInvoiceSummary(userId);
    }

    public ArrayList<Ride> addRides(String userId, ArrayList<Ride> rideList) {
            return rideRepository.addRides(userId, rideList);
    }

    public String showRideList(String userId) {
        return rideRepository.showRideList(userId);
    }

    public ArrayList<Ride> getUserRidesList(String userId) {
        return rideRepository.getUserRides(userId);
    }
}
