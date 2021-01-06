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
import org.junit.Before;
import org.junit.Test;
import commands.RemoveClass;
import filesystem.FileSystem;
import mock.MockFileSystem;

public class RmTest {
  private RemoveClass rm;
  private MockFileSystem mockSystem;

  @Before
  public void setUp() {
    rm = new RemoveClass();
    rm.setMock();
    this.mockSystem = MockFileSystem.currentFileSystemInstance();
    mockSystem.name = "";
    this.mockSystem.check = false;
  }

  // Assuming you are in root, checks if return is a "" (executed properly)
  @Test
  public void testIfWorks() {
    this.rm.setUserInput("rm one");
    rm.checkInput();
    assertTrue(rm.checkInput().equals(""));
  }

  // Verifies that the folder was made (part of the same test as above)
  @Test
  public void testIfRemoved() {
    this.rm.setUserInput("rm one");
    rm.checkInput();
    assertTrue(this.mockSystem.check == true);
  }

  // Assume you are inside /one/two, this test verifies that it does not let
  // you remove a parent of your current location
  // Null signifies that it failed to execute
  @Test
  public void testInCurrent() {
    this.rm.setUserInput("rm /one");
    rm.checkInput();
    mockSystem.name = "a parent";
    assertTrue(rm.checkInput() == null);
  }

  // Test to see a directory that does not exist
  @Test
  public void checkNonExistant() {
    this.rm.setUserInput("rm two");
    rm.checkInput();
    assertTrue(rm.checkInput() == null);
  }

  // In addition to the one above, veriifes that nothing was removed from
  // FileSystem
  @Test
  public void verifyDidNotRemove() {
    this.rm.setUserInput("rm two");
    rm.checkInput();
    assertTrue(this.mockSystem.check == false);
  }


  // Verify it was not actually removed and that our return null was satisfied
  @Test
  public void pathWayThatDoesNotExist() {
    this.rm.setUserInput("rm /one/two/three/four");
    rm.checkInput();
    assertTrue(rm.checkInput() == null);
  }

  // Check that it was not actually removed again
  @Test
  public void checkIfRemoved() {
    this.rm.setUserInput("rm /one/two/three/four");
    rm.checkInput();
    assertTrue(this.mockSystem.check == false);
  }

  // Check Rm with no parameters
  @Test
  public void checkRm() {
    this.rm.setUserInput("rm");
    rm.checkInput();
    assertTrue(rm.checkInput() == null);
  }

  // Verify the remove method was not reached
  @Test
  public void checkRemoved() {
    this.rm.setUserInput("rm");
    rm.checkInput();
    assertTrue(this.mockSystem.check == false);
  }

}
