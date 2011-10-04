import org.junit.* ;
import static org.junit.Assert.* ;

public class DaysTests {

  
  public static void main(String[] args) {
    org.junit.runner.JUnitCore.main("DaysTests");
  }
  
  @Test
  public void daysSince1800()
  throws Days.DateOutOfBoundsException {

// provided input
    assertTrue("Expected 1, result was: "  + Days.daysSince1800(1, 2, 1800),
               1 ==  Days.daysSince1800(1, 2, 1800) );
    assertTrue("Expected 59, result was: " + Days.daysSince1800(3, 1, 1800),
               59 == Days.daysSince1800(3, 1, 1800) );
               
    Days.daysSince1800(2, 29, 2000);
    Days.daysSince1800(2, 29, 2004);

// first leap day
    assertTrue("Expected 1518, result was: " + Days.daysSince1800(2, 28, 1804),
               1518 == Days.daysSince1800(2, 28, 1804));
    assertTrue("Expected 1519, result was: " + Days.daysSince1800(2, 29, 1804),
               1519 == Days.daysSince1800(2, 29, 1804));
    assertTrue("Expected 1520, result was: " + Days.daysSince1800(3, 1, 1804),
               1520 == Days.daysSince1800(3, 1, 1804));

// many leap years later
    assertTrue("Expected 77,490, result was: " + Days.daysSince1800(2, 29, 2012),
               77490 == Days.daysSince1800(2, 29, 2012));
    assertTrue("Expected 77,491, result was: " + Days.daysSince1800(3, 1, 2012),
               77491 == Days.daysSince1800(3, 1, 2012));
    assertTrue("Expected 77,342, result was: " + Days.daysSince1800(10, 4, 2011),
               77342 == Days.daysSince1800(10, 4, 2011));

  }
  
  @Test(expected=Days.DateOutOfBoundsException.class)
  public void daysSince1800BadDays()
  throws Days.DateOutOfBoundsException
  {
    Days.daysSince1800(6, 31, 1921);
  }
  
  @Test(expected=Days.DateOutOfBoundsException.class)
  public void daysSince1800BadDays2()
  throws Days.DateOutOfBoundsException
  {
    Days.daysSince1800(1, 2, 1790);
  }
  
  @Test(expected=Days.DateOutOfBoundsException.class)
  public void daysSince1800BadDays3()
  throws Days.DateOutOfBoundsException
  {
    Days.daysSince1800(2, 40, 1921);
  }
  
  @Test(expected=Days.DateOutOfBoundsException.class)
  public void daysSince1800BadDays4()
  throws Days.DateOutOfBoundsException
  {
    Days.daysSince1800(2, 29, 1800);
  }
  
  @Test(expected=Days.DateOutOfBoundsException.class)
  public void daysSince1800BadDays5()
  throws Days.DateOutOfBoundsException
  {
    Days.daysSince1800(2, 29, 2001);
  }
  
// test 1st leap
  @Test
  public void leaps1() {
    assertTrue( 0 == Days.leaps(1, 1, 1804));
  }

  @Test
  public void leaps2() {
    assertTrue( 0 == Days.leaps(2, 28, 1804));
  }

  @Test
  public void leaps3() {
    assertTrue( 0 == Days.leaps(2, 29, 1804));
  }

  @Test
  public void leaps4() {
    assertTrue( 1 == Days.leaps(3, 1, 1804));
  }
// test later leaps

  @Test
  public void leaps5() {
    assertTrue( 24 == Days.leaps(2, 28, 1904));
  }

  @Test
  public void leaps6() {
    assertTrue("actual: " + Days.leaps(2, 29, 1904), 24 == Days.leaps(2, 29, 1904));
  }

  @Test
  public void leaps7() {
    assertTrue( 25 == Days.leaps(3, 1, 1904));
  }

  
  @Test
  public void isLeapYear() {
    assertTrue(  Days.isLeapYear(2000));
    assertFalse( Days.isLeapYear(2001));
    assertFalse( Days.isLeapYear(2002));
    assertFalse( Days.isLeapYear(2003));
    assertTrue(  Days.isLeapYear(2004));
    assertFalse( Days.isLeapYear(2005));
  }
  
  @Test // test just years
  public void yearToDaysSince1800() {
    assertTrue( 365*2 == Days.yearsToDaysSince1800(1802) );
  }
  
}
  