/********************************/
/* Program: Mortgage Calculator */
/* Author: Evan Simmons         */
/* CMP 12A/L, Fall 2011         */
/* October 1st, 2011            */
/*                              */
/* See javadoc for more info    */
/********************************/
import java.util.*;

/**
 * The Mortgage class calculates the apr and 
 * monthly payment of a mortgage.
 * <p>
 * Mortgage information can be calculated interactively
 * on the console, or programitically by initializing the
 * class with the proper arguments then calling the calculation
 * methods.
 * 
 * @author Evan Simmons
 */

public class Mortgage {

  private int amount, duration;
  private double monthlyRate;

  /**
   * Method for interactive calculation on the console.
   * Prompts for ammount and rate, then calculates the
   * apr and payment, prints the result to two decimal places.
   */

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in); 
    System.out.println("What is your loan amount?");
    int amnt = scan.nextInt();
    System.out.println("What is the interest rate?");
    double rate = scan.nextDouble();
    System.out.println("How many years is the loan for?");
    int dura = scan.nextInt();
    
    Mortgage mort = new Mortgage(amnt, rate, dura);
    
    System.out.println( "Your APR is:" );
    System.out.printf(  "%.2f percent \n", mort.calcApr() );
    System.out.println( "Your monthly mortgage payment is:" );
    System.out.printf(  "$%.2f \n", mort.calcPayment() );
  }

  /**
   * Constructor for Mortgage, takes as arguments
   * details of the loan.
   *
   * @param amnt the ammount of the loan
   * @param rate the percent interest rate of the loan
   * @param dura the duration of the loan in years
   */

  public Mortgage(int amnt, double rate, int dura) {
    amount = amnt;
    monthlyRate = rate / 1200;
    duration = dura;
  }

  /**
   * Calculates the apr of the Mortgage object
   * @return double
   */

  public double calcApr() {
    double apr = Math.pow((1 + monthlyRate), 12) - 1;
    return apr * 100;  //make into a percent
  }

  /**
   * Calculates the payment of the Mortgage object
   * @return double
   */

  public double calcPayment() {
    double top = (amount * monthlyRate);
    double bottom =(1- 1/ Math.pow((1 + monthlyRate), duration * 12));
    return top / bottom;
  }
}