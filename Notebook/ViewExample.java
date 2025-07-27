import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class ViewExample {

    public static void main(String[] args) {
        // 1. 创建原列表并添加元素
        List<String> originallist = new ArrayList<>();
        CustomList<String> originalList = new CustomList<>(originallist);
        originalList.add("A");
        originalList.add("B");
        originalList.add("C");
        originalList.add("D");
        originalList.add("E");

        System.out.println("原列表: " + originalList); // [A, B, C, D, E]

        // 2. 创建子列表视图（索引 1~3，包含 1，不包含 4）
        List<String> subList = originalList.subList(1, 4);
        System.out.println("子列表: " + subList); // [B, C, D]

        // 3. 修改子列表会直接影响原列表
        subList.set(0, "X"); // 将子列表的 "B" 改为 "X"
        System.out.println("修改子列表后:");
        System.out.println("子列表: " + subList); // [X, C, D]
        System.out.println("原列表: " + originalList); // [A, X, C, D, E]

        // 4. 子列表支持 add/remove 等操作
        subList.add(1, "Y"); // 在子列表索引 1 处插入 "Y"
        System.out.println("插入元素后:");
        System.out.println("子列表: " + subList); // [X, Y, C, D]
        System.out.println("原列表: " + originalList); // [A, X, Y, C, D, E]
    }

    /**
     * 自定义列表类，支持 subList 视图功能。
     */
    static class CustomList<T> extends AbstractList<T> {
        private final List<T> data;

        public CustomList(List<T> data) {
            this.data = data;
        }

        @Override
        public T get(int index) {
            return data.get(index);
        }

        @Override
        public int size() {
            return data.size();
        }

        @Override
        public T set(int index, T element) {
            return data.set(index, element);
        }

        @Override
        public void add(int index, T element) {
            data.add(index, element);
        }

        @Override
        public T remove(int index) {
            return data.remove(index);
        }

        /**
         * 创建子列表视图（关键实现）。
         */
        @Override
        public List<T> subList(int fromIndex, int toIndex) {
            return new SubListView<>(this, fromIndex, toIndex);
        }

        /**
         * 子列表视图类（内部类）。
         */
        private static class SubListView<T> extends AbstractList<T> {
            private final CustomList<T> parentList;
            private final int offset; // 子列表的起始索引
            private int size; // 子列表的大小

            public SubListView(CustomList<T> parentList, int fromIndex, int toIndex) {
                this.parentList = parentList;
                this.offset = fromIndex;
                this.size = toIndex - fromIndex;
            }

            @Override
            public T get(int index) {
                // 将子列表的索引转换为原列表的索引
                return parentList.get(offset + index);
            }

            @Override
            public int size() {
                return size;
            }

            @Override
            public T set(int index, T element) {
                return parentList.set(offset + index, element);
            }

            @Override
            public void add(int index, T element) {
                parentList.add(offset + index, element);
                size++; // 子列表大小增加
            }

            @Override
            public T remove(int index) {
                T removed = parentList.remove(offset + index);
                size--; // 子列表大小减少
                return removed;
            }
        }
    }
}