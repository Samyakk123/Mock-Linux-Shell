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
import commands.TreeCommand;
import filesystem.Directory;
import mock.MockFileSystem;

public class TreeTest {
  private TreeCommand tree;
  private MockFileSystem mockSystem;

  @Before
  public void setUp() {
    tree = new TreeCommand();
    tree.setMock();
    this.tree.validateInput.setMock();
    this.tree.redirection.setMock();
    this.mockSystem = MockFileSystem.currentFileSystemInstance();
    this.mockSystem.check = false;
  }

  @Test
  public void testTreeWithInvalidParameters() {
    this.tree.setUserInput("tree folder");
    // check for invalid input
    assertTrue(this.tree.checkInput() == null);
  }

  @Test
  public void testTreeWithRedirectionInCurrentDirectory() {
    // assume your in root directory with no files/folders
    this.tree.setUserInput("tree > here");
    // check if redirected into file named here
    assertTrue(this.tree.checkInput().equals(""));
  }

  @Test
  public void testTreeWithRedirectionWithInvalidFileName() {
    // assume your in root directory with no files/folders
    this.tree.setUserInput("tree > her.e");
    // checks that you cannot create file with invalid char
    assertTrue(this.tree.checkInput() == null);
  }

  @Test
  public void testTreeAssumingYourAnywhereInFileSystem() {
    // assume your in folder /one/two and file system contains the path
    // one/two/three
    // and you have file named file in folder three
    this.tree.setUserInput("tree");
    Directory one = new Directory("one");
    Directory two = new Directory("two");
    Directory three = new Directory("three");
    Directory file = new Directory("file", null);
    this.mockSystem.workingDir.setChild(one);
    one.setChild(two);
    two.setChild(three);
    one.setParent(this.mockSystem.workingDir);
    two.setParent(one);
    three.setParent(two);
    three.setChild(file);
    this.mockSystem.workingDir = two;

    assertTrue(this.tree.checkInput()
        .equals("/\n" + " one\n" + "  two\n" + "   three\n" + "    file\n"));
  }

  @Test
  public void testTreewhenRedirectingToAbsPathwayFromAnywhereInFileSystem() {
    // assume your in folder /one/two and file system contains the path
    // one/two/three
    // and you have file named file in folder three
    this.tree.setUserInput("tree > /ok");

    // Hard code the directories
    Directory one = new Directory("one");
    Directory two = new Directory("two");
    Directory three = new Directory("three");
    Directory file = new Directory("file", null);
    this.mockSystem.workingDir.setChild(one);
    one.setChild(two);
    two.setChild(three);
    one.setParent(this.mockSystem.workingDir);
    two.setParent(one);
    three.setParent(two);
    three.setChild(file);
    this.mockSystem.workingDir = two;

    assertTrue(this.tree.checkInput().equals(""));
  }

}
