import java.time.LocalTime;

/**
 * Event creates a comparable  Event data type 
 * 
 * @author  Claire Liu
 * @version  October 27, 2020
 */
public class Event implements Comparable<Event>{
    //instance variables
    private int who; //the number of the customer
    private LocalTime time; //when the event will occur
    private int what; //ARRIVAL or DEPARTURE

    public static final int ARRIVAL = 1;
    public static final int DEPARTURE = 2; 

    /**
     * Event constructor
     * @param  name  the number of the customer
     * @param  tm  the local time of entry
     * @param  type  either ARRIVAL or DEPARTURE
     */
    public Event(int who, LocalTime time, int what){
        this.who = who;
        this.time = time;
        this.what = what;
    }

    /**
     * The Event class is comparable based on the time
     * 
     * @param  rhs  an Event to compare to 
     * 
     * @return <0 for less than, 0 for equals, >0 for greater than
     */
    @Override
    public int compareTo(Event rhs){
        return time.compareTo(rhs.time);  //uses the compareTo method from LocalTime class
    }

    /**
     * Gets who
     * @return who
     */
    public int getWho(){
        return who;
    }

    /**
     * Gets time
     * @return time
     */
    public LocalTime getTime(){
        return time;
    }

    /**
     * Get what 
     * @return gets what
     */
    public int getWhat(){
        return what;
    }

    /**
     * Sets new Who
     * @param  i  the customer's number
     * @return new who
     */
    public int setWho(int i){
        who = i;
        return who;
    }

    /**
     * Sets new time
     * @param  x  LocalTime to set time to 
     * @return new time
     */
    public LocalTime setTime(LocalTime x){
        time = x;
        return time;
    }

    /**
     * Sets new What
     * @param  i  either 1 (ARRIVAL) or 2 (DEPARTURE)
     * @return either 1 (ARRIVAL) or 2 (DEPARTURE)
     */
    public int setWhat(int i){
        what = i;
        return what;
    }
}

