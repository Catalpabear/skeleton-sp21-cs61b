## sentinel node

起因是，构建SLList(singly linked list)中,如果链表为空,**addlast方法将出错**  
最好不要在方法中使用if分支,即当链表为空时有特殊情况.  
引入sentinel节点:作用是防止链表为空,但其存在不计入链表的使用,用户不会发现.

对比有节点构造和空构造:
```Java
public SLList(int x) {
        first = new IntNode(x, null);
        size_cache = 1;
    }

    public SLList() {
        first = null;
        size_cache = 0;
        sentinel=new IntNode(0, null);
    }
```
以及头插法对sentinel 的操作:
```java
public void addFirst(int n) {
        first = new IntNode(n, first);
        size_cache += 1;
        sentinel.next=first;
    }
```
addlast方法的对比:
```java
public void addLast(int x) {
        size_cache += 1;
        if (first == null) {
            first = new IntNode(x, first);
        } else {
            IntNode p = first;
            while (p.next != null) {
                p = p.next;
            }
            p.next = new IntNode(x, null);
        }
    }
```
```java
public void addLast(int x) {
        size_cache += 1;
        IntNode p = sentinel;
        while (p.next != null) {
            p = p.next;
        }
        p.next = new IntNode(x, null);
    }
    
```