# binary search
O(log n)
# selection sort
思路:遍历找到最小的，与第一个交换位置
```java
for(int i=0;i<len;i++){
    for(int j=i;j<len;j++){
        int index=findminindex();//这个函数内无循环，为方便，省略
        swap(i,index);
    }
}
```
$\large O (N^2)$
# merge sort
归并排序有两个要点:
- 对两个已经排序好的数组进行整合,新数组已排序好; $\large O (N^2)$ ;
- 无限划分原数组至 **只有一个元素**(可视为已排序好) ;  
    
$\large O(NlogN)$

# index-elem array
- 将数组索引视为非负整数集合的元素是一种可能带来便利的好法子  
  
- 将每个集合视为树结构  
  
- 然后可以使用数组内容储存集合元素的索引作为指针,若为-1说明是父
