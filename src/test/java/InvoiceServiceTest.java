import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InvoiceServiceTest {
    static InvoiceService invoice;

    @BeforeEach
    public void setUp() {
        invoice = new InvoiceService();
    }

    @Test
    public void given2KmAnd5Min_shouldReturn25TotalFare() {
        Ride ride = new Ride(new MeasuredUnit(Unit.KM, 2.0), new MeasuredUnit(Unit.MIN, 5.0));
        double fare = invoice.calculateFare(ride);
        Assertions.assertEquals(25, fare);
    }

    @Test
    public void given100MeterAnd1Min_shouldReturnMinimumFare() {
        Ride ride = new Ride(new MeasuredUnit(Unit.METER, 100.0), new MeasuredUnit(Unit.MIN, 1.0));
        double fare = invoice.calculateFare(ride);
        Assertions.assertEquals(5, fare);
    }

    @Test
    public void givenMultipleRides_shouldReturnAggregateTotalFare() {
        Ride[] rideList = new Ride[]{
                new Ride(new MeasuredUnit(Unit.KM, 2), new MeasuredUnit(Unit.MIN, 5)),
                new Ride(new MeasuredUnit(Unit.METER, 500.0), new MeasuredUnit(Unit.MIN, 3)),
                new Ride(new MeasuredUnit(Unit.METER, 100.0), new MeasuredUnit(Unit.MIN, 1.0))
        };
        double totalAggregateFare = invoice.getTotalAggregateFare(rideList);
        double expectedResult = 38.0;
        Assertions.assertEquals(expectedResult, totalAggregateFare);
    }

    @Test
    public void givenMultipleRides_shouldReturnInvoiceSummary() {
        Ride[] rideList = new Ride[]{
                new Ride(new MeasuredUnit(Unit.KM, 2), new MeasuredUnit(Unit.MIN, 5)),
                new Ride(new MeasuredUnit(Unit.METER, 500.0), new MeasuredUnit(Unit.MIN, 3)),
                new Ride(new MeasuredUnit(Unit.METER, 100.0), new MeasuredUnit(Unit.MIN, 1.0)),
                new Ride(new MeasuredUnit(Unit.METER, 200.0), new MeasuredUnit(Unit.MIN, 4))
        };
        String invoiceSummary = invoice.getSummary(rideList);
        int noOfRides = 4;
        double totalAggregateFare = 44.0, averageFarePerRide = 11;
        String expectedSummary = "Total number of rides: " + noOfRides + "\nTotal fare amount: " + totalAggregateFare + "\nAverage Fare per ride: " + averageFarePerRide;
        Assertions.assertEquals(expectedSummary, invoiceSummary);
    }

}
