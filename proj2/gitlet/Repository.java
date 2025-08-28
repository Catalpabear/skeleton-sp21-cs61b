package gitlet;

import java.io.File;
import java.util.List;

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
        Commit commit = getCurrentCommit();

        //If the file unmodified and is in commit, it'll remove from addStage and after that we do nothing
        if(commit.containsHash(fileHashId)) {
            if(forAdd.containHashIdInAddStage(fileHashId)) {
                forAdd.RmFromAddStage(filename,fileHashId);
            }
            return;
        }

        // that's "adding for addStage" mentioned above
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

    public static void remove(String filename) {

        // TODO: needFixBug -> 我先建立一个文件并提交, 然后rm, 再重新建立输入相同的内容, 进行add, 结果add和remove区域里都有这个文件

        Stage forRemove = Utils.readObject(GITLET_STAGE, Stage.class);
        Commit commit = getCurrentCommit();

        boolean staged = forRemove.containInAddStage(filename);
        boolean tracked = commit.containsFileName(filename);
        if(!staged && !tracked){
            System.out.println("No reason to remove the file.");
            System.exit(0);
        }

        String fileHashId = createHashID(new File(CWD, filename));

        // the file is in addStage and only remove it from addStage
        if(staged) {
            forRemove.RmFromAddStage(filename,fileHashId);
        }

        // file is also in commit, add it to removeStage, the later commit will not have it (has implemented in commit func).
       if(tracked) {
           forRemove.addRmHashId(filename,fileHashId);
       }
       File file = new File(CWD, filename);
       if(file.exists()) {
           file.delete();
       }

       Utils.writeObject(GITLET_STAGE, forRemove);
    }

    //read current commit
    private static Commit getCurrentCommit(){
        File branch = new File(GITLET_BRANCH, readContentsAsString(GITLET_HEAD));
        return getPointCommit(readContentsAsString(branch));
    }

    private static Commit getPointCommit(String commitHashId){
        File commitFile = new File(GITLET_COMMIT, commitHashId);
        if(!commitFile.exists()) {
            System.out.println("No commit with that id exists.");
            System.exit(0);
        }
        return Utils.readObject(commitFile, Commit.class);
    }

    public static void log(){
        Commit commitMove = getCurrentCommit();
        while(commitMove.getParentHashId() != null) {
            System.out.println(commitMove);
            commitMove = commitMove.getParentCommit();
        }
        System.out.println(commitMove);
    }

    public static void globalLog(){
        List<String> totalCommit = Utils.plainFilenamesIn(GITLET_COMMIT);
        if (totalCommit != null) {
            for(String commit : totalCommit){
                System.out.println(Utils.readObject(Utils.join(GITLET_COMMIT,commit), Commit.class));
            }
        }
    }

    public static void find(String message){
        boolean found = false;
        List<String> totalCommit = Utils.plainFilenamesIn(GITLET_COMMIT);
        if (totalCommit != null) {
            for(String commit : totalCommit){
                Commit willCheck = Utils.readObject(Utils.join(GITLET_COMMIT,commit), Commit.class);
                if(willCheck.getMessage().equals(message)){
                    System.out.println(willCheck.getCommitHashId());
                    found = true;
                }
            }
            if(!found){
                System.out.println("Found no commit with that message.");
            }
        }
    }

    public static void status(){
        printBranch();
        printStaged();
        printRemoved();
        printModifications();
        printUntrackedFiles();
    }
    private static void printBranch(){
        System.out.println("=== Branches ===");
        List<String> branches = Utils.plainFilenamesIn(GITLET_BRANCH);
        if (branches != null) {
            for(String branch : branches){
                if (branch.equals(readContentsAsString(GITLET_HEAD))) {
                    System.out.print("*");
                }
                System.out.println(branch);
            }
        }
        System.out.println();
    }
    private static void printStaged(){
        System.out.println("=== Staged Files ===");
        Stage forStatus = Utils.readObject(GITLET_STAGE, Stage.class);
        if(forStatus.addHashIsEmpty()){
            System.out.println();
            return;
        }
        for(String filename: forStatus.getAddHashId().keySet()){
            System.out.println(filename);
        }
        System.out.println();
    }
    private static void printRemoved(){
        System.out.println("=== Removed Files ===");
        Stage forStatus = Utils.readObject(GITLET_STAGE, Stage.class);
        if(forStatus.addHashIsEmpty()){
            System.out.println();
            return;
        }
        for (String filename: forStatus.getRmHashId().keySet()){
            System.out.println(filename);
        }
        System.out.println();
    }

    private static void printModifications(){
        System.out.println("=== Modifications Not Staged For Commit ===");


        System.out.println();
    }
    private static void printUntrackedFiles(){
        System.out.println("=== Untracked Files ===");


        System.out.println();
    }

    // checkout
    public static void checkout(String filename,boolean isBranch){
        if(!isBranch){
            Commit commit = getCurrentCommit();
            helpCheckout(commit,filename);
        }else{
            // in that case, filename is branchName
            String branchName = filename;
            if(branchName.equals( readContentsAsString(GITLET_HEAD) )){
                System.out.println("No need to checkout the current branch.");
                System.exit(0);
            }
            File branchFile = new File(GITLET_BRANCH, branchName);
            if(!branchFile.exists()){
                System.out.println("No such branch exists.");
                System.exit(0);
            }
            Commit currentCommit = getCurrentCommit();
            // get commit in this branch
            File branchCommitHashId = new File(GITLET_BRANCH, branchName);
            Commit commitOfBranch = Utils.readObject(new File(GITLET_COMMIT,readContentsAsString(branchCommitHashId)), Commit.class);
            // check whether an untracked file will be created or not
            for (String file: commitOfBranch.getBlobFileName()){
                if(!currentCommit.getBlobFileName().contains(file)){  //遍历还原的所有文件， 存在未追踪的则停止程序
                    System.out.println("There is an untracked file in the way; delete it, or add and commit it first.");
                    System.exit(0);
                }
            }
            // above that is check failed case, after that we begin to change CWD
            //Any files that are tracked in the current branch but are not present in the checked-out branch are deleted.
            for(String file: currentCommit.getBlobFileName()){
                if(!commitOfBranch.getBlobFileName().contains(file)){
                    File fileWillDel = new File(CWD, filename);
                    if(fileWillDel.exists()) {
                        fileWillDel.delete();
                    }
                }
            }
            // relief all of file
            for(String hashId: commitOfBranch.getBlobHashId()){
                reliefBlob2File(hashId);
            }
            Stage stage = Utils.readObject(GITLET_STAGE, Stage.class);
            stage.clear();
        }
    }
    public static void checkout(String commitHashId, String filename){
        Commit commit = getPointCommit(commitHashId);
        helpCheckout(commit,filename);
    }
    private static void helpCheckout(Commit commit,String filename){
        String hashID = commit.getValueHashID(filename);
        if(hashID != null){
            System.out.println("File does not exist in that commit.");
            System.exit(0);
        }
        reliefBlob2File(hashID);
    }
    private static void reliefBlob2File(String hashId){
        File blobFile = new File(GITLET_BLOB, hashId);
        Blob blob = Utils.readObject(blobFile, Blob.class);

        File writeFile = new File(blob.getFilePath());
        Utils.writeContents(writeFile, blob.getFileContents());
    }

    // branch
    public static void branch(String branchName){
        File branchFile = new File(GITLET_BRANCH, branchName);
        if(branchFile.exists()){
            System.out.println("A branch with that name already exists.");
            System.exit(0);
        }
        File HEADCommit = Utils.join(GITLET_BRANCH,Utils.readContentsAsString(GITLET_HEAD));
        Utils.writeContents(branchFile,Utils.readContentsAsString(HEADCommit));
    }

    public static void rmBranch(String branchName){
        File branchFile = new File(GITLET_BRANCH, branchName);
        if(!branchFile.exists()){
            System.out.println("A branch with that name does not exist.");
            System.exit(0);
        }
        if(branchName.equals(readContentsAsString(GITLET_HEAD))){
            System.out.println("Cannot remove the current branch.");
            System.exit(0);
        }
        branchFile.delete();
    }

    //TODO: reset merge

    public static void reset(String commitHashId){

    }

    public static void merge(String branchName){

    }

}
