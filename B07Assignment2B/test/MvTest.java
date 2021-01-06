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
import commands.MvCommand;
import filesystem.Directory;
import filesystem.FileClass;
import filesystem.FileSystem;
import mock.MockFileSystem;

public class MvTest {
  private MvCommand mvComm;
  private MockFileSystem mockSystem;

  // NOTE: Ignore the numerical values preceding the 'mv' command in some
  // of these test cases as it was purely so that we could hard code and
  // assess these situations case by case inside our mockCheckPath

  @Before
  public void setUp() {
    mvComm = new MvCommand();
    mvComm.setMock();
    this.mvComm.validateInput.setMock();
    this.mockSystem = MockFileSystem.currentFileSystemInstance();
    mockSystem.name = "";
    this.mockSystem.check = false;
    mockSystem.workingDir = new Directory("/");
  }

  // Move a folder into a location unknown should not do anything
  @Test
  public void testFolderAndNotAFolder() {
    this.mvComm.setUserInput("mv folder1 notafolder");
    mvComm.checkInput();
    assertTrue(mvComm.checkInput() == null);

  }


  // Check if renaming between 2 files of mv works efficiently
  @Test
  public void testTwoFiles() {

    // Create 2 files (Files are still type Directories but with our extra param)
    FileClass first = new FileClass("blah");
    FileClass second = new FileClass("blah2");
    Directory one = new Directory("one", first);
    Directory two = new Directory("two", second);

    // Build the relation in our MocKCheckPath so that cp can execute
    // Assuming the pathways are correct
    // Helper function to help this process
    helperFunction(one, mockSystem.workingDir);
    helperFunction(two, mockSystem.workingDir);


    this.mvComm.setUserInput("mv one two");
    mvComm.checkInput();
    // After calling the method, check if the file 2 contains 'blah'
    assertTrue(mockSystem.getFile("two").getFileContent().equals("blah"));

  }


  // Check that the file was sucessfully placed inside the directory
  // Return type "" indicates executed properly.
  @Test
  public void testFileAndFolder() {
    FileClass first = new FileClass("blah");
    Directory file = new Directory("file", first);
    Directory directory = new Directory("folder");
    // Bulid the relationship to simulate if the command works for this case

    helperFunction(file, mockSystem.workingDir);
    helperFunction(directory, mockSystem.workingDir);


    this.mvComm.setUserInput("mv file folder");
    mvComm.checkInput();

    mockSystem.goToRoot();
    // Go from root directory into the folder (all hardcoded manual work)
    // Not using any dependencies

    mockSystem.workingDir = mockSystem.getWorkingDir().getTypeChildren().get(0);

    // Now we are inside folder so we can check the children
    // If it is equals file (We know index 0 because only one child)

    assertTrue(mockSystem.workingDir.getTypeChildren().get(0).getContent()
        .equals("file"));



  }

  // Verify that the top one in reverse order (i.e. mv folder file) does not
  // work since you cannot move a folder inside a file
  @Test
  public void testFolderAndFile() {
    FileClass first = new FileClass("blah");
    Directory file = new Directory("file", first);
    Directory directory = new Directory("folder");
    // Build the relationship
    helperFunction(file, mockSystem.workingDir);
    helperFunction(directory, mockSystem.workingDir);

    // Cannot move into a folder
    // Current implemetnation returns null if command failed to execute
    // Which is what we expect to happen
    this.mvComm.setUserInput("2mv folder file");
    assertTrue(mvComm.checkInput() == null);
  }


  // Test with 2 folders to see if they correctly add
  @Test
  public void testFolderAndFolder() {
    Directory directory = new Directory("folder");
    Directory directory2 = new Directory("folder2");
    // Setup the relationship with root directory
    helperFunction(directory, mockSystem.getWorkingDir());
    helperFunction(directory2, mockSystem.getWorkingDir());


    this.mvComm.setUserInput("3mv folder folder2");
    mvComm.checkInput();

    mockSystem.goToRoot();

    // Go from root into folder2 (second child added so index 2)
    mockSystem.workingDir = mockSystem.getWorkingDir().getTypeChildren().get(1);
    // Go from folder into folder
    mockSystem.workingDir = mockSystem.getWorkingDir().getTypeChildren().get(0);

    // Check if folder was added inside of folder2
    assertTrue(mockSystem.getWorkingDir().getContent().equals("folder"));
  }

  // File + nopath, verify that returns with null (i.e. Error)
  @Test
  public void testFileAndNoPath() {
    FileClass file = new FileClass("blah");
    Directory fileLoc = new Directory("folder", file);
    // Helps set parent and child relation
    helperFunction(fileLoc, mockSystem.getWorkingDir());
    this.mvComm.setUserInput("mv file randomPathwayHere");
    // Returns null when command fails to execute
    assertTrue(mvComm.checkInput() == null);

  }

  // nopath + File, verify that the return with this is also null (i.e. error)
  @Test
  public void testRandomPathwayAndFile() {
    FileClass file = new FileClass("blah");
    Directory fileLoc = new Directory("folder", file);

    // Helper function that helps build the child parent process
    helperFunction(fileLoc, mockSystem.workingDir);

    this.mvComm.setUserInput("mv randomPathwayHere file");
    // Returns null when command fails to execute
    assertTrue(mvComm.checkInput() == null);
  }

  // Try file + folderExist (i.e. end of pathway DNE to see if it will rename)
  @Test
  public void testFileAndFullPathway() {
    FileClass file = new FileClass("blah");
    Directory fileLoc = new Directory("file", file);
    mockSystem.workingDir.setChild(fileLoc);
    fileLoc.setParent(mockSystem.workingDir);

    Directory one = new Directory("one");
    Directory two = new Directory("two");
    Directory three = new Directory("three");

    // build the relationship
    helperFunction(one, mockSystem.workingDir);
    helperFunction(two, one);
    helperFunction(three, two);


    this.mvComm.setUserInput("4mv file one/two/three/newName");
    assertTrue(mockSystem.workingDir.getTypeChildren().get(0).getContent()
        .equals("file"));


  }

  public void helperFunction(Directory dir, Directory workingDir) {
    workingDir.setChild(dir);
    dir.setParent(workingDir);
  }

}


