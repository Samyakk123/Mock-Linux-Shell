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
import java.util.Arrays;
import filesystem.CommandsInterface;

/**
 * 
 * Class responsible for accessing folders in fileSystem.
 *
 */
public class CdCommand extends Commands implements CommandsInterface {

  private String user_input;
  private boolean validPath = false;
  private ArrayList<String> newWorkingDir;
  private String pathWayType;

  // Accounting for special cases
  private void specialCase() {

    if (super.arr.length != 0) {

      // Save the results from going through the users entered pathway
      this.pathWayType = super.checkPath.pathExist(super.userInput);
      this.validPath = pathWayType.equals("PathFolderExist");
    }

    // if user enters only type / as pathway
    else if (super.arr.length == 0) {
      validPath = true;
    }

    else {
      this.fileSystem.changeCurrentWorkingDir(newWorkingDir);
    }

    // Return to the users Current Working Directory
    this.fileSystem.goToCurrentWorkingDir();
  }

  /**
   * Executes cd command
   */
  protected void executeCommand() {

    // Using the parent class, Commands, get the userInput
    this.user_input = super.userInput.split("\\s+")[1];
    super.arr = this.user_input.split("/");

    // Create an ArrayList beginning with a "/"
    newWorkingDir = new ArrayList<String>(Arrays.asList("/"));
    this.specialCase();

    if (this.user_input.equals("..")) {
      if (this.fileSystem.sizeOfCurrentWorkingDir() > 1) {

        // go up one in the fileSystem
        this.fileSystem.cdUp();

        // Remove it from the current working directory (an ArrayList)
        this.fileSystem.removeToCurrentWorkingDir();
      }
    }

    // If validPath defined in the helper method is true
    else if (this.validPath) {
      int pathType = 0;
      if (this.user_input.startsWith("/")) {

        // If it starts with a "/" the first element in arr will be "/"
        pathType = 1;
      }

      else {

        // If not an absolute pathway, we begin from our current working dir
        newWorkingDir = fileSystem.getCurrentWorkingDirectory();
      }

      for (int i = pathType; i < super.arr.length; i++) {
        if (super.arr[i].equals("..") && newWorkingDir.size() > 1) {

          // If ".." is found remove it from the working Dir
          newWorkingDir.remove(newWorkingDir.size() - 1);
        }

        else if (!super.arr[i].equals("..") && !super.arr[i].equals(".")) {
          newWorkingDir.add(super.arr[i]);
        }
      }

      // Set this workingDir as the new one saved in FileSystem
      this.fileSystem.changeCurrentWorkingDir(newWorkingDir);
    }

    else if (this.pathWayType.equals("PathFileExist")) {
      super.content = null;
      ErrorOutput.accessingFile("cd");
    }

    else if (!this.user_input.equals(".") && super.arr.length != 0) {
      super.content = null;
      ErrorOutput.pathDoesNotExist(this.user_input);
    }

    this.fileSystem.goToCurrentWorkingDir();
  }

  /**
   * Checks if input from user is valid before calling executeCommand
   * 
   * @return super.content the output string
   */
  public String checkInput() {
    boolean checker = true;
    if (super.userInput.split("\\s+").length == 1) {

      // Check validPath that is common for all commands that take in 1 param
      validateInput.checkNumberOfParameterOne(super.userInput);
    }

    else if (super.userInput.split("\\s+")[1].startsWith("//")) {
      checker = false;
      super.content = null;
      ErrorOutput.pathDoesNotExist(super.userInput.split("\\s+")[1]);
    }

    else {

      // If this returns true then execute the Command
      if (validateInput.checkNumberOfParameterOne(super.userInput) && checker) {
        executeCommand();
      }

      else {
        super.content = null;
      }
    }

    return super.content;

  }
}
