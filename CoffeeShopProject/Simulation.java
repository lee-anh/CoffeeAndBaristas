import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.ArrayDeque;
import java.io.File;
import java.util.concurrent.ThreadLocalRandom;
import java.time.LocalTime;
import java.time.Duration;

/**
 * Class Simulation simulates the profit generated in a day at a coffee shop
 *
 * @author Claire Liu
 * @version October 25, 2020
 */
public class Simulation
{
    //instance variables
    private static final int TOTAL_BARISTAS = 1;
    private static final String CLOSINGTIME = "21:00:00"; 
    private static final File inputFile = new File("input.txt");

    //the following will be read from the file:
    private static double lowerLimitProfit;
    private static double upperLimitProfit;
    private static double baristaCost;
    private static int lowerLimitTime;
    private static int upperLimitTime;

    //variables that will change
    private static int availibleBaristas = TOTAL_BARISTAS; //initally, all baristas are availible 
    private static int customerNumber = 0;
    private static int totalNumberOverflowed; //totalNumberOverflowed/customerNumber *100 is the rate turned away
    private static double totalProfit; 

    private static ArrayList<Customer> customerRecords; //customer records //PUBLIC for unit testing
    private static Queue<Event> customerLine;//line in store
    private static PriorityQueue<Event> eventSet;//pending events //PUBlIC for unit testing

    /**
     * Constructor for the Simulation class
     */
    public Simulation(){
        customerRecords = new ArrayList<Customer>();
        customerLine = new ArrayDeque<Event>(); //change to LinkedList for comparison
        eventSet = new PriorityQueue<Event>();
    }

    /**
     * Main function 
     * 
     * @param  args  commandline arguements (none)
     */
    public static void main(String[] args){
        Simulation s = new Simulation();
        s.run();
    }

    /**
     * Run the simulation
     */
    public static void run(){

        readInput(); //read input file

        long startTime = System.currentTimeMillis(); //start timer
        runSim();
        long stopTime = System.currentTimeMillis(); //stop timer

        float overflowRate = overflowRate(); //overflowRate
        System.out.printf("Overflow Rate: %.2f%%\n", overflowRate);

        double netProfit = netProfit(); //net profit
        System.out.printf("Net Profit: $ %.2f \n", netProfit());

        float averageTime = averageTime(); //average time a customer waits
        System.out.printf("Average time a customer waited: %.2f seconds \n", averageTime);

        int maxTime = maxTime(); //max time a customer waits
        System.out.println("Longest time a customer waited: " + maxTime + " seconds"); 

        System.out.println("Program took: "+ (stopTime - startTime) +  " milliseconds to run");
    }

    /**
     * Calculates custsomer overflow rate 
     * 
     * @return  percentage of customers overflowed
     */
    public static float overflowRate(){
        return (float)totalNumberOverflowed/customerRecords.size() *100;
    }

    /**
     * Calulates the average time customers waited
     * 
     * @return   average time in seconds 
     */
    public static float averageTime(){
        int averageTimeSum = 0;
        //for each event in customerRecords, add howlong to the total sum
        for(int j = 0; j < customerRecords.size(); j++){
            averageTimeSum += customerRecords.get(j).getHowLong();
        }
        //average and return the times
        return (float) averageTimeSum/customerRecords.size();
    }    

    /**
     * Calculates the net profit 
     * 
     * @return  the net profit 
     */
    public static double netProfit(){
        return totalProfit - baristaCost * TOTAL_BARISTAS; 
    }

    /**
     * Finds maximum time a customer waited
     * 
     * @return  max time in seconds
     */
    public static int maxTime(){
        int maxTime = 0;
        //for each Event in customerRecords, if how long is greater than maxtime, replace maxTime with current Event's howLong
        for(int i = 0; i < customerRecords.size(); i++){
            if(customerRecords.get(i).getHowLong() > maxTime) 
                maxTime = customerRecords.get(i).getHowLong();
        }
        return maxTime;
    }

    /**
     * Reads input file
     */
    public static void readInput(){
        //read from the file
        Scanner scanner = null;
        try{
            scanner = new Scanner(inputFile);
            lowerLimitProfit = scanner.nextDouble();
            upperLimitProfit = scanner.nextDouble();
            baristaCost = scanner.nextDouble();
            lowerLimitTime = scanner.nextInt();
            upperLimitTime = scanner.nextInt();

            //read and add each customer arrival to the PriorityQueue
            while (scanner.hasNextLine()){
                LocalTime parsedTime = LocalTime.parse(scanner.next());
                if(parsedTime.compareTo(LocalTime.parse(CLOSINGTIME)) >= 0) break; 

                //add event to priority queue
                processNewEvent(customerNumber, parsedTime);
                //add new customer to customer records
                addToCustomerRecords(customerNumber, parsedTime);

                if(scanner.hasNextLine()) customerNumber++;
            }
        } catch (Exception e){
            System.out.println("Exception occured " + e);
        } finally {
            scanner.close();
        }
    }

    /**
     * Helper method to readInput, adds new Event to the eventSet PriorityQueue
     * 
     * @param  customerNumber  customer's number
     * @param  parsedTime  the customer's arrival time
     * @return the Event created
     */
    public static Event processNewEvent(int customerNumber, LocalTime parsedTime){
        //create new event that is an arrival
        Event ev = new Event(customerNumber, parsedTime, Event.ARRIVAL); 
        //add event to priority queue
        eventSet.add(ev);
        return ev;
    }

    /**
     * Helper method to readInput, adds new Customer to customerRecords
     * @param  customerNumber  customer's number
     * @param  parsedTime  the customer's arrival time
     * @return the Customer created
     */
    public static Customer addToCustomerRecords(int customerNumber, LocalTime parsedTime){
        //create new customer, preset to assuming the customer will just leave
        Customer customer = new Customer(customerNumber, parsedTime, parsedTime, 0, 0);
        //add customer to customerRecords
        customerRecords.add(customer);
        return customer;
    }

    /**
     * Runs the simulation
     */
    public static void runSim(){
        Event e = null;
        //while the eventSet is not empty
        while(!eventSet.isEmpty()){
            e = eventSet.poll(); //get the head (min) of the queue
            if(e.getWhat() == Event.DEPARTURE){ // process event as a DEPARTURE
                departure(e);
                if(customerLine.size() > 0)
                    serve(e.getTime()); //serve the next customer, the currentTime for the next customer is the departure time of the customer who just left
            } 
            else { // process event as an ARRIVAL
                arrival(e);
            }
        }
    }

    /**
     * Process a customer departure
     * 
     * @param  e  event to process
     */
    public static void departure(Event e){
        availibleBaristas++; //increment availibleBaristas
        //System.out.println("Customer " + e.who + " departs at time " + e.time + " and spent $" + customerRecords.get(e.who).profit + " and waited for " + customerRecords.get(e.who).howLong + " seconds.");
    }

    /**
     * Process a customer arrival
     * 
     * @param  e  event to process
     */
    public static void arrival(Event e){
        //System.out.print("Customer " + e.who + " arrives at time " + e.time + " ");
        if(customerLine.size() < 8*TOTAL_BARISTAS){
            customerLine.add(e);
            //serve the customer immediately if possible
            if(availibleBaristas > 0) {
                serve(e.getTime());
            }
            //System.out.println(); 
        } else {
            totalNumberOverflowed++;
            //System.out.println("and leaves because the line is too long");
        }
    }

    /**
     * Serve a customer
     * 
     * @param  currentTime  the time when the customer arrives at the counter to be served
     */
    public static void serve(LocalTime currentTime){
        Event e = customerLine.poll(); //remove customer from the line in the shop
        availibleBaristas--; //decrement availibleBaristas

        //profit from customers
        calculateProfit(e);

        //servingTime
        int servingTime = calculateServingTime(e);

        //Calculate depature time
        calculateDepartureTime(e, currentTime, servingTime);

        //calculate wait time
        calculateHowLong(e);

        //change event type to departure and add event back into priority queue.
        changeToDeparture(e);
        
        //add back to eventSet
        eventSet.add(e);
    }

    /**
     * Helper method to serve, changes event type to a departure
     * 
     * @param  e  Event to change
     * @param  2 (a departure) if change was sucessful
     */
    public static int changeToDeparture(Event e){
        e.setWhat(Event.DEPARTURE); //set event to a departure
        return e.getWhat();
    }

    /**
     * Helper method to serve, generates the profit from the customer and updates the program's records
     * 
     * @param  e  event to process
     * 
     * @return  the profit, rounded to 2 decimal places
     */
    public static void calculateProfit(Event e){
        //generate the profit to be made from the customer
        double profitRounded = generateProfit();

        //update customerRecords profit
        updateCRProfit(e, profitRounded);

        //add to total Profit
        updateTotalProfit(profitRounded);

    }

    /**
     * Helper Method to calculateProfit, generates the profit using ThreadLocalRandom
     * 
     * @return  the profit
     */
    public static double generateProfit(){
        //generate a profit within the specified the limit
        double profitRounded = Math.round(ThreadLocalRandom.current().nextDouble(lowerLimitProfit, upperLimitProfit)*100.0)/100.0;
        return profitRounded;
    }

    /**
     * Helper method to calculateProfit, updates the customerRecords
     * @param  e  event to proces
     * @param  profitRounded  profit generaged from generateProfit
     * @return  the profit from the customer
     */
    public static double updateCRProfit(Event e, double profitRounded){
        int who = e.getWho(); //specifies customer
        Customer c = customerRecords.get(who);
        c.setProfit(profitRounded); //sets the customer's profit in customerRecords
        return c.getProfit();
    }

    /**
     * Helper method to calculateProfit, updates the running total profit
     * 
     * @param  profitRounded  the profit from a custmer
     * @return  the current TotalProfit
     */
    public static double updateTotalProfit(double profitRounded){
        totalProfit += profitRounded;
        return totalProfit;
    }

    /**
     * Helper function to serve, generates how long it takes to serve a customer
     * 
     * @param  e  event to process
     * 
     * @return  the serving time in seconds
     */
    public static int calculateServingTime(Event e){
        //generate how long it will take to serve the customer
        int servingTime = ThreadLocalRandom.current().nextInt(lowerLimitTime, upperLimitTime+1);
        return servingTime;
    }

    /**
     * Helper function to serve, calculates the customer's departure time
     * 
     * @param  e  event to process
     * @param  currentTime  the time the customer walks up to the counter to be served
     * @param  servingTime  the amount of time it takes to serve the customer
     * @return the depatureTime from the Event
     */
    public static LocalTime calculateDepartureTime(Event e, LocalTime currentTime, int servingTime){
        //add the servingTime to the time that the customer arrives. This is the new departure time
        LocalTime departureTime = e.setTime(currentTime.plusSeconds(servingTime));
        //update customerRecords departure time 
        updateCRDepartureTime(e);
        return departureTime;
    }

    /**
     * Helper function to calculateDepartureTime, updates customerRecords with departure time
     * 
     * @param  e  event to process
     * @return  the departure time from customerRecords
     */
    public static LocalTime updateCRDepartureTime(Event e){
        int who = e.getWho(); //specified customer number
        Customer c = customerRecords.get(who);
        c.setDepartureTime(e.getTime()); //set customer's departure time 
        return c.getDepartureTime();
    }

    //unit tested
    /**
     * Helper function to serve, calculates how long a customer waited in line and to be served
     * 
     * @param  e  event to process
     * @return  the total wait time in seconds
     */
    public static int calculateHowLong(Event e){
        //update customerRecords howLong (total waittime)
        int who = e.getWho();
        Customer c = customerRecords.get(who);
        c.setHowLong((int) Duration.between(c.getArrivalTime(), c.getDepartureTime()).toSeconds()); //calculate how long (Departure - Arrival) and update the customerRecords
        return c.getHowLong(); //return for unit testing
    }

    /******************************** HELPER METHODS FOR UNIT TESTING *****************************************/

    /**
     * Helper method for unit testing, gets the number of baristas staffed
     * @return the total baristas
     */
    public int getTotalBaristas(){
        return TOTAL_BARISTAS;
    }

    /**
     * Helper method for unit testing, gets the number of availible baristas
     * @return number of availibleBaristas
     */
    public int getAvailibleBaristas(){
        return availibleBaristas;
    }

    /**
     * Helper method for unit testing, sets number of availible baristas
     * @param  i  the number of availible baristas
     * @return the number of availibleBaristas
     */
    public int setAvailibleBaristas(int i){
        availibleBaristas = i;
        return availibleBaristas;
    }

    /**
     * Helper method for unit testing, sets the barista cost
     * @param  x  the barista cost
     * @return the baristaCost
     */
    public double setBaristaCost(double x){
        baristaCost = x;
        return baristaCost;
    }

    /**
     * Helper method for unit testing, sets number overflowed
     * @param  i  number overflowed
     * @return  TotalNumberOverflowed
     */
    public int setTotalNumberOverflowed(int i){
        totalNumberOverflowed = i;
        return totalNumberOverflowed;
    }

    /**
     * Helper method for unit testing, sets totalProfit
     * @param  x  profit
     * @return total profit
     */
    public double setTotalProfit(double x){
        totalProfit = x;
        return totalProfit;
    }

    /**
     * Helper method for unit testing, adds a customer to the customerRecords
     * @param  c  customer to add
     * @return the customer added
     */
    public Customer addToCR(Customer c){
        customerRecords.add(c);
        return c;
    }

    /**
     * Helper method for unit testing, retrive a customer from customerRecords
     * @param  custNum  the customer's number
     * @return  the customer
     */
    public Customer getFromCR(int custNum){
        Customer c = customerRecords.get(custNum);
        return c;
    }

    /**
     * Helper method for unit testing, polls eventSet
     * @param event polled
     */
    public Event pollEventSet(){
        return eventSet.poll();
    }

    /**
     * Helper method for unit testing, polls customerLine
     * @param event polled
     */
    public Event pollCustomerLine(){
        return customerLine.poll();
    }
}
