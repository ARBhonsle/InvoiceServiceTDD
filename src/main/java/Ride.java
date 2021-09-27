public class Ride {
    private final MeasuredUnit distance, time;
    private final RideType type;

    public Ride(RideType type,MeasuredUnit distance, MeasuredUnit time) {
        this.type =type;
        this.distance = distance;
        this.time = time;
    }

    public MeasuredUnit getDistance() {
        return distance;
    }

    public MeasuredUnit getTime() {
        return time;
    }

    public RideType getType() {
        return type;
    }

    public double calculateFare() {
        double totalFare = this.getDistance().getDistanceInKm() * this.getType().getRatePerKm() + this.getTime().getTimeInMin() * this.getType().getRatePerMin();
        return Math.max(totalFare,this.getType().getMinRate());
    }
}
