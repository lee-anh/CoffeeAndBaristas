
import java.time.LocalTime;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class CustomerTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class CustomerTest
{
    /**
     * Default constructor for test class CustomerTest
     */
    public CustomerTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }

    @Test
    public void getCustomerNumberTest()
    {
        Customer customer1 = new Customer(0, null, null, 0, 0);
        assertEquals(0, customer1.getCustomerNumber());
    }
    
    @Test
    public void getArrivalTimeTest()
    {
        LocalTime arrive = LocalTime.parse("07:00:00");
        Customer customer1 = new Customer(0, arrive, null, 0, 0);
        assertEquals(arrive, customer1.getArrivalTime());
    }
    
    @Test
    public void getDepartureTimeTest()
    {
        LocalTime depart = LocalTime.parse("07:00:00");
        Customer customer1 = new Customer(0, null, depart, 0, 0); //why isn't departue setting correctly in the constructor?
        assertEquals(depart, customer1.getDepartureTime());
    }
    
    @Test
    public void getHowLongTest()
    {
        Customer customer1 = new Customer(0, null, null, 1, 0); //why isn't departue setting correctly in the constructor?
        assertEquals(1, customer1.getHowLong());
    }
    
    @Test
    public void getProfitTest()
    {
        Customer customer1 = new Customer(0, null, null, 0, 1.0); //why isn't departue setting correctly in the constructor?
        assertEquals(1.0, customer1.getProfit(), 0);
    }
    
    @Test
    public void setCustomerNumberTest()
    {
        Customer customer1 = new Customer(0, null, null, 0, 0);
        assertEquals(1, customer1.setCustomerNumber(1));
    }
    
    @Test
    public void setArrivalTimeTest()
    {
        LocalTime arrive = LocalTime.parse("07:00:00");
        Customer customer1 = new Customer(0, null, null, 0, 0);
        assertEquals(arrive, customer1.setArrivalTime(arrive));
    }
    
    @Test
    public void setDepartureTimeTest()
    {
        LocalTime depart = LocalTime.parse("07:00:00");
        Customer customer1 = new Customer(0, null, null, 0, 0); //why isn't departue setting correctly in the constructor?
        assertEquals(depart, customer1.setDepartureTime(depart));
    }
    
     @Test
    public void setHowLongTest()
    {
        Customer customer1 = new Customer(0, null, null, 0, 0); //why isn't departue setting correctly in the constructor?
        assertEquals(1, customer1.setHowLong(1));
    }
    
    @Test
    public void setProfitTest()
    {
        Customer customer1 = new Customer(0, null, null, 0, 0); //why isn't departue setting correctly in the constructor?
        assertEquals(1.0, customer1.setProfit(1.0), 0);
    }
}

