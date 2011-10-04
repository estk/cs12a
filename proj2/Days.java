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
  class DataOutOfBounds extends Exception {}
  
  public static void main(String[] args) {
    
    Scanner scan = new Scanner(System.in);
    System.out.print( "Enter month: " );
    int month = scan.nextInt();
    System.out.print( "Enter day: " );
    int day = scan.nextInt();
    System.out.print( "Enter year: " );
    int year = scan.nextInt();
    
    int res = daysSince1800(month, day, year);
    System.out.println( "Number of days since 1/1/1800 is: " + res );
    System.out.println( "Bye." );
  }
  
  public static int daysSince1800(int month, int day, int year) throws DateOutOfBounds {
    //check input
    if (day > daysInMonth(month)) throw DataOutOfBounds;
    if (day < 1)     throw DataOutOfBounds;
    if (month < 1)   throw DataOutOfBounds;
    if (month > 12)  throw DataOutOfBounds;
    if (year < 1800) throw DataOutOfBounds;
    
    int res = daysSince1800(year);
    res += day;
    res += monthToDays(month);
    res += leaps(month, day, year);
    return res;
  }
  
  public static int leaps(int month, int day, int year) {
    
  }

  public static int monthToDays(int month) {
    switch (month) {
      case 1:
        return 31;
      case 2:
        return 59;
      case 3:
        return 90;
      case 4:
        return 120;
      case 5:
        return 151;
      case 6:
        return 118;
      case 7:
        return 212;
      case 8:
        return 243;
      case 9:
        return 273;
      case 10:
        return 304;
      case 11:
        return 334;
      case 12:
        return 365;
    }
  }
  
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
    }
  }
  
  public static int daysSince1800(int year) {
    return year - 1800;
  }
  
  public static boolean isLeapYear(int year) {
    if (year % 4 == 0)
      return year % 100 != 0;
    else 
      return year % 400 == 0;
  }
}