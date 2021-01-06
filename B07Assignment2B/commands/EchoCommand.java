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

import filesystem.Output;
import filesystem.CommandsInterface;

/**
 * 
 * Class responsible for creating/appending files given a string or out-puting
 *  straight to Jshell
 */
public class EchoCommand extends Commands implements CommandsInterface {

  private String echoType;
  /** the redirection class that will be used */
  public Redirection redirection = new Redirection();

  // Get input string from user
  private String getInputString(String userInput) {
    String toReturn = "";
    int startFromInput = userInput.indexOf("\"") + 1;
    int endFromInput = userInput.indexOf("\"", startFromInput);
    toReturn = userInput.substring(startFromInput, endFromInput);
    return toReturn;
  }

  /**
   * Executes Echo Command
   */
  protected void executeCommand() {
    super.arr = super.userInput.split("\\s+");
    this.content = getInputString(super.userInput);
    if (super.arr[super.arr.length - 2].equals(echoType)) {
      this.redirection.changeType(echoType);
      if (this.redirection.executeRedirection(this.content,
          super.userInput) == null) {
        super.content = null;
      } ;
    }
    fileSystem.goToCurrentWorkingDir();
  }

  /**
   * Checks if input from user is valid before calling executeCommand
   * 
   * @return super.content the output string
   */
  public String checkInput() {

    int validateOutput = validateInput.checkEchoCommand(super.userInput);

    // Also keeps in mind if you calling echo with ">" or ">>"
    if (validateOutput == 0) {
      this.echoType = "";
      this.executeCommand();
    }

    else if (validateOutput == 1) {
      this.echoType = ">";
      this.executeCommand();
      super.content = "";
    }

    else if (validateOutput == 2) {
      this.echoType = ">>";
      this.executeCommand();
      super.content = "";
    }

    else {
      super.content = null;
    }
    return super.content;
  }
}


