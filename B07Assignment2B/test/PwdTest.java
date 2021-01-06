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
import commands.PwdCommand;
import mock.MockFileSystem;

public class PwdTest {
  private PwdCommand pwd;
  private MockFileSystem mockSystem;

  // NOTE: Ignore the numerical values preceding the 'pwd' command in some
  // of these test cases as it was purely so that we could hard code and
  // assess these situations case by case inside our mockCheckPath

  @Before
  public void setUp() {
    pwd = new PwdCommand();
    pwd.setMock();
    this.pwd.validateInput.setMock();
    this.pwd.redirection.setMock();
    this.mockSystem = MockFileSystem.currentFileSystemInstance();
    this.mockSystem.check = false;
  }


  @Test
  public void testPwdWithRootDirectory() {
    // testing when in root directory
    this.pwd.setUserInput("1pwd");
    mockSystem.name = "1pwd";
    assertTrue(this.pwd.checkInput().equals("/\n"));
  }

  @Test
  public void testPwdWithNoneRootDirectory() {
    // testing when in none root directory
    this.pwd.setUserInput("2pwd");
    mockSystem.name = "2pwd";
    assertTrue(this.pwd.checkInput().equals("/one/two\n"));
  }

  @Test
  public void testPwdWithImproperNumberOfParam() {
    // testing when in none root directory
    this.pwd.setUserInput("pwd hi whats up");
    mockSystem.name = "pwd hi whats up";
    assertTrue(this.pwd.checkInput() == null);
  }

  @Test
  public void testPwdWithRedirectionWhereFileDoesNotExistA() {
    // assuming at root directory and redirecting in a file that doesn't exist
    this.pwd.setUserInput("pwd > one");
    mockSystem.name = "pwd > one";
    // checking if output is correct
    assertTrue(this.pwd.checkInput().equals(""));
  }

  @Test
  public void testPwdWithRedirectionWhereFileDoesNotExistB() {
    // assuming at root directory and redirecting in a file that doesn't exist
    this.pwd.setUserInput("pwd > one");
    mockSystem.name = "pwd > one";
    this.pwd.checkInput();
    // checking if file is added
    assertTrue(mockSystem.check == true);
  }

  @Test
  public void testPwdWithRedirectionWhereFileDoesExistA() {
    // assuming at root directory and redirecting in a file that does exist
    this.pwd.setUserInput("2pwd > one");
    mockSystem.name = "2pwd > one";
    assertTrue(this.pwd.checkInput().equals(""));
  }

  @Test
  public void testPwdWithRedirectionWhereFileDoesExistB() {
    // assuming at root directory and redirecting in a file that does exist
    this.pwd.setUserInput("2pwd > one");
    mockSystem.name = "2pwd > one";
    // checking if filecontents were updated
    this.pwd.checkInput();
    assertTrue(mockSystem.check == true);
  }

  @Test
  public void testPwdWithRedirectionWhereFileDoesExistWithAppendingA() {
    // assuming at root directory and redirecting in a file that does exist
    this.pwd.setUserInput("2pwd >> /one");
    mockSystem.name = "2pwd >> /one";
    // checking if filecontents were updated
    assertTrue(this.pwd.checkInput().equals(""));
  }

  @Test
  public void testPwdWithRedirectionWhereFileDoesExistWithAppendingB() {
    // assuming at root directory and redirecting in a file that does exist
    this.pwd.setUserInput("2pwd >> /one");
    mockSystem.name = "2pwd >> /one";
    // checking if filecontents were updated
    this.pwd.checkInput();
    assertTrue(mockSystem.check == true);
  }

  @Test
  public void testPwdWithRedirectionInvalidCharacters() {
    // assuming at root directory and redirecting in a file with invalid
    // characters
    this.pwd.setUserInput("pwd > o$o");
    mockSystem.name = "pwd > o$o";
    // checking if filecontents were updated
    assertTrue(this.pwd.checkInput() == null);
  }

  @Test
  public void testPwdWithRedirectionWithInvalidPath() {
    // assuming at root directory and redirecting in a folder that does exist
    // assume one is a directory that doesn't exist
    this.pwd.setUserInput("pwd > /one/two");
    mockSystem.name = "pwd > /one/two";
    // checking if filecontents were updated
    assertTrue(this.pwd.checkInput() == null);
  }

}
