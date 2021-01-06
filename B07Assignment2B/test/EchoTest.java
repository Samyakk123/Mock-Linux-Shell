package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import commands.EchoCommand;
import commands.HistoryCommand;
import mock.MockFileSystem;

public class EchoTest {

  private EchoCommand echoComm;
  private MockFileSystem mockSystem;

  @Before
  public void setUp() {
    echoComm = new EchoCommand();
    mockSystem = MockFileSystem.currentFileSystemInstance();
    echoComm.setMock();
    this.echoComm.validateInput.setMock();
    this.echoComm.redirection.setMock();
    this.mockSystem.name = "";
    this.mockSystem.check = false;
  }

  @Test
  public void testEchoWithJustStringInputValid() {
    // checking if correct output given with normal echo
    echoComm.setUserInput("echo \"hi\"");
    assertTrue(echoComm.checkInput().equals("hi"));
  }

  @Test
  public void testEchoWithJustStringInputInvalid() {
    // checking if correct output given with normal echo with invalid input
    echoComm.setUserInput("echo \"hi\"\"");
    assertTrue(echoComm.checkInput() == null);
  }

  @Test
  public void testEchoWithInputOverWriteIntoFileA() {
    // assuming file one exists
    echoComm.setUserInput("echo \"hi\" > one");
    // checking if empty string returned, so no error
    assertTrue(echoComm.checkInput().equals(""));
  }

  @Test
  public void testEchoWithInputOverWriteIntoFileB() {
    // assuming file one exists
    echoComm.setUserInput("echo \"hi\" > one");
    echoComm.checkInput();
    // checking if file contents is set
    assertTrue(mockSystem.check == true);
  }

  @Test
  public void testEchoWithInputAppendIntoFileA() {
    // assuming file one exists
    echoComm.setUserInput("echo \"hi\" >> one");
    // checking if empty string returned, so no error
    assertTrue(echoComm.checkInput().equals(""));
  }

  @Test
  public void testEchoWithInputAppendIntoFileB() {
    // assuming file one exists
    echoComm.setUserInput("echo \"hi\" >> one");
    echoComm.checkInput();
    // checking if file contents is set
    assertTrue(mockSystem.check == true);
  }

  @Test
  public void testEchoWithInputIntoFileThatDoesNotExistA() {
    // assuming file one doesn't exits
    echoComm.setUserInput("1echo \"hi\" > one");
    echoComm.checkInput();
    // checking if empty string returned, so no error
    assertTrue(echoComm.checkInput().equals(""));
  }

  @Test
  public void testEchoWithInputIntoFileThatDoesNotExistB() {
    // assuming file one doesn't exits
    echoComm.setUserInput("1echo \"hi\" > one");
    echoComm.checkInput();
    // checking if file contents is set
    assertTrue(mockSystem.check == true);
  }

  @Test
  public void testEchoWithInputIntoFileWithPath() {
    // assuming file two does exist
    echoComm.setUserInput("echo \"hi\" > /one/two");
    echoComm.checkInput();
    // checking if file contents is set
    assertTrue(mockSystem.check == true);
  }

  @Test
  public void testEchoWithInputIntoFileWithInvalidPath() {
    // assuming folder one does not exist
    echoComm.setUserInput("echo \"hi\" > /one/three");
    // checking if returns null which means error
    assertTrue(echoComm.checkInput() == null);
  }

  @Test
  public void testEchoWithInvalidNumberOfParam() {
    // assuming folder one does not exist
    echoComm.setUserInput("echo 1 2 3 4");
    // checking if returns null which means error
    assertTrue(echoComm.checkInput() == null);
  }

  @Test
  public void testEchoWithJustEcho() {
    // assuming folder one does not exist
    echoComm.setUserInput("echo");
    // checking if returns null which means error
    assertTrue(echoComm.checkInput() == null);
  }
}
