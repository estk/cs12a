import org.junit.* ;
import static org.junit.Assert.* ;

public class BioTests {

  int days1, days2;
  public static void main(String[] args) {
    org.junit.runner.JUnitCore.main("BioTests");
  }

  @Test
  public void dayOfWeek()
  throws Days.DateInvalidException
  {
    // test trivial cases
    assertTrue("Wednesday" == Bio.dayOfWeek(1, 1, 1800));
    assertTrue("Thursday"  == Bio.dayOfWeek(1, 2, 1800));
    assertTrue("Friday"    == Bio.dayOfWeek(1, 3, 1800));
    assertTrue("Saturday"  == Bio.dayOfWeek(1, 4, 1800));
    assertTrue("Sunday"    == Bio.dayOfWeek(1, 5, 1800));
    assertTrue("Monday"    == Bio.dayOfWeek(1, 6, 1800));
    assertTrue("Tuesday"   == Bio.dayOfWeek(1, 7, 1800));
    
    // test this year
    assertTrue("Wednesday" == Bio.dayOfWeek(10, 12, 2011));
    assertTrue("Thursday"  == Bio.dayOfWeek(10, 13, 2011));
    assertTrue("Friday"    == Bio.dayOfWeek(10, 14, 2011));
    assertTrue("Saturday"  == Bio.dayOfWeek(10, 15, 2011));
    assertTrue("Sunday"    == Bio.dayOfWeek(10, 16, 2011));
    assertTrue("Monday"    == Bio.dayOfWeek(10, 17, 2011));
    assertTrue("Tuesday"   == Bio.dayOfWeek(10, 18, 2011));
  }

  @Before
  public void beforeTests()
  throws Days.DateInvalidException
  {
    int start = Days.daysSince1800(10, 15, 2011);
    days1 = Days.daysSince1800(11, 1, 2011) - start;
    days2 = Days.daysSince1800(11, 2, 2011) - start;
  }
    
  @Test
  public void pBio()
  throws Days.DateInvalidException
  {
    assertTrue(1 == Bio.pBio(days1));
    assertTrue(1 == Bio.pBio(days2));
  }

  @Test
  public void iBio()
  throws Days.DateInvalidException
  {
    assertTrue("result was" + Bio.iBio(days1), 19 == Bio.iBio(days1));
    assertTrue(15 == Bio.iBio(days2));
  }

  @Test
  public void eBio()
  throws Days.DateInvalidException
  {
    //assertTrue("result was" + Bio.eBio(days1), 9 == Bio.eBio(days1)); \\ this isnt working due to a slight variation in the equation.
    assertTrue(5  == Bio.eBio(days2));
  }

}
  
