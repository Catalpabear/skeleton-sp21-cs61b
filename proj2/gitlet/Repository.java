package gitlet;

import java.io.File;
import java.nio.file.FileSystems;
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
    public static final String fileSepChar = FileSystems.getDefault().getSeparator();

    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    public static final File GITLET_COMMIT = join(GITLET_DIR, "commits");
    public static final File GITLET_BRANCH = join(GITLET_DIR, "branches");
    public static final File GITLET_STAGE = join(GITLET_DIR, "stage");
    public static final File GITLET_BLOB = join(GITLET_DIR, "blobs");
    public static final File GITLET_HEAD = join(GITLET_DIR, "HEAD");
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
        if(commit.containsHash(fileHashId) && commit.containsFileName(filename)) {
            if(forAdd.containHashIdInAddStage(fileHashId) && forAdd.containInAddStage(filename)) {
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
        if(forStatus.rmHashIsEmpty()){
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
                    File fileWillDel = new File(CWD, file);
                    if(fileWillDel.exists()) {
                        fileWillDel.delete();
                    }
                }
            }
            // relief all of file
            for(String hashId: commitOfBranch.getBlobHashId()){
                reliefBlob2File(hashId);
            }
            Utils.writeContents(GITLET_HEAD,branchName);
            Stage stage = Utils.readObject(GITLET_STAGE, Stage.class);
            stage.clear();
        }
    }
    public static void checkout(String commitHashId, String filename){
        Commit commit = getPointCommit(commitHashId); //TODO: 前六位查找
        helpCheckout(commit,filename);
    }
    private static void helpCheckout(Commit commit,String filename){
        String hashID = commit.getValueHashID(filename);
        if(hashID == null){
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

    public static void reset(String commitHashId){
        Commit currentCommit = getCurrentCommit();
        // get commit in this hashcode
        Commit commitOfBranch = Utils.readObject(new File(GITLET_COMMIT,commitHashId), Commit.class);
        // check whether an untracked file will be created or not
        for (String file: commitOfBranch.getBlobFileName()){
            if(!currentCommit.getBlobFileName().contains(file)){  //遍历还原的所有文件， 存在未追踪的则停止程序
                System.out.println("There is an untracked file in the way; delete it, or add and commit it first.");
                System.exit(0);
            }
        }
        for(String file: currentCommit.getBlobFileName()){
            if(!commitOfBranch.getBlobFileName().contains(file)){
                File fileWillDel = new File(CWD, file);
                if(fileWillDel.exists()) {
                    fileWillDel.delete();
                }
            }
        }
        // relief all of file
        for(String hashId: commitOfBranch.getBlobHashId()){
            reliefBlob2File(hashId);
        }
        
        File currentBranchFile = new File(GITLET_BRANCH, readContentsAsString(GITLET_HEAD));
        Utils.writeContents(currentBranchFile,commitHashId);

        Stage stage = Utils.readObject(GITLET_STAGE, Stage.class);
        stage.clear();
    }

    public static void merge(String branchName){

        // exception check
        // 暂存区非空，需要提交
        Stage stage = readObject(GITLET_STAGE, Stage.class);
        if(!stage.isEmpty()){
            System.out.println("You have uncommitted changes.");
        }
        File branchFile = new File(GITLET_BRANCH, branchName);
        if(!branchFile.exists()){
            System.out.println("A branch with that name does not exist.");
            System.exit(0);
        }
        String commitOfBranchHashId = readContentsAsString(branchFile); // 给定分支指向的提交ID
        Commit ancestorCommit = findCommonAncestor();                   // 最新公共父提交
        // 没有分叉， 不需要合并
        if(commitOfBranchHashId.equals(ancestorCommit.getCommitHashId())){
            System.out.println("Given branch is an ancestor of the current branch.");
            System.exit(0);
        }
        File currentBranch = new File(GITLET_BRANCH, readContentsAsString(GITLET_HEAD) );// 当前分支的文件，读取获得当前提交的ID
        // 不能将分支与自身合并
        if(commitOfBranchHashId.equals(readContentsAsString(GITLET_HEAD))){
            System.out.println("Cannot merge a branch with itself.");
            System.exit(0);
        }
        // 给定分支在当前分支前面
        if(ancestorCommit.getCommitHashId().equals(readContentsAsString(currentBranch))){
            System.out.println("Current branch fast-forwarded.");
            System.exit(0);
        }
        // start to work
        // TODO: a exception named "There is an untracked file in the way; delete it, or add and commit it first." at working to deal with
        // case 1 : 从分叉点开始, 当前分支未修改的文件(=split) 如果在给定分支已经修改(!=split), 需要被给定分支替换, 并且暂存
        // case 2 : 从分叉点开始, 当前分支已修改的文件(!=split) 并且在给定分支未修改(=split), 保持原样
        // case 3 : 从分叉点开始, 当前分支和给定分支修改完全一样的文件和 CWD下未跟踪的文件, 保持原样
        // case 4 : 当前分支对比分叉点新增的文件应该存在
        // case 5 : 仅在给定分支存在的文件, 直接移入当前分支, 并且暂存
        // case 6 : 从分叉点开始, 当前分支未修改的文件(=split) 并且给定分支不存在, 需要移除(下次提交不跟踪)
        // case 7 : 从分叉点开始, 给定分支未修改的文件(=split) 并且当前分支不存在, 不需要做什么(不用把文件加入当前分支)
        // case 8 : 当前分支和给定分支修改 存在冲突的文件, 将两个文件的内容进行简单的拼接 用 "===\n" 分隔
        // 以不同方式修改可以指两个文件的内容都发生了变化且与其他不同，或者一个文件的内容发生了变化而另一个文件被删除，或者该文件在分叉点时不存在，而在给定分支和当前分支中具有不同的内容。
        // TODO: a exception named "Encountered a merge conflict." at appearing merge conflict to deal with

        // TODO: after finishing work write a merge log to current commit (merge type will be update to 1 and toString method will change)
    }

    private static Commit findCommonAncestor(){

        return null;
    }
}
