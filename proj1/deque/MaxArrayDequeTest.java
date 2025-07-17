package deque;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Comparator;

public class MaxArrayDequeTest {

    @Test
    public void testMaxWithIntegers() {
        Comparator<Integer> cmp = Integer::compare;
        MaxArrayDeque<Integer> deque = new MaxArrayDeque<>(cmp);
        deque.addLast(3);
        deque.addLast(7);
        deque.addLast(2);
        deque.addLast(5);
        assertEquals((Integer)7, deque.max());
    }

    @Test
    public void testMaxWithStrings() {
        Comparator<String> cmp = (a, b) -> a.length() - b.length();
        MaxArrayDeque<String> deque = new MaxArrayDeque<>(cmp);
        deque.addLast("a");
        deque.addLast("abcd");
        deque.addLast("abc");
        assertEquals("abcd", deque.max());
    }

    @Test
    public void testMaxWithCustomComparator() {
        Comparator<Integer> cmp = (a, b) -> -(a.compareTo(b)); // 取最小值
        MaxArrayDeque<Integer> deque = new MaxArrayDeque<>(cmp);
        deque.addLast(10);
        deque.addLast(5);
        deque.addLast(8);
        assertEquals((Integer)5, deque.max());
    }

    @Test
    public void testMaxEmptyDeque() {
        Comparator<Integer> cmp = Integer::compare;
        MaxArrayDeque<Integer> deque = new MaxArrayDeque<>(cmp);
        assertNull(deque.max());
    }
}