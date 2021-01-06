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

import filesystem.CheckPath;
import filesystem.CommandsInterface;
import filesystem.ErrorOutput;

/**
 * 
 * Class responsible for making a new directory given a pathway.
 *
 */
public class MkdirCommand extends Commands implements CommandsInterface {

  private boolean toReturn = false;

  /**
   * Executes mkdir Command
   */
  protected void executeCommand() {

    String temp = super.userInput.split("\\s+")[1];
    super.arr = temp.split("/");

    // Go through the pathway provided by the user
    String checker = super.checkPath.pathExist(super.userInput);
    int length = super.arr.length;

    // Folder exist implies that pathway up until the last element exists
    if (checker.equals("FolderExist")) {
      this.toReturn = true;

      // Create the new folder
      super.fileSystem.addFolder(super.arr[length - 1]);

      // PathFolderExist means that entire pathway exists and leads to a
      // directory
    }

    else if (checker.equals("PathFolderExist")) {
      ErrorOutput.directoryAlreadyExists(super.arr[super.arr.length - 1]);

      // PathFolderExist means that entire pathway exists and leads to a file
    }

    else if (checker.equals("PathFileExist")) {
      ErrorOutput.fileAlreadyExists(super.arr[super.arr.length - 1]);
    }

    fileSystem.goToCurrentWorkingDir();
  }

  /**
   * Checks if input from user is valid before calling executeCommand
   * 
   * @return super.content the output string
   */
  public String checkInput() {
    String[] tempUserInput = super.userInput.split("\\s+");

    if (super.userInput.split("\\s+").length == 1) {
      validateInput.checkNumberOfParameterOne(super.userInput);
    }

    // Given multiple pathways
    else {
      for (int i = 1; i < tempUserInput.length; i++) {
        super.setUserInput(tempUserInput[0] + " " + tempUserInput[i]);
        if (validateInput.checkValidCharsAndPath(super.userInput)) {
          fileSystem.goToCurrentWorkingDir();
          executeCommand();
        }
      }
    }

    if (this.toReturn) {
      return super.content;
    }

    // if there is an error
    return null;
  }
}
