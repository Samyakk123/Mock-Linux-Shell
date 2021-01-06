package mock;

import filesystem.CheckPathInterface;

public class MockCheckPath implements CheckPathInterface {

  MockFileSystem fileSystem = MockFileSystem.currentFileSystemInstance();


  public String pathExist(String string) {
    // testing mkdir
    if (string.equals("1mkdir one")) {
      return "FolderExist";
    } else if (string.equals("2mkdir one")) {
      return "PathFolderExist";
    } else if (string.equals("1mkdir /one/two")) {
      return "FolderExist";
    } else if (string.equals("mkdir /one")) {
      return "PathFileExist";
    } else if (string.equals("3mkdir one")) {
      return "FolderExist";
    } else if (string.equals("3mkdir two")) {
      return "FolderExist";
    }

    // tesing pwd
    else if (string.equals("pwd one")) {
      return "FolderExist";
    } else if (string.equals("2pwd one")) {
      return "PathFileExist";
    } else if (string.equals("2pwd /one")) {
      return "PathFileExist";
    } else if (string.equals("pwd > o$o")) {
      return "NoPath";
    } else if (string.equals("pwd > /one/two")) {
      return "NoPath";
    }

    // testing ls
    else if (string.equals("ls /")) {
      return "PathFolderExist";
    } else if (string.equals("ls /one/two")) {
      return "NoPath";
    } else if (string.equals("ls /one")) {
      return "PathFolderExist";
    } else if (string.equals("ls ../.")) {
      return "PathFolderExist";
    } else if (string.equals("ls new")) {
      return "FolderExist";
    } else if (string.equals("ls -R /")) {
      return "PathFolderExist";
    }

    // testing tree
    // > >> is disregarded (tested in redirection test)
    else if (string.equals("tree here")) {
      return "FolderExist";
    } else if (string.equals("tree /ok")) {
      return "FolderExist";
    }

    // testing echo
    else if (string.equals("echo one")) {
      return "PathFileExist";
    } else if (string.equals("echo /one/two")) {
      return "PathFileExist";
    } else if (string.equals("1echo one")) {
      return "FolderExist";
    } else if (string.equals("echo /one/three")) {
      return "NoPath";
    }

    // testing cat
    else if (string.equals("cat does/not/exist")) {
      return "NoPath";
    } else if (string.equals("cat directory")) {
      return "PathFolderExist";
    } else if (string.equals("cat catme")) {
      return "PathFileExist";
    } else if (string.equals("cat ../first")) {
      return "PathFileExist";
    } else if (string.equals("cat ./second")) {
      return "PathFileExist";
    } else if (string.equals("invalidPath")) {
      return "NoPath";
    } else if (string.equals("cat /one/two")) {
      return "FolderExist";
    } else if (string.equals("cat /first")) {
      while (!fileSystem.workingDir.getContent().equals("/")) {
        fileSystem.workingDir = fileSystem.getWorkingDir().getParent();
      }
      return "PathFileExist";
    }

    // testing find
    if (string.equals("find /")) {
      return "PathFolderExist";
    } else if (string.equals("find ..")) {
      return "PathFolderExist";
    } else if (string.equals("find ../../././")) {
      while (!fileSystem.workingDir.getContent().equals("/")) {
        fileSystem.workingDir = fileSystem.getWorkingDir().getParent();
      }
      return "PathFolderExist";
    } else if (string.equals("find redirection")) {
      return "FolderExist";
    }

    // testing redirection
    if (string.equals("Command /no/path")) {
      return "NoPath";
    } else if (string.equals("Command /dir1")) {
      return "PathFolderExist";
    } else if (string.equals("Command /work")) {
      return "FolderExist";
    } else if (string.equals("Command /one/two/three/filename")) {
      if (!fileSystem.workingDir.getContent().equals("three")) {
        fileSystem.workingDir = fileSystem.workingDir.getTypeChildren().get(0);
      }
      return "FolderExist";
    } else if (string.equals("Command three/filename")) {
      if (!fileSystem.workingDir.getContent().equals("three")) {
        fileSystem.workingDir = fileSystem.workingDir.getTypeChildren().get(0);
      }
      return "FolderExist";
    }

    // testing rm
    else if (string.equals("rm one")) {
      return "PathFolderExist";
    }
    // Assuming you are in the root directory this returns FolderExists
    // (Since directory up unto last element exists (i.e. root)
    else if (string.equals("rm two")) {
      return "FolderExist";
    }
    // Assume you are anywhere except in the pathway one/two/three/four
    else if (string.equals("rm /one/two/three/four")) {
      return "NoPath";
    }

    // testing mv
    else if (string.equals("mv one")) {
      return "PathFileExist";
    } else if (string.equals("mv two")) {
      return "PathFileExist";
    }

    // testing cat
    else if (string.equals("cat does/not/exist")) {
      return "NoPath";
    } else if (string.equals("cat directory")) {
      return "PathFolderExist";
    } else if (string.equals("cat catme")) {
      return "PathFileExist";
    }

    // testing mv
    else if (string.equals("mv file")) {
      while (!fileSystem.workingDir.getContent().equals("/")) {
        fileSystem.workingDir = fileSystem.getWorkingDir().getParent();
      }
      return "PathFileExist";
    } else if (string.equals("2mv file")) {
      return "PathFileExist";
    } else if (string.equals("2mv folder")) {
      return "PathFolderExist";
    } else if (string.equals("mv folder")) {
      fileSystem.workingDir =
          fileSystem.getWorkingDir().getTypeChildren().get(0);
      return "PathFolderExist";
    } else if (string.equals("3mv folder")) {
      fileSystem.goToRoot();
      fileSystem.workingDir = fileSystem.workingDir.getTypeChildren().get(0);
      return "PathFolderExist";
    } else if (string.equals(("3mv folder2"))) {
      fileSystem.goToRoot();
      fileSystem.workingDir = fileSystem.workingDir.getTypeChildren().get(1);
      return "PathFolderExist";
    } else if (string.equals("4mv file")) {
      while (!fileSystem.workingDir.getContent().equals("/")) {
        fileSystem.workingDir = fileSystem.getWorkingDir().getParent();
      }
      return "PathFileExist";
    } else if (string.equals("4mv one/two/three/newName")) {
      fileSystem.workingDir =
          fileSystem.getWorkingDir().getTypeChildren().get(0);
      return "FolderExists";
    }

    // testing cp
    else if (string.equals("cp folder1")) {
      return "PathFolderExist";
    } else if (string.equals("cp one")) {
      return "PathFileExist";
    } else if (string.equals("cp two")) {
      return "PathFileExist";
    } else if (string.equals("cp file")) {
      return "PathFileExist";
    } else if (string.equals("cp folder")) {
      return "PathFolderExist";
    } else if (string.equals("2cp folder")) {
      return "PathFolderExist";
    } else if (string.equals("2cp file")) {
      return "PathFileExist";
    } else if (string.equals("3cp folder")) {
      fileSystem.goToRoot();
      fileSystem.workingDir = fileSystem.workingDir.getTypeChildren().get(0);
      return "PathFolderExist";
    } else if (string.equals("3cp folder2")) {
      fileSystem.goToRoot();
      fileSystem.workingDir = fileSystem.workingDir.getTypeChildren().get(1);
      return "PathFolderExist";
    } else if (string.equals("4cp /one/two")) {
      fileSystem.goToRoot();
      fileSystem.workingDir = fileSystem.workingDir.getTypeChildren().get(0);
      fileSystem.workingDir = fileSystem.workingDir.getTypeChildren().get(0);
      return "PathFolderExist";
    } else if (string.equals("4cp /newDir")) {
      fileSystem.goToRoot();
      fileSystem.workingDir = fileSystem.workingDir.getTypeChildren().get(1);
      return "PathfolderExist";
    } else if (string.equals("5cp one")) {
      return "NoPath";
    }
    return "NoPath";
  }
}
