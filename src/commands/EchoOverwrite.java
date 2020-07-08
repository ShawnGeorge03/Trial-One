package commands;

import data.Node;

/**
 * Class EchoOverwrite overwrites the contents of requested file with given string
 */
public class EchoOverwrite extends Echo {

  /**
   * Takes contents and overwrites it to the contents of the requested file
   * 
   * @param fileContents the contents that the user wants to overwrite with
   * @param fileName name of the file
   */
  public void execute(String fileContents, String fileName) {

    String[] givenPath = fileName.split("/");

    if (givenPath.length == 1) {
      if (isValidFileName(fileName)) {
        if (findFileGivenRelative(fileName) != null) {
          Node file = findFileGivenRelative(fileName);
          file.setContent(fileContents);
        } else {
          Node newFile = new Node();
          newFile.setDir(false);
          newFile.setContent(fileContents);
          newFile.setName(fileName);
          newFile.setParent(filesys.getCurrent());
          filesys.addToDirectory(newFile);
        }
      } else
        System.out
            .println(this.getErrorHandler().getError("Invalid File", fileName));
    }

    else {
      if (isValidFileName(givenPath[givenPath.length - 1])) {
        if (findFileGivenAbsolute(fileName) != null) {
          Node file = findFileGivenAbsolute(fileName);
          file.setContent(fileContents);
        } else {
          Node currentNode = this.filesys.getCurrent();
          String desiredPath = getCurrentPath() + fileName;
          desiredPath = desiredPath.substring(0, desiredPath.lastIndexOf("/"));
          Node parent = findFolderGivenAbsolute(desiredPath);
          this.filesys.assignCurrent(parent);
          Node newFile = new Node();
          newFile.setDir(false);
          newFile.setContent(fileContents);
          newFile.setName(givenPath[givenPath.length - 1]);
          newFile.setParent(parent);
          filesys.addToDirectory(newFile);
          this.filesys.assignCurrent(currentNode);
        }
      } else
        System.out.println(this.getErrorHandler().getError("Invalid File",
            givenPath[givenPath.length - 2]));
    }

  }

}
