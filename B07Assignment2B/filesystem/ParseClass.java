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

import java.util.HashMap;
import commands.*;

/**
 * 
 * The Class calls commands based on userInput.
 *
 */
public class ParseClass {

  private HashMap<String, String> commandFunctions;
  private HistoryCommand historyTracker;
  /** disabled load used for when to load */
  public boolean disableLoad = false;
  private boolean check = false;
  private String commandName;

  /*
   * Constructor for ParseClass
   */
  public ParseClass() {
    this.historyTracker = HistoryCommand.currentHistoryClassInstance();
    this.commandFunctions = new HashMap<String, String>();
  }

  private void parseHelper(String userInput) {
    String className = this.commandFunctions.get(this.commandName);
    try {

      // Creates a new instance of the appropriate class type
      CommandsInterface commandToBeCalled = (CommandsInterface) Class
          .forName(className).getDeclaredConstructor().newInstance();

      // Calls the correct methods from the correct class type
      commandToBeCalled.setUserInput(userInput);
      String output = commandToBeCalled.checkInput();

      if (output != null) {
        this.disableLoad = true;
      }

      if ((output != null) && !output.equals("")) {
        Output.printWithNewLine(output);
      }

      this.check = true;
    }

    catch (Exception e) {
      // TODO Auto-generated catch block
      if (!commandName.equals("load")) {
        ErrorOutput.notValidCommand();
      }

      else {
        ErrorOutput.printWithNewLine("Error: The load command should "
            + "be the first command to be called!");
      }

      this.check = false;
    }
  }

  /**
   * 
   * Calls the appropriate command depending on the user input to be validated 
   * first and then executed
   * if proper input is given.
   * 
   * @param userInput the user input
   * @return boolean (true, false)
   * 
   */
  public boolean checkCommand(String userInput) {

    this.check = false;
    userInput = userInput.trim();
    historyTracker.addToList(userInput);
    String[] userInputInArray = userInput.split("\\s+");

    // Initializes hashmap with appropriate class names for commands
    if (!this.disableLoad) {
      initializeHashMap(this.commandFunctions);
    }
    this.commandName = userInputInArray[0];

    // Adds user input to history to keep track of it
    if (commandName.equals("history")) {
      historyTracker.setUserInput(userInput);
      String output = historyTracker.checkInput();

      if (output != null) {
        disableLoad = true;
      }

      if ((output != null) && !output.equals("")) {
        Output.printWithNewLine(output);
      }

    } else {
      try {
        // Call helper
        parseHelper(userInput);

      } catch (Exception e) {

        ErrorOutput.notValidCommand();
        e.printStackTrace();
        this.check = false;
      }
    }

    if (disableLoad) {
      commandFunctions.remove("load");
    }

    return this.check;
  }

  /**
   * Initializes HashMap
   * 
   * @param CommandHashMap the commandsHashMap
   */
  public static void initializeHashMap(HashMap<String, String> CommandHashMap) {
    CommandHashMap.put("speak", "commands.SpeakCommand");
    CommandHashMap.put("cd", "commands.CdCommand");
    CommandHashMap.put("man", "commands.UserManual");
    CommandHashMap.put("mkdir", "commands.MkdirCommand");
    CommandHashMap.put("ls", "commands.LsCommand");
    CommandHashMap.put("pwd", "commands.PwdCommand");
    CommandHashMap.put("cat", "commands.CatCommand");
    CommandHashMap.put("echo", "commands.EchoCommand");
    CommandHashMap.put("pushd", "commands.PushdCommand");
    CommandHashMap.put("popd", "commands.PopdCommand");
    CommandHashMap.put("tree", "commands.TreeCommand");
    CommandHashMap.put("rm", "commands.RemoveClass");
    CommandHashMap.put("find", "commands.FindCommand");
    CommandHashMap.put("curl", "commands.CurlCommand");
    CommandHashMap.put("save", "commands.SaveCommand");
    CommandHashMap.put("load", "commands.LoadCommand");
    CommandHashMap.put("find", "commands.FindCommand");
    CommandHashMap.put("cp", "commands.CpCommand");
    CommandHashMap.put("mv", "commands.MvCommand");
  }
}
