package gitlet;

import static gitlet.Repository.*;
import static gitlet.Utils.readObject;

import java.io.File;
import java.io.Serializable;
import java.util.*;

/** Represents a gitlet commit object.
 *  does at a high level.
 *
 *  @author catalpabear
 */
public class Commit implements Serializable {
    /**
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */
    private static final long serialVersionUID = 2L;
    /** The message of this Commit. */
    private String message;
    /** The time of this Commit */
    private String timestamp;
    /** The blobHashId of updated files */
    private Map<String, String> blobHashId;
    /** The HashId of this Commit. */
    private String commitHashId;

    private String parentHashId;

    public Commit() {
        this.message = "initial commit";
        this.timestamp = Utils.makeUTCTimestamp(0L);
        this.blobHashId = new HashMap<>();
        this.commitHashId = Utils.sha1(this.message,this.timestamp);
        this.parentHashId = null;
    }

    public String getCommitHashId(){
        return commitHashId;
    }
    public String getParentHashId(){
        return parentHashId;
    }
    public Commit getParentCommit(){
        File parentFile = new File(GITLET_COMMIT, parentHashId);
        return readObject(parentFile, Commit.class);
    }
    public String getMessage(){
        return message;
    }

    public Commit(String message, Commit commit,Stage stage) {
        this.message = message;
        this.timestamp = Utils.makeUTCTimestamp(System.currentTimeMillis()/1000L + 28800L);

        this.blobHashId = new HashMap<>(commit.blobHashId);
        //更改已拷贝提交里面的Blob值
        this.blobHashId.putAll(stage.getAddHashId());//把暂存区全部添加到Map中

        for (String fileName : stage.getRmHashId().keySet()) {
            this.blobHashId.remove(fileName);
        }//移除Rm里面的文件

        this.parentHashId = commit.commitHashId; //after this commit committed, variable 'commit' is the parent commit
        this.commitHashId = Utils.sha1(this.message,this.timestamp,this.parentHashId);
    }
    public boolean containsFileName(String filename){
        return blobHashId.containsKey(filename);
    }
    public boolean containsHash(String hashId){
        return blobHashId.containsValue(hashId);
    }

    public String getValueHashID(String filename){
        return blobHashId.get(filename);
    }

    public Set<String> getBlobFileName(){
        return blobHashId.keySet();
    }
    public Collection<String> getBlobHashId(){
        return blobHashId.values();
    }

    public boolean hasKeyAndValue(String key, String value){
        return blobHashId.containsKey(key) && blobHashId.get(key).equals(value);
    }

    @Override
    public String toString(){
        return "===\n" +
                "commit " + commitHashId + "\n" +
                "Date: " + timestamp + "\n" +
                message + "\n";
    }

}
