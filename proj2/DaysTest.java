import org.junit.* ;
import static org.junit.Assert.* ;
import java.text.* ;

public class DaysTests {

  
  public static void main(String[] args) {
    org.junit.runner.JUnitCore.main("MortgageTests");
  }
  
  @test
  public void daysSince1800() {
    assertTrue( 1 ==  Days.daysSince1800(1, 2, 1800) );
    assertTrue( 59 == Days.daysSince1800(3, 1, 1800) );
  }
  
  @test(expected=DateOutOfBoundsException.class)
  public void daysSince1800BadDays() {
    Days.daysSince1800(6, 31, 1921);
  }
  
  @test(expected=DateOutOfBoundsException.class)
  public void daysSince1800BadDays() {
    Days.daysSince1800(1, 2, 1790);
  }
  
  @test
  public void isLeapYear() {
    assertTrue(  Days.isLeapYear(2000));
    assertFalse( Days.isLeapYear(2001));
    assertFalse( Days.isLeapYear(2002));
    assertFalse( Days.isLeapYear(2003));
    assertTrue(  Days.isLeapYear(2004));
    assertFalse( Days.isLeapYear(2005));
  }
  
  @test // test just years
  public void daysSince1800() {
    assertTrue( 365*2 == daysSince1800(1802) );
  }
  
}
  