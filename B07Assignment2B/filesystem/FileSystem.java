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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import commands.HistoryCommand;
import commands.StackClass;
import filesystem.FileSystemInterface;

/**
 * 
 * Represents a mock file system that Contains the directories Tree and current 
 * position in the Tree. Able to navigate through the Tree and make Files 
 * / Directories.
 * 
 */
public class FileSystem implements Serializable, FileSystemInterface {

  private static final long serialVersionUID = 1L;

  /** Creating one instance of fileSystem */
  public static FileSystem fileSystem = null;

  // FileSystem contains a tree of the working directory
  private Directory workingDir = new Directory("/");

  // Current position in the workingDir
  private ArrayList<String> currentWorkingDir =
      new ArrayList<String>(Arrays.asList("/"));

  private ArrayList<String> history;
  private Stack<ArrayList<String>> stack;

  // FileSystem Constructor, private so user can't access.
  private FileSystem() {

  }

  /**
   * Singleton Responsibility Principle used to only ever create one instance 
   * of FileSystem
   * 
   * @return fileSystem the current working fileSystem
   */
  public static FileSystem currentFileSystemInstance() {

    if (fileSystem == null) {
      fileSystem = new FileSystem();
    }

    return fileSystem;
  }

  /**
   * Set new FileSystem
   * 
   * @param newFileSystem the new file system
   */
  public static void setFileSystem(FileSystem newFileSystem) {
    fileSystem = newFileSystem;
  }

  /**
   * Returns number of dir/folder in current dir
   * 
   * @return this.currentWorkingDir.size() the number of dir/folder
   */
  public int sizeOfCurrentWorkingDir() {
    return this.currentWorkingDir.size();
  }

  /**
   * Changes the current working dir with a new working dir
   * 
   * @param newCurrent the new current workingDirectory
   */
  public void changeCurrentWorkingDir(ArrayList<String> newCurrent) {
    this.currentWorkingDir = newCurrent;
  }

  /**
   * Adds a folder to the current working directory
   * 
   * @param folder the new folder
   */
  public void addToCurrentWorkingDir(String folder) {
    this.currentWorkingDir.add(folder);
  }

  /**
   * Removes last directory in the currentWorkingDir
   */
  public void removeToCurrentWorkingDir() {
    this.currentWorkingDir.remove(currentWorkingDir.size() - 1);
  }

  /**
   * Regardless of your position in currentWorkingDir Go to root Directory
   */
  public void goToRoot() {
    while (this.workingDir.getParent() != null) {
      this.workingDir = this.workingDir.getParent();
    }
  }

  /**
   * Returns the array list of currentWorkingDir
   * 
   * @return this.currentWorkingDir the currentWorkingDir
   */
  public ArrayList<String> getCurrentWorkingDirectory() {
    return this.currentWorkingDir;
  }

  /**
   * Go to current working dir from anywhere in tree (file system)
   */
  public void goToCurrentWorkingDir() {
    goToRoot();
    for (int i = 1; i < currentWorkingDir.size(); i++) {
      cdDown(currentWorkingDir.get(i));
    }
  }

  /**
   * 
   * Return directory object given name if exist
   * 
   * @param fileName the file name
   * @return workingDir.getTypeChildren().get(i) the directory
   */
  public Directory getFile(String fileName) {
    // fileSystem.goToCurrentWorkingDir();
    for (int i = 0; i < this.workingDir.getTypeChildren().size(); i++) {

      if (workingDir.getTypeChildren().get(i).getContent().equals(fileName)) {
        return workingDir.getTypeChildren().get(i);
      }

    }
    return null;
  }

  /**
   * Given a string input, returns if its a folder, a file, or neither
   * 
   * @param input the name of file/folder
   * @return String ("folder, "file", "none")
   */
  public String checkType(String input) {
    for (int i = 0; i < workingDir.getTypeChildren().size(); i++) {
      if (((Directory) workingDir.getTypeChildren().get(i)).getContent()
          .equals(input)) {

        if (((Directory) workingDir.getTypeChildren().get(i)).getType()) {
          return "folder";
        }

        else {
          return "file";
        }

      }
    }
    return "none";
  }

  /**
   * Given a folder name, return true if you could go into the folder else return false
   * 
   * @param input the name of folder
   * @return boolean
   */
  public boolean cdDown(String input) {
    if (checkType(input).equals("folder")) {
      for (int i = 0; i < this.workingDir.getTypeChildren().size(); i++) {

        if (((Directory) workingDir.getTypeChildren().get(i)).getContent()
            .equals(input)) {
          workingDir = (Directory) workingDir.getTypeChildren().get(i);
          return true;
        }

      }
    }
    return false;
  }

  /**
   * Return true if you could go to parent of current folder, else return false
   * 
   * @return boolean
   */
  public boolean cdUp() {

    if (!(workingDir.getParent() == null)) {
      this.workingDir = workingDir.getParent();
      return true;
    }

    else {
      return false;
    }

  }

  /**
   * Move to parrentDirectory if folder has a parent folder
   * 
   * @return boolean
   */
  public boolean goParentDirectory() {

    if ((((Directory) workingDir.getParent()) == null)) {
      return false;
    }

    workingDir = (Directory) workingDir.getParent();
    return true;
  }

  /**
   * Adds folder to current workingDir
   * 
   * @param folderName the name of the new folder
   */
  public void addFolder(String folderName) {
    Directory newFolder = new Directory(folderName);
    workingDir.setChild(newFolder);
  }

  /**
   * Adds file to current workingDir
   * 
   * @param fileName the name of the new file
   * @param fileContent the file's content
   */
  public void addFile(String fileName, String fileContent) {
    FileClass file = new FileClass(fileContent);
    Directory newFile = new Directory(fileName, file);
    workingDir.setChild(newFile);
  }

  /**
   * Set a file's content
   * 
   * @param fileName the name of the file
   * @param fileContent the file's new content
   */
  public void setFileContents(String fileName, String fileContent) {
    for (int i = 0; i < this.workingDir.getTypeChildren().size(); i++) {

      if (workingDir.getTypeChildren().get(i).getContent().equals(fileName)) {
        workingDir.getTypeChildren().get(i).setFileContent(fileContent);
      }

    }
  }

  /**
   * Get a file's content given you know the file exist
   * 
   * @param fileName the name of the file
   * @return workingDir.getTypeChildren().get(i).getFileContent() the file content
   */
  public String getFileContents(String fileName) {
    for (int i = 0; i < this.workingDir.getTypeChildren().size(); i++) {

      if (workingDir.getTypeChildren().get(i).getContent().equals(fileName)) {
        return workingDir.getTypeChildren().get(i).getFileContent();
      }

    }
    return null;
  }

  /**
   * Returns the name of the Current Directory
   * 
   * @return workingDir.getContent() the name of the current dir
   */
  public String getContentsOfWorkingDir() {
    return workingDir.getContent();
  }

  /**
   * Returns a string of all folders/file in a current directory
   * 
   * @return workingDir.getChildren() the children of current Dir
   */
  public String getChildrenOfWorkingDir() {
    return workingDir.getChildren();
  }

  /**
   * Return the current working Dir object
   * 
   * @return this.workingDir the current working Dir
   */
  public Directory getWorkingDir() {
    return this.workingDir;
  }

  /**
   * 
   * Remove directory with given name in fileSystem
   * 
   * @param toRemove the name of directory to be removed
   */
  public void RemoveFromFileSystem(String toRemove) {

    // fileSystem.cdDown(toRemove);
    // workingDir.setParent(null);
    // fileSystem.cdUp();
    fileSystem.cdUp();
    workingDir.removeChild(toRemove);
  }

  /**
   * 
   * Remove file with given name in fileSystem
   * 
   * @param toRemove the name of file to be removed
   */
  public void RemoveFromFileSystemFile(String toRemove) {
    workingDir.removeChild(toRemove);
  }


  /**
   * 
   * Check if file inputed already exist in file System in given pathway
   * 
   * @param toAdd the file name
   * @return boolean (true/false)
   */
  public boolean checkIfExists(String toAdd) {
    for (int i = 0; i < fileSystem.getWorkingDir().getTypeChildren()
        .size(); i++) {

      if (fileSystem.getWorkingDir().getTypeChildren().get(i).getContent()
          .equals(toAdd)) {
        return false;
      }

    }
    return true;
  }

  /**
   * 
   * Recursively add all sub directories from root2 into root
   * 
   * @param root the root directory you want to add to
   * @param root2 the working directory
   */
  public void executeCommandDirectory(Directory root, Directory root2) {

    // Go through the roots elements
    for (int i = 0; i < root.getTypeChildren().size(); i++) {

      // If it is a directory, set the child into our new
      // and recursively call it again
      Directory temp = null;

      if (root.getTypeChildren().get(i).getType()) {
        String name = root.getTypeChildren().get(i).getContent();
        temp = new Directory(name);
        root2.setChild(temp);

        executeCommandDirectory(root.getTypeChildren().get(i),
            root2.getTypeChildren().get(i));

        // If it is a file simply add it
      }

      else {
        FileClass temp2;
        String name = root.getTypeChildren().get(i).getContent();
        String content = root.getTypeChildren().get(i).getFileContent();
        temp2 = new FileClass(content);
        temp = new Directory(name, temp2);
        root2.setChild(temp);
      }

    }
  }

  /**
   * 
   * Set history arraylist
   * 
   * @param historyToSave the new history
   */
  public void setHistory(ArrayList<String> historyToSave) {
    this.history = historyToSave;
  }

  /**
   * set Stack arraylist
   * 
   * @param stackToSave the new stack
   */
  public void setStack(Stack<ArrayList<String>> stackToSave) {
    this.stack = stackToSave;
  }

  /**
   * Create stack class and set stack to stack class
   */
  public void setStackOfStackClass() {
    StackClass stack = StackClass.currentStackClassInstance();
    stack.setStack(this.stack);
  }

  /**
   * create history class and set history to history class
   */
  public void setHistoryOfHistoryClass() {
    HistoryCommand history = HistoryCommand.currentHistoryClassInstance();
    history.setHistory(this.history);
  }

  /**
   * 
   * Check if inputed directory is the current directory or it's parents
   * 
   * @param currLoc the current location
   * @return boolean (true/False)
   */
  public boolean checkIfInCurrent(Directory currLoc) {
    for (int i = 0; i < fileSystem.getCurrentWorkingDirectory().size(); i++) {

      if (currLoc.getContent()
          .equals(fileSystem.getCurrentWorkingDirectory().get(i))) {
        return false;
      }

    }
    return true;
  }
}

