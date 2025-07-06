## Interface Inheritance
(纯虚函数)
```java
public interface List61B<Item> {
    public void addFirst(Item x);
    public void add Last(Item y);
    public Item getFirst();
    public Item getLast();
    public Item removeLast();
    public Item get(int i);
    public void insert(Item x, int position);
    public int size();
}
```
Use`@Override
public void addFirst(Item x) {insert(x, 0);}`

## Implementation Inheritance
(虚函数)
Use     **`default`**
```java
default public void print() {
    for (int i = 0; i < size(); i += 1) {
        System.out.print(get(i) + " ");
    }
    System.out.println();
}
```

## Override
(参数列表不同)