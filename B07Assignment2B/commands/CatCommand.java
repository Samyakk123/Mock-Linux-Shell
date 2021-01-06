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
package commands;

import filesystem.CommandsInterface;
import filesystem.ErrorOutput;
import filesystem.Output;

/**
 * 
 * Class responsible for reading the contents of a file.
 *
 */
public class CatCommand extends Commands implements CommandsInterface {

  /** the redirection that will be used */
  public Redirection redirection = new Redirection();
  private boolean redirect = false;
  private boolean toReturn = false;
  private boolean error = false;
  private boolean spacing = false;
  private boolean checkerTwo = false;

  private String getFileName(String pathWay) {
    String[] pathWayArray = pathWay.split("/");
    return pathWayArray[pathWayArray.length - 1];
  }

  /**
   * Executes Cat Command
   */
  protected void executeCommand() {

    String[] tempUserInput = super.userInput.split("\\s+");
    String fileName = getFileName(tempUserInput[1]);

    // Check if the pathway provided exists, and if it does go through it
    if (super.checkPath.pathExist(super.userInput).equals("PathFileExist")) {
      this.content += this.fileSystem.getFileContents(fileName);
      this.toReturn = true;
    }

    // Sends the workingDir back to match up with the CurrentWorkingDir
    fileSystem.goToCurrentWorkingDir();
  }

  // Redirection Helper
  private String redirectHelper(String originalInput) {

    if (this.redirect && checkerTwo) {

      // If the want to redirect (i.e. > or >> )
      super.content =
          this.redirection.executeRedirection(this.content, originalInput);
    }

    // If errors will return "" otherwise return the output back to Parse
    if (this.toReturn) {
      return super.content;
    }

    return null;
  }

  /**
   * Checks if input from user is valid before calling executeCommand
   * 
   * @return super.content the output string
   */
  public String checkInput() {
    String originalInput = super.userInput;
    String[] tempUserInput = super.userInput.split("\\s+");
    fileSystem.goToCurrentWorkingDir();
    int length = 0;
    length = tempUserInput.length;

    // Validate cat input from the user for validity
    boolean checker = validateInput.validateCatCommand(super.userInput);
    if (length > 3 && this.redirection
        .checkRedirection(tempUserInput[tempUserInput.length - 2])) {
      this.redirect = true;
      length = length - 2;
    }

    if (checker) {

      // For each pathway entered by the user go through it and perform
      // executeCommand();
      for (int i = 1; i < length; i++) {

        // Check if the pathway entered is a valid File
        if (super.checkPath.pathExist(tempUserInput[0] + " " + tempUserInput[i])
            .equals("PathFileExist")) {
          fileSystem.goToCurrentWorkingDir();
          super.setUserInput(tempUserInput[0] + " " + tempUserInput[i]);
          checkerTwo = true;

          // Spacing
          if (spacing) {
            this.content += "\n";
          }

          spacing = true;
          executeCommand();
        }

        else {
          ErrorOutput.pathDoesNotExist(tempUserInput[i]);
          error = true;
        }

      }
    }

    if (error) {
      Output.printWithNewLine("");
    }

    return redirectHelper(originalInput);
  }
}
