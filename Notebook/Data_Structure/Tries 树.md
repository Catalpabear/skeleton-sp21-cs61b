[Goto Overview](./总览.md)

---
### 应用场景

Tries 树, 全称Retrieval tree,也叫前缀树, 我们用于实现Map这个数据结构  
当输入的键值(Key), 体现出一定的规律时, 不需要通过哈希进行转换时  
我们使用前缀树方便地表示键值

#### 哈希表实现Map回顾

如果hashmap均匀分布, 以下操作均为 $O(1)$
put 我们将键值转换为哈希值再取模, 得到哈希数组的索引, 再将对应的值插入(插入Key和Value值)  
get 将键值转换为索引, 再从索引指向的链表中寻找(不均匀链表操作时可能 $O(N)$ )  
remove 一样的,找到对应的链表, 进行的链表的删除
containKey 操作 O(logN)

### 前缀树Map

在键可以被拆分成"字符"并与其他键共享前缀的情况下（例如字符串）, TriesMap是很好的实现  
书上写到‘The Trie is a specific implementation for Sets and Maps that is specialized for strings.’  Tries可以说是专用于字符串的  
下面这张图很好的描绘了树的结构
![](./picturefield/triestree.png)
以此为例分析, Map中存在键值对:

| by  | sea | she | shells | shore | the |
| --- | --- | --- | ------ | ----- | --- |
| 4   | 6   | 0   | 3      | 7     | 5   |

每个节点含一个标签, 白节点(作为共用字符) , 蓝节点(储存值)  
关于这个设计思路的构建, 请看cs61b课程中的[文档与视频]([15.1 Introduction to Tries · Hug61B](https://joshhug.gitbooks.io/hug61b/content/chap15/chap151.html))

### 实现TriesMap

书上实现借用了 DataIndexedCharMap 这个数据结构, 这是一个理想哈希表映射,操作全为O(1)  

DICM 的具体实现,基于一个128长度的数组, 数组储存Tries的每种节点(节点只有128种), 数组索引对应字符的ASCII值, 未出现在树里的字符数组内置为 'null' 

![alt text](./picturefield/image.png)

[图(来自61bsp21 ppt)](https://www.youtube.com/watch?v=DqfZ4BEVDgk) 中是一个只有两个节点的树的内存表示, 可以看到, 这样的思路的内存浪费很大! 为了更好的方案, 我们可以试着用其他方法改进:

- DICM实际上是特殊的哈希表, 我们可以使用长度较短的哈希表代替, 需要使用更多空间时进行扩展, 但是代码实现上难度较大
- 使用Balance S T构建, 实现难度也大(用红黑树,AVL树, B树储存非二叉节点), 因为不是二叉树, 查找效率还是 O(logN)

### 前缀思想

Tries 的限制很大, 只能应用于字符串, 但是涉及到字符串的领域,它的应用很多:
- 自动补全 -> 根据前缀输入值推断可能的命令
- 搜索引擎有搜索提示

