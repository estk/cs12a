import java.util.*;

public class Bio {
  
  public static void main(String[] args) {
    int birthMonth, birthDay, birthYear, chartMonth, chartYear, birthTo1800, daysToChart;
    Scanner scan = new Scanner(System.in);
    
    try {
      // get input
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
      String dayBorn = dayOfWeek(birthMonth, birthDay, birthYear);
      System.out.printf("    Did you know you were born on a %s?\n\n", dayBorn);
      daysToChart = Days.daysSince1800(chartMonth, 1, chartYear) 
                  - Days.daysSince1800(birthMonth, birthDay, birthYear) - 1; // subtract 1 day to match sample data
      printChart(daysToChart, chartMonth, chartYear);
    }
    catch (Days.DateInvalidException e) {
      System.out.println( "The date you entered is invalid: " + e.getMessage() );
    }
    finally {
      System.out.println( "Bye." );
    }
  }

  static int pBio(int days) {
    double tmp = 21 + Math.sin( (2* Math.PI *days)/23 ) * 20; // scale to an integer between 1 and 40 inclusive
    return (int) tmp;
  }

  static int iBio(int days) {
    double tmp = 21 + Math.sin( (2* Math.PI *days)/33 ) * 20;
    return (int) tmp;
  }

  static int eBio(int days) {
    double tmp = 21 + Math.sin( (2* Math.PI *days)/28 ) * 20;
    return (int) tmp;
  }
  
  static void printChart(int daysToChart, int chartMonth, int chartYear)
  throws Days.DateInvalidException
  {
    int phys, intel, emo;
    String day, bioStr, borderString   = "+--------------------+--------------------+", 
                        emptyBioString = "|                    +                    |\n";
    // preamble
    System.out.println("    Numeromancer Biorhythm Chart\n");
    System.out.printf("%-17s", "Date");
    System.out.println( borderString );
    
    for ( int i = 1; i <= Days.daysInMonth(chartMonth); i++) {
      bioStr = emptyBioString; // reset string
      phys = pBio(daysToChart + i); intel = iBio(daysToChart + i); emo = eBio(daysToChart + i);
      day = dayOfWeek(chartMonth, i, chartYear);
      // Add 'P', 'I' and 'E' to string
      bioStr = changeCharacter( phys, 'P', bioStr );
      bioStr = changeCharacter( intel, 'I', bioStr);
      bioStr = changeCharacter( emo, 'E', bioStr);
      // Print line info
      System.out.printf("%2d/%2d/%4d  %.3s  ", chartMonth, i, chartYear, day);
      System.out.print(bioStr);

      //System.out.printf("    p: %2d  i: %2d  e: %2d\n", phys, intel, emo); // show actual pie values (for debugging)
    }

    System.out.printf( "%17s" + borderString + "\n", "" );
  }
  
  static String dayOfWeek(int month, int day, int year)
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
    /** Returns a new string which is a copy of s,
        but with the nth character replaced by ch.
     */

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

// todo: document


/**
 * The Days class calculates the number of
 * days since 1, 1, 1800.
 * <p>
 * The number of days since 1, 1, 1800 can be calculated interactively
 * on the console, or programitically by calling its method daysSince1800
 * with the proper arguments

 @author Evan Simmons
 */

class Days {
  
  /**
   * Custom throwable for invalid Date Entries.
   */
  static class DateInvalidException extends Exception {
    public DateInvalidException(String msg) { super(msg); }
  }
  
  /**
   * Determines the number of days since 1, 1, 1800 (including leap days)

   @param month the month of the date
   @param day   the day of the date
   @param year  the year of the date
   @return      the number of days since 1800
   @throws Days.DateInvalidException if the date is not valid
   */
  
  public static int daysSince1800(int month, int day, int year)
  throws DateInvalidException
  {
    isDayValid(month, day, year); //check input
    int res = yearsToDaysSince1800(year);
    res += day;
    res += monthToDays(month);
    res += leaps(month, day, year);
    return res - 1;
  }
  
  /**
   * Determines if the date given is valid

   @param month the month of the date
   @param day   the day of the date
   @param year  the year of the date
   @throws Days.DateInValidException if the date is not valid
   */
   
  public static void isDayValid(int month, int day, int year)
  throws DateInvalidException
  {
    if (day < 1)     throw new DateInvalidException("Day too small");
    if (month < 1)   throw new DateInvalidException("Month too small");
    if (month > 12)  throw new DateInvalidException("Month too big");
    if (year < 1800) throw new DateInvalidException("Year must be >= 1800");

    if ( isLeapYear(year) && month == 2 && day == 29 );
    else if (day > daysInMonth(month)) {
      if ( month == 2 && day == 29 ) throw new DateInvalidException(year + " is not a leap year");
      else throw new DateInvalidException("The month specified doesn't have that many days");
    }
  }
  
  /**
   * Calculates and returns the number of leap days since 1, 1, 1800
   
   @param month the month of the date
   @param day   the day of the date
   @param year  the year of the date
   @return an integer representing leap days 
   */
  
  public static int leaps(int month, int day, int year) {
    int res = (year - 1800) / 4; // every 4   years (from last occurence 1800) add 1
    res -= (year - 1800) / 100;  // every 100 years (from last occurence 1800) subtract 1
    res += (year - 1600) / 400;  // every 400 years (from last occurence 1600) add 1
    
    if ( isLeapYear(year) && month <= 2 )
      res--;   // there is already a leap day factored in if the year is a leap
    return res;
  }
  
  /**
   * Caveats: Doesn't care about leap days
   @param month the month to be converted to days
   @returns the number of days in a year previous to the month
   */

  public static int monthToDays(int month) {
    int i, res = 0;
    for (i = month - 1 ; i > 0 ; i--) {
      res += daysInMonth(i);
    }
    return res;
  }
  
  /**
   * Caveats: Doesn't care about leap days
   @param month the month in question
   @return  an integer representing the number days in the month
   */
   
  public static int daysInMonth(int month) {
    switch (month) {
      case 1:
        return 31;
      case 2:
        return 28;
      case 3:
        return 31;
      case 4:
        return 30;
      case 5:
        return 31;
      case 6:
        return 30;
      case 7:
        return 31;
      case 8:
        return 31;
      case 9:
        return 30;
      case 10:
        return 31;
      case 11:
        return 30;
      case 12:
        return 31;
      default: return 0;
    }
  }
  
  /**
   * Converts a year to days since 1, 1, 1800 <p>
   * Caveats: Doesn't care about leap days
   @param year the year which is being converted
   @return an integer representing days
   */
   
  public static int yearsToDaysSince1800(int year) {
    return (year - 1800) * 365;
  }
  
  /**
   * Determines if the year given is a leap year<p>
   * Caveats: Doesn't care about leap days
   @param year the year that is being tested
   @return a boolean value representing the leapness of the year
   */
   
  public static boolean isLeapYear(int year) {
    if (year % 4 == 0) {
      if (year % 100 != 0)
        return true;
      else
        return year % 400 == 0;
    }
    return false;
  }
}

