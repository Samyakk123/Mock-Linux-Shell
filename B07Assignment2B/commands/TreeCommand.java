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

/*
 * 
 * Prints out all files in the fileSystem
 * 
 */
public class TreeCommand extends Commands implements CommandsInterface {

  private int spacing = 0;
  /** the redirection that will be used */
  public Redirection redirection = new Redirection();
  private boolean redirect = false;

  private void callRecursively(Directory name) {

    // Recursively goes through each directory given a directory name
    for (int i = 0; i < name.getTypeChildren().size(); i++) {
      this.printSpacing();

      // If redirection is given
      super.content += name.getTypeChildren().get(i).getContent() + "\n";

      // For proper output specificially spacing
      if (name.getTypeChildren().get(i).getType()) {
        this.spacing++;
        callRecursively(name.getTypeChildren().get(i));
        this.spacing--;
      }

    }
  }

  private void printSpacing() {

    // For proper output message
    for (int i = 0; i < spacing; i++) {
      super.content += " ";
    }
  }

  /**
   * Executes tree Command
   */
  protected void executeCommand() {

    // Go to start of file system
    super.fileSystem.goToRoot();
    this.spacing = 1;
    super.content += "/" + "\n";
    callRecursively(fileSystem.getWorkingDir());
    super.fileSystem.goToCurrentWorkingDir();
  }

  /**
   * Checks if input from user is valid before calling executeCommand
   * 
   * @return this.content the output string
   */
  public String checkInput() {

    // User input
    super.arr = super.userInput.split("\\s+");
    if (super.arr.length > 2
        && this.redirection.checkRedirection(super.arr[super.arr.length - 2])) {
      this.redirect = true;
    }

    if (this.redirect && super.arr.length == 3) {
      this.executeCommand();
    }

    else {

      // invalid input
      if (super.arr.length > 3) {
        super.content = null;
        ErrorOutput.printWithNewLine("Error: Invalid Parameters");
      }

      else if (!this.redirect
          && validateInput.checkNumberOfParameterZero(super.userInput)) {
        this.executeCommand();
      }

      else {
        super.content = null;
      }

    }

    // redirection given in input
    if (this.redirect && super.arr.length == 3) {

      super.content =
          this.redirection.executeRedirection(super.content, super.userInput);
    }

    return super.content;
  }
}
