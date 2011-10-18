import java.util.*;

public class Bio {

  
  public static void main(String[] args) {
    int birthMonth, birthDay, birthYear, chartMonth, chartYear, birthTo1800, daysToChart;
    String dayBorn;
    Scanner scan = new Scanner(System.in);
    
    try {
      System.out.print("Enter birth month: ");
      birthMonth = scan.nextInt();
      System.out.print("Enter birth day: ");
      birthDay   = scan.nextInt();
      System.out.print("Enter birth year: ");
      birthYear  = scan.nextInt();
      System.out.print("Enter desired month: ");
      chartMonth = scan.nextInt();
      System.out.print("Enter desired year: ");
      chartYear  = scan.nextInt();
      System.out.println("");
      // calculate then chart
      dayBorn = dayOfWeek(birthMonth, birthDay, birthYear);
      System.out.printf("    Did you know you were born on a %s?\n\n", dayBorn);
      daysToChart = Days.daysSince1800(chartMonth, 1, chartYear) 
                  - Days.daysSince1800(birthMonth, birthDay, birthYear);
      printChart(daysToChart, chartMonth, chartYear);
    }
    catch (Days.DateInvalidException e) {
      System.out.println( "The date you entered is invalid: " + e.getMessage() );
    }
    finally {
      System.out.println( "Bye." );
    }
  }

  public static int pBio(int days) {
    double tmp = 21 + Math.sin( (2.0* Math.PI *(double)days)/23.0 ) * 20; // scale to an integer between 0 and 19 inclusive
    return (int) tmp;
  }

  public static int iBio(int days) {
    double tmp = 21 + Math.sin( (2.0* Math.PI *(double)days)/28.0 ) * 20;
    return (int) tmp;
  }

  public static int eBio(int days) {
    double tmp = 21 + Math.sin( (2.0* Math.PI *(double)days)/33.0 ) * 20;
    return (int) tmp;
  }
  
  public static void printChart(int daysToChart, int chartMonth, int chartYear)
  throws Days.DateInvalidException
  {
    String dashes = "--------------------";
    System.out.println("    Numeromancer Biorhythm Chart\n");
    System.out.printf("%-17s+%s+%<s+\n", "Date", dashes);
    String bioStringTemp = "|                    +                    |";
    for ( int i = 1; i <= Days.daysInMonth(chartMonth); i++) {
      String day = dayOfWeek(chartMonth, i, chartYear);
      int phys = pBio(daysToChart + i), intel = iBio(daysToChart + i), emo = eBio(daysToChart + i);
      
      String bioStr = changeCharacter( phys, 'P', bioStringTemp);
      bioStr = changeCharacter( intel, 'I', bioStr);
      bioStr = changeCharacter( emo, 'E', bioStr);
      System.out.printf("%2d/%2d/%4d  %.3s  ", chartMonth, i, chartYear, day);
      System.out.print(bioStr);
      System.out.printf("    p: %2d  i: %2d  e: %2d\n", phys, intel, emo); // show actual pie values
    }
    System.out.printf("%17s+%s+%<s+\n", "", dashes);
  }
  
  public static String dayOfWeek(int month, int day, int year)
  throws Days.DateInvalidException
  {
    int days = Days.daysSince1800(month, day, year);
    switch (days % 7) {
      case 0:
          return "Wednesday";
      case 1:
          return "Thursday";
      case 2:
          return "Friday";
      case 3:
          return "Saturday";
      case 4:
          return "Sunday";
      case 5:
          return "Monday";
      case 6:
          return "Tuesday";
      default:
          return "Error";
    }
  }

      /*************************************************/
    /* Returns a new string which is a copy of s,    */
    /* but with the nth character replaced by ch.    */
    /*************************************************/

    static String changeCharacter( int n, char ch, String s )
    {
      // check to see if n is valid
      if (n < 0 || n > s.length()-1)
      {
          System.out.println( "Invalid parameters to changeCharacter\n" );
          return( null );
      }

      char[] tmp = s.toCharArray();
      tmp[n] = ch;
      return( new String(tmp) );
    }
}
