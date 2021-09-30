package cab.invoice.system;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class InvoiceServiceTest {
    static InvoiceService invoice;
    static ArrayList<Ride> rideList;
    static RideRepository rideRepository;

    @BeforeEach
    public void setUp() {
        invoice = new InvoiceService();
        rideRepository = new RideRepository();
        invoice.setRideRepository(rideRepository);
        rideList = new ArrayList<>();
    }

    @Test
    public void given2KmAnd5Min_NormalRide_shouldReturn25AsTotalNormalRideFare() {
        Ride ride = new Ride(RideType.NORMAL,2.0, 5.0);
        double fare = invoice.calculateFare(ride.getType(), ride.distanceInKm,ride.timeInMin);
        Assertions.assertEquals(25, fare);
    }

    @Test
    public void given100MeterAnd1Min_NormalRide_shouldReturnMinimumNormalRideFare() {
        Ride ride = new Ride(RideType.NORMAL, 0.1, 1.0);
        double fare = invoice.calculateFare(ride.getType(), ride.distanceInKm,ride.timeInMin);
        Assertions.assertEquals(5, fare);
    }

    @Test
    public void givenMultipleRides_shouldReturnAggregateTotalFare() {
        rideList.add(new Ride(RideType.NORMAL, 2.0, 5.0));
        rideList.add(new Ride(RideType.NORMAL, 0.5, 3.0));
        rideList.add(new Ride(RideType.NORMAL, 0.2, 1.0));
        invoice.addRides("abc1", rideList);
        double totalAggregateFare = invoice.getTotalAggregateFare("abc1");
        double expectedResult = 38.0;
        Assertions.assertEquals(expectedResult, totalAggregateFare, 0.01);
    }

    @Test
    public void givenMultipleRides_shouldReturnInvoiceSummary() {
        rideList.add(new Ride(RideType.NORMAL, 2.0, 5.0));
        rideList.add(new Ride(RideType.NORMAL, 0.5, 3.0));
        rideList.add(new Ride(RideType.NORMAL, 0.2, 1.0));
        rideList.add(new Ride(RideType.NORMAL, 0.2, 4.0));
        invoice.addRides("abc1", rideList);
        String invoiceSummary = invoice.getInvoiceSummary("abc1");
        double noOfRides = 4.0, totalAggregateFare = 44.0, averageFarePerRide = 11.0;
        String expectedSummary = "Total number of rides: " + noOfRides + "\nTotal fare amount: " + totalAggregateFare + "\nAverage Fare per ride: " + averageFarePerRide;
        Assertions.assertEquals(expectedSummary, invoiceSummary);
    }

    @Test
    public void givenUserId_shouldReturnRideListAndInvoiceSummary() {
        rideList.add(new Ride(RideType.NORMAL, 2.0, 5.0));
        rideList.add(new Ride(RideType.NORMAL, 0.5, 3.0));
        rideList.add(new Ride(RideType.NORMAL, 0.2, 1.0));
        rideList.add(new Ride(RideType.NORMAL, 0.2, 4.0));
        invoice.addRides("abc1", rideList);
        // rides list
        String ridesList = invoice.showRideList("abc1");
        StringBuilder expectedRidesList = new StringBuilder();
        expectedRidesList.append("User Id:").append("abc1");
        for (Ride ride : invoice.getUserRidesList("abc1")) {
            expectedRidesList.append("\ncab.invoice.system.Ride Type: ");
            expectedRidesList.append(ride.getType());
            expectedRidesList.append("\nDistance: ");
            expectedRidesList.append(ride.distanceInKm);
            expectedRidesList.append("\tTime: ");
            expectedRidesList.append(ride.timeInMin);
        }
        Assertions.assertEquals(expectedRidesList.toString(), ridesList);

        // invoice summary
        String invoiceSummary = invoice.getInvoiceSummary("abc1");
        double noOfRides = 4.0, totalAggregateFare = 44.0, averageFarePerRide = 11.0;
        String expectedSummary = "Total number of rides: " + noOfRides + "\nTotal fare amount: " + totalAggregateFare + "\nAverage Fare per ride: " + averageFarePerRide;
        Assertions.assertEquals(expectedSummary, invoiceSummary);
    }

    @Test
    public void given2KmAnd5Min_PremiumRide_shouldReturn40AsTotalPremiumRideFare() {
        Ride ride = new Ride(RideType.PREMIUM, 2.0, 5.0);
        double fare = invoice.calculateFare(ride.getType(), ride.distanceInKm,ride.timeInMin);
        Assertions.assertEquals(40, fare);
    }

    @Test
    public void given100MeterAnd1Min_PremiumRide_shouldReturnMinimumPremiumRideFare() {
        Ride ride = new Ride(RideType.PREMIUM, 0.1,1.0);
        double fare = invoice.calculateFare(ride.getType(), ride.distanceInKm,ride.timeInMin);
        Assertions.assertEquals(20, fare);
    }
    @Test
    public void givenUserIdForNormalAndPremiumRides_shouldReturnRideListAndInvoiceSummary() {
        // Normal Rides
        rideList.add(new Ride(RideType.NORMAL, 2.0, 5.0));
        rideList.add(new Ride(RideType.NORMAL, 0.5, 3.0));
        rideList.add(new Ride(RideType.NORMAL, 0.2, 1.0));
        rideList.add(new Ride(RideType.NORMAL, 0.2, 4.0));
        // Premium Rides
        rideList.add(new Ride(RideType.PREMIUM,  2.0,5.0));
        rideList.add(new Ride(RideType.PREMIUM, 0.5,3.0));
        rideList.add(new Ride(RideType.PREMIUM,  0.2,1.0));
        rideList.add(new Ride(RideType.PREMIUM,  0.2,4.0));
        // add rides
        invoice.addRides("abc1", rideList);

        // rides list
        String ridesList = invoice.showRideList("abc1");
        StringBuilder expectedRidesList = new StringBuilder();
        expectedRidesList.append("User Id:").append("abc1");
        for (Ride ride : invoice.getUserRidesList("abc1")) {
            expectedRidesList.append("\ncab.invoice.system.Ride Type: ");
            expectedRidesList.append(ride.getType());
            expectedRidesList.append("\nDistance: ");
            expectedRidesList.append(ride.distanceInKm);
            expectedRidesList.append("\tTime: ");
            expectedRidesList.append(ride.timeInMin);
        }
        Assertions.assertEquals(expectedRidesList.toString(), ridesList);

        // invoice summary
        String invoiceSummary = invoice.getInvoiceSummary("abc1");
        double noOfRides = 8.0, totalAggregateFare = 144.0, averageFarePerRide = 18.0;
        String expectedSummary = "Total number of rides: " + noOfRides + "\nTotal fare amount: " + totalAggregateFare + "\nAverage Fare per ride: " + averageFarePerRide;
        Assertions.assertEquals(expectedSummary, invoiceSummary);
    }
}
