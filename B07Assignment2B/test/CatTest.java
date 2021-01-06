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
import commands.CatCommand;
import commands.LsCommand;
import filesystem.Directory;
import filesystem.FileClass;
import mock.MockFileSystem;

public class CatTest {

  private CatCommand cat;
  private MockFileSystem mockSystem;

  @Before
  public void setUp() {
    cat = new CatCommand();
    cat.setMock();
    this.cat.validateInput.setMock();
    this.cat.redirection.setMock();
    this.mockSystem = MockFileSystem.currentFileSystemInstance();
    this.mockSystem.check = false;
  }


  // Test redirection with a pathway that does not exist
  @Test
  public void testCatOnAPathwayThatDoesNotExist() {
    this.cat.setUserInput("cat does/not/exist > nope");
    assertTrue(this.cat.checkInput() == null);
  }

  // Test cat with a directory
  @Test
  public void testCatOnADirectory() {
    this.cat.setUserInput("cat directory");
    assertTrue(this.cat.checkInput() == null);
  }


  // Test cat with a file named 'catme'
  @Test
  public void testCatOnAFileInRootDirectory() {

    // there is a file named catme in root directory
    this.cat.setUserInput("cat catme");


    FileClass file = new FileClass("This is the content");
    Directory one = new Directory("catme", file);
    this.mockSystem.workingDir.setChild(one);
    one.setParent(this.mockSystem.workingDir);


    // Check if the command returned the contents
    assertTrue(this.cat.checkInput().equals("This is the content"));
  }

  // Test cat with multiple pathways

  @Test
  public void testCatOnMultipleValidPaths() {

    // there is a file named first in root directory with contents "hello"
    // there is a file named second in root directory with contents "bye"
    // assume your in root directory
    this.cat.setUserInput("cat ../first ./second");


    FileClass file = new FileClass("hello");
    FileClass fileTwo = new FileClass("bye");
    Directory one = new Directory("first", file);
    Directory two = new Directory("second", fileTwo);

    // Build the child parent relationship to simulate the situation
    // (hard coded)

    this.mockSystem.workingDir.setChild(one);
    this.mockSystem.workingDir.setChild(two);
    one.setParent(this.mockSystem.workingDir);
    two.setParent(this.mockSystem.workingDir);

    // See if cat returned correct output
    assertTrue(this.cat.checkInput().equals("hello\nbye"));
  }

  // Test cat with a valid path proceeded by an invalid one
  @Test
  public void testCatOnOneInvalidPathGivenMultiplePath() {

    // there is a file named first in root directory with contents "hello"
    // there is a file named second in root directory with contents "bye"
    // assume your in root directory
    this.cat.setUserInput("cat ../first invalidPath");


    FileClass file = new FileClass("hello");
    FileClass fileTwo = new FileClass("bye");
    Directory one = new Directory("first", file);
    Directory two = new Directory("second", fileTwo);
    this.mockSystem.workingDir.setChild(one);
    this.mockSystem.workingDir.setChild(two);
    one.setParent(this.mockSystem.workingDir);
    two.setParent(this.mockSystem.workingDir);



    assertTrue(this.cat.checkInput().equals("hello"));
  }


  // Test cat with redirection using '>>' an absolute pathways
  @Test
  public void testCatWithRedirectionGivenRelativePathway() {

    // there is a file named first in root directory with contents "hello"
    // assume your in root directory
    this.cat.setUserInput("cat ../first >> /one/two");


    FileClass file = new FileClass("hello");

    Directory one = new Directory("first", file);
    Directory dir1 = new Directory("dir1");
    Directory dir2 = new Directory("dir2");
    this.mockSystem.workingDir.setChild(one);
    this.mockSystem.workingDir.setChild(dir1);
    dir1.setChild(dir2);
    dir2.setParent(this.mockSystem.workingDir);
    dir1.setParent(this.mockSystem.workingDir);
    one.setParent(this.mockSystem.workingDir);

    // Since Redirection, does not output to terminal.
    // And current implementation returns null if problem while executing
    assertTrue(this.cat.checkInput().equals(""));
  }

  @Test
  public void testCatWithWithABiggerFileSystem() {

    // there is a file named first in root directory with contents "hello"
    // assume that your in /one/two
    this.cat.setUserInput("cat /first");


    FileClass file = new FileClass("hello");
    Directory one = new Directory("first", file);
    Directory dir1 = new Directory("dir1");
    Directory dir2 = new Directory("dir2");

    this.mockSystem.workingDir.setChild(one);
    this.mockSystem.workingDir.setChild(dir1);
    dir1.setChild(dir2);

    dir2.setParent(dir1);
    dir1.setParent(this.mockSystem.workingDir);
    one.setParent(this.mockSystem.workingDir);

    this.mockSystem.workingDir = dir2;

    // Check if this command only returns "hello"

    assertTrue(this.cat.checkInput().equals("hello"));
  }


}
