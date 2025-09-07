package gitlet;

import java.io.File;
import java.io.IOException;

import static gitlet.Repository.*;

public class Init {
    static void init() throws IOException {
        if (Utils.hasGitRepo()){
            System.out.println("A Gitlet version-control system already exists in the current directory.");
            return;
        }
        File mkgit = new File(System.getProperty("user.dir"));
        mkgit = Utils.join(mkgit, ".gitlet");
        mkgit.mkdir();
        Utils.join(mkgit, "blobs").mkdir();
        Utils.join(mkgit, "commits").mkdir();
        Utils.join(mkgit, "stage").createNewFile();
        Utils.join(mkgit, "branches").mkdir();
        Utils.join(mkgit, "branches" + fileSepChar + "master").createNewFile();
        File head = new File(mkgit, "HEAD");
        head.createNewFile();
        Utils.writeContents(head, "master");

        Commit init = new Commit();
        Utils.writeObject(Utils.join(GITLET_COMMIT,init.getCommitHashId()),init);
        Utils.writeContents(Utils.join(GITLET_BRANCH,"master"),init.getCommitHashId());

        Stage initStage = new Stage();
        Utils.writeObject(GITLET_STAGE,initStage);
    }
}
