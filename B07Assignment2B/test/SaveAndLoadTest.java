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
import commands.LoadCommand;
import commands.MkdirCommand;
import commands.SaveCommand;
import filesystem.FileSystem;
import mock.MockFileSystem;

public class SaveAndLoadTest {
  private SaveCommand save;
  private LoadCommand load;
  private FileSystem fileSystem;

  @Before
  public void setUp() {
    this.fileSystem = FileSystem.currentFileSystemInstance();
    this.save = new SaveCommand();
    this.load = new LoadCommand();
  }

  @Test
  public void testSaveAndLoadIfFileSavesOnSystem() {
    // testing if save and load works by adding a file and then saving it
    // then adding another file, and then loading to see if the file added after
    // saving is there or not
    // in this case two is not there, which would mean the test passed
    this.fileSystem.addFile("one", "");
    this.save.setUserInput("save hi");
    this.save.checkInput();
    this.fileSystem.addFile("two", "");
    this.load.setUserInput("load hi");
    this.load.checkInput();
    this.fileSystem = FileSystem.currentFileSystemInstance();
    assertTrue(this.fileSystem.getChildrenOfWorkingDir().equals(" one"));
  }

  @Test
  public void testSaveWithInValidParam() {
    // testing save with improper parameter
    this.save.setUserInput("save hi whats up");
    assertTrue(this.save.checkInput() == null);
  }

  @Test
  public void testLoadWithInValidParam() {
    // testing load with improper parameter
    this.save.setUserInput("load hi whats up");
    assertTrue(this.save.checkInput() == null);
  }


}
