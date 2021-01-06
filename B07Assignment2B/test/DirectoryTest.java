package test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Test;
import filesystem.Directory;
import filesystem.FileClass;

public class DirectoryTest {

  // test getType function of directory
  // returns true because directory is of type 'directory'
  @Test
  public void testDirectoriesGetTypeOnFolder() {
    Directory directory = new Directory("folderName");
    boolean expectedOutput = true;
    assertEquals(directory.getType(), expectedOutput);
  }

  // Files are also directories but with the extra parameter
  // So this getType returns false because the method returns true if it is
  // a directory and false if it is a file
  @Test
  public void testDirectoriesGetTypeOnFile() {
    FileClass file = new FileClass("file contents");
    Directory directory = new Directory("fileName", file);
    boolean expectedOutput = false;
    assertEquals(directory.getType(), expectedOutput);
  }

  // test getParent() method inside Directories
  // returns dir1 because dir2 is a child of dir1
  @Test
  public void testDirectoriesGetParentMethod() {
    Directory dir1 = new Directory("file1");
    Directory dir2 = new Directory("file2");
    dir1.setChild(dir2);
    dir2.setParent(dir1);

    assertEquals(dir2.getParent(), dir1);
  }

  // test setParent() by setting the parent of dir2 as dir1
  // since getParent returned true (
  @Test
  public void testDirectoriesSetParentMethod() {
    // Make a folder
    Directory dir1 = new Directory("file1");
    Directory dir2 = new Directory("file2");
    dir2.setParent(dir1);

    assertEquals(dir2.getParent(), dir1);
  }


  // test getFileContents() by making a file (still type directory)
  // and seeing if the content matches expected output
  @Test
  public void testDirectoriesGetFileContentMethod() {
    FileClass file = new FileClass("file contents");
    Directory directory = new Directory("fileName", file);
    String expectedOutput = "file contents";
    assertEquals(file.getFileContents(), expectedOutput);
  }

  // test the setContent by set the file content initally as 'file contents'
  // then use getFileContents() to see if the content was updated
  // Since getFileContent() is a getter we can use this to confirm the test
  @Test
  public void testDirectoriesSetFileMethod() {
    FileClass file = new FileClass("file contents");
    Directory directory = new Directory("fileName", file);
    file.setContent("NEwCONent!");
    String expectedOutput = "NEwCONent!";
    assertEquals(file.getFileContents(), expectedOutput);
  }


  // test setChidl by setting dir2 as a child of dir1
  // Then test if the relation was made by calling dir2.getParent() and seeing
  // the relatoinship was established
  @Test
  public void testDirectoriesSetChildMethod() {
    Directory dir1 = new Directory("file1");
    Directory dir2 = new Directory("file2");
    dir1.setChild(dir2);
    assertEquals(dir2.getParent(), dir1);
  }

  // test getChildren() which returns a string of the children of a directory
  @Test
  public void testDirectoriesGetChildrenMethod() {
    Directory dir1 = new Directory("dir1");
    Directory dir2 = new Directory("dir2");
    Directory dir3 = new Directory("dir3");
    FileClass f = new FileClass("fileContent");
    Directory dir4 = new Directory("file1", f);
    dir1.setChild(dir2);
    dir1.setChild(dir3);
    dir1.setChild(dir4);

    String expectedOutput = " dir2 dir3 file1";
    assertEquals(dir1.getChildren(), expectedOutput);
  }

  // test getTypeChildren which returns an arrayList of all the children
  // (ArrayList of type Directory)
  @Test
  public void testDirectoriesGetTypeChildren() {
    Directory dir1 = new Directory("d1");
    Directory dir2 = new Directory("d2");
    Directory dir3 = new Directory("d3");
    FileClass f = new FileClass("ireallydo");
    Directory dir4 = new Directory("iloveb07", f);
    dir1.setChild(dir2);
    dir1.setChild(dir3);
    dir1.setChild(dir4);

    ArrayList<Directory> expectedOutput = new ArrayList<Directory>();
    expectedOutput.add(dir2);
    expectedOutput.add(dir3);
    expectedOutput.add(dir4);

    assertEquals(dir1.getTypeChildren(), expectedOutput);
  }

  // test getContent which returns the name of the directory
  @Test
  public void testDirectoriesSetNameMethod() {
    Directory dir1 = new Directory("oldDirectoryName");
    dir1.setName("newDirectoryName");
    String expectedOutput = "newDirectoryName";
    assertEquals(dir1.getContent(), expectedOutput);
  }
}
