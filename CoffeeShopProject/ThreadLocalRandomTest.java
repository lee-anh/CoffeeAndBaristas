import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Write a description of class ThreadLocalRandomTest here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ThreadLocalRandomTest
{
    private static int availibleBaristas;
    private static double lowerLimitProfit;
    private static double upperLimitProfit;
    private static double baristaCost;
    private static int lowerLimitTime = 60;
    private static int upperLimitTime = 240;
    
    private static int customerNumber = 0;
    private static int inLine;
    private static int totalNumberOverflowed;
    private static double totalProfit;
    
    //private Random random = new Random(System.currentTimeMillis());
    
    public static void main(String[] args){
        int howLong = ThreadLocalRandom.current().nextInt(lowerLimitTime, upperLimitTime);
        System.out.println("How long: " + howLong);
        int howLong2 = ThreadLocalRandom.current().nextInt(lowerLimitTime, upperLimitTime);
        System.out.println("How long 2: " + howLong2);
        int howLong3 = ThreadLocalRandom.current().nextInt(lowerLimitTime, upperLimitTime);
        System.out.println("How long 3: " + howLong3);
        int howLong4 = ThreadLocalRandom.current().nextInt(lowerLimitTime, upperLimitTime);
        System.out.println("How long 4: " + howLong4);
    }
}
