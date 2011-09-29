import java.util.*;

class Mortgage {
  private int amount, duration;
  private double monthlyRate;

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
  
  public Mortgage(int amnt, double rate, int dura) {
    amount = amnt;
    monthlyRate = rate / 1200;
    duration = dura;
  }

  public double calcApr() {
    double apr = Math.pow((1 + monthlyRate), 12) - 1;
    return apr * 100;  //make into a percent
  }

  public double calcPayment() {
    double top = (amount * monthlyRate);
    double bottom =(1- 1/ Math.pow((1 + monthlyRate), duration * 12));
    return top / bottom;
  }
}