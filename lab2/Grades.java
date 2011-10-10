import java.util.*;

public class Grades {
  static class InvalidGradeException extends Exception {
    public InvalidGradeException(String message) { super(message); }
  }
  
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    System.out.print("Enter the grade letter: ");

    try {
      String str = scan.nextLine();
      char grade = Character.toUpperCase( str.charAt(0) );
      String meaning = gradeToMeaning(grade);
      System.out.printf("Grade letter %c means %s.\n", grade, meaning);
    }
    catch (InvalidGradeException e) {
      System.out.println( e.getMessage() );
    }
    finally { System.out.println("Bye."); }
  }
  
  public static String gradeToMeaning(char grade)
  throws InvalidGradeException
  {
    switch (grade) {
      case 'A':
        return "Average";
      case 'B':
        return "Bad";
      case 'C':
        return "Catastrophe";
      case 'D':
        return "Disowned";
      case 'F':
        return "Forever Forgotten";
      default:
        throw new InvalidGradeException("The grade entered is not valid.");
    }
  }
}