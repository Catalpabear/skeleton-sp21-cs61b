package gitlet;

import java.io.File;
import java.io.Serializable;

public class Blob implements Serializable {

    private String blobHashID;

    private String fileContents;

    private String filePath;

    public Blob(File file) {
        filePath = file.getPath();
        this.fileContents = Utils.readContentsAsString(file);
        this.blobHashID = Utils.sha1(fileContents);
    }

    public String getBlobHashID() {
        return blobHashID;
    }

    public String getFileContents() {
        return fileContents;
    }

    public String getFilePath() {
        return filePath;
    }
}
