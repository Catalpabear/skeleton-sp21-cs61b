package deque;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Iterator;
public class ArrayDequeTest {

    @Test
    public void testEmpty() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        assertTrue(deque.isEmpty());
        assertEquals(0, deque.size());
    }

    @Test
    public void testAddFirst() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.addFirst("a");
        assertFalse(deque.isEmpty());
        assertEquals(1, deque.size());
        assertEquals("a", deque.get(0));
        deque.addFirst("b");
        assertEquals(2, deque.size());
        assertEquals("b", deque.get(0));
        assertEquals("a", deque.get(1));
    }

    @Test
    public void testAddLast() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addLast(1);
        assertEquals(1, deque.size());
        assertEquals((Integer)1, deque.get(0));
        deque.addLast(2);
        assertEquals(2, deque.size());
        assertEquals((Integer)2, deque.get(1));
    }

    @Test
    public void testRemoveFirst() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.addLast("x");
        deque.addLast("y");
        assertEquals("x", deque.removeFirst());
        assertEquals(1, deque.size());
        assertEquals("y", deque.get(0));
        assertEquals("y", deque.removeFirst());
        assertTrue(deque.isEmpty());
        assertNull(deque.removeFirst());
    }

    @Test
    public void testRemoveLast() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addFirst(10);
        deque.addFirst(20);
        assertEquals((Integer)10, deque.removeLast());
        assertEquals(1, deque.size());
        assertEquals((Integer)20, deque.removeLast());
        assertTrue(deque.isEmpty());
        assertNull(deque.removeLast());
    }

    @Test
    public void testAddRemoveMix() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addFirst(1);
        deque.addLast(2);
        deque.addFirst(0);
        assertEquals((Integer)0, deque.removeFirst());
        assertEquals((Integer)2, deque.removeLast());
        assertEquals((Integer)1, deque.removeFirst());
        assertTrue(deque.isEmpty());
    }

    @Test
    public void testResize() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < 20; i++) {
            deque.addLast(i);
        }
        assertEquals(20, deque.size());
        for (int i = 0; i < 20; i++) {
            assertEquals((Integer)i, deque.get(i));
        }
    }

    @Test
    public void testGet() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.addLast("a");
        deque.addLast("b");
        deque.addLast("c");
        assertEquals("a", deque.get(0));
        assertEquals("b", deque.get(1));
        assertEquals("c", deque.get(2));
    }
    @Test
    public void testPrintDeque() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.addLast("a");
        deque.addLast("b");
        deque.addLast("c");
        deque.addFirst("d");
        deque.printDeque();
    }


    @Test
    public void testIterator() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < 5; i++) {
            deque.addLast(i);
        }
        int expected = 0;
        for(int i: deque) {
           assertEquals(expected, i);
           expected++;
        }
        assertEquals(5, expected); // 确保遍历了5个元素
    }
}
