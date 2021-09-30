// enum RideType
enum RideType {
    NORMAL(10, 1, 5),
    PREMIUM(15, 2, 20);
    final double ratePerKm, ratePerMin, minRate;

    RideType(double ratePerKm, double ratePerMin, double minRate) {
        this.ratePerKm = ratePerKm;
        this.ratePerMin = ratePerMin;
        this.minRate = minRate;
    }
}
// class Ride
public class Ride {
    final double distanceInKm;
    final double timeInMin;
    private final RideType type;

    public Ride(RideType type, double distanceInKm, double timeInMin) {
        this.type = type;
        this.distanceInKm = distanceInKm;
        this.timeInMin = timeInMin;
    }

    public RideType getType() {
        return type;
    }
}
