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
import filesystem.Directory;
import filesystem.Output;

/**
 * 
 * Class responsible for removing directory/file from file System
 *
 */
public class RemoveClass extends Commands implements CommandsInterface {

  /**
   * Execute rm Command
   */
  protected void executeCommand() {

    // Get current working dir
    fileSystem.getCurrentWorkingDirectory();
    String tempUserInput = super.userInput.split("\\s+")[1];
    super.arr = tempUserInput.split("/");

    // Check type of path
    String pathValidator = super.checkPath.pathExist(super.userInput);
    Directory checkParent = fileSystem.getWorkingDir();
    boolean checkNotParent = fileSystem.checkIfInCurrent(checkParent);

    // If folder exist, remove from file System
    if (pathValidator.equals("PathFolderExist") && checkNotParent) {
      fileSystem.getCurrentWorkingDirectory();

      if (arr.length > 0) {
        fileSystem.RemoveFromFileSystem(arr[arr.length - 1]);

      }

      else {
        fileSystem.RemoveFromFileSystem(arr[arr.length]);
      }

      // else you have an error
    }

    else {

      if (pathValidator.equals("PathFileExist")) {
        Output.printWithNewLine("You cannot Rm a file!");
      }

      else {
        Output.printWithNewLine("You cannot remove a parent!");
      }

      super.content = null;
    }
    fileSystem.goToCurrentWorkingDir();
  }

  /**
   * Checks if input from user is valid before calling executeCommand
   * 
   * @return super.content the output string
   */
  public String checkInput() {

    if (validateInput.checkNumberOfParameterOne(super.userInput)
        && validateInput.checkValidCharsAndPath(super.userInput)) {
      fileSystem.goToCurrentWorkingDir();
      executeCommand();
    }

    else {
      // Error
      super.content = null;
    }

    return super.content;
  }
}
