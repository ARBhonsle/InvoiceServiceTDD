package cab.invoice.system;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
//@RunWith(MockitoJUnitRunner.class)
public class InvoiceServiceTestMockito {
    static ArrayList<Ride> rideList;

//    @Rule
//    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    static RideRepository rideRepository;

    @InjectMocks
    static InvoiceService invoice;

    @BeforeEach
    public void setUp() {
//        MockitoAnnotations.openMocks(this);
        invoice.setRideRepository(rideRepository);
        rideList = new ArrayList<>();
    }

    @Test
    public void given2KmAnd5Min_NormalRide_shouldReturn25AsTotalNormalRideFare() {
        Ride ride = new Ride(RideType.NORMAL, 2.0, 5.0);

        when(rideRepository.calculateFare(ride.getType(), ride.distanceInKm, ride.timeInMin)).thenReturn(25.0);
        double fare = invoice.calculateFare(ride.getType(), ride.distanceInKm, ride.timeInMin);
        Assertions.assertEquals(25.0, fare);
    }

    @Test
    public void given100MeterAnd1Min_NormalRide_shouldReturnMinimumNormalRideFare() {
        Ride ride = new Ride(RideType.NORMAL, 0.1, 1.0);

        when(rideRepository.calculateFare(ride.getType(), ride.distanceInKm, ride.timeInMin)).thenReturn(5.0);
        double fare = invoice.calculateFare(ride.getType(), ride.distanceInKm, ride.timeInMin);
        Assertions.assertEquals(5, fare);
    }

    @Test
    public void givenMultipleRides_shouldReturnAggregateTotalFare() {
        String userId = "abc1";
        rideList.add(new Ride(RideType.NORMAL, 2.0, 5.0));
        rideList.add(new Ride(RideType.NORMAL, 0.5, 3.0));
        rideList.add(new Ride(RideType.NORMAL, 0.2, 1.0));

        when(rideRepository.getTotalAggregateFare(userId)).thenReturn(38.0);
        double totalAggregateFare = invoice.getTotalAggregateFare(userId);
        double expectedResult = 38.0;
        Assertions.assertEquals(expectedResult, totalAggregateFare, 0.01);
    }

    @Test
    public void givenMultipleRides_shouldReturnInvoiceSummary() {
        String userId = "abc1";
        rideList.add(new Ride(RideType.NORMAL, 2.0, 5.0));
        rideList.add(new Ride(RideType.NORMAL, 0.5, 3.0));
        rideList.add(new Ride(RideType.NORMAL, 0.2, 1.0));
        rideList.add(new Ride(RideType.NORMAL, 0.2, 4.0));

        when(rideRepository.addRides(userId,rideList)).thenReturn(rideList);
        invoice.addRides(userId, rideList);

        when(rideRepository.getInvoiceSummary(userId)).thenReturn("Total number of rides: 4.0\nTotal fare amount: 44.0\nAverage Fare per ride: 11.0");
        String invoiceSummary = invoice.getInvoiceSummary(userId);

        double noOfRides = 4.0, totalAggregateFare = 44.0, averageFarePerRide = 11.0;
        String expectedSummary = "Total number of rides: " + noOfRides + "\nTotal fare amount: " + totalAggregateFare + "\nAverage Fare per ride: " + averageFarePerRide;
        Assertions.assertEquals(expectedSummary, invoiceSummary);
    }

    @Test
    public void givenUserId_shouldReturnRideListAndInvoiceSummary() {
        String userId = "abc1";
        rideList.add(new Ride(RideType.NORMAL, 2.0, 5.0));
        rideList.add(new Ride(RideType.NORMAL, 0.5, 3.0));
        rideList.add(new Ride(RideType.NORMAL, 0.2, 1.0));
        rideList.add(new Ride(RideType.NORMAL, 0.2, 4.0));

        when(rideRepository.addRides(userId,rideList)).thenReturn(rideList);
        invoice.addRides(userId, rideList);

        // rides list
        String showList = "User Id:abc1\n" +
                "cab.invoice.system.Ride Type: NORMAL\n" +
                "Distance: 2.0\tTime: 5.0\n" +
                "cab.invoice.system.Ride Type: NORMAL\n" +
                "Distance: 0.5\tTime: 3.0\n" +
                "cab.invoice.system.Ride Type: NORMAL\n" +
                "Distance: 0.2\tTime: 1.0\n" +
                "cab.invoice.system.Ride Type: NORMAL\n" +
                "Distance: 0.2\tTime: 4.0";
        when(rideRepository.showRideList(userId)).thenReturn(showList);
        String ridesList = invoice.showRideList(userId);

        StringBuilder expectedRidesList = new StringBuilder();
        expectedRidesList.append("User Id:").append(userId);

        when(rideRepository.getUserRides(userId)).thenReturn(rideList);
        for (Ride ride : invoice.getUserRidesList(userId)) {
            expectedRidesList.append("\ncab.invoice.system.Ride Type: ");
            expectedRidesList.append(ride.getType());
            expectedRidesList.append("\nDistance: ");
            expectedRidesList.append(ride.distanceInKm);
            expectedRidesList.append("\tTime: ");
            expectedRidesList.append(ride.timeInMin);
        }
        Assertions.assertEquals(expectedRidesList.toString(), ridesList);

        // invoice summary
        when(rideRepository.getInvoiceSummary(userId)).thenReturn("Total number of rides: 4.0\nTotal fare amount: 44.0\nAverage Fare per ride: 11.0");
        String invoiceSummary = invoice.getInvoiceSummary(userId);
        double noOfRides = 4.0, totalAggregateFare = 44.0, averageFarePerRide = 11.0;
        String expectedSummary = "Total number of rides: " + noOfRides + "\nTotal fare amount: " + totalAggregateFare + "\nAverage Fare per ride: " + averageFarePerRide;
        Assertions.assertEquals(expectedSummary, invoiceSummary);
    }

    @Test
    public void given2KmAnd5Min_PremiumRide_shouldReturn40AsTotalPremiumRideFare() {
        Ride ride = new Ride(RideType.PREMIUM, 2.0, 5.0);

        when(rideRepository.calculateFare(ride.getType(),ride.distanceInKm,ride.timeInMin)).thenReturn(40.0);
        double fare = invoice.calculateFare(ride.getType(), ride.distanceInKm, ride.timeInMin);
        Assertions.assertEquals(40.0, fare);
    }

    @Test
    public void given100MeterAnd1Min_PremiumRide_shouldReturnMinimumPremiumRideFare() {
        Ride ride = new Ride(RideType.PREMIUM, 0.1, 1.0);

        when(rideRepository.calculateFare(ride.getType(),ride.distanceInKm,ride.timeInMin)).thenReturn(20.0);
        double fare = invoice.calculateFare(ride.getType(), ride.distanceInKm, ride.timeInMin);
        Assertions.assertEquals(20.0, fare);
    }

    @Test
    public void givenUserIdForNormalAndPremiumRides_shouldReturnRideListAndInvoiceSummary() {
        String userId = "abc1";
        // Normal Rides
        rideList.add(new Ride(RideType.NORMAL, 2.0, 5.0));
        rideList.add(new Ride(RideType.NORMAL, 0.5, 3.0));
        rideList.add(new Ride(RideType.NORMAL, 0.2, 1.0));
        rideList.add(new Ride(RideType.NORMAL, 0.2, 4.0));
        // Premium Rides
        rideList.add(new Ride(RideType.PREMIUM, 2.0, 5.0));
        rideList.add(new Ride(RideType.PREMIUM, 0.5, 3.0));
        rideList.add(new Ride(RideType.PREMIUM, 0.2, 1.0));
        rideList.add(new Ride(RideType.PREMIUM, 0.2, 4.0));

        // add rides
        when(rideRepository.addRides(userId,rideList)).thenReturn(rideList);
        invoice.addRides(userId, rideList);

        // rides list
        String showList = "User Id:abc1\n" +
                "cab.invoice.system.Ride Type: NORMAL\n" +
                "Distance: 2.0\tTime: 5.0\n" +
                "cab.invoice.system.Ride Type: NORMAL\n" +
                "Distance: 0.5\tTime: 3.0\n" +
                "cab.invoice.system.Ride Type: NORMAL\n" +
                "Distance: 0.2\tTime: 1.0\n" +
                "cab.invoice.system.Ride Type: NORMAL\n" +
                "Distance: 0.2\tTime: 4.0\n" +
                "cab.invoice.system.Ride Type: PREMIUM\n" +
                "Distance: 2.0\tTime: 5.0\n" +
                "cab.invoice.system.Ride Type: PREMIUM\n" +
                "Distance: 0.5\tTime: 3.0\n" +
                "cab.invoice.system.Ride Type: PREMIUM\n" +
                "Distance: 0.2\tTime: 1.0\n" +
                "cab.invoice.system.Ride Type: PREMIUM\n" +
                "Distance: 0.2\tTime: 4.0";
        when(rideRepository.showRideList(userId)).thenReturn(showList);
        String ridesList = invoice.showRideList(userId);
        StringBuilder expectedRidesList = new StringBuilder();
        expectedRidesList.append("User Id:").append(userId);

        when(rideRepository.getUserRides(userId)).thenReturn(rideList);
        for (Ride ride : invoice.getUserRidesList(userId)) {
            expectedRidesList.append("\ncab.invoice.system.Ride Type: ");
            expectedRidesList.append(ride.getType());
            expectedRidesList.append("\nDistance: ");
            expectedRidesList.append(ride.distanceInKm);
            expectedRidesList.append("\tTime: ");
            expectedRidesList.append(ride.timeInMin);
        }
        Assertions.assertEquals(expectedRidesList.toString(), ridesList);

        // invoice summary
        when(rideRepository.getInvoiceSummary(userId)).thenReturn("Total number of rides: 8.0\nTotal fare amount: 144.0\nAverage Fare per ride: 18.0");
        String invoiceSummary = invoice.getInvoiceSummary("abc1");
        double noOfRides = 8.0, totalAggregateFare = 144.0, averageFarePerRide = 18.0;
        String expectedSummary = "Total number of rides: " + noOfRides + "\nTotal fare amount: " + totalAggregateFare + "\nAverage Fare per ride: " + averageFarePerRide;
        Assertions.assertEquals(expectedSummary, invoiceSummary);
    }
}

