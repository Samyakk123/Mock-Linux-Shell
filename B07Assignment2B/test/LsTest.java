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
import commands.LsCommand;
import commands.MkdirCommand;
import filesystem.Directory;
import mock.MockFileSystem;

public class LsTest {
  private LsCommand ls;
  private MockFileSystem mockSystem;

  @Before
  public void setUp() {
    ls = new LsCommand();
    ls.setMock();
    this.ls.validateInput.setMock();
    this.ls.redirection.setMock();
    this.mockSystem = MockFileSystem.currentFileSystemInstance();
    this.mockSystem.check = false;
  }

  // Test ls at the root directory
  @Test
  public void testLsOnRootDirectory() {
    this.ls.setUserInput("ls /");
    this.mockSystem.name = "ls /";
    assertTrue(this.ls.checkInput().equals(": "));
  }

  // Test ls with an invalid folder
  @Test
  public void testLsOnDirectoryThatDoesNotExist() {
    // assuming that folder one does not exist
    this.ls.setUserInput("ls one");
    this.mockSystem.name = "ls one";

    assertTrue(this.ls.checkInput() == null);
  }

  // test ls with an absolute pathway
  @Test
  public void testLsOnAbsolutePathway() {
    // assuming that folder one exist in root and has folder two three
    this.ls.setUserInput("ls /one");
    this.mockSystem.name = "ls /one";

    assertTrue(this.ls.checkInput().equals(": two, three"));
  }

  // test ls with '..'
  @Test
  public void testLsOnSpecialCases() {
    // assuming your in /one/two and one contains two
    this.ls.setUserInput("ls ../.");
    this.mockSystem.name = "ls ../.";

    assertTrue(this.ls.checkInput().equals(": two"));
  }

  // test ls with redirection into a file
  @Test
  public void testLsWithRootDirectoryAndRedirectingToAFile() {
    // assuming your in root directory and redirect into file new
    // root directory contains two and three
    this.ls.setUserInput("ls / > new");
    this.mockSystem.name = "ls / > new";

    assertTrue(this.ls.checkInput().equals(""));
  }


  // test ls with recursive call at root
  @Test
  public void testLsWithRecursiveCallOnRootDirectory() {
    // assuming your in root
    // Root has 1 which has 2 which has 3 (all folders)
    this.ls.setUserInput("ls -R /");

    Directory one = new Directory("one");
    Directory two = new Directory("two");
    Directory three = new Directory("three");

    // Set the parent child relation

    this.mockSystem.workingDir.setChild(one);
    one.setChild(two);
    two.setChild(three);
    one.setParent(this.mockSystem.workingDir);
    two.setParent(one);
    three.setParent(two);
    // See if the pathway matches our expected output
    assertTrue(this.ls.checkInput()
        .equals(": \n" + " one\n" + "  two\n" + "   three\n"));
  }
}
