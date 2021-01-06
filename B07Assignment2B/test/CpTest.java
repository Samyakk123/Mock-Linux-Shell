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
import commands.CpCommand;
import filesystem.Directory;
import filesystem.FileClass;
import mock.MockFileSystem;

public class CpTest {
  private CpCommand cpComm;
  private MockFileSystem mockSystem;
  int count = 0;

  // NOTE: Ignore the numerical values preceding the 'cp' command in some
  // of these test cases as it was purely so that we could hard code and
  // assess these situations case by case inside our mockCheckPath

  @Before
  public void setUp() {
    cpComm = new CpCommand();
    cpComm.setMock();
    this.cpComm.validateInput.setMock();
    this.mockSystem = MockFileSystem.currentFileSystemInstance();
    mockSystem.name = "";
    this.mockSystem.check = false;
    mockSystem.workingDir = new Directory("/");
    count = 0;
  }

  // Cannot copy a folder into an unknown location
  @Test
  public void testFolderAndNoPathway() {
    this.cpComm.setUserInput("cp folder1 notafolder");
    cpComm.checkInput();
    assertTrue(cpComm.checkInput() == null);

  }



  // See if cp file1 file2 copies file1 content onto file2
  @Test
  public void testFileAndFile() {
    FileClass first = new FileClass("blah");
    FileClass second = new FileClass("blah2");
    Directory one = new Directory("one", first);
    Directory two = new Directory("two", second);

    // helper function that helps set the parent child relation between
    // the directories
    helperFunction(one, mockSystem.workingDir);
    helperFunction(two, mockSystem.workingDir);


    this.cpComm.setUserInput("cp one two");
    String output = cpComm.checkInput();

    // VERIFY that the command executed properly
    if (output.equals("")) {
      count += 1;
    }

    // Check that the folder was added even though executed correctly
    if (mockSystem.getFile("two").getFileContent().equals("blah")) {
      count += 1;
    }

    // If both above statements are true count must be 2
    assertTrue(count == 2);

  }

  @Test
  public void testFileAndFolder() {
    FileClass first = new FileClass("blah");
    Directory file = new Directory("file", first);
    Directory directory = new Directory("folder");

    helperFunction(directory, mockSystem.workingDir);
    helperFunction(file, mockSystem.workingDir);


    this.cpComm.setUserInput("cp file folder");
    cpComm.checkInput();

    assertTrue(mockSystem.workingDir.getTypeChildren().get(1).getContent()
        .equals("file"));


  }

  @Test
  // Verify that the file from the above test case is still present
  public void testFileStillThere() {
    FileClass first = new FileClass("blah");
    Directory file = new Directory("file", first);
    Directory directory = new Directory("folder");
    // Sets the relationship to project the situation
    helperFunction(directory, mockSystem.workingDir);
    helperFunction(file, mockSystem.workingDir);

    this.cpComm.setUserInput("cp file folder");
    cpComm.checkInput();

    mockSystem.goToRoot();
    // Go into the child and check if it's content is equal to file
    // (i.e. that it was added)
    assertTrue(mockSystem.workingDir.getTypeChildren().get(1).getContent()
        .equals("file"));
  }

  @Test
  public void checkFolderAndFile() {

    FileClass first = new FileClass("blah");
    Directory file = new Directory("file", first);
    Directory directory = new Directory("folder");

    helperFunction(file, mockSystem.workingDir);
    helperFunction(directory, mockSystem.workingDir);
    // Cannot cp a folder into a file so it returns null
    this.cpComm.setUserInput("2cp folder file");
    assertTrue(cpComm.checkInput() == null);
  }

  @Test
  public void checkFolderAndFolder() {
    Directory directory = new Directory("folder");
    Directory directory2 = new Directory("folder2");

    helperFunction(directory, mockSystem.workingDir);
    helperFunction(directory2, mockSystem.workingDir);

    mockSystem.name = "blah";
    this.cpComm.setUserInput("3cp folder folder2");
    cpComm.checkInput();

    mockSystem.goToRoot();
    // Go into folder2
    mockSystem.workingDir = mockSystem.getWorkingDir().getTypeChildren().get(1);
    // Go into folder
    mockSystem.workingDir = mockSystem.getWorkingDir().getTypeChildren().get(0);

    // Check if the folder was added and name equal to folder
    assertTrue(mockSystem.getWorkingDir().getContent().equals("folder"));
  }


  @Test
  public void checkAbsolutePathway() {
    Directory one = new Directory("one");
    Directory two = new Directory("two");
    Directory newDir = new Directory("newDir");

    helperFunction(one, mockSystem.getWorkingDir());
    helperFunction(two, one);


    mockSystem.getWorkingDir().setChild(newDir);
    newDir.setParent(mockSystem.getWorkingDir());

    mockSystem.goToRoot();
    mockSystem.workingDir = mockSystem.getWorkingDir().getTypeChildren().get(0);
    mockSystem.workingDir = mockSystem.getWorkingDir().getTypeChildren().get(0);


    // Now we are inside one/two

    this.cpComm.setUserInput("4cp /newDir /one/two");
    this.cpComm.checkInput();
    // Check if the childrens content is equal to newDir
    // (i.e. that it was added)
    assertTrue(mockSystem.workingDir.getTypeChildren().get(0).getContent()
        .equals("newDir"));

  }

  @Test
  public void checkNoPath() {
    Directory one = new Directory("one");

    helperFunction(one, this.mockSystem.workingDir);

    this.cpComm.setUserInput("5cp notAPathWay one");
    this.cpComm.checkInput();
    // cannot copy NoPath into one
    assertTrue(cpComm.checkInput() == null);


  }

  public void checkFileWithMultiplePathWays() {
    FileClass file = new FileClass("blah");
    Directory fileLoc = new Directory("file", file);
    mockSystem.workingDir.setChild(fileLoc);
    fileLoc.setParent(mockSystem.workingDir);

    Directory one = new Directory("one");
    Directory two = new Directory("two");
    Directory three = new Directory("three");
    // Set up the relationship
    helperFunction(one, mockSystem.workingDir);
    helperFunction(two, one);
    helperFunction(three, two);


    this.cpComm.setUserInput("4mv file one/two/three/newName");
    // Already inside one/two/three after calling cp
    // check if the child inside three is equal to file
    assertTrue(mockSystem.workingDir.getTypeChildren().get(0).getContent()
        .equals("file"));


  }



  public void helperFunction(Directory dir, Directory workingDir) {
    workingDir.setChild(dir);
    dir.setParent(workingDir);
  }



}
