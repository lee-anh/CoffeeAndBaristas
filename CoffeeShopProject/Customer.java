import java.time.LocalTime;

/**
 * Creates a customer data type
 *
 * @author Claire Liu
 * @version October 27, 2020
 */
public class Customer //implements Comparable?
{
    // instance variables
    private int customerNumber; 
    private LocalTime arrivalTime;
    private LocalTime departureTime;
    private int howLong; 
    private double profit;

    /**
     * Constructor
     * 
     * @param  customerNumber  customer number
     * @param  arrivalTime  time customer arrives
     * @param  departureTime  time customer departs
     * @param  howLong  the total time the customer waits in line and at the counter to be served (in seconds)
     * @param  profit  the total profit from serving the customer
     */
    public Customer(int customerNumber, LocalTime arrivalTime, LocalTime departureTime, int howLong, double profit){
        this.customerNumber = customerNumber;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.howLong = howLong;
        this.profit = profit;
    }

    /**
     * Gets customerNumber
     * @return the customer's number
     */
    public int getCustomerNumber(){
        return customerNumber;
    }

    /**
     * Gets ArrivalTime
     * @return the customer's arrival time
     */
    public LocalTime getArrivalTime(){
        return arrivalTime;
    }

    /**
     * Gets DepatureTime
     * @return the customer's departure time
     */
    public LocalTime getDepartureTime(){
        return departureTime;
    }

    /**
     * Gets howLong
     * @return how long the customer waited
     * 
     */
    public int getHowLong(){
        return howLong;
    }

    /**
     * Gets profit
     * @return the profit made from the customer
     */
    public double getProfit(){
        return profit;
    }

    /**
     * Set new customer number
     * @param  i  new customer number
     * @return new customer number
     */
    public int setCustomerNumber(int i){
        customerNumber = i; 
        return customerNumber;
    }

    /**
     * Set new arrivalTime
     * @param  x  new arrivalTime (LocalTime)
     * @return  new arrivalTime
     */
    public LocalTime setArrivalTime(LocalTime x){
        arrivalTime = x;
        return arrivalTime;
    }

    /**
     * Sets new departureTime
     * @param x  new departureTime(LocalTime)
     */
    public LocalTime setDepartureTime(LocalTime x){
        departureTime = x;
        return departureTime;
    }

    /**
     * Sets new howLong
     * @param i  new howLong (in seconds)
     * @return  new howLong
     */
    public int setHowLong(int i){
        howLong = i; 
        return howLong;
    }

    /**
     * Sets new profit
     * @param p new profit
     * @return new profit
     */
    public double setProfit(double p){
        profit = p;
        return profit;
    }
}
