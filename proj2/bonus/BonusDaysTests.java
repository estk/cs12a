import org.junit.* ;
import static org.junit.Assert.* ;

public class BonusDaysTests {

  
  public static void main(String[] args) {
    org.junit.runner.JUnitCore.main("BonusDaysTests");
  }
  
  @Test
  public void daysBetween1()
  throws BonusDays.DateInvalidException
  {
    assertTrue( 62 == BonusDays.daysBetween(1, 2, 2011, 3, 4, 2011) );
  }

  @Test
  public void daysBetween2()
  throws BonusDays.DateInvalidException
  {
    assertTrue( 63 == BonusDays.daysBetween(3, 4, 2012, 1, 2, 2012) );
  }

  @Test
  public void daysSince1800()
  throws BonusDays.DateInvalidException {

// provided input
    assertTrue("Expected 1, result was: "  + BonusDays.daysSince1800(1, 2, 1800),
               1 ==  BonusDays.daysSince1800(1, 2, 1800) );
    assertTrue("Expected 59, result was: " + BonusDays.daysSince1800(3, 1, 1800),
               59 == BonusDays.daysSince1800(3, 1, 1800) );
               
    BonusDays.daysSince1800(2, 29, 2000);
    BonusDays.daysSince1800(2, 29, 2004);

// first leap day
    assertTrue("Expected 1518, result was: " + BonusDays.daysSince1800(2, 28, 1804),
               1518 == BonusDays.daysSince1800(2, 28, 1804));
    assertTrue("Expected 1519, result was: " + BonusDays.daysSince1800(2, 29, 1804),
               1519 == BonusDays.daysSince1800(2, 29, 1804));
    assertTrue("Expected 1520, result was: " + BonusDays.daysSince1800(3, 1, 1804),
               1520 == BonusDays.daysSince1800(3, 1, 1804));

// many leap years later
    assertTrue("Expected 77,490, result was: " + BonusDays.daysSince1800(2, 29, 2012),
               77490 == BonusDays.daysSince1800(2, 29, 2012));
    assertTrue("Expected 77,491, result was: " + BonusDays.daysSince1800(3, 1, 2012),
               77491 == BonusDays.daysSince1800(3, 1, 2012));
    assertTrue("Expected 77,342, result was: " + BonusDays.daysSince1800(10, 4, 2011),
               77342 == BonusDays.daysSince1800(10, 4, 2011));

  }
  
  @Test(expected=BonusDays.DateInvalidException.class)
  public void daysSince1800BadBonusDays()
  throws BonusDays.DateInvalidException
  {
    BonusDays.daysSince1800(6, 31, 1921);
  }
  
  @Test(expected=BonusDays.DateInvalidException.class)
  public void daysSince1800BadBonusDays2()
  throws BonusDays.DateInvalidException
  {
    BonusDays.daysSince1800(1, 2, 1790);
  }
  
  @Test(expected=BonusDays.DateInvalidException.class)
  public void daysSince1800BadBonusDays3()
  throws BonusDays.DateInvalidException
  {
    BonusDays.daysSince1800(2, 40, 1921);
  }
  
  @Test(expected=BonusDays.DateInvalidException.class)
  public void daysSince1800BadBonusDays4()
  throws BonusDays.DateInvalidException
  {
    BonusDays.daysSince1800(2, 29, 1800);
  }
  
  @Test(expected=BonusDays.DateInvalidException.class)
  public void daysSince1800BadBonusDays5()
  throws BonusDays.DateInvalidException
  {
    BonusDays.daysSince1800(2, 29, 2001);
  }
  
// test 1st leap
  @Test
  public void leaps1() {
    assertTrue( 0 == BonusDays.leaps(1, 1, 1804));
  }

  @Test
  public void leaps2() {
    assertTrue( 0 == BonusDays.leaps(2, 28, 1804));
  }

  @Test
  public void leaps3() {
    assertTrue( 0 == BonusDays.leaps(2, 29, 1804));
  }

  @Test
  public void leaps4() {
    assertTrue( 1 == BonusDays.leaps(3, 1, 1804));
  }
// test later leaps

  @Test
  public void leaps5() {
    assertTrue( 24 == BonusDays.leaps(2, 28, 1904));
  }

  @Test
  public void leaps6() {
    assertTrue("actual: " + BonusDays.leaps(2, 29, 1904), 24 == BonusDays.leaps(2, 29, 1904));
  }

  @Test
  public void leaps7() {
    assertTrue( 25 == BonusDays.leaps(3, 1, 1904));
  }

  
  @Test
  public void isLeapYear() {
    assertTrue(  BonusDays.isLeapYear(2000));
    assertFalse( BonusDays.isLeapYear(2001));
    assertFalse( BonusDays.isLeapYear(2002));
    assertFalse( BonusDays.isLeapYear(2003));
    assertTrue(  BonusDays.isLeapYear(2004));
    assertFalse( BonusDays.isLeapYear(2005));
  }
  
  @Test // test just years
  public void yearToBonusDaysSince1800() {
    assertTrue( 365*2 == BonusDays.yearsToDaysSince1800(1802) );
  }
  
}
  
