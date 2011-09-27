import java.util.*;

class Mortgage {
  private static int amount, duration;
  private static double monthlyRate;

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in); 
    System.out.println("What is your loan amount?");
    amount = scan.nextInt();
    System.out.println("What is the interest rate?");
    monthlyRate = scan.nextDouble() / 1200;
    System.out.println("How many years is the loan for?");
    duration = scan.nextInt();
    
    System.out.println( "Your APR is:" );
    System.out.printf(  "%.2f percent \n", calcApr() );
    System.out.println( "Your monthly mortgage payment is:" );
    System.out.printf(  "$%.2f \n", calcPayment() );
  }

  private static double calcApr() {
    double apr = Math.pow((1 + monthlyRate), 12) - 1;
    return apr * 100;  //make into a percent
  }

  private static double calcPayment() {
    double top = (amount * monthlyRate);
    double bottom =(1- 1/ Math.pow((1 + monthlyRate), duration * 12));
    return top / bottom;
  }
}