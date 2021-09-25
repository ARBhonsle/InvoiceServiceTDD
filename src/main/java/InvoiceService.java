public class InvoiceService {
    private static final double DISTANCE_KM_FARE_RATE = 10.0, TIME_MIN_FARE_RATE = 1, MINIMUM_FARE = 5;

    public double calculateFare(Ride ride) {
        double totalFare = ride.getDistance().getDistanceInKm() * DISTANCE_KM_FARE_RATE + ride.getTime().getTimeInMin() * TIME_MIN_FARE_RATE;
        return Math.max(totalFare, MINIMUM_FARE);
    }

    public double getFare(Ride[] rideList) {
        double totalAggregateFare = 0;
        for (Ride ride : rideList) {
            totalAggregateFare += calculateFare(ride);
        }
        return totalAggregateFare;
    }
}
