package randomizedtest;

import edu.princeton.cs.algs4.Alphabet;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import timingtest.AList;

import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
    @Test
    public void testThreeAddThreeRemove(){
        AList<Integer> alpha = new AList<>();
        BuggyAList<Integer> beta= new BuggyAList<>();
        for (int i = 4; i < 7; i++) {
            alpha.addLast(i);
            beta.addLast(i);
        }
        for (int i = 0; i < 3; i++) {
            assertEquals(alpha.removeLast(), beta.removeLast());
        }
    }
    @Test
    public void randomizedTest(){
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> alpha = new BuggyAList<>();
        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                alpha.addLast(randVal);
                System.out.println("Adding " + randVal);
            } else if (operationNumber == 1) {
                // size
                int size_a = L.size();
                int size_b = alpha.size();
                assertEquals(size_a, size_b);
            }else if (operationNumber == 2) {
                if (L.size()>0&&alpha.size()>0) {
                    int last1 = L.removeLast();
                    int last2 = alpha.removeLast();
                    assertEquals(last1, last2);
                }
            }else if (operationNumber == 3) {
                if (L.size()>0&&alpha.size()>0) {
                    int get1 = L.getLast();
                    int get2 = alpha.getLast();
                    assertEquals(get1, get2);
                }
            }
        }
    }
}
