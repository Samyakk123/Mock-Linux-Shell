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
import java.util.Hashtable;
import filesystem.CommandsInterface;
import filesystem.Output;

/**
 * 
 * Class responsible for printing out the manual for each command.
 *
 */
public class UserManual extends Commands implements CommandsInterface {

  private Hashtable<String, String> manCommands =
      new Hashtable<String, String>();
  /** the current redirection that will be used */
  public Redirection redirection = new Redirection();
  private boolean redirect = false;

  /**
   * Constructor for userManual
   */
  public UserManual() {
    String manSpeak = " NAME\n\n speak - says audio to your computer \n\n "
        + "SYNPOSIS \n\n speak [Enter text that you would like spoken] \n\n "
        + "DESCRIPTION \n\n The speak command converts user inputted text from "
        + "the terminal into audible noise emitted through your computer";
    String manMan = " NAME\n\n man - provides a description about any command "
        + "that is usable in this command line \n\n SYNOPSIS \n\n man COM "
        + "[Enter command you want clarification about] \n\n DESCRIPTION \n\n "
        + "The man command provides a description regarding how to use the "
        + "command provided. It is used to clarify misunderstandings\n and "
        + "help understand the way the commands function";
    String manCd = " NAME\n cd - Changes the users current working directory to"
        + " the one provided. \n\n SYNOPSIS \n\n cd .. [Used to go one "
        + "directory back] \n cd /DIR [Used to cd into a directory] \n cd "
        + "/DIR/DIR... [Used to cd into multiple directories at once] \n\n "
        + "DESCRIPTION \n\n The cd command is responsible for changing the "
        + "Users current working directory stored inside the FileSystem. "
        + "\nIf the DIR parameter exists, it updates the working directory to "
        + "be the new one in the filesystem";
    String manMkdir = " NAME\n\n mkdir - Creates a new directory inside the "
        + "fileSystem. \n\n SYNPOSIS \n\n mkdir DIR [Creates a new Directory "
        + "with the name specified as DIR] \n mkdir DIR/DIR/... [Builds the "
        + "directory at the last DIR assuming the pathway up until the last "
        + "DIR exists] \n\n DESCRIPTION \n\n The mkdir command is responsible "
        + "for creating a new directory inside the FileSystem hierarchy and "
        + "making it accessible \nto the working directory (if they want to "
        + "cd into it). Sets the name of the directory as the one specified "
        + "inside the parameter";
    String manLs = " NAME\n\n ls - displays all the content inside a current "
        + "directories location. \n\n SYNPOSIS \n\n ls [prints all the current"
        + " files and directories located in the users current working "
        + "directory] \n ls DIR [prints all the current files and directories "
        + "located inside DIR] \n ls DIR/DIR/(DIR/FILE)... [prints all the "
        + "current files or files and directories located inside the path "
        + "provided] \n ls DIR/(DIR/FILE).. DIR/(DIR/FILE).. DIR/(DIR/FILE).. "
        + "[prints the current files and directories in each of the paths "
        + "provided\n\n DESCRIPTION \n\n The ls command is responsible for "
        + "displaying the current directories and files located in a pathway. "
        + "\nIts main purpose is to allow the user to see what exists inside "
        + "each directory and assess their current options";
    String manPwd = " NAME\n\n pwd - prints the current working directory \n\n "
        + "SYNPOSIS \n\n pwd [No parameters] \n\n DESCRIPTION \n\n The pwd "
        + "command is used to show the user their current location inside the "
        + "fileSystem. It displays to them their pathway \nfrom the root "
        + "directory";
    String manPushd = " NAME\n\n Pushd - Saves the users current working "
        + "directory inside a stack \n\n SYNPOSIS \n\n Pushd DIR [Moves your "
        + "current working directory to DIR] \n Pushd DIR/DIR/... [Moves your "
        + "current working directory to the PATH pathway provided] \n\n "
        + "DESCRIPTION \n\n The Pushd command is used to save the users current"
        + " working directory inside a stack (LIFO format) \nand continues "
        + "to change the users current working directory into the one provided "
        + "in the parameter";
    String manPopd = " NAME\n\n Popd - Sets the users current working "
        + "directory as the last saved entry in the stack \n\n SYNPOSIS \n\n"
        + " popd [No Parameters] \n\n DESCRIPTION\n\n Moves the users current"
        + " working directory to the most recently saved one in the Stack "
        + "(Recall this is conducted in LIFO format, \nso the location most"
        + " recently saved is the one that gets updated). The location after"
        + " setting it to the user, is then removed from the stack.";
    String manCat = " NAME\n\n cat - prints the contents inside a file \n\n "
        + "SYNPOSIS \n\n cat FILE [Prints content of FILE] \n cat DIR/.../FILE"
        + " [Prints content of FILE at the end of pathway] \n\n DESCRIPTION "
        + "\n\n Outputs the contents of the file provided in the parameter "
        + "(FILE). Must be a file! NOT a directory! Now supports redirection!";
    String manEcho = " NAME\n\n Echo > - Takes in 2 files as parameters and "
        + "overwrites the second with first \n\n SYNPOSIS \n\n echo > FILE1 "
        + "FILE2 [Replaces FILE1 with FILE2] \n echo >> FILE1 FILE2 [Appends "
        + "FILE1 onto FILE2] \n Works with both absolute and relative pathways"
        + " \n\n DESCRIPTION \n\n Depending on user input, if > was entered "
        + "FILE1 will replace the contents of FILE2. If >> was entered after,"
        + " \nthen FILE1 will append onto FILE2 content. If FILE2 does not "
        + "exist, it will create one. Now supports redirection!";

    String manCp = " NAME \n\n cp - Takes in 2 files or directories and copies"
        + " the first one into the second. \n\n SYNPOSIS \n\n cp FILE1 DIR1 "
        + "[Copies FILE1 to DIR1]\n"
        + " cp DIR1 DIR2 [Copies DIR1 into DIR2 including all subdirectories]"
        + "\n\n DESCRIPTION \n\n Copies the first contents into "
        + "the second (unless first one is a directory and second is a file)";

    String manMv = " NAME \n\n mv - Takes in 2 files or directories and moves "
        + "the first one into the second. \n\n SYNPOSIS \n\n mv FILE1 FILE2"
        + " [Renames FILE1 to FILE2] \n  mv FILE1 DIR1 [Moves FILE1 inside DIR1"
        + "[Removes the old instnace] \n mv DIR1 DIR2 [Moves DIR1 into DIR2 "
        + "removingit from DIR1s location \n\n DESCRIPTION \n\n If two files "
        + "have the  same name they replace FILE1 with FILE2";

    String manFind = " NAME \n\n find - Looks through all subdirectories and "
        + "finds all instances of the one entered. \n\n SYNPOSIS \n\n "
        + "find PATHWAY -type d -name NAME [Finds all DIRECTORIES with the name"
        + "NAME inside all the subdirectories. \n find PATHWAY -type f -name "
        + "NAME [Finds all FILES with the name NAME inside all the "
        + "subdirectories \n\n DESCRIPTION \n\n Finds all instances of a file "
        + "or directory located in the sub directories. Now supports "
        + "redirection!";

    String manSave = " NAME \n\n save - Saves the currnt fileSystem into a file"
        + "inside your computer \n\n SYNOPSIS \n\n save FILENAME [Saves a file"
        + "which contains the current fileSystem onto your computer] \n\n "
        + "DESCRIPTION \n\n Works in collaboration with Load";

    String manLoad = " NAME \n\n load - Loads the last working fileSystem that"
        + "was saved so that the user can continue working from last location."
        + "\n\n SYNPOSIS \n\n load FILENAME [Looks for FILENAME inside your"
        + "computer and opens up the fileSystem that has that at it's location"
        + "\n\n DESCRIPTION \n\n Moves you to your last current working "
        + "directory as well as all old files and directories located before";

    String manRemove = " NAME \n\n Remove - Removes a directory from the users"
        + "computer \n\n SYNPOSIS \n\n Remove DIR [Removes DIR from fileSystem]"
        + " \n\n DESCRIPTION \n\n It is used to remove a directory from your "
        + "fileSystem";

    String manTree = " NAME \n\n tree - Prints the entrire fileSystem hierarchy"
        + "with all subdirectories and files. \n\n SYNPOSIS \n\n tree [Prints "
        + "from the root directory] \n\n DESCRIPTION \n\n It is used to have "
        + "a blueprint form of an entire outline of the entire fileSystem";

    // String manMv = " NAME \n\n mv - Takes in 2 files or directories and moves"
    // + " the first one into the second. \n\n SYNPOSIS \N\N mv FILE1 FILE"

    // So that we can loop through a hashMap and save each value easily
    String[] commandText = {manSpeak, manMan, manCd, manMkdir, manLs, manPwd,
        manPushd, manPopd, manCat, manEcho, manCp, manMv, manFind, manSave,
        manLoad, manRemove, manTree};

    String[] allCommands =
        {"speak", "man", "cd", "mkdir", "ls", "pwd", "pushd", "popd", "cat",
            "echo", "cp", "mv", "find", "save", "load", "rm", "tree"};

    // Traverse through the hashmap and place each element with it's
    // corresponding String text
    for (int i = 0; i < commandText.length; i++) {
      manCommands.put(allCommands[i], commandText[i]);
    }
  }

  /**
   * Executes man command
   */
  protected void executeCommand() {

    // Split the user input by each word
    String user_input = super.userInput.split("\\s+")[1];

    if (manCommands.containsKey(user_input)) {
      // Output.printToUser(manCommands.get(user_input));
      super.content += manCommands.get(user_input);
    }

    else {
      super.content = null;
      ErrorOutput.notValidInput(user_input);
    }

  }

  /**
   * Checks if input from user is valid before calling executeCommand
   * 
   * @return super.content the output string
   */
  public String checkInput() {

    super.arr = super.userInput.split("\\s+");

    if (super.arr.length > 3
        && this.redirection.checkRedirection(super.arr[super.arr.length - 2])) {
      this.redirect = true;
    }

    if (this.redirect && super.arr.length == 4) {
      executeCommand();

    }

    else {

      if (super.arr.length > 4) {
        super.content = null;
        ErrorOutput
            .printWithNewLine("Error: The param you inputted is invalid!");

      }

      else if (!this.redirect
          && validateInput.checkNumberOfParameterOne(super.userInput)) {
        // checker == true) {
        this.executeCommand();

      }

      else {
        super.content = null;
      }

    }

    if (this.redirect && super.arr.length == 4) {
      this.redirection.executeRedirection(super.content, super.userInput);
      super.content = "";
    }

    return super.content;
  }

  public Hashtable<String, String> getCommandsOutput() {
    return this.manCommands;
  }
}
