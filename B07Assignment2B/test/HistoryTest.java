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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import commands.CurlCommand;
import commands.HistoryCommand;
import filesystem.Directory;
import java.lang.reflect.Field;
import mock.MockFileSystem;



public class HistoryTest {
  private HistoryCommand historyComm;
  private MockFileSystem mockSystem;



  // NOTE: In these test cases the actual 'history' command itself is not
  // added into the output because that role is performed by parse and we
  // test history in isolation in this command so it does not get added


  @Before
  public void setUp() throws Exception {
    historyComm = HistoryCommand.currentHistoryClassInstance();
    historyComm.setMock();
    this.historyComm.validateInput.setMock();
    this.mockSystem = MockFileSystem.currentFileSystemInstance();
    mockSystem.name = "";
    this.mockSystem.check = false;
    mockSystem.workingDir = new Directory("/");
  }

  // Reset the singleton after each use
  @After
  public void afterCase() throws Exception {
    Field field = (historyComm.getClass()).getDeclaredField("history");
    field.setAccessible(true);
    field.set(null, null); // setting the ref parameter to null
  }

  // Test history with 3 valid command inputs
  @Test
  public void checkNoParam() {
    historyComm.addToList("ls");
    historyComm.addToList("mkdir one");
    historyComm.addToList("mkdir one/two");
    historyComm.setUserInput("history");
    String returnVal = historyComm.checkInput();
    String output = "1.) ls\n" + "2.) mkdir one\n" + "3.) mkdir one/two\n";
    assertEquals(returnVal, output);


  }

  // Test history assuming the user entered junk
  @Test
  public void checkInvalidInputStillAddedToHistory() {
    historyComm.addToList("random");
    historyComm.addToList("incorrect");
    historyComm.addToList("statements");
    historyComm.addToList("here");
    historyComm.setUserInput("history");
    String returnVal = historyComm.checkInput();
    String output = "1.) random\n2.) incorrect\n3.) statements\n4.) here\n";
    assertEquals(returnVal, output);
  }

  // Test history with a paraMeter
  @Test
  public void checkHistoryWithParam() {
    historyComm.addToList("one");
    historyComm.addToList("two");
    historyComm.addToList("three");
    historyComm.addToList("four");
    historyComm.addToList("five");
    historyComm.setUserInput("history 3");
    String returnVal = historyComm.checkInput();
    String output = "3.) three\n4.) four\n5.) five\n";
    assertEquals(returnVal, output);
  }

  // Test history with an invalid non-numerical paraMeter
  @Test
  public void checkInvalidIntegerParam() {
    historyComm.addToList("one");
    historyComm.addToList("two");
    historyComm.addToList("three");
    historyComm.setUserInput("history notRealNumber");
    String returnVal = historyComm.checkInput();
    assertEquals(returnVal, null);
  }

  // Test history with an invalid negative paraMeter
  @Test
  public void historyWithNegativeNumbers() {
    historyComm.addToList("one");
    historyComm.addToList("two");
    historyComm.addToList("three");
    historyComm.setUserInput("history -20");

    String returnVal = historyComm.checkInput();
    assertEquals(returnVal, null);
  }


  // Test history with an invalid number of parameters (should only have 1 number)
  @Test
  public void historyWithMultipleNumbers() {
    historyComm.addToList("one");
    historyComm.addToList("two");
    historyComm.addToList("three");
    historyComm.setUserInput("history 10 20");
    String returnVal = historyComm.checkInput();
    // System.out.println(returnVal);
    assertEquals(returnVal, null);
  }

  // Test history with an valid number which is greater then the size
  @Test
  public void historyWithHigherParamThenElements() {
    historyComm.addToList("one");
    historyComm.addToList("two");
    historyComm.addToList("three");
    historyComm.setUserInput("history 10");
    String returnVal = historyComm.checkInput();

    String output = "1.) one\n2.) two\n3.) three\n";
    assertEquals(returnVal, output);
  }


}
