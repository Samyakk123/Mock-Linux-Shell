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

import java.util.ArrayList;
import java.util.Stack;

/**
 * 
 * Class responsible for fileSystem Interface
 *
 */
public interface FileSystemInterface {

  public static FileSystemInterface currentFileSystemInstance() {
    return FileSystemInterface.currentFileSystemInstance();
  }

  /**
   * Returns number of dir/folder in current dir
   * 
   * @return this.currentWorkingDir.size() the number of dir/folder
   */
  public int sizeOfCurrentWorkingDir();

  /**
   * Changes the current working dir with a new working dir
   * 
   * @param newCurrent the new current workingDirectory
   */
  public void changeCurrentWorkingDir(ArrayList<String> newCurrent);

  /**
   * Adds a folder to the current working directory
   * 
   * @param folder the new folder
   */
  public void addToCurrentWorkingDir(String folder);

  /**
   * Removes last directory in the currentWorkingDir
   */
  public void removeToCurrentWorkingDir();

  /**
   * Regardless of your position in currentWorkingDir Go to root Directory
   */
  public void goToRoot();

  /**
   * Returns the array list of currentWorkingDir
   * 
   * @return this.currentWorkingDir the currentWorkingDir
   */
  public ArrayList<String> getCurrentWorkingDirectory();

  /**
   * Go to current working dir from anywhere in tree (file system)
   */
  public void goToCurrentWorkingDir();

  /**
   * 
   * Return directory object given name if exist
   * 
   * @param fileName the file name
   * @return workingDir.getTypeChildren().get(i) the directory
   */
  public Directory getFile(String fileName);

  /**
   * Given a string input, returns if its a folder, a file, or neither
   * 
   * @param input the name of file/folder
   * @return String ("folder, "file", "none")
   */
  public String checkType(String input);

  /**
   * Given a folder name, return true if you could go into the folder else 
   * return false
   * 
   * @param input the name of folder
   * @return boolean
   */
  public boolean cdDown(String input);

  /**
   * Return true if you could go to parent of current folder, else return false
   * 
   * @return boolean
   */
  public boolean cdUp();

  /**
   * Move to parrentDirectory if folder has a parent folder
   * 
   * @return boolean
   */
  public boolean goParentDirectory();

  /**
   * Adds folder to current workingDir
   * 
   * @param folderName the name of the new folder
   */
  public void addFolder(String folderName);

  /**
   * Adds file to current workingDir
   * 
   * @param fileName the name of the new file
   * @param fileContent the file's content
   */
  public void addFile(String fileName, String fileContent);

  /**
   * Set a file's content
   * 
   * @param fileName the name of the file
   * @param fileContent the file's new content
   */
  public void setFileContents(String fileName, String fileContent);

  /**
   * Get a file's content given you know the file exist
   * 
   * @param fileName the name of the file
   * @return workingDir.getTypeChildren().get(i).getFileContent() the file 
   * content
   */
  public String getFileContents(String fileName);

  /**
   * Returns the name of the Current Directory
   * 
   * @return workingDir.getContent() the name of the current dir
   */
  public String getContentsOfWorkingDir();

  /**
   * Returns a string of all folders/file in a current directory
   * 
   * @return workingDir.getChildren() the children of current Dir
   */
  public String getChildrenOfWorkingDir();

  /**
   * Return the current working Dir object
   * 
   * @return this.workingDir the current working Dir
   */
  public Directory getWorkingDir();

  /**
   * 
   * Remove directory with given name in fileSystem
   * 
   * @param toRemove the name of directory to be removed
   */
  public void RemoveFromFileSystem(String toRemove);

  /**
   * 
   * Remove file with given name in fileSystem
   * 
   * @param toRemove the name of file to be removed
   */
  public void RemoveFromFileSystemFile(String toRemove);

  /**
   * 
   * Check if file inputed already exist in file System in given pathway
   * 
   * @param toAdd the file name
   * @return boolean (true/false)
   */
  public boolean checkIfExists(String toAdd);

  /**
   * 
   * Recursively add all sub directories from root2 into root
   * 
   * @param root the root directory you want to add to
   * @param root2 the working directory
   */
  public void executeCommandDirectory(Directory root, Directory root2);

  /**
   * 
   * Set history arraylist
   * 
   * @param historyToSave the new history
   */
  public void setHistory(ArrayList<String> historyToSave);

  /**
   * set Stack arraylist
   * 
   * @param stackToSave the new stack
   */
  public void setStack(Stack<ArrayList<String>> stackToSave);

  /**
   * Create stack class and set stack to stack class
   */
  public void setStackOfStackClass();

  /**
   * create history class and set history to history class
   */
  public void setHistoryOfHistoryClass();

  /**
   * 
   * Check if inputed directory is the current directory or it's parents
   * 
   * @param currLoc the current location
   * @return boolean (true/False)
   */
  public boolean checkIfInCurrent(Directory currLoc);

}
