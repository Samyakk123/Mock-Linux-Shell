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
 * Output messages used by Commands.
 *
 */
public class ErrorOutput {

  public static void pathDoesNotExist(String path) {
    System.out.println("Error: The path '" + path + "' does not exist!");
  }

  public static void notValidInput(String output) {
    System.out.println("Error: The input '" + output + "' is not vaild!");
  }

  public static void wrongNumberOfParameters(String commandName,
      int numberOfParamInserted, String numberParamForCommand) {
    System.out
        .println("Error: " + commandName + " requires " + numberParamForCommand
            + " parameter(s), inputted " + numberOfParamInserted + ".");
  }

  public static void accessingFolder(String nameOfCommand) {
    System.out.println(
        "Error: " + nameOfCommand + " cannot be called on" + " directories!");
  }

  public static void accessingFile(String nameOfCommand) {
    System.out.println(
        "Error: " + nameOfCommand + " cannot be called on" + " files!");
  }

  public static void wrongCharacterUsed(String invalidCharacter) {
    System.out.println("Error: The " + invalidCharacter + " character cannot be"
        + " used " + "when creating the new directory/file.");
  }

  public static void directoryAlreadyExists(String userInput) {
    System.out.println(
        "Error: The directory with name '" + userInput + "' already exists!");
  }

  public static void fileAlreadyExists(String userInput) {
    System.out.println(
        "Error: The file with name '" + userInput + "' already exists!");
  }

  public static void printWithNewLine(String output) {
    System.out.println(output);
  }

  public static void notValidCommand() {
    System.out.println("Error: The inputted command is not valid!");
  }

  public static void wrongFormatOfFind() {
    System.out.println("Error: These are the wrong parameters of find!");
  }
}
