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
import org.junit.Test;
import commands.Exit;

public class ExitTest {

  // Check correct exit

  @Test
  public void testOne() {
    assertTrue(Exit.ExitCheck("exit") == true);
  }

  // Test exit with multiple pathways
  @Test
  public void testTwo() {
    assertTrue(Exit.ExitCheck("exit 1 2 3 4") == false);
  }

  // test exit with the speak command
  @Test
  public void testThree() {
    assertTrue(Exit.ExitCheck("exit speak") == false);
  }

}
