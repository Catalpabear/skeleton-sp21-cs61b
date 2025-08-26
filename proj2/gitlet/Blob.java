package gitlet;

import java.io.File;
import java.io.Serializable;

public class Blob implements Serializable{

    private String blobHashID;

    private String fileContents;

    public Blob(File file) {
        this.fileContents = Utils.readContentsAsString(file);
        this.blobHashID = Utils.sha1(fileContents);
    }

    public String getBlobHashID() {
        return blobHashID;
    }
}
