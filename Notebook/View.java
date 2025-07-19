import java.util.*;

/**
 * 演示自实现 ArrayList + SubList 视图
 */
public class View {

    /*-------------------------------------------------
     * 1. 极简动态数组（static 内部类，方便 main 直接 new）
     *------------------------------------------------*/
    public static class MyArrayList<E> extends AbstractList<E> {
        private Object[] data;
        private int size;

        public MyArrayList() {
            data = new Object[10];
        }

        private void ensureCapacity(int need) {
            if (need > data.length) {
                data = Arrays.copyOf(data, data.length * 2);
            }
        }

        @Override
        public boolean add(E e) {
            ensureCapacity(size + 1);
            data[size++] = e;
            return true;
        }

        @Override
        public E get(int index) {
            Objects.checkIndex(index, size);
            @SuppressWarnings("unchecked")
            E e = (E) data[index];
            return e;
        }

        @Override
        public E set(int index, E element) {
            Objects.checkIndex(index, size);
            @SuppressWarnings("unchecked")
            E old = (E) data[index];
            data[index] = element;
            return old;
        }

        @Override
        public int size() {
            return size;
        }

        /*-------------------------------------------------
         * 2. subList 视图
         *------------------------------------------------*/
        @Override
        public List<E> subList(int fromIndex, int toIndex) {
            Objects.checkFromToIndex(fromIndex, toIndex, size);
            return new SubList(fromIndex, toIndex);
        }

        /*----------------- 内部视图类 -----------------*/
        private class SubList extends AbstractList<E> {
            private final int offset;
            private int len;

            SubList(int from, int to) {
                offset = from;
                len = to - from;
            }

            @Override
            public int size() {
                return len;
            }

            @Override
            public E get(int index) {
                Objects.checkIndex(index, len);
                return MyArrayList.this.get(offset + index);
            }

            @Override
            public E set(int index, E element) {
                Objects.checkIndex(index, len);
                return MyArrayList.this.set(offset + index, element);
            }

            @Override
            public void add(int index, E element) {
                Objects.checkIndex(index, len + 1);
                MyArrayList.this.add(offset + index, element);
                len++;
            }

            @Override
            public E remove(int index) {
                Objects.checkIndex(index, len);
                E removed = MyArrayList.this.remove(offset + index);
                len--;
                return removed;
            }
        }
    }

    /*-------------------------------------------------
     * 3. 测试入口
     *------------------------------------------------*/
    public static void main(String[] args) {
        MyArrayList<String> list = new MyArrayList<>();
        Collections.addAll(list, "A", "B", "C", "D", "E");

        List<String> sub = list.subList(1, 4); // 视图 B,C,D
        System.out.println("原列表: " + list); // [A, B, C, D, E]
        System.out.println("子列表: " + sub); // [B, C, D]

        // 通过子列表修改
        sub.set(0, "Z");
        sub.add("X");

        System.out.println("修改后原列表: " + list); // [A, Z, C, D, X, E]
        System.out.println("修改后子列表: " + sub); // [Z, C, D, X]
    }
}