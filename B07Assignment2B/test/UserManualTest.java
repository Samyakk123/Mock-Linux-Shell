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

package test;

import static org.junit.Assert.*;
import java.util.Hashtable;
import org.junit.Test;
import commands.UserManual;

public class UserManualTest {
  UserManual manCommand = new UserManual();
  Hashtable<String, String> commandsManual = new Hashtable<String, String>();

  public UserManualTest() {
    commandsManual = manCommand.getCommandsOutput();
  }

  // test if man 'speak' works
  @Test
  public void testOne() {
    manCommand.setUserInput("man speak");
    String output = manCommand.checkInput();
    assertTrue(output.equals(this.commandsManual.get("speak")));
  }

  // test man with another command
  @Test
  public void testTwo() {
    manCommand.setUserInput("man ls");
    String output = manCommand.checkInput();
    assertTrue(output.equals(this.commandsManual.get("ls")));
  }

  // test man with no parameters
  @Test
  public void testThree() {
    manCommand.setUserInput("man");
    String output = manCommand.checkInput();
    assertTrue(output == null);
  }

  // test man with 2 parameters instead of one
  @Test
  public void testFour() {
    manCommand.setUserInput("man speak load");
    String output = manCommand.checkInput();
    assertTrue(output == null);
  }

  // test man with an invalid command
  @Test
  public void testFive() {
    manCommand.setUserInput("man sort");
    String output = manCommand.checkInput();
    assertTrue(output == null);
  }

  // test man with a bunch of incorrect parameters
  @Test
  public void testSix() {
    manCommand.setUserInput("man 1 2 3 4 5 6");
    String output = manCommand.checkInput();
    assertTrue(output == null);
  }
}
