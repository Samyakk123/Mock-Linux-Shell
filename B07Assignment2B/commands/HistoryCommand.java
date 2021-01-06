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
// Author:
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
import filesystem.ErrorOutput;
import filesystem.Output;

/**
 * 
 * Class responsible for keeping track of user input and printing it.
 *
 */
public class HistoryCommand extends Commands {

  /** the history class that will be used */
  public static HistoryCommand history = null;
  /** the historyTracker that will be used */
  public ArrayList<String> historyTracker = new ArrayList<String>();
  /** the redirection that will be used */
  public Redirection redirection = new Redirection();
  private boolean redirect = false;
  private boolean check = false;

  /**
   * Constructor for HistoryCommand
   */
  private HistoryCommand() {

  }

  /**
   * Singleton Responsibility Principle used to only ever create one instance 
   * of HistoryCommand
   * 
   * @return history the current history instance
   */
  public static HistoryCommand currentHistoryClassInstance() {
    if (history == null) {
      history = new HistoryCommand();
      history.historyTracker = new ArrayList<String>();
    }
    return history;
  }

  /**
   * Add the users input to the arrayList
   * 
   * @param userInput the user input
   */
  public void addToList(String userInput) {
    historyTracker.add(userInput);
  }

  private void printList(int n) {
    n = historyTracker.size() - n;

    // Print out the entire history arrayList
    for (int i = n; i < historyTracker.size(); i++) {
      super.content += "" + (i + 1) + ".) " + historyTracker.get(i) + "\n";
    }
  }

  /**
   * Execute History Command
   */
  protected void executeCommand() {

    // check array length and convert the String to an Integer
    // (We know this can be done because checkInput has verified it is an
    // Integer)
    if (super.arr.length > 1 && !this.redirection.checkRedirection(super.arr[1])
        && Integer.parseInt(super.arr[1]) < 0) {
      ErrorOutput
          .printWithNewLine("Error: History cannot take negative " + "values!");
      super.content = null;

    }

    else if (super.arr.length == 1 || super.arr.length == 3
        || Integer.parseInt(super.arr[1]) > historyTracker.size()) {
      printList(historyTracker.size());
    }

    else {
      printList((Integer.parseInt(super.arr[1])));
    }

  }

  private boolean helper() {
    super.arr = super.userInput.split("\\s+");

    if (arr.length == 2 || arr.length == 4) {
      try {
        Integer.parseInt(super.arr[1]);
        check = true;

        // If the value associated with History is not changeable from a
        // string to an int I.e not a number, then return an Error message
      } catch (NumberFormatException e) {
        super.content = null;
        ErrorOutput.notValidInput(super.arr[1]);
        return true;
      }
    }
    return false;
  }

  /**
   * Checks if input from user is valid before calling executeCommand
   * 
   * @return super.content the output string
   */
  public String checkInput() {

    super.content = "";
    super.arr = super.userInput.split("\\s+");

    if (this.helper()) {
      return super.content;
    }

    // Check whether a single or two parameters are provided and check both
    // for validity
    if (super.arr.length > 2
        && this.redirection.checkRedirection(super.arr[super.arr.length - 2])) {
      this.redirect = true;
    }

    // If redirection provided in input
    if (this.redirect && super.arr.length == 3) {
      executeCommand();
    }

    else if (this.redirect && super.arr.length == 4 && check) {
      executeCommand();
    }

    else {
      if (super.arr.length > 4) {
        super.content = null;
        ErrorOutput.printWithNewLine("Error: The input is invalid!");
      }

      else if (super.validateInput
          .checkNumberOfParameterZeroOrOne(super.userInput)) {
        if (super.arr.length == 1 || check) {
          executeCommand();
        } else {
          super.content = null;
          return super.content;
        }
      }

      else {
        super.content = null;
        return super.content;
      }

    }

    // redirection
    if (this.redirect && (super.arr.length == 3 || super.arr.length == 4)) {
      super.content =
          this.redirection.executeRedirection(super.content, super.userInput);
    }
    return super.content;
  }

  /**
   * 
   * Returns all commands inputed by user
   * 
   * @return this.historyTracker the history of inputed command
   */
  public ArrayList<String> getHistory() {
    return this.historyTracker;
  }

  /**
   * 
   * Sets old history to new history
   * 
   * @param newhistory the new history
   */
  public void setHistory(ArrayList<String> newhistory) {
    this.historyTracker = newhistory;
  }
}
