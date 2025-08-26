package gitlet;

import java.io.File;
import static gitlet.Utils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    public static final File GITLET_COMMIT = join(GITLET_DIR, "\\commits");
    public static final File GITLET_BRANCH = join(GITLET_DIR, "\\branches");
    public static final File GITLET_STAGE = join(GITLET_DIR, "\\stage");
    public static final File GITLET_BLOB = join(GITLET_DIR, "\\blobs");
    public static final File GITLET_HEAD = join(GITLET_DIR, "\\HEAD");
    /* TODO: fill in the rest of this class. */
    public static void add(String filename) {
        File adding = new File(CWD, filename);
        if (!adding.exists()) {
            System.out.println("File does not exist.");
            System.exit(0);
        }
        Stage forAdd = Utils.readObject(GITLET_STAGE, Stage.class);
        String fileHashId = createHashID(adding);

        //If the file is in remove, it'll remove from it and be added for addStage
        if(forAdd.containInRmStage(filename)) {
            forAdd.RmFromRmStage(filename,fileHashId);
        }// adding for addStage is done by below code

        //read current commit
        File branch = new File(GITLET_BRANCH, readContentsAsString(GITLET_HEAD));
        File commitFile = new File(GITLET_COMMIT, readContentsAsString(branch));
        Commit commit = Utils.readObject(commitFile, Commit.class);

        //If the file unmodified and is in commit, it'll remove from addStage and after that we do nothing
        if(commit.containsHash(fileHashId)) {
            if(forAdd.containHashIdInAddStage(fileHashId)) {
                forAdd.RmFromAddStage(filename,fileHashId);
            }
            return;
        }
        //
        forAdd.addHashId(filename,makeBlob(adding));
        Utils.writeObject(GITLET_STAGE, forAdd);
    }
    private static String makeBlob(File file) {
        Blob blob = new Blob(file);
        File blobPath = Utils.join(GITLET_BLOB,blob.getBlobHashID());
        if(blobPath.exists()) {
            return blob.getBlobHashID();
        }
        Utils.writeObject(blobPath, blob);
        return blob.getBlobHashID();
    }
    private static String createHashID(File file){
        return Utils.sha1(Utils.readContentsAsString(file));
    }


    public static void commit(String message) {
        Stage forCommit = Utils.readObject(GITLET_STAGE, Stage.class);
        if(forCommit.isEmpty()){
            System.out.println("No changes added to the commit.");
            System.exit(0);
        }
        if(message.isEmpty()){
            System.out.println("Please enter a commit message.");
            System.exit(0);
        }
        //make parent commit
        File branch = new File(GITLET_BRANCH, readContentsAsString(GITLET_HEAD));
        File commitFile = new File(GITLET_COMMIT, readContentsAsString(branch));
        Commit parentCommit = Utils.readObject(commitFile, Commit.class);

        Commit commit = new Commit(message,parentCommit,forCommit);

        //写入commit, 头指针更新
        Utils.writeObject(Utils.join(GITLET_COMMIT,commit.getCommitHashId()),commit);
        Utils.writeContents(Utils.join(GITLET_BRANCH,readContentsAsString(GITLET_HEAD)),commit.getCommitHashId());

        forCommit.clear();
        Utils.writeObject(GITLET_STAGE,forCommit);
    }
}
