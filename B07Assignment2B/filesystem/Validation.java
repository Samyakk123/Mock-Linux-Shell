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

import commands.Redirection;
import mock.MockCheckPath;

/**
 * 
 * Responsible for checking the input for commands
 * 
 */
public class Validation {

  private CheckPathInterface checkPath = new CheckPath();

  private String[] invalidCharacters = {".", "!", "@", "#", "$", "%", "^", "&",
      "*", "(", ")", "{", "}", "~", "|", "<", ">", "?"};

  FileSystem fileSystem = FileSystem.currentFileSystemInstance();

  /**
   * Returns a boolean, true if quotations for given input are correct,else false
   * 
   * @param userInput the user input
   * @return boolean (true, false)
   */
  public boolean checkQuotation(String userInput) {
    // Checks if there are proper quotations for the "userInput"
    // One at the first index, and last and none within
    userInput = userInput.trim();
    int length = userInput.length();

    if (userInput.charAt(0) != '"' || userInput.charAt(length - 1) != '"') {
      return false;
    }

    for (int i = 0; i < length; i++) {

      if ((userInput.charAt(i) == '"') && ((i != 0) && (i != length - 1))) {
        return false;
      }

    }

    if ((userInput.charAt(0) != '"') || (userInput.charAt(length - 1) != '"')) {
      return false;
    }

    return true;
  }

  /**
   * Returns a boolean, true if user inputed "QUIT" or " QUIT", false otherwise.
   * 
   * @param userInput the user input
   * @return boolean (true, false)
   */
  public boolean validateSpeakCommand(String userInput) {

    // Checks if the "userInput" has "QUIT" within
    userInput = userInput.replaceAll("\\s+$", "");

    if (userInput.equals("QUIT")) {
      return true;
    }

    int index = userInput.indexOf(" QUIT");

    if (index != -1) {
      if (userInput.substring(index, userInput.length()).equals(" QUIT")) {
        return true;
      }
    }

    return false;
  }

  /**
   * Returns a boolean, true if the input has proper character and if its a 
   * valid path, false
   * otherwise.
   * 
   * @param userInput the user input
   * @return boolean (true, false)
   */
  public boolean checkValidCharsAndPath(String userInput) {

    // Checks if the path exists
    if (this.checkPath.pathExist(userInput).equals("NoPath")) {
      fileSystem.goToCurrentWorkingDir();
      ErrorOutput.pathDoesNotExist(userInput.split("\\s+")[1]);
      return false;
    }
    fileSystem.goToCurrentWorkingDir();

    // Checks if there are valid characters in the "userInput"
    String[] arr2 = userInput.split("/");
    userInput = arr2[arr2.length - 1];
    for (int i = 0; i < invalidCharacters.length; i++) {

      if (userInput.contains(invalidCharacters[i])) {
        ErrorOutput.wrongCharacterUsed(invalidCharacters[i]);
        return false;
      }

    }
    return true;
  }

  /**
   * 
   * Check if input contains only valid characters
   * 
   * @param userInput the user input
   * @return boolean (true/false)
   */
  public boolean checkValidChars(String userInput) {
    String[] arr = userInput.split("/");
    userInput = arr[arr.length - 1];
    for (int i = 0; i < invalidCharacters.length; i++) {

      if (userInput.contains(invalidCharacters[i])) {
        return false;
      }

    }
    return true;
  }

  /**
   * 
   * Check number of parameters given input
   * 
   * @param userInput the user input
   * @param numOfInput the number of inputs
   * @return boolean (true/false)
   */
  public boolean checkNumberOfParams(String userInput, int numOfInput) {
    String[] arr = userInput.split("\\s+");

    if (arr.length == numOfInput + 1) {
      return true;
    }

    ErrorOutput.wrongNumberOfParameters(arr[0], arr.length - 1,
        Integer.toString(numOfInput));
    return false;

  }

  /**
   * Returns a boolean, true if the number of parameter is equal to one, false otherwise.
   * 
   * @param userInput the user input
   * @return boolean (true, false)
   */
  public boolean checkNumberOfParameterOne(String userInput) {

    // Checks if the number of parameter is one
    String[] arr = userInput.split("\\s+");

    if (arr.length == 2) {
      return true;
    }

    ErrorOutput.wrongNumberOfParameters(arr[0], arr.length - 1, "1");
    return false;
  }

  /**
   * Returns a boolean, true if the number of parameter is equal to zero, false 
   * otherwise.
   * 
   * @param userInput the user input
   * @return boolean (true, false)
   */
  public boolean checkNumberOfParameterZero(String userInput) {

    // Checks if the number of parameter is zero
    String[] arr = userInput.split("\\s+");

    if (arr.length == 1) {
      return true;
    }

    ErrorOutput.wrongNumberOfParameters(arr[0], arr.length - 1, "0");
    return false;
  }

  /**
   * Returns a boolean, true if the number of parameter is equal to zero/one 
   * and false otherwise.
   * 
   * @param userInput the user input
   * @return boolean (true, false)
   */
  public boolean checkNumberOfParameterZeroOrOne(String userInput) {

    // Checks if the number of parameter is zero or one
    String[] arr = userInput.split("\\s+");

    if (arr.length == 1 || arr.length == 2) {
      return true;
    }

    ErrorOutput.wrongNumberOfParameters(arr[0], arr.length - 1, "0-1");
    return false;
  }

  private int echoHelper(boolean checker, String userInput, String[] arr) {
    if (checker) {

      // It is valid type of echo One
      if (arr[arr.length - 2].equals(">")) {
        return 1;
      }

      // Other components of checkEchOCommand already narrowed it down to these
      // two so this must be echo 2 (return 2)
      return 2;
    }

    else {
      ErrorOutput.notValidInput(userInput);
      return -1;
    }

  }

  /**
   * Returns a integer indicating the type of echo that should be called. 0 -
   *  basic echo, 1 - echo
   * with ">", 2 - echo with ">>", -1 - improper input
   * 
   * @param userInput the user input
   * @return int (0, 1, 2, -1)
   */
  public int checkEchoCommand(String userInput) {

    // Checks if the input is just equal to "echo"
    if (userInput.strip().equals("echo")) {
      ErrorOutput.wrongNumberOfParameters("echo", 0, "2/4");
      return -1;
    }
    String[] arr = userInput.split("\\s+");
    String toSend = "";
    boolean checker = false;

    // Checks for proper quotations
    if (checkQuotation(userInput.substring(4, userInput.length()).strip())) {

      // Returns zero as a indicator, that its the first type of echo, without
      // ">>" or ">"
      return 0;
    } else {

      // Checks if its the echo that overwrites or appends to file
      if (!(arr[arr.length - 2].equals(">")
          || arr[arr.length - 2].equals(">>"))) {
        ErrorOutput.notValidInput(userInput);
        return -1;
      }
      for (int i = 1; i < arr.length - 2; i++) {
        toSend += " " + arr[i];
      }
      checker = checkQuotation(toSend.strip());

      // Checks quotations for the echo type with ">>" or ">"
      if (checker == false) {
        ErrorOutput.notValidInput(userInput);
        return -1;
      }

      // Checks for valid character and path for the file inputed
      if (!checkValidCharsAndPath(arr[0] + " " + arr[arr.length - 1])) {
        return -1;
      } else {

        // Differentiates between ">>" or ">" and returns correct indicator
        return echoHelper(checker, userInput, arr);
      }
    }

  }

  /**
   * Returns a boolean false if any of the paths provided are not valid or if 
   * true if all the paths
   * are valid.
   * 
   * @param userInput the user input
   * @return boolean (true, false)
   */
  public boolean validateCatCommand(String userInput) {

    // Checks if user only inputed "cat" alone
    if (userInput.strip().equals("cat")) {
      ErrorOutput.wrongNumberOfParameters("cat", 0, "1+");
      return false;
    }

    return true;
  }


  /**
   * 
   * Return boolean false if any of the paths provided are not valid else true
   * 
   * @param userInput the user input
   * @param length the number of words in userInput
   * @return boolean (true, false)
   */
  public boolean validateFindCommand(String userInput, int length) {
    String[] arr = userInput.split("\\s+");

    if (length >= 6) {
      if (!arr[length - 4].equals("-type")) {
        ErrorOutput.wrongFormatOfFind();
        return false;
      }

      else if (!arr[length - 3].equals("d") && !arr[length - 3].equals("f")) {
        System.out.println(arr[length - 3]);
        ErrorOutput.wrongFormatOfFind();
        return false;
      }

      else if (!arr[length - 2].equals("-name")) {
        ErrorOutput.wrongFormatOfFind();
        return false;
      }

      else if (!checkQuotation(arr[length - 1])) {
        ErrorOutput.wrongFormatOfFind();
        return false;
      }

      return true;
    }
    ErrorOutput.wrongFormatOfFind();
    return false;
  }

  public void setMock() {
    this.checkPath = new MockCheckPath();
  }
}
