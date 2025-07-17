package tester;

import static org.junit.Assert.*;

import edu.princeton.cs.introcs.StdRandom;
import org.junit.Test;
import student.StudentArrayDeque;

import java.util.ArrayDeque;

public class TestArrayDequeEC {

    @Test
    public void test_Last() {
        StudentArrayDeque<Integer> stu = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> arr = new ArrayDequeSolution<>();
        StringBuilder msg = new StringBuilder();
        for (int i = 0; i < 100; i += 1) {
            double numberBetweenZeroAndOne = StdRandom.uniform();

            if (numberBetweenZeroAndOne < 0.5) {
                stu.addLast(i);
                arr.addLast(i);
                msg.append("addLast(").append(i).append(")\n");
            } else if (arr.isEmpty()) {
                stu.addLast(i);
                arr.addLast(i);
                msg.append("addLast(").append(i).append(")\n");
            } else {
                Integer s = stu.removeLast();
                Integer a = arr.removeLast();
                msg.append("removeLast()").append("\n");
                assertEquals(msg.toString(),a, s);
            }
        }
    }
}
