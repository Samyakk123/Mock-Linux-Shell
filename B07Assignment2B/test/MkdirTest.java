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
import commands.MkdirCommand;
import mock.MockFileSystem;

public class MkdirTest {

  private MkdirCommand mkdir;
  private MockFileSystem mockSystem;


  // NOTE: Ignore the numerical values preceding the 'mkdir' command in some
  // of these test cases as it was purely so that we could hard code and
  // assess these situations case by case inside our mockCheckPath

  @Before
  public void setUp() {
    mkdir = new MkdirCommand();
    mkdir.setMock();
    this.mkdir.validateInput.setMock();
    this.mockSystem = MockFileSystem.currentFileSystemInstance();
    this.mockSystem.check = false;
  }

  // added 1, 2, 3.. etc. in front of mkdir to differentiate between same command
  // input so MockCheckPath works properly.
  @Test
  public void testMkdirWithCreatingDirectoryInRootA() {
    // assuming current directory is root
    this.mkdir.setUserInput("1mkdir one");
    // checks if no errors were given and command ran successfully.
    assertTrue(this.mkdir.checkInput().equals(""));
  }

  // test creating a directory in the root
  @Test
  public void testMkdirWithCreatingDirectoryInRootB() {
    this.mkdir.setUserInput("1mkdir one");
    this.mkdir.checkInput();
    // checks if folder was added.
    assertTrue(this.mockSystem.check == true);
  }

  // Testing duplicate directory situation
  @Test
  public void testMkdirWithCreatingDirectoryInRootWithSameNameDirRelativePath() {
    // Won't add directory cause directory already exists
    this.mkdir.setUserInput("2mkdir one");
    // checks if folder was added.
    assertTrue(this.mkdir.checkInput() == null);
  }

  // Test absolute and building on the directory. Check if command executed
  // properly by checking for the "" return
  @Test
  public void testMkdirWithCreatingDirectoryInAnotherDirectoryA() {
    // creating a directory inside a directory
    this.mkdir.setUserInput("1mkdir /one/two");
    // checks if no errors were given and command ran successfully.
    assertTrue(this.mkdir.checkInput().equals(""));
  }

  // Check the same case as before but validate that the directory was indeed
  // made
  @Test
  public void testMkdirWithCreatingDirectoryInAnotherDirectoryB() {
    this.mkdir.setUserInput("1mkdir /one/two");
    this.mkdir.checkInput();
    // checks if folder was added.
    assertTrue(this.mockSystem.check == true);
  }


  // test duplicate case but from absolute path instead of relative
  @Test
  public void testMkdirWithCreatingDirectoryInRootWithSameNameDirAbsolutePath() {
    // assuming one already exists in root
    // creating a file with the name of the directory "one" that already exists
    // won't allow you to
    this.mkdir.setUserInput("2mkdir /one");
    assertTrue(this.mkdir.checkInput() == null);
  }

  // Test mkdir with no parameters provided
  // Should return null since it failed to execute
  @Test
  public void testMkdirWithNoParam() {
    // should return null since can't call mkdir alone
    this.mkdir.setUserInput("mkdir");
    assertTrue(this.mkdir.checkInput() == null);
  }

  // test mkdir to see if valid character names are being checked before making
  @Test
  public void testMkdirWithInvalidParam() {
    // should return null since can't call mkdir on random input, so error given
    this.mkdir.setUserInput("mkdir 32$");

    // return null since $ is not a valid name
    assertTrue(this.mkdir.checkInput() == null);
  }


  // test mkdir with 2 parameters and see if it executed with "" return
  public void testMkdirWithMultiplePathWaysA() {
    // assuming in root and creating one and two
    this.mkdir.setUserInput("3mkdir one two");
    assertTrue(this.mkdir.checkInput().equals(""));
  }


  // same test as above but verify that they were actually made
  public void testMkdirWithMultiplePathWaysB() {
    // assuming in root and creating one and two
    this.mkdir.setUserInput("3mkdir one two");
    this.mkdir.checkInput();
    assertTrue(this.mockSystem.check == true);
  }

  // test mkdir with multiple pathways but the last one being invalid
  public void testMkdirWithMultiplePathwaysWithOneInvalid() {
    // assuming in root and have one invalid and one valid directory created
    this.mkdir.setUserInput("3mkdir one t$o");
    assertTrue(this.mockSystem.check == false);
  }

}
