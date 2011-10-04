/********************************/
/* Program: Days since 1800     */
/* Author: Evan Simmons         */
/* CMP 12A/L, Fall 2011         */
/* October 4rd, 2011            */
/*                              */
/* See javadoc for more info    */
/********************************/

import java.util.*;

/**
 * The Days class calculates the number of
 * days since 1, 1, 1800.
 * <p>
 * The number of days since 1, 1, 1800 can be calculated interactively
 * on the console, or programitically by calling its method daysSince1800
 * with the proper arguments

 @author Evan Simmons
 */

public class Days {
  
  /**
   * Custom throwable for invalid Date Entries.
   */
  static class DateInvalidException extends Exception {
    public DateInvalidException(String msg) { super(msg); }
  }
  
  /**
   * Main method for interactive calculation of the
   * number of days since 1, 1, 1800.
   */
  
  public static void main(String[] args) {
    
    Scanner scan = new Scanner(System.in);
    System.out.print( "Enter month: " );
    int month = scan.nextInt();
    System.out.print( "Enter day: " );
    int day = scan.nextInt();
    System.out.print( "Enter year: " );
    int year = scan.nextInt();
    
    try {
      int res = daysSince1800(month, day, year);
      System.out.println( "Number of days since 1/1/1800 is: " + res );
    }
    catch (DateInvalidException e) {
      System.out.println( "The date you entered is invalid: " + e.getMessage() );
    }
    finally {
      System.out.println( "Bye." );
    }
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
    switch (month) {
      case 1:
        return 0;
      case 2:
        return 31;
      case 3:
        return 59;
      case 4:
        return 90;
      case 5:
        return 120;
      case 6:
        return 151;
      case 7:
        return 118;
      case 8:
        return 212;
      case 9:
        return 243;
      case 10:
        return 273;
      case 11:
        return 304;
      case 12:
        return 334;
      default: return 0;
    }
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