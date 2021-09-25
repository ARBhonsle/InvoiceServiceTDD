import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class InvoiceServiceTest {
    static InvoiceService invoice;
    static ArrayList<Ride> rideList;

    @BeforeEach
    public void setUp() {
        invoice = new InvoiceService();
        rideList = new ArrayList<>();
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
        invoice.addRides("1",new Ride(new MeasuredUnit(Unit.KM, 2.0), new MeasuredUnit(Unit.MIN, 5.0)));
        invoice.addRides("1",new Ride(new MeasuredUnit(Unit.METER, 500.0), new MeasuredUnit(Unit.MIN, 3.0)));
        invoice.addRides("1",new Ride(new MeasuredUnit(Unit.METER, 200.0), new MeasuredUnit(Unit.MIN, 1.0)));
        double totalAggregateFare = invoice.getTotalAggregateFare("1");
        double expectedResult = 38.0;
        Assertions.assertEquals(expectedResult, totalAggregateFare,0.01);
    }

    @Test
    public void givenMultipleRides_shouldReturnInvoiceSummary() {
        invoice.addRides("1",new Ride(new MeasuredUnit(Unit.KM, 2.0), new MeasuredUnit(Unit.MIN, 5.0)));
        invoice.addRides("1",new Ride(new MeasuredUnit(Unit.METER, 500.0), new MeasuredUnit(Unit.MIN, 3.0)));
        invoice.addRides("1",new Ride(new MeasuredUnit(Unit.METER, 200.0), new MeasuredUnit(Unit.MIN, 1.0)));
        invoice.addRides("1",new Ride(new MeasuredUnit(Unit.METER, 200.0), new MeasuredUnit(Unit.MIN, 4.0)));
        String invoiceSummary = invoice.getSummary("1");
        double noOfRides = 4.0, totalAggregateFare = 44.0, averageFarePerRide = 11.0;
        String expectedSummary = "Total number of rides: " + noOfRides + "\nTotal fare amount: " + totalAggregateFare + "\nAverage Fare per ride: " + averageFarePerRide;
        Assertions.assertEquals(expectedSummary, invoiceSummary);
    }

    @Test
    public void givenUserId_shouldReturnRideListAndInvoiceSummary() {
        invoice.addRides("1",new Ride(new MeasuredUnit(Unit.KM, 2.0), new MeasuredUnit(Unit.MIN, 5.0)));
        invoice.addRides("1",new Ride(new MeasuredUnit(Unit.METER, 500.0), new MeasuredUnit(Unit.MIN, 3.0)));
        invoice.addRides("1",new Ride(new MeasuredUnit(Unit.METER, 200.0), new MeasuredUnit(Unit.MIN, 1.0)));
        invoice.addRides("1",new Ride(new MeasuredUnit(Unit.METER, 200.0), new MeasuredUnit(Unit.MIN, 4.0)));
        // rides list
        String ridesList = invoice.showRideList("1");
        StringBuilder expectedRidesList = new StringBuilder();
        expectedRidesList.append("User Id:").append("1");
        for (Ride ride : invoice.getUserRidesMap().get("1")) {
            expectedRidesList.append("\nDistance: ");
            expectedRidesList.append(ride.getDistance().getDistanceInKm());
            expectedRidesList.append("\tTime: ");
            expectedRidesList.append(ride.getTime().getTimeInMin());
        }
        Assertions.assertEquals(expectedRidesList.toString(), ridesList);

        // invoice summary
        String invoiceSummary = invoice.getSummary("1");
        double noOfRides = 4.0, totalAggregateFare = 44.0, averageFarePerRide = 11.0;
        String expectedSummary = "Total number of rides: " + noOfRides + "\nTotal fare amount: " + totalAggregateFare + "\nAverage Fare per ride: " + averageFarePerRide;
        Assertions.assertEquals(expectedSummary, invoiceSummary);
    }
}
