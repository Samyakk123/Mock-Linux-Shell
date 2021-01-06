package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import commands.CatCommand;
import commands.FindCommand;
import filesystem.Directory;
import filesystem.FileClass;
import mock.MockFileSystem;

public class FindTest {

  private FindCommand find;
  private MockFileSystem mockSystem;

  @Before
  public void setUp() {
    find = new FindCommand();
    find.setMock();
    this.find.validateInput.setMock();
    this.find.redirection.setMock();
    this.mockSystem = MockFileSystem.currentFileSystemInstance();
    this.mockSystem.workingDir = new Directory("/");
    this.mockSystem.check = false;
  }

  // test invalid pathway
  @Test
  public void testFindToSerachFileOnInvalidPathway() {
    this.find.setUserInput("find path/does/not/exist -type f -name \"folder\"");
    assertTrue(this.find.checkInput() == null);
  }

  // test invalid pathway for directories
  @Test
  public void testFindToSerachDirectoryOnInvalidPathway() {
    this.find.setUserInput("find path/does/not/exist -type d -name \"folder\"");
    assertTrue(this.find.checkInput() == null);
  }

  // test if the searching for directories in multiple sub locations works
  @Test
  public void testFindToSearchDirectoriesInMoreThanOneLocation() {
    this.find.setUserInput("find / -type d -name \"PrintMe\"");

    Directory dir1 = new Directory("one");
    Directory find1 = new Directory("PrintMe");
    Directory dir2 = new Directory("two");
    Directory find2 = new Directory("PrintMe");

    this.mockSystem.workingDir.setChild(dir1);
    this.mockSystem.workingDir.setChild(dir2);
    dir2.setChild(find2);
    dir1.setChild(find1);

    assertTrue(this.find.checkInput().equals("one/PrintMe\ntwo/PrintMe"));

  }

  // test to make sure it does not print the file and only the one specified
  // i.e. directories in this case
  @Test
  public void testFindToOnlyPrintFoldersGivenFilesHaveSameName() {
    this.find.setUserInput("find / -type d -name \"PrintMe\"");

    Directory dir1 = new Directory("one");
    Directory find1 = new Directory("PrintMe");
    Directory dir2 = new Directory("two");
    Directory find2 = new Directory("PrintMe");
    FileClass f = new FileClass("contents");
    Directory file1 = new Directory("PrintMe", f);

    this.mockSystem.workingDir.setChild(dir1);
    this.mockSystem.workingDir.setChild(dir2);
    this.mockSystem.workingDir.setChild(file1);
    dir2.setChild(find2);
    dir1.setChild(find1);

    assertTrue(this.find.checkInput().equals("one/PrintMe\ntwo/PrintMe"));



    // test to make sure it only prints the file with the same name
    // and not directories (in this case)
  }

  @Test
  public void testFindToOnlyPrintFilesGivenFoldersHaveSameName() {
    this.find.setUserInput("find / -type f -name \"PrintMe\"");

    Directory dir1 = new Directory("one");
    Directory find1 = new Directory("PrintMe");
    Directory dir2 = new Directory("two");
    Directory find2 = new Directory("PrintMe");
    FileClass f = new FileClass("contents");
    Directory file1 = new Directory("PrintMe", f);

    this.mockSystem.workingDir.setChild(dir1);
    this.mockSystem.workingDir.setChild(dir2);
    this.mockSystem.workingDir.setChild(file1);
    dir2.setChild(find2);
    dir1.setChild(find1);


    assertTrue(this.find.checkInput().equals("PrintMe"));


    // find with multiple pathways
  }

  @Test
  public void testFindWithMultiplePathwaysAndOneInvalid() {
    this.find.setUserInput("find / .. nope -type d -name \"PrintMe\"");

    Directory dir1 = new Directory("one");
    Directory find1 = new Directory("PrintMe");
    Directory dir2 = new Directory("two");
    Directory find2 = new Directory("PrintMe");


    this.mockSystem.workingDir.setChild(dir1);
    this.mockSystem.workingDir.setChild(dir2);

    dir2.setChild(find2);
    dir1.setChild(find1);

    assertTrue(this.find.checkInput()
        .equals("one/PrintMe\ntwo/PrintMe\n" + "\none/PrintMe\ntwo/PrintMe"));

  }

  // test find with an invalid folder name ( has '.')
  @Test
  public void testFindWithAInvalidFolderName() {
    this.find.setUserInput("find / -type f -name \"Pr.Me\"");

    assertTrue(this.find.checkInput() == null);

    // test find with an invalid file name
  }

  @Test
  public void testFindWithAInvalidFileName() {
    this.find.setUserInput("find / -type d -name \"Pr.Me\"");

    assertTrue(this.find.checkInput() == null);

  }

  // test find given in the middle of a location (.. back)
  @Test
  public void testFindGivenYourInMiddleOfFileSystem() {
    this.find.setUserInput("find ../../././ -type d -name \"PrintMe\"");
    Directory dir1 = new Directory("one");
    Directory find1 = new Directory("PrintMe");
    Directory dir2 = new Directory("two");
    Directory find2 = new Directory("PrintMe");
    this.mockSystem.workingDir.setChild(dir1);
    this.mockSystem.workingDir.setChild(dir2);
    dir2.setChild(find2);
    dir1.setChild(find1);

    this.mockSystem.workingDir = find2;
    assertTrue(this.find.checkInput().equals("one/PrintMe\ntwo/PrintMe"));

  }

  // test find on a file pathway
  @Test
  public void testFindOnAFilePathway() {

    this.find.setUserInput("find file -type d -name \"PrintMe\"");
    Directory dir1 = new Directory("one");
    Directory find1 = new Directory("PrintMe");
    Directory dir2 = new Directory("two");
    Directory find2 = new Directory("PrintMe");
    FileClass f = new FileClass("contents");
    Directory file1 = new Directory("PrintMe", f);
    this.mockSystem.workingDir.setChild(dir1);
    this.mockSystem.workingDir.setChild(dir2);
    this.mockSystem.workingDir.setChild(file1);
    dir2.setChild(find2);
    dir1.setChild(find1);

    assertTrue(this.find.checkInput() == null);

  }

  // test find with using redirection ( >> )
  @Test
  public void testFindIfRedirectedIntoFile() {
    this.find.setUserInput("find / -type d -name \"PrintMe\" >> redirection");

    Directory dir1 = new Directory("one");
    Directory find1 = new Directory("PrintMe");
    Directory dir2 = new Directory("two");
    Directory find2 = new Directory("PrintMe");

    this.mockSystem.workingDir.setChild(dir1);
    this.mockSystem.workingDir.setChild(dir2);
    dir2.setChild(find2);
    dir1.setChild(find1);

    assertTrue(this.find.checkInput().equals(""));



  }

}
