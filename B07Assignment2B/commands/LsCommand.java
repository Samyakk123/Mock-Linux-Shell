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
import filesystem.Directory;
import filesystem.ErrorOutput;
import filesystem.Output;
import mock.MockFileSystem;
import commands.TreeCommand;

/**
 * 
 * Class responsible for getting contents of a directory or if called on file,
 *  gets name of file.
 *
 */
public class LsCommand extends Commands implements CommandsInterface {

  private String[] array;
  private String listOfDir;
  private String validPath = "";
  private int type = 1;
  private int space = 1;
  private ArrayList<String> tempLocation;
  /** the redirection that will be used */
  public Redirection redirection = new Redirection();
  private boolean redirect = false;
  private boolean toReturn = false;
  private int length = 0;
  private boolean checker = false;
  private boolean error = false;
  private boolean spacing = false;

  private void printPathWay() {

    // Print pathway for each path inputed for ls (ie /one : ______ )
    CdCommand cd = new CdCommand();
    this.tempLocation = new ArrayList<String>();

    for (int i = 0; i < fileSystem.getCurrentWorkingDirectory().size(); i++) {
      tempLocation.add(fileSystem.getCurrentWorkingDirectory().get(i));
    }

    // Call pwd command to get pathway
    cd.setUserInput(super.userInput);
    cd.checkInput();
    PwdCommand pwd = new PwdCommand();
    super.content += pwd.currentDirectory(this.redirect);
    this.fileSystem.changeCurrentWorkingDir(tempLocation);

  }

  /**
   * Executes Ls Command
   */
  protected void executeCommand() {

    super.arr = super.userInput.split("\\s+");

    if (super.arr.length > 1) {
      validPath = super.checkPath.pathExist(super.userInput);

      // Since super.checkPath changes CurrentWorkingDir
      this.fileSystem.goToCurrentWorkingDir();
    }

    if (validPath.equals("PathFolderExist") || super.arr.length == 1) {
      
      if (super.userInput.strip().endsWith("..")) {
        fileSystem.cdUp();
      }

      // To match formatting provided on the assignment sheet
      if (super.arr.length > 1) {
        if (!(this.fileSystem instanceof MockFileSystem)) {
          printPathWay();
        }
        super.content += ": ";
      }

      // Get the children (Directories and files) located inside of the current
      // directory
      if (type == 1) {
        typeOne();
      } 
      
      else {
        super.content += "\n";
        typeTwo(this.fileSystem.getWorkingDir());
      }

    } 
    
    else if (validPath.equals("PathFileExist")) {
      String[] temp = super.userInput.split(" ");
      String[] splitter = temp[temp.length - 1].split("/");

      // Print the fileName itself if it points to a file (i.e. if
      // returned PathFileExist)
      super.content += splitter[splitter.length - 1] + "\n";

    } 
    
    else {
      // DO ERROR MESSAGES GO TO THE FILE AS WELL?!
      ErrorOutput
          .printWithNewLine("Error: The input path provided does not exist!");
    }

    this.fileSystem.goToCurrentWorkingDir();
  }

  // Without -R inside inputed Ls command
  private void typeOne() {
    this.toReturn = true;
    listOfDir = this.fileSystem.getChildrenOfWorkingDir();
    array = listOfDir.split("\\s+");

    for (int i = 1; i < array.length; i++) {

      // Print the children in the current directory
      super.content += array[i];
      if (i != array.length - 1) {
        super.content += ", ";
      }
    }
  }

  // With -R inside inputed Ls command
  private void typeTwo(Directory name) {
    this.toReturn = true;

    for (int i = 0; i < name.getTypeChildren().size(); i++) {
      printSpacing();

      super.content += name.getTypeChildren().get(i).getContent() + "\n";

      if (name.getTypeChildren().get(i).getType()) {
        this.space++;
        typeTwo(name.getTypeChildren().get(i));
        this.space--;
      }
      
    }
  }

  // For desired output for ls
  private void printSpacing() {
    for (int i = 0; i < space; i++) {
      super.content += " ";
    }
  }

  // Redirection Helper
  private String redirectHelper(String originalInput) {
    
    if (this.redirect && checker) {
      
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

  private void helper(int i, String[] tempArray) {

    // for spacing
    this.checker = true;
    
    if (spacing) {
      super.content += "\n";
    }
    
    this.spacing = true;
    executeCommand();
  }

  /**
   * Checks if input from user is valid before calling executeCommand
   * 
   * @return super.content the output string
   */
  public String checkInput() {

    String originalInput = super.userInput;
    String[] tempArray = super.userInput.split("\\s+");
    fileSystem.goToCurrentWorkingDir();

    length = tempArray.length;

    // Use the validate command specially made for Ls&Cat (common functionality)

    if (length > 2 && tempArray[1].equals("-R")) {
      this.type = 2;
    }

    if (length > 2
        && this.redirection.checkRedirection(tempArray[tempArray.length - 2])) {
      this.redirect = true;
      length = length - 2;
    }

    // If multiple pathways are provided
    if (length > 1) {
      for (int i = type; i < length; i++) {

        // Check if path exist in file system
        String pathChecker =
            super.checkPath.pathExist(tempArray[0] + " " + tempArray[i]);
        fileSystem.goToCurrentWorkingDir();

        if (pathChecker.equals("PathFileExist")
            || pathChecker.equals("PathFolderExist")) {
          super.setUserInput(tempArray[0] + " " + tempArray[i]);
          this.helper(i, tempArray);

          // Print error
        } 
        
        else {
          ErrorOutput.pathDoesNotExist(tempArray[i]);
          error = true;
        }
        
      }
    } 
    
    else {
      if (tempArray.length == 3 && redirection.checkRedirection(tempArray[1])) {
        super.userInput = "ls";
      }
      checker = true;
      executeCommand();
    }

    // Spacing
    if (error) {
      Output.printWithNewLine("");
    }

    return redirectHelper(originalInput);
  }
}
