/********************************/
/* Program: Days since 1800     */
/* Author: Evan Simmons         */
/* CMP 12A/L, Fall 2011         */
/* October 3rd, 2011            */
/*                              */
/* See javadoc for more info    */
/********************************/

import java.util.*;

class Days {
  
  static class DateOutOfBoundsException extends Exception {
    public DateOutOfBoundsException(String msg) { super(msg); }
  }
  
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
      System.out.println( "Bye." );
    }
    catch (DateOutOfBoundsException e) {
      System.out.println( "Date out of bounds: " + e.getMessage() );
    }
  }
  
  public static int daysSince1800(int month, int day, int year)
  throws DateOutOfBoundsException
  {
    isDayKosher(month, day, year); //check input
    int res = yearsToDaysSince1800(year);
    res += day;
    res += monthToDays(month);
    res += leaps(month, day, year);
    return res - 1;
  }
  
  public static void isDayKosher(int month, int day, int year)
  throws DateOutOfBoundsException
  {
    if (day < 1)     throw new DateOutOfBoundsException("Day too small");
    if (month < 1)   throw new DateOutOfBoundsException("Month too small");
    if (month > 12)  throw new DateOutOfBoundsException("Month too big");
    if (year < 1800) throw new DateOutOfBoundsException("Year must be >= 1800");
  
    if (day > daysInMonth(month)) {
      if ( isLeapYear(year) && month == 2 && day == 29);
      else throw new DateOutOfBoundsException("The month specified doesn't have that many days");
    }
  }
  
  
  public static int leaps(int month, int day, int year) {
    int res = (year - 1800) / 4; // every 4   years (from last occurence 1800) add 1
    res -= (year - 1800) / 100;  // every 100 years (from last occurence 1800) subtract 1
    res += (year - 1600) / 400;  // every 400 years (from last occurence 1600) add 1
    
    if ( isLeapYear(year) && month <= 2 )
      res--;   // there is already a leap day factored in if the year is a leap
    return res;
  }

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
   * Returns the number of days in a month
   * @param month the month in question
   * @returns the days in the month
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
   * Converts a year to days since 1, 1, 1800
   * @param year the year which is being converted
   * @returns an integer representing days
   */
  public static int yearsToDaysSince1800(int year) {
    return (year - 1800) * 365;
  }
  
  /**
   * Determines if the year given is a leap year
   * @param year the year that is being tested
   * @returns a boolean value of the leapness of the year
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