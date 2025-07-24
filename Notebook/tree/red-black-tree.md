## Rotation of tree
再讨论第二种平衡树的方法:
即保持树等价的情况下,对树进行旋转,降低高度,使树平衡
- rotateLeft()  将该节点与右子节点结合,再进入其的左侧
- rotateRight() 将该节点与左子节点结合,再进入其的右侧

## Left-Leaning Red-Black tree
B-tree的平衡方法是极好的,但是实现很复杂  
我们可以通过旋转保证插入后的树结构上与B-tree相同,从而平衡  
红黑树具有大量规则,保证树结构一致,这些规则规定了旋转应该在什么时候进行  
  
[61B](https://joshhug.gitbooks.io/hug61b/content/chap11/chap115.html)介绍了一种非标准红黑树LLRB  
 
LLRB引入了节点之间的**红链接**,**黑链接**,还存在一种翻转(flip)操作,反转某一节点的所有链接  
按BST方法进行插入,插入后借**链接**按一定规则进行旋转

