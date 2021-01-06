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
import filesystem.FileSystem;
import filesystem.FileSystemInterface;
import filesystem.Validation;
import mock.MockCheckPath;
import mock.MockFileSystem;

/**
 *
 * Abstract Class Responsible for creating template that all commands to 
 * follow Contains common
 * methods and variables.
 *
 */
public abstract class Commands {

  /** the fileSystem that will be used */
  protected FileSystemInterface fileSystem = null;
  /** the validateInput that will be used */
  public Validation validateInput = null;
  /** the userInput that will be used */
  protected String userInput = null;
  /** the arr of userInput that will be used */
  protected String[] arr = null;
  /** the content that will be used */
  public String content = "";
  /** the checkPath that will be used */
  protected CheckPathInterface checkPath = null;

  /**
   * Constructor for Commands
   */
  public Commands() {

    // Getting most updated version of FileSystem
    this.fileSystem = FileSystem.currentFileSystemInstance();
    this.validateInput = new Validation();
    this.checkPath = new CheckPath();
  }

  /**
   * Creates Mock File System and Mock CheckPath for Junit Testing
   */
  public void setMock() {
    this.fileSystem = MockFileSystem.currentFileSystemInstance();
    this.checkPath = new MockCheckPath();
  }

  /**
   * Sets user input
   * 
   * @param newUserInput the new user input
   */
  public void setUserInput(String newUserInput) {
    this.userInput = newUserInput;
  }

  /**
   * Gets user input
   * 
   * @return this.userInput the user input
   */
  public String getUserInput() {

    // Gets user input
    return this.userInput;
  }

  protected abstract void executeCommand();

  public abstract String checkInput();
}
