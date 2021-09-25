import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InvoiceServiceTest {
    @Test
    public void given2KmAnd5Min_shouldReturn25TotalFare(){
        InvoiceService invoice = new InvoiceService();
        double distanceKm = 2.0;
        int timeMin = 5;
        double fare = invoice.calculateFare(distanceKm,timeMin);
        Assertions.assertEquals(25,fare);
    }

    @Test
    public void given100MeterAnd1Min_shouldReturnMinimumFare(){
        InvoiceService invoice = new InvoiceService();
        double distanceKm = 0.1; // meter in Km (conversion)
        int timeMin = 1;
        double fare = invoice.calculateFare(distanceKm,timeMin);
        Assertions.assertEquals(5,fare);
    }
}
