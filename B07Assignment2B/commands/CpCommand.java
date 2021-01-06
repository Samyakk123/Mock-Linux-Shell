package commands;

import filesystem.CommandsInterface;
import filesystem.Directory;
import filesystem.ErrorOutput;
import filesystem.FileClass;

/**
 * 
 * Class responsible for copying files/directories to other directories 
 * given a pathway
 *
 */
public class CpCommand extends Commands implements CommandsInterface {

  private String validPathNew;
  private String validPathOld;
  private Directory file;
  private String[] arrOne;
  private String[] arrTwo;

  /**
   * Executes cp Command
   */
  protected void executeCommand() {

    fileSystem.goToCurrentWorkingDir();
    boolean checkIfExists = true;
    super.arr = super.userInput.split("\\s+");

    // Initialize arrays for the new path and old path
    arrOne = super.arr[2].split("/");
    arrTwo = super.arr[1].split("/");

    // Create temp directory
    file = null;

    // Check if path is valid
    validPathNew = super.checkPath.pathExist(super.arr[0] + " " + arr[2]);
    checkIfExists = fileSystem.checkIfExists(arrTwo[arrTwo.length - 1]);
    fileSystem.goToCurrentWorkingDir();
    validPathOld = super.checkPath.pathExist(super.arr[0] + " " + arr[1]);

    // As long as it is valid types and doesn't exist in the new one proceed
    if (checkInvalidCase()) {

      if (!checkIfExists && validPathOld.equals("PathFolderExist")
          && validPathNew.equals("PathFolderExist")) {
        caseExistsFolderAndFolder();

      }

      else if (validPathOld.equals("PathFolderExist")
          && validPathNew.equals("FolderExist")) {
        caseTwoFolderAndFolderExist();
      }

      // If we have a file and a directory where the last / doesn't exist
      else if (validPathOld.equals("PathFileExist")
          && (validPathNew.equals("FolderExist")
              || validPathNew.equals("PathFileExist"))) {
        caseFileFolderAndFile();
      }

      // Path is valid
      else if (validPathOld.equals("PathFileExist")
          && validPathNew.equals("PathFolderExist")) {
        caseFileAndFolder();
      }

      // If the new location of cp is a Directory
      else if (validPathNew.equals("PathFolderExist")) {
        caseEndWithFolder();
      }

    } else {
      super.content = null;
      ErrorOutput.printWithNewLine("Error: The input is invalid!");
    }

    fileSystem.goToCurrentWorkingDir();
  }



  private void caseFileAndFolder() {
    Directory toChange = fileSystem.getFile(arrTwo[arrTwo.length - 1]);
    String name = arrTwo[arrTwo.length - 1];
    String fileContent = toChange.getFileContent();
    fileSystem.goToCurrentWorkingDir();

    // Go to the new location
    super.checkPath.pathExist(super.arr[0] + " " + arr[2]);

    // Create the File and set it's content inside the fileSystem at the
    // new location
    FileClass tempFile = new FileClass(fileContent);
    file = new Directory(name, tempFile);
    file.setParent(fileSystem.getWorkingDir());
    fileSystem.getWorkingDir().setChild(file);
    fileSystem.goToCurrentWorkingDir();
  }

  private void caseFileFolderAndFile() {
    Directory toChange = fileSystem.getFile(arrTwo[arrTwo.length - 1]);
    String fileContent = toChange.getFileContent();

    super.checkPath.pathExist(super.arr[0] + " " + super.arr[2]);

    if (validPathNew.equals("PathFileExist")) {
      toChange = fileSystem.getFile(arrOne[arrOne.length - 1]);
      toChange.setFileContent(fileContent);
      fileSystem.goToCurrentWorkingDir();
    }

    else if (validPathNew.equals("FolderExist")) {
      String name = arrOne[arrOne.length - 1];
      FileClass tempFile = new FileClass(fileContent);
      file = new Directory(name, tempFile);
      file.setParent(fileSystem.getWorkingDir());
      fileSystem.getWorkingDir().setChild(file);
      fileSystem.goToCurrentWorkingDir();
    }

  }

  private void caseEndWithFolder() {
    String name = "";
    String fileContent = "";

    // If the old location is a file
    if (validPathOld.equals("PathFileExist")) {

      // Get the old ones File and save it's content and name
      Directory toChange = fileSystem.getFile(arrTwo[arrTwo.length - 1]);
      name = arrTwo[arrTwo.length - 1];
      fileContent = toChange.getFileContent();
    }

    else if (validPathNew.equals("PathFolderExist")) {
      // Create a new file inside the workingDir (set toe new location
      file = new Directory(fileSystem.getWorkingDir().getContent());
      fileSystem.executeCommandDirectory(fileSystem.getWorkingDir(), file);
    }

    fileSystem.goToCurrentWorkingDir();

    // Go back to the old location
    super.checkPath.pathExist(super.arr[0] + " " + arr[2]);

    // If File == NULL (.e. it is a file) then initialize the file
    if (file == null) {
      FileClass tempFile = new FileClass(fileContent);
      file = new Directory(name, tempFile);
      file.setParent(fileSystem.getWorkingDir());
      fileSystem.getWorkingDir().setChild(file);
    }

    else {
      // Else initialize the directory
      fileSystem.getWorkingDir().setChild(file);
      file.setParent(fileSystem.getWorkingDir());
    }

    fileSystem.goToCurrentWorkingDir();
  }


  private void caseExistsFolderAndFolder() {
    String name = fileSystem.getWorkingDir().getContent();
    file = new Directory(fileSystem.getWorkingDir().getContent());
    fileSystem.executeCommandDirectory(fileSystem.getWorkingDir(), file);
    fileSystem.goToCurrentWorkingDir();
    super.checkPath.pathExist(super.arr[0] + " " + arr[2] + "/" + name);

    // Removed the old instance
    fileSystem.RemoveFromFileSystem(name);
    fileSystem.getWorkingDir().setChild(file);
    file.setParent(fileSystem.getWorkingDir());
    fileSystem.goToCurrentWorkingDir();

  }

  private void caseTwoFolderAndFolderExist() {
    String name = arrOne[arrOne.length - 1];
    file = new Directory(name);
    fileSystem.executeCommandDirectory(fileSystem.getWorkingDir(), file);
    fileSystem.goToCurrentWorkingDir();
    super.checkPath.pathExist(super.arr[0] + " " + arr[2]);
    fileSystem.getWorkingDir().setChild(file);
    file.setParent(fileSystem.getWorkingDir());
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

    else {
      super.content = null;
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

      // Check if the file already exists inside the new location
      if (fileSystem.getWorkingDir().getTypeChildren().get(i).getContent()
          .equals(olderInput)) {
        ErrorOutput
            .printWithNewLine("Error: The file/directory already exists!");

        // Set content to null if it's an error
        super.content = null;
        return false;
      }

    }
    return true;
  }

  private boolean checkInvalidCase() {

    // All the invalid combinations, e.g. can't have cp folder file
    if (validPathOld.equals("NoPath") || validPathNew.equals("NoPath")
        || validPathOld.equals("FolderExist")) {
      super.content = null;
      return false;
    }

    // If they try copying a directory into a file
    else if (validPathOld.equals("PathFolderExist")
        && validPathNew.equals("PathFileExist")) {
      super.content = null;
      return false;
    }

    // To prevent them from trying to copy the root directory
    else if ((super.arr[1].equals("..") || super.arr[1].equals("."))
        && fileSystem.sizeOfCurrentWorkingDir() == 1) {
      super.content = null;
      return false;
    }

    return true;
  }



}
