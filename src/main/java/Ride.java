public class Ride {
    private final MeasuredUnit distance, time;

    public Ride(MeasuredUnit distance, MeasuredUnit time) {
        this.distance = distance;
        this.time = time;
    }

    public MeasuredUnit getDistance() {
        return distance;
    }

    public MeasuredUnit getTime() {
        return time;
    }
}
