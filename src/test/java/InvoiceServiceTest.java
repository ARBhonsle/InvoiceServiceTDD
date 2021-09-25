import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InvoiceServiceTest {

    @Test
    public void given2KmAnd5Min_shouldReturn25TotalFare() {
        InvoiceService invoice = new InvoiceService();
        Ride ride = new Ride(new MeasuredUnit(Unit.KM, 2.0), new MeasuredUnit(Unit.MIN, 5.0));
        double fare = invoice.calculateFare(ride);
        Assertions.assertEquals(25, fare);
    }

    @Test
    public void given100MeterAnd1Min_shouldReturnMinimumFare() {
        InvoiceService invoice = new InvoiceService();
        Ride ride = new Ride(new MeasuredUnit(Unit.METER, 100.0), new MeasuredUnit(Unit.MIN, 1.0));
        double fare = invoice.calculateFare(ride);
        Assertions.assertEquals(5, fare);
    }

    @Test
    public void givenMultipleRides_shouldReturnAggregateTotalFare() {
        InvoiceService invoice = new InvoiceService();
        Ride[] rideList = new Ride[]{
                new Ride(new MeasuredUnit(Unit.KM, 2), new MeasuredUnit(Unit.MIN, 5)),
                new Ride(new MeasuredUnit(Unit.METER, 500.0), new MeasuredUnit(Unit.MIN, 3)),
                new Ride(new MeasuredUnit(Unit.METER, 100.0), new MeasuredUnit(Unit.MIN, 1.0))
        };
        double totalAggregateFare = invoice.getFare(rideList);
        double expectedResult = 38.0;
        Assertions.assertEquals(expectedResult, totalAggregateFare);
    }
}
