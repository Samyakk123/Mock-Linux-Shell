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
import commands.CurlCommand;
import filesystem.Directory;
import filesystem.FileClass;
import mock.MockFileSystem;

public class CurlTest {

  private CurlCommand curlComm;
  private MockFileSystem mockSystem;

  @Before
  public void setUp() {
    curlComm = new CurlCommand();
    curlComm.setMock();
    this.curlComm.validateInput.setMock();
    this.mockSystem = MockFileSystem.currentFileSystemInstance();
    mockSystem.name = "";
    this.mockSystem.check = false;
    mockSystem.workingDir = new Directory("/");
  }

  // Test with valid URL if file was created
  @Test
  public void testValidWebsite() {
    curlComm.setUserInput(
        "curl http://www.cs.utoronto.ca/~trebla/CSCB09-2020-Summer/");
    curlComm.checkInput();

    // See if the correct file name was added

    assertTrue(mockSystem.getWorkingDir().getTypeChildren().get(0).getContent()
        .equals("CSCB09-2020-Summer"));

  }


  // Test with already existing file with that name so see if it over writes
  @Test
  public void testOverwrite() {
    FileClass file = new FileClass("blah");
    Directory dir = new Directory("comm-input-1.txt", file);
    curlComm.setUserInput(
        "curl http://www.cs.utoronto.ca/~trebla/CSCB09-2020-Summer/l02/"
            + "comm-input-1.txt");
    curlComm.checkInput();
    // See if the content was modified and no longer blah
    assertTrue(!mockSystem.getWorkingDir().getTypeChildren().get(0)
        .getFileContent().equals("blah"));

  }

  // Test random website that does not exist
  @Test
  public void testDoesntExist() {
    curlComm.setUserInput("curl randomwebsitehere!!!@#!@#!");
    curlComm.checkInput();

    // Since failed to execute command should return null
    assertTrue(curlComm.checkInput() == null);
  }

}
