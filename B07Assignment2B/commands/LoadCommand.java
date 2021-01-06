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

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import filesystem.CommandsInterface;
import filesystem.ErrorOutput;
import filesystem.FileSystem;

/**
 * 
 * Class Responsible for loading any saved JShell from computer
 *
 */
public class LoadCommand extends Commands implements CommandsInterface {

  private static String filepath = null;

  /**
   * Executes load Command
   */
  protected void executeCommand() {

    // Loads the file saved on the actual file system of the computer
    super.arr = super.userInput.split("\\s+");
    filepath = super.arr[1];

    try {

      // File exists
      FileInputStream file = new FileInputStream(filepath);
      ObjectInputStream object = new ObjectInputStream(file);
      FileSystem.setFileSystem((FileSystem) object.readObject());
      object.close();
      FileSystem fileSystem = FileSystem.currentFileSystemInstance();
      fileSystem.setStackOfStackClass();
      fileSystem.setHistoryOfHistoryClass();
      // File does not exist
    } catch (Exception FileNotFoundException) {
      super.content = null;
      ErrorOutput.printWithNewLine("Error: The path does not exist!");
    }

  }

  /**
   * Checks if input from user is valid before calling executeCommand
   * 
   * @return super.content the output string
   */
  public String checkInput() {

    if (super.validateInput.checkNumberOfParameterOne(super.userInput)) {
      executeCommand();
    }

    else {
      super.content = null;
    }

    return super.content;
  }
}
