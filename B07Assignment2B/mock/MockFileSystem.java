package mock;

import java.util.ArrayList;
import java.util.Stack;
import filesystem.Directory;
import filesystem.FileClass;
import filesystem.FileSystemInterface;

public class MockFileSystem implements FileSystemInterface {

  public static MockFileSystem fileSystem = null;
  public Directory workingDir = new Directory("/");

  // checks if certain methods are ran or certain events occur and sets this to
  // true for testing
  public boolean check = false;

  // used to allow a certain event to occur by setting this string to a specific
  // input for testing
  public String name = "";

  // Constructor for MockFileSystem
  private MockFileSystem() {

  }

  public static MockFileSystem currentFileSystemInstance() {
    if (fileSystem == null) {
      fileSystem = new MockFileSystem();
    }
    return fileSystem;
  }

  public int sizeOfCurrentWorkingDir() {
    return 0;
  }

  public void changeCurrentWorkingDir(ArrayList<String> newCurrent) {}

  public void addToCurrentWorkingDir(String folder) {}

  public void removeToCurrentWorkingDir() {

  }

  public void goToRoot() {
    while (this.workingDir.getParent() != null) {
      this.workingDir = this.workingDir.getParent();
    }

  }

  public ArrayList<String> getCurrentWorkingDirectory() {
    ArrayList<String> currentWorkingDir = new ArrayList<String>();

    if (name.equals("1pwd")) {
      currentWorkingDir.add("/");
    } else if (name.equals("2pwd")) {
      currentWorkingDir.add("/");
      currentWorkingDir.add("one");
      currentWorkingDir.add("two");
    } else if (name.equals("pwd > one") || name.equals("pwd > o$o")
        || name.equals("pwd > /one/two")) {
      currentWorkingDir.add("/");
    } else if (name.equals("2pwd > one") || name.equals("2pwd >> /one")) {
      currentWorkingDir.add("/");
      currentWorkingDir.add("one");
    }

    return currentWorkingDir;
  }


  public void goToCurrentWorkingDir() {}

  public Directory getFile(String fileName) {
    for (int i = 0; i < this.workingDir.getTypeChildren().size(); i++) {
      if (workingDir.getTypeChildren().get(i).getContent().equals(fileName)) {
        return workingDir.getTypeChildren().get(i);
      }
    }
    return null;
  }

  public String checkType(String input) {
    for (int i = 0; i < workingDir.getTypeChildren().size(); i++) {
      if (((Directory) workingDir.getTypeChildren().get(i)).getContent()
          .equals(input)) {
        if (((Directory) workingDir.getTypeChildren().get(i)).getType()) {
          return "folder";
        } else {
          return "file";
        }
      }
    }
    return "none";
  }

  public boolean cdDown(String input) {
    if (checkType(input).equals("folder")) {
      for (int i = 0; i < this.workingDir.getTypeChildren().size(); i++) {
        if (((Directory) workingDir.getTypeChildren().get(i)).getContent()
            .equals(input)) {
          workingDir = (Directory) workingDir.getTypeChildren().get(i);
          return true;
        }
      }
    }
    return false;
  }

  public boolean cdUp() {
    return false;
  }

  public boolean goParentDirectory() {
    return false;
  }

  public void addFolder(String folderName) {
    check = true;
  }

  public void addFile(String fileName, String fileContent) {
    check = true;
  }

  public void setFileContents(String fileName, String fileContent) {
    check = true;
  }

  public String getFileContents(String fileName) {
    for (int i = 0; i < this.workingDir.getTypeChildren().size(); i++) {
      if (workingDir.getTypeChildren().get(i).getContent().equals(fileName)) {
        return workingDir.getTypeChildren().get(i).getFileContent();
      }
    }
    for (int i = 0; i < this.workingDir.getTypeChildren().size(); i++) {
      if (workingDir.getTypeChildren().get(i).getContent().equals(fileName)) {
        return workingDir.getTypeChildren().get(i).getFileContent();
      }
    }
    return null;
  }

  public String getContentsOfWorkingDir() {
    return null;
  }

  public String getChildrenOfWorkingDir() {
    if (name.equals("ls /")) {
      return "";
    } else if (name.equals("ls one")) {
      return "";
    }

    else if (name.equals("ls /one")) {
      return " two three";
    }

    else if (name.equals("ls ../.")) {
      return " two";
    } else if (name.equals("ls / > new")) {
      return " two three";
    }

    return null;
  }

  public Directory getWorkingDir() {
    return workingDir;
  }

  public void RemoveFromFileSystem(String toRemove) {
    if (!name.equals("a parent")) {
      check = true;
    } else {
      check = false;
    }
  }

  public void RemoveFromFileSystemFile(String toRemove) {}

  public boolean checkIfExists(String toAdd) {
    return true;
  }

  public void executeCommandDirectory(Directory root, Directory root2) {
    for (int i = 0; i < root.getTypeChildren().size(); i++) {

      // If it is a directory, set the child into our new
      // and recursively call it again
      Directory temp = null;

      if (root.getTypeChildren().get(i).getType()) {
        String name = root.getTypeChildren().get(i).getContent();
        temp = new Directory(name);
        root2.setChild(temp);

        executeCommandDirectory(root.getTypeChildren().get(i),
            root2.getTypeChildren().get(i));

        // If it is a file simply add it
      } else {
        FileClass temp2;
        String name = root.getTypeChildren().get(i).getContent();
        String content = root.getTypeChildren().get(i).getFileContent();
        temp2 = new FileClass(content);
        temp = new Directory(name, temp2);
        root2.setChild(temp);
      }
    }
  }

  public void setHistory(ArrayList<String> historyToSave) {}

  public void setStack(Stack<ArrayList<String>> stackToSave) {}

  public void setStackOfStackClass() {}

  public void setHistoryOfHistoryClass() {}

  public boolean checkIfInCurrent(Directory currLoc) {
    if (!name.equals("a parent")) {
      return true;
    }
    return false;
  }

}
