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

import filesystem.ErrorOutput;

/**
 * Class responsible for exiting JShell.
 */
public class Exit {

  /**
   * Exit Jshell if user input is only exit and nothing else
   * 
   * @param userInput the user input
   * @return boolean (true, false)
   */
  public static boolean ExitCheck(String userInput) {

    // If the user entered exit then return true so JShell can exit the loop
    if (userInput.strip().equals("exit")) {
      return true;
    }
    
    return false;
  }
}
