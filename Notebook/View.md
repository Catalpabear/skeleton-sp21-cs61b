## View

Java的视图语法我还云里雾里，先搁置在这里, [**点我**](https://joshhug.gitbooks.io/hug61b/content/chap8/chap81.html) 回去再看!  

整体作用是通过extends返回原数据结构的子结构,实现对部分元素的修改[^1]
[^1]:这句话是扯淡,extends作用是防止重复造轮子,和本节没有关系:smile:   

## view  
严格来说,这不算一种语法,应该是一种算法(?),总之构建subclass类时使用了这些巧思:

- 构建subclass的函数,传入this指针
```java
  public List<T> subList(int fromIndex, int toIndex) {
    return new SubListView<>(this, fromIndex, toIndex);
}
```
- subclass类的成员含有父类对象,充当储存指针的作用
```java
private static class SubListView<T> extends AbstractList<T> {
            private final CustomList<T> parentList;
            private final int offset; // 子列表的起始索引
            private int size; // 子列表的大小
}
```
- subclass类的成员含有索引信息,便于调用父类方法时进行转换,如上  

故而新建了sub对象,却没有复制任何元素,仅仅只有构建时传入了父类的指针和切片信息  
观察Java Visualizer图可以发现:
![idea调试截图](./picturefield/view.png)
