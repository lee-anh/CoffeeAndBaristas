
import java.time.LocalTime;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class EventTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class EventTest
{
    /**
     * Default constructor for test class EventTest
     */
    public EventTest()
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
    public void getWhoTest()
    {
        Event event1 = new Event(0, null, 1);
        assertEquals(0, event1.getWho());
    }
    
    @Test
    public void getWhatTest()
    {
        Event event1 = new Event(0, null, 1);
        assertEquals(1, event1.getWhat());
    }
    
    @Test
    public void getTimeTest()
    {
        LocalTime depart = LocalTime.parse("07:00:00");
        Event event1 = new Event(0, depart, 1);
        assertEquals(depart, event1.getTime());
    }
    
    @Test
    public void setWhoTest()
    {
        Event event1 = new Event(0, null, 1);
        assertEquals(1, event1.setWho(1));
    }
    
    @Test
    public void setWhatTest()
    {
        Event event1 = new Event(0, null, 1);
        assertEquals(2, event1.setWhat(2));
    }
    
    @Test
    public void setTimeTest()
    {
        LocalTime depart = LocalTime.parse("07:00:00");
        Event event1 = new Event(0, null, 1);
        assertEquals(depart, event1.setTime(depart));
    }
    
}

