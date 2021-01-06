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
// UTORID user_name: pate1069
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
package driver;

import java.util.Scanner;
import filesystem.ParseClass;
import commands.Exit;
import commands.PwdCommand;
import filesystem.Output;

/**
 * 
 * Responsible for continuously prompting user until they type 'exit' and
 * executing any command if given input is valid
 *
 */
public class JShell {

  public static void main(String[] args) {

    ParseClass parseClass = new ParseClass();
    PwdCommand pwdComm = new PwdCommand();
    String userInput = "";
    Scanner in = new Scanner(System.in);

    // Keeps getting user input, until user runs the exit command.
    while (true) {
      Output.printWithoutNewLine(pwdComm.currentDirectory(false) + " $: ");
      userInput = in.nextLine();
      // If the first word is not 'exit'
      if (!Exit.ExitCheck(userInput)) {
        parseClass.checkCommand(userInput);
      }
      if (Exit.ExitCheck(userInput)) {
        break;
      }

    }
    in.close();

  }

}
