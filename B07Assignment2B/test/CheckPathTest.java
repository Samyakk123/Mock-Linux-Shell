package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import commands.CpCommand;
import filesystem.CheckPath;
import filesystem.Directory;
import filesystem.FileClass;
import mock.MockFileSystem;

public class CheckPathTest {
  private CheckPath checkPath;
  private MockFileSystem mockSystem;


  @Before
  public void setUp() {
    checkPath = new CheckPath();
    checkPath.setMock();
    this.mockSystem = MockFileSystem.currentFileSystemInstance();
    mockSystem.workingDir = new Directory("/");
  }

  // test checkPath with an invalidPathawy
  @Test
  public void testCheckPathWithNonExistantPath() {
    String expectedOutput = "NoPath";
    String actualOutput = this.checkPath.pathExist("command no/path/exist ");
    // expectedOutput is that it returns Nopath
    assertEquals(expectedOutput, actualOutput);
  }

  // test CheckPath for a pathway leading to a Folder
  // returns PathFolderExist to indicate that it is a valid pathway to a folder
  @Test
  public void testCheckPathGivenAFolderPathExist() {
    // assuming your in root directory and folder dir1 exist
    String expectedOutput = "PathFolderExist";

    Directory dir1 = new Directory("dir1");
    this.mockSystem.workingDir.setChild(dir1);

    String actualOutput = this.checkPath.pathExist("command ./dir1 ");

    assertEquals(expectedOutput, actualOutput);
  }


  // test CheckPath for a pathway leading to a file
  // returns PathFileExist to indicate that it is a valid pathway to a file
  @Test
  public void testCheckPathGivenFilePathExist() {
    // assuming your in root directory and file named file1 exist
    String expectedOutput = "PathFileExist";

    FileClass f = new FileClass("this is the content");
    Directory file = new Directory("file1", f);
    this.mockSystem.workingDir.setChild(file);

    String actualOutput = this.checkPath.pathExist("command /../file1 ");
    assertEquals(expectedOutput, actualOutput);
  }

  // test absolute pathway to a folder
  @Test
  public void testCheckPathWithAbsolutePathway() {

    // assume your in /dir1/dir2/dir3 where dir3 is a directory
    Directory dir1 = new Directory("dir1");
    Directory dir2 = new Directory("dir2");
    Directory dir3 = new Directory("dir3");
    this.mockSystem.workingDir.setChild(dir1);
    dir1.setChild(dir2);
    dir2.setChild(dir3);
    dir3.setParent(dir2);
    dir2.setParent(dir1);
    dir1.setParent(this.mockSystem.workingDir);
    this.mockSystem.workingDir = dir3;

    String expectedOutput = "PathFolderExist";
    String actualOutput = this.checkPath.pathExist("command /dir1/dir2/dir3 ");
    assertEquals(expectedOutput, actualOutput);
  }


  // test relative pathway to a file
  @Test
  public void testCheckPathWithRelativePathway() {
    // assume your in /dir1
    // and pathway /dir1/dir2/dir3 where dir3 is a directory exist
    Directory dir1 = new Directory("dir1");
    Directory dir2 = new Directory("dir2");
    Directory dir3 = new Directory("dir3");
    FileClass f = new FileClass("content of file dont matter");
    Directory file = new Directory("file", f);

    // build the pathway
    this.mockSystem.workingDir.setChild(dir1);
    dir1.setChild(dir2);
    dir2.setChild(dir3);
    dir3.setChild(file);
    file.setParent(dir3);
    dir3.setParent(dir2);
    dir2.setParent(dir1);
    dir1.setParent(this.mockSystem.workingDir);
    this.mockSystem.workingDir = dir1;

    String expectedOutput = "PathFileExist";
    String actualOutput = this.checkPath.pathExist("command dir2/dir3/file");
    assertEquals(expectedOutput, actualOutput);
  }

}
