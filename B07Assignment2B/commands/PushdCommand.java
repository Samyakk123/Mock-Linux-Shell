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
import filesystem.ErrorOutput;
import filesystem.CommandsInterface;

/**
 * 
 * Class responsible for saving current location in fileSystem
 *
 */
public class PushdCommand extends Commands implements CommandsInterface {

  private StackClass stack = null;
  private CdCommand cdComm = new CdCommand();
  /** store new location to be used */
  public ArrayList<String> newLocation;

  /**
   * Constructor for PushdCommand
   */
  public PushdCommand() {

    // Creating singleton of stack class
    this.stack = StackClass.currentStackClassInstance();
  }

  private ArrayList<String> getCurrent() {

    // Gets the current directory of the user
    newLocation = new ArrayList<String>();
    for (int i = 0; i < fileSystem.getCurrentWorkingDirectory().size(); i++) {
      newLocation.add(fileSystem.getCurrentWorkingDirectory().get(i));
    }
    return newLocation;
  }

  /**
   * Executes pushd command
   */
  protected void executeCommand() {

    // Adds the current directory of user to stack and changes directory to the
    // one user inputed
    newLocation = getCurrent();
    stack.addNewLocation(newLocation);
    cdComm.setUserInput(super.userInput);
    cdComm.checkInput();
  }

  /**
   * Checks if input from user is valid before calling executeCommand
   * 
   * @return super.content the output string
   */
  public String checkInput() {

    // Checks if number of parameters inputed by user is one
    if (super.validateInput.checkNumberOfParameterOne(super.userInput)) {

      // Checks for double "/"
      if (super.userInput.split("\\s+")[1].contains("//")) {
        ErrorOutput.pathDoesNotExist(userInput.split("\\s+")[1]);
        super.content = null;
      }

      // Executes command if the path is of a directory and if it exists
      else if (super.userInput.split("\\s+")[1].split("/").length == 0
          || super.checkPath.pathExist(super.userInput)
              .equals("PathFolderExist")) {
        super.fileSystem.goToCurrentWorkingDir();

        // execute the pushd command
        executeCommand();
      }

      else {
        ErrorOutput.pathDoesNotExist(super.userInput.split("\\s+")[1]);
        super.content = null;
        super.fileSystem.goToCurrentWorkingDir();
      }

    }

    else {
      super.content = null;
    }

    return super.content;
  }
}
