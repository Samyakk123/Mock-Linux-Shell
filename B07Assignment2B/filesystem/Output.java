// **********************************************************
// Assignment2:
// Student1:Divyam Patel
// UTORID user_name: pate1006
// UT Student #: 1006139698
// Author: Divyam Patel
//
// Student2: Samyak Mehta
// UTORID user_name: mehtas28
// UT Student #: 1006298542
// Author: Samyak Mehta
//
// Student3: Aryan Patel
// UTORID user_name: pate1065
// UT Student #: 1006273514
// Author: Aryan Patel
//
// Student4: None
// UTORID user_name:
// UT Student #:
// Author:
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************
package filesystem;

/**
 * 
 * Outputs Message to JShell.
 *
 */
public class Output {

  public static void printToUser(String output) {
    System.out.println(output);
  }

  public static void printImproperInput() {
    System.out.println("Invalid Input!");
  }

  public static void stackIsEmptyMessage() {
    System.out.println("There are currently no saved locations!");
  }

  public static void printCurrentPathway(String output) {
    System.out.print(output);
  }

  public static void printWithNewLine(String output) {
    System.out.println(output);
  }

  public static void printWithoutNewLine(String output) {
    System.out.print(output);
  }
}
