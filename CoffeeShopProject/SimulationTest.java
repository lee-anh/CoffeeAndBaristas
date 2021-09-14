
import java.time.LocalTime;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class SimulationTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class SimulationTest
{
    /**
     * Default constructor for test class SimulationTest
     */
    public SimulationTest()
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
    public void overflowRateTest(){
        Simulation sim = new Simulation();
        Customer customer1 = new Customer(0, null, null, 10, 0);
        Customer customer2 = new Customer(1, null, null, 20, 0);
        sim.addToCR(customer1);
        sim.addToCR(customer2);
        sim.setTotalNumberOverflowed(1);
        assertEquals(50, sim.overflowRate(), 0);
    }

    @Test
    public void averageTimeTest()
    {
        Simulation sim = new Simulation();
        Customer customer1 = new Customer(0, null, null, 10, 0);
        Customer customer2 = new Customer(1, null, null, 20, 0);
        sim.addToCR(customer1);
        sim.addToCR(customer2);

        assertEquals(15, sim.averageTime(), 0); 
    }

    @Test
    public void netProfitTest(){
        Simulation sim = new Simulation();
        sim.setTotalProfit(1000);
        sim.setBaristaCost(100);
        assertEquals(1000-100*sim.getTotalBaristas(), sim.netProfit(), 0);
    }

    @Test
    public void maxTimeTest(){
        Simulation sim = new Simulation(); 
        Customer customer1 = new Customer(0, null, null, 30, 0);
        Customer customer2 = new Customer(1, null, null, 20, 0);
        sim.addToCR(customer1);
        sim.addToCR(customer2);

        assertEquals(30, sim.maxTime()); 
    }

    @Test
    public void processNewEventTest(){
        Simulation sim = new Simulation();
        int custNum = 0;
        LocalTime time = LocalTime.parse("07:00:02");
        Event e = sim.processNewEvent(custNum, time);

        assertEquals(e, sim.pollEventSet());
    }

    @Test 
    public void addToCustomerRecordsTest(){
        Simulation sim = new Simulation();
        int custNum = 0;
        LocalTime time = LocalTime.parse("07:00:02");
        Customer c = sim.addToCustomerRecords(0, time);

        assertEquals(c, sim.getFromCR(custNum));
    }

    @Test 
    public void depatureTest(){
        Simulation sim = new Simulation(); 

        Event ev1 = new Event(0, null, Event.DEPARTURE);
        sim.departure(ev1);
        assertEquals(sim.getTotalBaristas() + 1, sim.getAvailibleBaristas());
    }

    @Test 
    public void arrivalTest1(){
        Simulation sim = new Simulation();
        sim.setAvailibleBaristas(0);
        Event ev1 = new Event(0, null, Event.ARRIVAL);
        sim.arrival(ev1);
        assertEquals(ev1, sim.pollCustomerLine());
    }

    @Test
    public void changeToDepartureTest(){
        Simulation sim = new Simulation(); 

        Event ev1 = new Event(0, null, Event.ARRIVAL);
        assertEquals(2, sim.changeToDeparture(ev1));

    }

    @Test
    public void updateCRProfitTest(){
        Simulation sim = new Simulation(); 

        Event ev1 = new Event(0, null, Event.DEPARTURE);
        Customer customer1 = new Customer(0, null, null, 0, 0);
        sim.addToCR(customer1);

        double profitRounded = 5.00;

        assertEquals(5.00, sim.updateCRProfit(ev1, profitRounded), 0);
    }

    @Test 
    public void updateTotalProfitTest(){
        Simulation sim = new Simulation();
        sim.setTotalProfit(1.0);
        assertEquals(2.50, sim.updateTotalProfit(1.50), 0);
    }

    @Test
    public void calculateDepartureTimeTest(){
        Simulation sim1 = new Simulation();
        LocalTime arrive = LocalTime.parse("06:59:00");
        LocalTime currentTime = LocalTime.parse("07:00:02");
        int counterWaitTime = 30;

        Event ev1 = new Event(0, arrive, Event.DEPARTURE);
        Customer customer1 = new Customer(0, arrive, null, 30, 0);
        sim1.addToCR(customer1);

        assertEquals(LocalTime.parse("07:00:32"), sim1.calculateDepartureTime(ev1, currentTime, counterWaitTime)); 
    }

    @Test
    public void updateCRDepartureTimeTest(){
        Simulation sim1 = new Simulation();

        Event ev1 = new Event(0, LocalTime.parse("07:00:32"), Event.DEPARTURE);
        Customer customer1 = new Customer(0, null, null, 0, 0);
        sim1.addToCR(customer1);

        assertEquals(LocalTime.parse("07:00:32"), sim1.updateCRDepartureTime(ev1)); 
    }

    @Test
    public void howLongTest(){
        Simulation sim1 = new Simulation();
        LocalTime depart = LocalTime.parse("07:00:00");
        Event ev1 = new Event(0, depart, Event.DEPARTURE);
        Customer customer1 = new Customer(0, LocalTime.parse("06:59:00"), depart, 0, 0);
        sim1.addToCR(customer1);

        assertEquals(60, sim1.calculateHowLong(ev1)); 
    }

}
