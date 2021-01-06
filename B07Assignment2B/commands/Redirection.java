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
import filesystem.CheckPathInterface;
import filesystem.ErrorOutput;
import filesystem.FileSystem;
import filesystem.FileSystemInterface;
import filesystem.Output;
import filesystem.Validation;
import mock.MockCheckPath;
import mock.MockFileSystem;


/**
 * 
 * Class responsible for redirecting to file if redirection provided in input
 *
 */
public class Redirection {

  private String userInput;
  private String type;
  private FileSystemInterface fileSystem;

  /** the current validateInput that will be used */
  public Validation validateInput = new Validation();
  /** the current checkPath that will be used */
  public CheckPathInterface checkPath = new CheckPath();

  /**
   * Creates Mock File System and CheckPath for Junit Testing
   */
  public void setMock() {
    this.checkPath = new MockCheckPath();
    this.validateInput.setMock();
    this.fileSystem = MockFileSystem.currentFileSystemInstance();
  }

  /**
   * Constructor for Redirection
   */
  public Redirection() {
    this.fileSystem = FileSystem.currentFileSystemInstance();
  }

  /**
   * 
   * Check if redirection provided in input
   * 
   * @param indexOfRedirection the input string at 2nd last index
   * @return boolean (true/false)
   */
  public boolean checkRedirection(String indexOfRedirection) {

    if (indexOfRedirection.equals(">")) {
      this.type = ">";
      return true;
    }

    else if (indexOfRedirection.equals(">>")) {
      this.type = ">>";
      return true;
    }

    return false;
  }

  private String getFileName(String pathWay) {
    String[] pathWayArray = pathWay.split("/");
    return pathWayArray[pathWayArray.length - 1];
  }

  /**
   * Execute redirection
   * 
   * @param content the current output content
   * @param userInput the user input
   */
  public String executeRedirection(String content, String userInput) {
    this.userInput = userInput;
    String[] arr = this.userInput.split("\\s+");
    String path = arr[arr.length - 1];
    String fileName = this.getFileName(arr[arr.length - 1]);
    String validPath = this.checkPath.pathExist(arr[0] + " " + path);

    // Validate input
    if (this.validateInput.checkValidChars(arr[arr.length - 1])) {

      // No such path exist in file System
      if (validPath.equals("NoPath")) {
        ErrorOutput.pathDoesNotExist(path);
        return null;
      }

      else if (validPath.equals("PathFolderExist")) {
        Output.printWithNewLine("Error: Can not Output to a directory!");
        return null;

        // If file exist in fileSystem
        // Add dependent on if its > or >>
      }

      else if (validPath.equals("PathFileExist")) {
        if (this.type.equals(">>")) {
          fileSystem.setFileContents(fileName,
              fileSystem.getFileContents(fileName) + "\n" + content);
          return "";

        }

        else {
          fileSystem.setFileContents(fileName, content);
          return "";
        }

      }

      else {
        fileSystem.addFile(fileName, content);
        return "";
      }

      // Output Error
    }

    else {
      Output.printWithNewLine("Error: Invalid Characters for a file!");
      return null;
    }
  }

  /**
   * 
   * Change type of redirection ( > or >>)
   * 
   * @param type the new type ( > or >>)
   */
  public void changeType(String type) {
    this.type = type;
  }
}
