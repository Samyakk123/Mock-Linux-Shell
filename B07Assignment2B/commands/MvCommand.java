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
import filesystem.FileClass;

/**
 * Class responsible for moving a inputed file/directory to a new location
 *
 */
public class MvCommand extends Commands implements CommandsInterface {

  private String validPathOld;
  private String validPathNew;
  private Directory file;
  private String[] arrOne;
  private String[] arrTwo;

  /**
   * Execute mv Command
   */
  protected void executeCommand() {
    fileSystem.goToCurrentWorkingDir();
    boolean checkIfExists = true;
    boolean checkInCurrent = true;
    super.arr = super.userInput.split("\\s+");

    arrOne = super.arr[2].split("/");
    arrTwo = super.arr[1].split("/");

    // Create a temp directory
    file = null;

    validPathNew = super.checkPath.pathExist(super.arr[0] + " " + arr[2]);
    checkIfExists = fileSystem.checkIfExists(arrTwo[arrTwo.length - 1]);
    fileSystem.goToCurrentWorkingDir();
    validPathOld = super.checkPath.pathExist(super.arr[0] + " " + arr[1]);
    Directory wantToMove = fileSystem.getWorkingDir();

    if (!validPathOld.equals("PathFileExist")) {

      checkInCurrent = fileSystem.checkIfInCurrent(wantToMove);
    }

    if (checkInCurrent && checkInvalidCase()) {

      // if already exists inside new location and both folders
      if (!checkIfExists && validPathOld.equals("PathFolderExist")
          && validPathNew.equals("PathFolderExist")) {
        caseExistFolderAndFolder();
      }

      // if old location is a folder and new locaiton is a valid path
      // up until the last element (i.e. need to make with the new name)
      else if (validPathOld.equals("PathFolderExist")
          && validPathNew.equals("FolderExist")) {
        caseFolderAndFolderExist();
      }

      // if old is a correct pathway to a file, and the new is either a file
      // or a correct pathway up until the last element
      else if (validPathOld.equals("PathFileExist")
          && (validPathNew.equals("PathFileExist")
              || validPathNew.equals("FolderExist"))) {

        caseFileFileFolder();
      }

      // if old is a correct file pathway or old is a correct folder pathway
      // and new is also a folder pathway
      else if ((validPathOld.equals("PathFileExist")
          || validPathOld.equals("PathFolderExist"))
          && (validPathNew.equals("PathFolderExist"))) {
        caseFileFolderFolder();
      }

    }

    else {
      super.content = null;
      ErrorOutput.printWithNewLine("Error: The input is invalid!");
    }

  }


  private void caseExistFolderAndFolder() {
    String name = fileSystem.getWorkingDir().getContent();
    file = new Directory(fileSystem.getWorkingDir().getContent());

    // recursively copy the directory and its sub directories into file
    fileSystem.executeCommandDirectory(fileSystem.getWorkingDir(), file);
    fileSystem.RemoveFromFileSystem(name);
    fileSystem.goToCurrentWorkingDir();

    // Go to new location with name added in the end
    super.checkPath.pathExist(super.arr[0] + " " + arr[2] + "/" + name);

    // Removed the new one and replace it with the current one copied above
    fileSystem.RemoveFromFileSystem(name);
    fileSystem.getWorkingDir().setChild(file);
    file.setParent(fileSystem.getWorkingDir());
    fileSystem.goToCurrentWorkingDir();
  }

  private void caseFolderAndFolderExist() {
    String name = arrOne[arrOne.length - 1];

    // Since this is the case with FolderExist, we need the name of the old
    // one as that will be the name of the new location
    file = new Directory(name);

    // recursively copies the sub-directories into file
    fileSystem.executeCommandDirectory(fileSystem.getWorkingDir(), file);

    // Remove the old one
    fileSystem.RemoveFromFileSystem(fileSystem.getWorkingDir().getContent());
    fileSystem.goToCurrentWorkingDir();

    // Go to new location
    super.checkPath.pathExist(super.arr[0] + " " + arr[2]);
    fileSystem.getWorkingDir().setChild(file);
    file.setParent(fileSystem.getWorkingDir());
    fileSystem.goToCurrentWorkingDir();
  }

  private void caseFileFileFolder() {
    Directory toChange = fileSystem.getFile(arrTwo[arrTwo.length - 1]);
    String name = arrOne[arrOne.length - 1];
    String fileContent = toChange.getFileContent();

    // Remove the old instance
    fileSystem.RemoveFromFileSystemFile(arrTwo[arrTwo.length - 1]);
    fileSystem.goToCurrentWorkingDir();
    super.checkPath.pathExist(super.arr[0] + " " + arr[2]);

    // if we need to create a new file for it
    if (validPathNew.equals("FolderExist")) {
      FileClass tempFile = new FileClass(fileContent);
      file = new Directory(name, tempFile);
      file.setParent(fileSystem.getWorkingDir());
      fileSystem.getWorkingDir().setChild(file);

      // else we can just get the file and replace its content and name
    }

    else {
      file = fileSystem.getFile(arrOne[arrOne.length - 1]);
      file.setName(name);
      file.setFileContent(fileContent);
    }

    fileSystem.goToCurrentWorkingDir();
  }

  private void caseFileFolderFolder() {
    String name = "";
    String fileContent = "";
    // If the old location is a file
    if (validPathOld.equals("PathFileExist")) {

      // get the old location file
      Directory toChange = fileSystem.getFile(arrTwo[arrTwo.length - 1]);

      // save it's name and content
      name = arrTwo[arrTwo.length - 1];
      fileContent = toChange.getFileContent();

    }

    else {

      // otherwise we know its a directory and recursively copy
      file = new Directory(fileSystem.getWorkingDir().getContent());
      fileSystem.executeCommandDirectory(fileSystem.getWorkingDir(), file);
    }

    // if it's a path remove from fileSystem using this method
    if (validPathOld.equals("PathFileExist")) {
      fileSystem.RemoveFromFileSystemFile(arrTwo[arrTwo.length - 1]);

      // otherwise this
    }

    else if (validPathOld.equals("PathFolderExist")) {
      fileSystem.RemoveFromFileSystem(arrTwo[arrTwo.length - 1]);
    }

    fileSystem.goToCurrentWorkingDir();
    super.checkPath.pathExist(super.arr[0] + " " + arr[2]);

    // File will be == null if it is a File location

    if (file == null) {
      FileClass tempFile = new FileClass(fileContent);
      file = new Directory(name, tempFile);
      file.setParent(fileSystem.getWorkingDir());
      fileSystem.getWorkingDir().setChild(file);

    }

    else {
      // If the else executes we know it is a directory
      fileSystem.getWorkingDir().setChild(file);
    }

    fileSystem.goToCurrentWorkingDir();
  }

  /**
   * Checks if input from user is valid before calling executeCommand
   * 
   * @return super.content the output string
   */
  public String checkInput() {

    if (super.validateInput.checkNumberOfParams(super.userInput, 2)) {
      this.executeCommand();
    }

    return super.content;
  }

  /**
   * 
   * Checks if new inputed path is valid
   * 
   * @param olderInput the destination output
   * @return boolean (true/false)
   */
  public boolean checkNew(String olderInput) {
    for (int i = 0; i < fileSystem.getWorkingDir().getTypeChildren()
        .size(); i++) {

      if (fileSystem.getWorkingDir().getTypeChildren().get(i).getContent()
          .equals(olderInput)) {
        return false;
      }

    }
    return true;
  }

  private boolean checkInvalidCase() {

    // if either pathway doesn't exist or the old pathway exists up until the
    // last name
    if (validPathOld.equals("NoPath") || validPathNew.equals("NoPath")
        || validPathOld.equals("FolderExist")) {
      super.content = null;
      return false;

      // if we have a Folder as the old one and a File as the new one
    }

    else if (validPathOld.equals("PathFolderExist")
        && validPathNew.equals("PathFileExist")) {
      super.content = null;
      return false;
    }

    else if ((super.arr[1].equals("..") || super.arr[1].equals("."))
        && fileSystem.sizeOfCurrentWorkingDir() == 1) {

      super.content = null;
      return false;

    }
    return true;
  }
}
