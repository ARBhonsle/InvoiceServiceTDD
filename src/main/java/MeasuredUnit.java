public class MeasuredUnit {
    private final double value;
    private final Unit unit;

    public MeasuredUnit(Unit unit, double value) {
        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public Unit getUnit() {
        return unit;
    }

    public double getDistanceInKm() {
        if (!this.getUnit().getType().equals(Type.DISTANCE)) {
            return 0.0;
        }
        if (this.getUnit().equals(Unit.KM)) {
            return getValue();
        }
        return this.getUnit().convertToBaseUnit(this.getValue());
    }

    public double getTimeInMin() {
        if (!this.getUnit().getType().equals(Type.TIME)) {
            return 0.0;
        }
        if (this.getUnit().equals(Unit.MIN)) {
            return getValue();
        }
        return this.getUnit().convertToBaseUnit(this.getValue());
    }

}
