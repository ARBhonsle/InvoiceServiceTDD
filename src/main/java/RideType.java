public enum RideType {
    NORMAL(10, 1, 5),
    PREMIUM(15, 2, 20);
    private final double ratePerKm, ratePerMin, minRate;

    RideType(double ratePerKm, double ratePerMin, double minRate) {
        this.ratePerKm = ratePerKm;
        this.ratePerMin = ratePerMin;
        this.minRate = minRate;
    }

    public double getMinRate() {
        return minRate;
    }

    public double getRatePerKm() {
        return ratePerKm;
    }

    public double getRatePerMin() {
        return ratePerMin;
    }

}
