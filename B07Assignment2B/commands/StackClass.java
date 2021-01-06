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

import java.util.ArrayList;
import java.util.Stack;
import filesystem.Output;
import filesystem.FileSystem;

/**
 * 
 * Class responsible for keeping track of saved locations for pushd and popd 
 * Commands.
 * 
 */
public class StackClass {

  /** the stack that will be used */
  public static StackClass stack = null;
  /** the current fileSystem that will be used */
  public FileSystem fileSystem = FileSystem.currentFileSystemInstance();
  /** the savedLocations that will be used */
  protected Stack<ArrayList<String>> savedLocations;

  /**
   * Constructor for StackClass Empty private constructor so that this cannot
   *  be accessed by the user
   */
  private StackClass() {

  }

  /**
   * Singleton Responsibility Principle used to only ever create one instance 
   * of StackClass
   * 
   * @return StackClass the current StackClass instance
   */
  public static StackClass currentStackClassInstance() {
    if (stack == null) {
      stack = new StackClass();
      stack.savedLocations = new Stack<ArrayList<String>>();
    }
    return stack;
  }

  /**
   * Gets new location from stack class
   * 
   * @return savedLocations.pop() location of directory you should cd into
   */
  public ArrayList<String> getNewLocation() {

    // Outputs the top element from the stack to user if stack is not empty
    // and removes it from stack
    if (savedLocations.isEmpty() == true) {
      Output.stackIsEmptyMessage();
      return null;
    }

    else {
      return savedLocations.pop();
    }
  }

  /**
   * Add new location into stack class
   * 
   * @param newLocation the new location
   */
  public void addNewLocation(ArrayList<String> newLocation) {

    // Adds a new element to the stack
    savedLocations.add(newLocation);
  }

  /**
   * Return current saved location
   * 
   * @return this.savedLocations the saved locations
   */
  public Stack<ArrayList<String>> getStack() {
    return this.savedLocations;
  }

  /**
   * Set current saved location
   * 
   * @param newStack the new saved locations
   */
  public void setStack(Stack<ArrayList<String>> newStack) {
    this.savedLocations = newStack;
  }
}
