import org.junit.* ;
import static org.junit.Assert.* ;

public class DaysTests {

  
  public static void main(String[] args) {
    org.junit.runner.JUnitCore.main("DaysTests");
  }
  
  @Test
  public void daysSince1800()
  throws Days.DateOutOfBoundsException {
    assertTrue( 1 ==  Days.daysSince1800(1, 2, 1800) );
    assertTrue( 59 == Days.daysSince1800(3, 1, 1800) );
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
  