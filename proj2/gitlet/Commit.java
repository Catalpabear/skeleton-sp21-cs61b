package gitlet;

// TODO: any imports you need here
import static gitlet.Repository.*;
import static gitlet.Utils.readContentsAsString;

import java.io.File;
import java.io.Serializable;
import java.util.*;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Commit implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /** The message of this Commit. */
    private String message;
    /** The time of this Commit */
    private String timestamp;
    /** The blobHashId of updated files */
    private Map<String, String> blobHashId;
    /** The HashId of this Commit. */
    private String commitHashId;

    private String parentHashId;

    /* TODO: fill in the rest of this class. */
    public Commit() {
        this.message = "initial commit";
        this.timestamp = "0";
        this.blobHashId = new HashMap<>();
        this.commitHashId = Utils.sha1(this.message,this.timestamp);
        this.parentHashId = null;
    }

    public String getCommitHashId(){
        return commitHashId;
    }

    public Commit(String message, Commit commit,Stage stage) {
        this.message = message;
        this.timestamp = Utils.makeUTCTimestamp(System.currentTimeMillis());

        this.blobHashId = new HashMap<>(commit.blobHashId);
        //更改已拷贝提交里面的Blob值
        this.blobHashId.putAll(stage.getAddHashId());//把暂存区全部添加到Map中

        for (String fileName : stage.getRmHashId().keySet()) {
            this.blobHashId.remove(fileName);
        }//移除Rm里面的文件

        this.commitHashId = Utils.sha1(this.message,this.timestamp);
        this.parentHashId = commit.commitHashId; //after this commit committed, variable 'commit' is the parent commit
    }
    public boolean containsFileName(String filename){
        return blobHashId.containsKey(filename);
    }
    public boolean containsHash(String hashId){
        return blobHashId.containsValue(hashId);
    }

}
