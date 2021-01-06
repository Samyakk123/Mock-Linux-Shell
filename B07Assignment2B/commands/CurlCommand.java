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

import java.net.*;
import filesystem.CommandsInterface;
import filesystem.Directory;
import filesystem.ErrorOutput;
import filesystem.FileClass;
import java.io.*;

/**
 * Class responsible for retreiving a file given a URL into current working dir
 *
 */
public class CurlCommand extends Commands implements CommandsInterface {

  /**
   * Executes Curl Command
   */
  protected void executeCommand() {

    try {
      super.arr = super.userInput.split("\\s+");
      String[] tempArray = super.arr[1].split("/");
      String fileName = tempArray[tempArray.length - 1];

      // Check that the fileNmae doesn't already exist inside the workingDir
      String toReturn = "";
      URL oracle = new URL(super.arr[1]);
      URLConnection yc = oracle.openConnection();
      BufferedReader in =
          new BufferedReader(new InputStreamReader(yc.getInputStream()));
      String inputLine;

      // Read the input of the file line by line
      // Add a new line after each one so that it is separated
      while ((inputLine = in.readLine()) != null) {
        toReturn = toReturn + inputLine + "\n";
      }

      // Add the new file into the workingDir
      FileClass tempFile = new FileClass(toReturn);
      Directory file = new Directory(fileName, tempFile);
      if (!checkNew(fileName)) {
        Directory temp = fileSystem.getFile(fileName);
        temp.setFileContent(toReturn);
      }

      else {
        file.setParent(fileSystem.getWorkingDir());
        fileSystem.getWorkingDir().setChild(file);

        // Close after finishing
      }
      in.close();

    } catch (Exception e) {
      ErrorOutput.printWithNewLine("Error: Curl cannot create a file ");
      super.content = null;
    }

  }

  /**
   * Check if new inputed path is valid
   * 
   * @param olderInput the new file
   * @return boolean (true/false)
   */
  public boolean checkNew(String olderInput) {

    // Loop through all content of current working dir
    for (int i = 0; i < fileSystem.getWorkingDir().getTypeChildren()
        .size(); i++) {

      // Check if already exists inside the current directory
      if (fileSystem.getWorkingDir().getTypeChildren().get(i).getContent()
          .equals(olderInput)) {
        return false;
      }

    }
    return true;
  }

  /**
   * Checks if input from user is valid before calling executeCommand
   * 
   * @return super.content the output string
   */
  public String checkInput() {

    // Verify that there is only 1 parameter
    if (validateInput.checkNumberOfParameterOne(super.userInput)) {
      executeCommand();
    }

    else {
      super.content = null;
    }

    return super.content;
  }
}
