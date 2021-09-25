public class InvoiceService {
    private static final double DISTANCE_KM_FARE_RATE = 10.0, TIME_MIN_FARE_RATE = 1, MINIMUM_FARE = 5;

    public double calculateFare(double distanceKm, int timeMin) {
        double totalFare = distanceKm*DISTANCE_KM_FARE_RATE+timeMin* TIME_MIN_FARE_RATE;
        return Math.max(totalFare, MINIMUM_FARE);
    }
}
