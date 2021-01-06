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
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import filesystem.Directory;
import filesystem.FileClass;
import filesystem.FileSystem;


public class FileSystemTest {

  private FileSystem fileSystem;

  @Before
  public void setUp() throws Exception {
    fileSystem = FileSystem.currentFileSystemInstance();


  }

  // After every test that runs reset the singleton back using reflection

  @After
  public void afterTest() throws Exception {
    Field field = (fileSystem.getClass()).getDeclaredField("fileSystem");
    field.setAccessible(true);
    field.set(null, null); // setting the ref parameter to null
  }


  // Test the sizeOfFileSystem() method
  @Test
  public void testSizeOfFileSystem() {
    Directory one = new Directory("one");
    fileSystem.getWorkingDir().setChild(one);
    fileSystem.addToCurrentWorkingDir("one");

    assertTrue(fileSystem.sizeOfCurrentWorkingDir() == 2);

  }


  // Change the Current Working Directory and see if the size of the new is
  // also updated to 3 (old one would have returned 0)
  @Test
  public void testChangeCurrentWorkingDir() {
    ArrayList<String> newCurr = new ArrayList<String>(Arrays.asList("/"));
    newCurr.add("one");
    newCurr.add("two");
    fileSystem.changeCurrentWorkingDir(newCurr);
    assertTrue(fileSystem.getCurrentWorkingDirectory().size() == 3);
  }

  // Check if the element was sucessfully added to the current working directory
  // when using .getCurrentWorkingDirectory()
  @Test
  public void testAddToCurrentWorkingDirAddition() {
    fileSystem.addToCurrentWorkingDir("size_now_should_be_2");
    assertTrue(fileSystem.getCurrentWorkingDirectory().get(1)
        .equals("size_now_should_be_2"));
  }

  // Test adding again to see if current working directory size is also updated
  @Test
  public void testAddToCurrentWorkingDir() {
    fileSystem.addToCurrentWorkingDir("size_now_should_be_2");
    assertTrue(fileSystem.getCurrentWorkingDirectory().size() == 2);
  }


  // Test removing from working Directory
  @Test
  public void testRemoveFromWorkingDir() {
    fileSystem.getCurrentWorkingDirectory().add("This_should_be_gone");
    fileSystem.removeToCurrentWorkingDir();
    assertTrue(fileSystem.getCurrentWorkingDirectory().size() == 1);
  }

  // Test goToRoot method
  @Test
  public void testGoToRoot() {
    Directory one = new Directory("one");
    helperFunction(one, fileSystem.getWorkingDir());
    fileSystem.cdDown("one");
    fileSystem.getCurrentWorkingDirectory().add("one");

    // Now we are inside one
    // If we go back to root, contents of getWorkingDir() should still
    // be '/' and no longer one

    fileSystem.goToRoot();

    // Should now be in the home root directory
    assertTrue(fileSystem.getWorkingDir().getContent().equals("/"));
  }

  // Test goToCurrentWorkingDir to see if workingDir and currentWorkingDir
  // are in different locations if it will take it
  @Test
  public void testGoCurrentWorkingDir() {
    Directory one = new Directory("one");
    Directory two = new Directory("two");
    Directory three = new Directory("three");

    helperFunction(one, fileSystem.getWorkingDir());
    helperFunction(two, one);
    helperFunction(three, two);

    fileSystem.getWorkingDir().getTypeChildren().get(0);
    fileSystem.getWorkingDir().getTypeChildren().get(0);
    fileSystem.getWorkingDir().getTypeChildren().get(0);
    fileSystem.getCurrentWorkingDirectory().add("one");


    // Now we are inside one/two/three
    // In this simulation workingDir is inside one/two/three
    // And currentWorkingDir is inside /one
    // Now if after calling GoToCurrentWorkingDir() it should
    // take us to /one
    fileSystem.goToCurrentWorkingDir();
    assertTrue(fileSystem.getWorkingDir().getContent().equals("one"));

  }

  // Test getFile to see if it will return a file in the pathway
  @Test
  public void testGetFile() {
    FileClass file = new FileClass("content here!");
    Directory typeFile = new Directory("file", file);
    helperFunction(typeFile, fileSystem.getWorkingDir());

    Directory toCompare = fileSystem.getFile("file");
    // Check that getFile got the right file
    assertEquals(typeFile, toCompare);
  }

  // Test checkType with a folder
  @Test
  public void testCheckTypeFolder() {
    Directory dir = new Directory("dir");
    helperFunction(dir, fileSystem.getWorkingDir());

    String output = fileSystem.checkType("dir");
    assertEquals("folder", output);
  }

  // Test checkType with a file
  @Test
  public void testCheckTypeFile() {
    FileClass file = new FileClass("Content");
    Directory dir = new Directory("file", file);
    helperFunction(dir, fileSystem.getWorkingDir());

    String output = fileSystem.checkType("file");
    assertEquals("file", output);

  }

  // Test cdDown to see if it successfully changes the workingDir
  @Test
  public void testCdDown() {
    Directory dir = new Directory("dir");
    helperFunction(dir, fileSystem.getWorkingDir());

    fileSystem.cdDown("dir");
    // Now we should be inside dir
    assertTrue(fileSystem.getWorkingDir().getContent().equals("dir"));
  }

  // Test cdUp() if it properly changes the workingDir
  @Test
  public void testCdUp() {
    Directory dir = new Directory("dir");
    helperFunction(dir, fileSystem.getWorkingDir());
    fileSystem.cdDown("dir");
    fileSystem.cdUp();
    assertTrue(fileSystem.getWorkingDir().getContent().equals("/"));


  }

  // Test if addFolder successfully adds a folder with the given name
  @Test
  public void testAddFolder() {
    fileSystem.addFolder("name");
    assertTrue(fileSystem.getWorkingDir().getTypeChildren().get(0).getContent()
        .equals("name"));
  }

  // Test if addFileName successfully adds a file with the given name and
  // content
  @Test
  public void testAddFileName() {
    fileSystem.addFile("name", "Content");
    assertTrue(fileSystem.getWorkingDir().getTypeChildren().get(0).getContent()
        .equals("name"));
  }

  // Same test as above but verifies that the content was correct as well
  // Not just the name
  @Test
  public void testAddFileContent() {
    fileSystem.addFile("name", "content");
    assertTrue(fileSystem.getWorkingDir().getTypeChildren().get(0)
        .getFileContent().equals("content"));
  }

  // Test setFileContent which should change the contents of a file given it's
  // name and the new content you want
  @Test
  public void testSetFileContents() {
    FileClass file = new FileClass("content");
    Directory dir = new Directory("name", file);
    helperFunction(dir, fileSystem.getWorkingDir());
    fileSystem.setFileContents("name", "new content");

    // Check if the content was successfully changed
    assertEquals("new content",
        fileSystem.getWorkingDir().getTypeChildren().get(0).getFileContent());

  }

  // Test getFileContent to see if it returns the right File Content
  @Test
  public void testGetFileContents() {
    FileClass file = new FileClass("contents");
    Directory dir = new Directory("name", file);
    helperFunction(dir, fileSystem.getWorkingDir());
    assertEquals("contents", fileSystem.getFileContents("name"));
  }

  // Test removing a folder from FileSystem (method in fileSystem)
  @Test
  public void testRemoveFromFileSystem() {
    Directory dir = new Directory("name");
    helperFunction(dir, fileSystem.getWorkingDir());
    fileSystem.RemoveFromFileSystem("name");
    assertTrue(fileSystem.getWorkingDir().getTypeChildren().size() == 0);
  }

  // Test removeFromFileSystemFile to see if it sucessfully removes
  // The file off of the current working Directory
  @Test
  public void testRemoveFromFileSystemFile() {
    FileClass file = new FileClass("content");
    Directory fileLoc = new Directory("file", file);
    helperFunction(fileLoc, fileSystem.getWorkingDir());
    fileSystem.RemoveFromFileSystemFile("file");
    assertTrue(fileSystem.getWorkingDir().getTypeChildren().size() == 0);
  }


  // Test if checkIfExist() returns the correct boolean value
  @Test
  public void testCheckIfExists() {
    Directory dir = new Directory("name");
    helperFunction(dir, fileSystem.getWorkingDir());
    // returns false if it does exist so we need to check
    // if it's!
    assertTrue(!fileSystem.checkIfExists("name"));
  }

  // Test executeCommandDirectory() by making sure each subdirectory was
  // sucessfully added
  @Test
  public void testExecuteCommandDirectory() {
    Directory one = new Directory("one");
    Directory two = new Directory("two");
    Directory three = new Directory("three");
    helperFunction(one, fileSystem.getWorkingDir());
    helperFunction(two, one);
    helperFunction(three, one);


    Directory seeIfReplicated = new Directory("replication");
    fileSystem.executeCommandDirectory(fileSystem.getWorkingDir(),
        seeIfReplicated);

    int count = 0;
    // Check that one and seeIfReplicated's first child are the same
    if (one.getContent()
        .equals(seeIfReplicated.getTypeChildren().get(0).getContent())) {
      count += 1;
    }
    // Check that two and the recurrsive child of the child above ^ are the same
    if (two.getContent().equals(seeIfReplicated.getTypeChildren().get(0)
        .getTypeChildren().get(0).getContent())) {
      count += 1;
    }
    // Check that three and the other recurrsive child from above are the same
    if (three.getContent().equals(seeIfReplicated.getTypeChildren().get(0)
        .getTypeChildren().get(1).getContent())) {
      count += 1;
    }
    // All 3 must be true, and if they are count == 3
    assertTrue(count == 3);
  }

  // Test checkIfInCurrent by adding to currentWorkingDir and changing
  // Working directory into it and seeing if it returns true
  @Test
  public void testCheckIfInCurrent() {
    Directory one = new Directory("one");
    Directory two = new Directory("two");
    Directory three = new Directory("three");
    helperFunction(one, fileSystem.getWorkingDir());
    helperFunction(two, one);
    helperFunction(three, two);
    fileSystem.addToCurrentWorkingDir("one");
    fileSystem.addToCurrentWorkingDir("two");
    fileSystem.addToCurrentWorkingDir("three");
    fileSystem.goToCurrentWorkingDir();

    // Now assuming we are inside one/two/three

    // False because it returns false if it is
    // inside the current working Directory
    assertTrue(!fileSystem.checkIfInCurrent(one));


  }

  public void helperFunction(Directory dir, Directory workingDir) {
    workingDir.setChild(dir);
    dir.setParent(workingDir);
  }



}
