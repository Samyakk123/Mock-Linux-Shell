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

import java.util.ArrayList;
import filesystem.CommandsInterface;
import filesystem.ErrorOutput;
import filesystem.FileSystem;
import filesystem.Output;

/**
 * 
 * Class responsible for printing out current working directories pathway.
 *
 */
public class PwdCommand extends Commands implements CommandsInterface {

  /** the redirection that will be used */
  public Redirection redirection = new Redirection();
  private boolean redirect = false;

  /**
   * Executes pwd command
   */
  protected void executeCommand() {

    // Returns the users current location in the directory
    // For junit testing using instanceof
    if (this.fileSystem instanceof FileSystem) {
      this.fileSystem = FileSystem.currentFileSystemInstance();
    }

    ArrayList<String> pathWay = fileSystem.getCurrentWorkingDirectory();
    String fullPathway = "";

    for (int i = 0; i < pathWay.size(); i++) {
      if (pathWay.get(i).equals("/")) {
        fullPathway = "/";
        continue;
      }

      else {

        // Builds the pathway in a string
        fullPathway += pathWay.get(i) + "/";
      }

    }

    // If they are not in the root directory set the value without the ending '/'
    if (!fullPathway.equals("/")) {
      fullPathway = fullPathway.substring(0, fullPathway.length() - 1);
    }

    // Print it to terminal
    super.content = fullPathway;
  }

  /**
   * Checks if input from user is valid before calling executeCommand
   * 
   * @return super.content the output string
   */
  public String checkInput() {
    super.arr = super.userInput.split("\\s+");

    // Check if redirection is provided in input
    if (super.arr.length > 2
        && this.redirection.checkRedirection(super.arr[super.arr.length - 2])) {
      this.redirect = true;
    }

    if (this.redirect && super.arr.length == 3) {
      executeCommand();

      // If no redirection
    }

    else {

      if (super.arr.length > 3) {
        super.content = null;
        ErrorOutput.printWithNewLine(
            "Error: The inputted parameters are not correct!");
      }

      else if (!this.redirect
          && validateInput.checkNumberOfParams(super.userInput, 0)) {
        executeCommand();

        // Proper spacing
        super.content += "\n";
      }

      else {
        super.content = null;
      }

    }

    // If redirection provided, set content to empty so it does not print in to
    // Jshell
    if (this.redirect && super.arr.length == 3) {
      super.content =
          this.redirection.executeRedirection(super.content, super.userInput);
    }

    return super.content;
  }

  /**
   * Executes pwd command without checking input. Used to get current directory
   * when user input is not required.
   * 
   * @param redirect boolean(true/false) provided redirection
   * @return super.content the output string
   */
  public String currentDirectory(boolean redirect) {

    if (redirect) {
      this.redirect = true;
    }

    this.executeCommand();
    return super.content;
  }
}
