import org.junit.* ;
import static org.junit.Assert.* ;
import java.text.* ;

public class MortgageTests {
public Mortgage mort;
public DecimalFormat df, df2;
  
  public static void main(String[] args) {
    org.junit.runner.JUnitCore.main("MortgageTests");
  }
  @Before
  public void setUp() {
    df = new DecimalFormat("#.##");
    df2 = new DecimalFormat("####.##");
    mort = new Mortgage(500000, 4.5, 30);
  }
  
  @Test
  public void calcApr() {
    assertEquals( df.format(mort.calcApr()), df.format(4.59) );
  }
  
  @Test
  public void calcPayment() {
    assertEquals( df2.format(mort.calcPayment()), df2.format(2533.43) );
  }
}