package capers;

import java.io.File;
import java.io.IOException;

import static capers.Utils.*;

/** A repository for Capers 
 * @author TODO
 * The structure of a Capers Repository is as follows:
 *
 * .capers/ -- top level folder for all persistent data in your lab12 folder
 *    - dogs/ -- folder containing all of the persistent data for dogs
 *    - story -- file containing the current story
 *
 * TODO: change the above structure if you do something different.
 */
public class CapersRepository {
    /** Current Working Directory. */
    static final File CWD = new File(System.getProperty("user.dir"));

    /** Main metadata folder. */
    static final File CAPERS_FOLDER =
            Utils.join(System.getProperty("user.dir"),".capers"); // TODO Hint: look at the `join`
                                            //      function in Utils

    /**
     * Does required filesystem operations to allow for persistence.
     * (creates any necessary folders or files)
     * Remember: recommended structure (you do not have to follow):
     *
     * .capers/ -- top level folder for all persistent data in your lab12 folder
     *    - dogs/ -- folder containing all of the persistent data for dogs
     *    - story -- file containing the current story
     */
    public static void setupPersistence() {
        // TODO
        File f1=CAPERS_FOLDER;
        if(!f1.exists()) {
            f1.mkdir();
        }
        File f2=Utils.join(CAPERS_FOLDER,"dogs");
        if(!f2.exists()) {
            f2.mkdir();
        }
        File f3=new File(CAPERS_FOLDER,"story");
        if(!f3.exists()) {
            try{
                f3.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Appends the first non-command argument in args
     * to a file called `story` in the .capers directory.
     * @param text String of the text to be appended to the story
     */
    public static void writeStory(String text) {
        // TODO
        File story= Utils.join(CAPERS_FOLDER,"story");
        String return_story = Utils.readContentsAsString(story);
        return_story=return_story+text+"\n";
        Utils.writeContents(story,return_story);
        System.out.print(return_story);
    }

    /**
     * Creates and persistently saves a dog using the first
     * three non-command arguments of args (name, breed, age).
     * Also prints out the dog's information using toString().
     */
    public static void makeDog(String name, String breed, int age) {
        // TODO
        Dog dog=new Dog(name,breed,age);
        dog.saveDog();
        System.out.print(dog.toString());
    }

    /**
     * Advances a dog's age persistently and prints out a celebratory message.
     * Also prints out the dog's information using toString().
     * Chooses dog to advance based on the first non-command argument of args.
     * @param name String name of the Dog whose birthday we're celebrating.
     */
    public static void celebrateBirthday(String name) {
        // TODO
        Dog dog = Dog.fromFile(name);
        if (dog != null) {
            dog.haveBirthday();
            dog.saveDog();
        }
    }
}
