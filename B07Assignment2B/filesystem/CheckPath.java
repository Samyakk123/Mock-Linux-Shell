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
package filesystem;

import mock.MockFileSystem;

/**
 * 
 * Responsible for returning a string of the type of path given.
 *
 */
public class CheckPath implements CheckPathInterface {

  private FileSystemInterface fileSystem;
  private boolean mock = false;

  /**
   * Creates Mock File System for Junit Testing
   */
  public void setMock() {
    this.fileSystem = MockFileSystem.currentFileSystemInstance();
    this.mock = true;
  }

  /**
   * Returns a string with a specific type of pathway given userInput
   * 
   * @param userInput the user input
   * @return String ("NoPath", "PathFolderExist", "PathFileExist", "FolderExist")
   */
  public String pathExist(String userInput) {

    // Getting current instance of FileSystem
    if (!mock) {
      fileSystem = FileSystem.currentFileSystemInstance();
    }

    String temp = userInput.split("\\s+")[1];
    String arr[] = temp.split("/");

    // PathType = 0 : Relative Pathway
    // PathType = 1 : Absolute Pathway
    int pathType = 0;

    // If your pathway starts with '/'
    // You know it is an Absolute Pathway
    // Start from your root directory
    if (temp.startsWith("/")) {
      pathType = 1;
      fileSystem.goToRoot();
    }

    // Accounting for special cases such as ".." and "."
    // Checking for each folder in path excluding the last
    // eg. /A/B/C, check up to B
    for (int i = pathType; i < arr.length - 1; i++) {
      if (!arr[i].equals(".")) {
        if (arr[i].equals("..")) {
          fileSystem.cdUp();
        }

        // return NoPath if unable to cd down into a file
        else if (fileSystem.cdDown(arr[i]) == false) {
          return "NoPath";
        }

      }
    }

    // If array is empty, you know folder exist.
    if (arr.length == 0) {
      return "PathFolderExist";
    }

    // If you can cd down the whole pathway given
    else if (fileSystem.cdDown(arr[arr.length - 1])
        || arr[arr.length - 1].equals("..")
        || arr[arr.length - 1].equals(".")) {
      return "PathFolderExist";
    }

    // If the pathway is a File Pathway
    else if (fileSystem.checkType(arr[arr.length - 1]).equals("file")) {
      return "PathFileExist";
    }

    // if the given pathway ex. /A/B/C, C does not exist as a file or Folder
    // return "FolderExist"
    return "FolderExist";
  }
}
