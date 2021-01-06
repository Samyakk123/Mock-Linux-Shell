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
import filesystem.ErrorOutput;
import filesystem.Output;
import java.util.ArrayList;

/*
 * 
 * Responsible for finding all paths to a given folder/directory given a path
 *
 */
public class FindCommand extends Commands implements CommandsInterface {

  private String[] tempArr = new String[3];
  /** the redirection class that will be used */
  public Redirection redirection = new Redirection();
  private boolean redirect = false;
  private boolean spacing = false;
  private boolean spacing2 = false;
  private boolean toReturn = false;
  private boolean checkerTwo = false;
  private boolean error = false;

  /**
   * Executes Find Command
   */
  protected void executeCommand() {

    // type can be 0 (searching for a folder)
    // type can be 1 (searching for a directory)
    int type = 0;
    this.tempArr[2] = this.tempArr[2].replaceAll("\"", "");

    // Check if path exist
    String pathChecker =
        super.checkPath.pathExist("find" + " " + this.tempArr[0]);
    if (!tempArr[0].equals("/") && tempArr[0].split("/")[0].equals("..")) {
      fileSystem.cdUp();
    }

    // differentiate between type of file/directory
    if (this.tempArr[1].equals("f")) {
      type = 1;
    }

    else if (this.tempArr[1].equals("d")) {
      type = 2;
    }

    // Execute command if path exist
    if (!pathChecker.equals("NoPath") && !pathChecker.equals("FolderExist")) {

      // Depending on if its a file / directory
      if (type == 1) {
        recursiveTypeOne(fileSystem.getWorkingDir(), this.tempArr[2]);
      }

      else if (type == 2) {
        recursiveTypeTwo(fileSystem.getWorkingDir(), this.tempArr[2]);
      }

    }
    fileSystem.goToCurrentWorkingDir();
  }

  // Find all folders given a pathway
  private void recursiveTypeOne(Directory currentLocation, String name) {

    // Loop through all files in current location
    for (int i = 0; i < currentLocation.getTypeChildren().size(); i++) {

      // For each folder found, loop through that folder using recursion
      if (currentLocation.getTypeChildren().get(i).getType()) {
        recursiveTypeOne(currentLocation.getTypeChildren().get(i), name);

        // Add name of file / folder into output content
      } else {
        if (currentLocation.getTypeChildren().get(i).getContent()
            .equals(name)) {
          if (this.spacing2) {
            super.content += "\n";
          }
          this.spacing2 = true;
          super.content += getReturnValue(currentLocation, name);
        }
      }
    }
  }

  // Find all directories given a pathway
  private void recursiveTypeTwo(Directory currentLocation, String name) {

    // Loop through all files in current location
    for (int i = 0; i < currentLocation.getTypeChildren().size(); i++) {
      if (currentLocation.getTypeChildren().get(i).getType()) {

        // Add name of file / folder into output content
        if (currentLocation.getTypeChildren().get(i).getContent()
            .equals(name)) {
          if (this.spacing2) {
            this.content += "\n";
          }
          this.spacing2 = true;
          this.content += getReturnValue(currentLocation, name);
        }

        // For each folder found, loop through that folder using recursion
        recursiveTypeTwo(currentLocation.getTypeChildren().get(i), name);
      }
    }
  }

  // From current file and inputed file/dir name, returns you a string
  private String getReturnValue(Directory endLoc, String name) {
    this.toReturn = true;

    // Contain temp location
    ArrayList<String> tempLocation = new ArrayList<String>();
    String stringToReturn = "";

    // Loop through each folder and add it's content
    while (!endLoc.equals(fileSystem.getWorkingDir())) {

      tempLocation.add(endLoc.getContent());
      endLoc = endLoc.getParent();
    }
    for (int i = tempLocation.size() - 1; i >= 0; i--) {
      stringToReturn += tempLocation.get(i) + "/";
    }
    stringToReturn += name;

    return stringToReturn;
  }

  // Redirection Helper
  private String redirectHelper() {

    if (this.redirect && checkerTwo) {

      // If the want to redirect (i.e. > or >> )
      super.content =
          this.redirection.executeRedirection(this.content, super.userInput);
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

    int length = 0;
    String[] tempUserInput = super.userInput.split("\\s+");
    length = tempUserInput.length;

    // If input contains redirection
    if (length > 7 && this.redirection
        .checkRedirection(tempUserInput[tempUserInput.length - 2])) {
      this.redirect = true;
      length = length - 2;
    }

    // Validate input
    boolean checker =
        validateInput.validateFindCommand(super.userInput, length);

    if (checker) {
      this.tempArr[1] = tempUserInput[length - 3];
      this.tempArr[2] = tempUserInput[length - 1];

      // If multiple pathways are given in input
      for (int i = 1; i < length - 4; i++) {

        // Check each pathway
        String pathChecker = super.checkPath
            .pathExist(tempUserInput[0] + " " + tempUserInput[i]);
        fileSystem.goToCurrentWorkingDir();
        boolean path = pathChecker.equals("PathFileExist")
            || pathChecker.equals("PathFolderExist");

        if (path) {
          this.tempArr[0] = tempUserInput[i];
          checkerTwo = true;

          // Spacing
          if (spacing) {
            this.content += "\n";
          }
          spacing = true;
          this.executeCommand();
        }

        else {

          // Output error
          error = true;
          ErrorOutput.pathDoesNotExist(tempUserInput[i]);
        }

      }
    }

    if (error) {
      Output.printWithNewLine("");
    }

    return redirectHelper();
  }
}

