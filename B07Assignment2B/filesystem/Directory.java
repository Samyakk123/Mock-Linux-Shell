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

/**
 * Directory Class holds a Tree which acts like a Mock fileSystem. Can navigate
 * the tree while creating directories and files.
 * 
 */
public class Directory implements Serializable {

  // Each directory contains ArrayList of children of directories or file
  // Each directory/file has a parent class except null for root directory
  private ArrayList<Directory> children = new ArrayList<Directory>();
  private Directory parent = null;
  private FileClass file = null;

  // Name of directory or file
  private String name = null;

  // True == Folder
  // False == File
  private boolean fileOrFolder;

  /**
   * Directory constructor when creating folders
   * 
   * @param input the user input
   */
  public Directory(String input) {
    this.name = input;
    this.fileOrFolder = true;
  }

  /**
   * Directory constructor overwritten when creating files
   * 
   * @param input the user input
   * @param addFile the new file to be added
   */
  public Directory(String input, FileClass addFile) {

    // Constructor for a FileClass
    this.name = input;
    this.file = addFile;
    this.fileOrFolder = false;

  }

  /**
   * Return if current item in Directory is a file or folder
   * 
   * @return boolean
   */
  public boolean getType() {
    return this.fileOrFolder;
  }

  /**
   * Set's current directories's to new parent
   * 
   * @param parent the new parent
   */
  public void setParent(Directory parent) {
    this.parent = parent;
  }

  /**
   * Set's current directories's child
   * 
   * @param child the child
   */
  public void setChild(Directory child) {
    child.setParent(this);
    this.children.add(child);
  }

  /**
   * 
   * Remove current directorie's child
   * 
   * @param child the child
   */
  public void removeChild(String child) {
    for (int i = 0; i < children.size(); i++) {
      if (children.get(i).getContent().equals(child)) {
        children.remove(i);
      }
    }
  }

  /**
   * Returns name of current dir/file
   * 
   * @return String the name of dir/file
   */
  public String getContent() {
    return this.name;
  }

  /**
   * Returns content of current file
   * 
   * @return String content of file
   */
  public String getFileContent() {
    return this.file.getFileContents();
  }

  /**
   * Sets current file's content to newContent
   * 
   * @param newContent the new content
   */
  public void setFileContent(String newContent) {
    this.file.setContent(newContent);
  }

  /**
   * Returns the parent of current dir/file
   * 
   * @return this.parent the parent of current dir/file
   */
  public Directory getParent() {
    return this.parent;
  }

  /**
   * Returns a string of all folders/file in a current directory
   * 
   * @return String the list of children of current directory
   */
  public String getChildren() {
    String toReturn = "";
    for (int i = 0; i < this.children.size(); i++) {
      toReturn += " " + children.get(i).getContent();
    }
    return toReturn;
  }

  /**
   * 
   * Return raw children
   * 
   * @return this.children the children
   */
  public ArrayList<Directory> getTypeChildren() {
    return this.children;
  }

  /**
   * 
   * Sets name of current directory
   * 
   * @param newName the new name
   */
  public void setName(String newName) {

    // have to change the name in the current working directory?...
    this.name = newName;
  }
}
