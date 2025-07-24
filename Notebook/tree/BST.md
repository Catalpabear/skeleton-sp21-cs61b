# binary search tree
## define
当链表是有序的时候,我们需要一种办法对链表进行二分查找,降低复杂度  
一种方法是不断取链表的中间元素相连接,此时链表变为BST,例如:
1->2->3->4->5->6->7  
4           
2   6  
1 3 5 7     
对于树中任意一个节点,左子树值小于之,右子树值大于之;   
## class-implement
参考61B书中的[实现](https://joshhug.gitbooks.io/hug61b/content/chap10/chap102.html)
```java
private class BST<K> {
    private K key;
    private BST left;
    private BST right;

    public BST(K key, BST left, BST Right) {
        this.key = key;
        this.left = left;
        this.right = right;
    }

    public BST(K key) {
        this.key = key;
    }
}
```
## opera-implement
树应该有以下方法:find insert delete 
   
均使用递归实现.

### delete
实现时需要考虑三种情况:
- 删除叶子
- 删除有一个子的节点
- 删除有两个子的节点