package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import filesystem.FileClass;

public class FileClassTest {

  private FileClass file;

  @Before
  public void setUp() {
    file = new FileClass("ThisIsTheContentOfTheFilefile");
  }


  // created a file in the before setup
  // test and verify this is of type FileClass
  @Test
  public void testFileClassWhenCreatingANewFile() {
    assertTrue(file instanceof FileClass);
  }

  // test getFileContents() which returns the content of the file
  @Test
  public void testFileContentAfterCreatingFile() {
    String expectedOutput = "ThisIsTheContentOfTheFilefile";
    String actualOutput = file.getFileContents();
    assertEquals(expectedOutput, actualOutput);
  }

  // test getFileContent which gets the content of the file
  @Test
  public void testFileContentWhenSettingContent() {
    String expectedOutput = "NewContent";
    file.setContent("NewContent");

    String actualOutput = file.getFileContents();
    assertEquals(expectedOutput, actualOutput);
  }

}
