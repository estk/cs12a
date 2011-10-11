import org.junit.* ;
import static org.junit.Assert.* ;

public class GradesTests {


  public static void main(String[] args) {
    org.junit.runner.JUnitCore.main("GradesTests");
  }
  
  @Test
  public void aToMeaning()
  throws Grades.InvalidGradeException
  {
    assertTrue("Average" == Grades.gradeToMeaning('a'));
    assertTrue("Average" == Grades.gradeToMeaning('A'));
  }
  
  @Test
  public void bToMeaning()
  throws Grades.InvalidGradeException
  {
    assertTrue("Bad" == Grades.gradeToMeaning('b'));
    assertTrue("Bad" == Grades.gradeToMeaning('B'));
  }
  
  @Test
  public void cToMeaning()
  throws Grades.InvalidGradeException
  {
    assertTrue("Catastrophe" == Grades.gradeToMeaning('c'));
    assertTrue("Catastrophe" == Grades.gradeToMeaning('C'));
  }
  
  @Test
  public void dToMeaning()
  throws Grades.InvalidGradeException
  {
    assertTrue("Disowned" == Grades.gradeToMeaning('d'));
    assertTrue("Disowned" == Grades.gradeToMeaning('D'));
  }
  
  @Test
  public void fToMeaning()
  throws Grades.InvalidGradeException
  {
    assertTrue("Forever Forgotten" == Grades.gradeToMeaning('f'));
    assertTrue("Forever Forgotten" == Grades.gradeToMeaning('F'));
  }
  
  @Test(expected=Grades.InvalidGradeException.class)
  public void eToMeaningFailure()
  throws Grades.InvalidGradeException
  {
    Grades.gradeToMeaning('e');
  }
  
  @Test(expected=Grades.InvalidGradeException.class)
  public void bigEToMeaningFailure()
  throws Grades.InvalidGradeException
  {
    Grades.gradeToMeaning('E');
  }

  @Test(expected=Grades.InvalidGradeException.class)
  public void gToMeaningFailure()
  throws Grades.InvalidGradeException
  {
    Grades.gradeToMeaning('g');
  }

  @Test(expected=Grades.InvalidGradeException.class)
  public void bigGToMeaningFailure()
  throws Grades.InvalidGradeException
  {
    Grades.gradeToMeaning('G');
  }
}