package gitlet;

import java.io.File;
import java.io.IOException;


/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author TODO
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static final File checkPath = new File(System.getProperty("user.dir"));

    public static void main(String[] args) {
        // TODO: what if args is empty?
        if(args.length == 0) {
            System.out.println("Please enter a command.");
            System.exit(0);
        }
        if(args.length > 4){// TODO: need to fix
            System.out.println("Incorrect operands.");
            System.exit(0);
        }
        if( !(args[0].equals("init")) & !Utils.hasGitRepo() ){
            System.out.println("Not in an initialized Gitlet directory.");
            System.exit(0);
        }
        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                // TODO: handle the `init` command
                try {
                    Init.init();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "add":
                // TODO: handle the `add [filename]` command
                Repository.add(args[1]);
                break;
            // TODO: FILL THE REST IN
            case "commit":
                // handle commit "stringContents"
                Repository.commit(args[1]);
                break;
            default:
                System.out.println("No command with that name exists.");
                break;
        }
    }

}
