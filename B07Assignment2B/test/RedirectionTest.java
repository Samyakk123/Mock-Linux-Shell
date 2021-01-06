package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import commands.CpCommand;
import commands.Redirection;
import filesystem.Directory;
import mock.MockFileSystem;

public class RedirectionTest {
  private Redirection redirect;
  private MockFileSystem mockSystem;

  @Before
  public void setUp() {
    redirect = new Redirection();
    redirect.setMock();
    this.mockSystem = MockFileSystem.currentFileSystemInstance();
    mockSystem.name = "";
    this.mockSystem.check = false;
    mockSystem.workingDir = new Directory("/");
  }

  // redirection with an invalid pathway
  @Test
  public void testRedirectionWithInvalidPathway() {
    assertTrue(this.redirect.executeRedirection("contentOfFile",
        "Command >> /no/path") == null);
  }

  // redirection with invalid file name
  @Test
  public void testRedirectionWithInvalidFileName() {
    assertTrue(this.redirect.executeRedirection("contentOfFile",
        "Command > /dont.make") == null);
  }

  // redirection with an invalid input
  @Test
  public void testRedirectionWithInvalidInput() {
    assertTrue(this.redirect.executeRedirection("contentOfFile",
        "Command >> > /dontmake") == null);
  }

  // redirection with an existing folder
  @Test
  public void testRedirectionToAnAlreadyExistingFolder() {
    Directory one = new Directory("dir1");
    mockSystem.workingDir.setChild(one);

    assertTrue(this.redirect.executeRedirection("contentOfFile",
        "Command >> /dir1") == null);
  }

  // redirection with file in Root directory
  @Test
  public void testRedirectionToFileInRootDirectory() {
    assertTrue(this.redirect
        .executeRedirection("contentOfFile", "Command > /work").equals(""));
  }

  // redirection to into a file and verify that check was set true
  // (i.e. that the file was added)
  @Test
  public void testRedirectionToFileInRootDirectoryAndSeeIfFileAdded() {
    this.redirect.executeRedirection("contentOfFile", "Command > /work");
    assertTrue(this.mockSystem.check == true);
  }

  // redirection to a file that already exists absolute pathway
  @Test
  public void testRedirectionToAFileAlreadyExisting() {
    this.redirect.executeRedirection("newcontentOfFile", "Command > /work");
    assertTrue(this.redirect
        .executeRedirection("contentOfFile", "Command >> /work").equals(""));
  }

  // normal redirection with absolute pathway
  @Test
  public void testRedirectionWithAbsolutePathway() {
    Directory one = new Directory("dir1");
    Directory two = new Directory("dir2");
    Directory three = new Directory("dir3");
    this.mockSystem.workingDir.setChild(one);
    one.setChild(two);
    two.setChild(three);
    three.setParent(two);
    two.setParent(one);
    one.setParent(this.mockSystem.workingDir);
    this.mockSystem.workingDir = two;

    assertTrue(this.redirect.executeRedirection("this is the content",
        "Command >> /one/two/three/filename").equals(""));
  }

  // normal redirection with absolute pathway
  @Test
  public void testRedirectionWithRelativePathway() {
    Directory one = new Directory("dir1");
    Directory two = new Directory("dir2");
    Directory three = new Directory("dir3");
    this.mockSystem.workingDir.setChild(one);
    one.setChild(two);
    two.setChild(three);
    three.setParent(two);
    two.setParent(one);
    one.setParent(this.mockSystem.workingDir);
    this.mockSystem.workingDir = two;

    assertTrue(this.redirect
        .executeRedirection("this is the content", "Command > three/filename")
        .equals(""));
  }

}
