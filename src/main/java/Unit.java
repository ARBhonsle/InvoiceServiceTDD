enum Type {
    DISTANCE, TIME
}

public enum Unit {
    // Distance
    KM(Type.DISTANCE, 1.0), METER(Type.DISTANCE, 0.001),
    // Time
    MIN(Type.TIME, 1.0), HR(Type.TIME, 60);

    private final Type type;
    private final double conversionValue;

    Unit(Type type, double conversionValue) {
        this.type = type;
        this.conversionValue = conversionValue;
    }

    public double getConversionValue() {
        return conversionValue;
    }

    public Type getType() {
        return type;
    }

    public double convertToBaseUnit(double value) {
        return value * getConversionValue();
    }

}
