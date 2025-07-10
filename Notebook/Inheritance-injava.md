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

cpp里面声明是 **`void addFirst(Item x)=0;`**  和  **`void addFirst(Item x)override{}`**  

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
同样使用 `@Override` 重写  

多态中对于这种重写,优先调用动态类型中的成员方法  

动态类型指的是这个变量 **申请内存**(new) 的时候使用的类型,运行时类型(runtime type)  
静态类型指的是这个变量 **创建指针** 的时候使用的类型,编译时类型(compile-time type)  


### 以上继承使用的是implements关键字
## Overload
(参数列表不同)


## Extends

- " The extends keyword lets us keep **the original functionality** of SLList, while enabling us to make **modifications and add additional functionality.** "
  - modify v.修改  
- " By using the extends keyword, subclasses inherit *all members* of the parent class "  

- 使用extends时,子类完全基于父类,但不继承构造函数,于是在new子类对象的时候需要使用`super`调用父类的构造函数:
  - 当父类可以无参构造时,可以隐式调用:
```java
public VengefulSLList() {
    //super();        这个写不写编译器都会自动生成
    deletedItems = new SLList<Item>();
}
```

  - 当父类需要参数构造时,必须显式调用:
```java
public VengefulSLList(Item x) {
    super(x);
    deletedItems = new SLList<Item>();
}
```  

- 编译器根据对象的静态类型来判断**某项内容是否有效**  
例如:
```Java
VengefulSLList<Integer> vsl = new VengefulSLList<Integer>(9);
SLList<Integer> sl = vsl;
sl.addLast(50);
sl.removeLast();
sl.printLostItems();//这一行编译报错,SLList没有这个成员函数
VengefulSLList<Integer> vsl2 = sl;//这一行编译报错,子类不能容纳父类
```
以上代码来自[here](https://joshhug.gitbooks.io/hug61b/content/chap4/chap42.html)  

- 使用类型转换时
编译器的类型检查只考虑静态类型  
运行时如果类型不兼容会抛出异常ClassCastException  
例如:
```Java
/*maxDog返回Dog类型,Dog类型有两个子类Poodle和Malamute*/
Poodle frank = new Poodle("Frank", 5);
Malamute frankSr = new Malamute("Frank Sr.", 100);
//maxDog 返回 Malamute 时会 抛出异常ClassCastException  
Poodle largerPoodle = (Poodle) maxDog(frank, frankSr); // runtime exception!
```
## The Object Class 
Java中所有类都隐式extends **Object**这个类  
都可以使用 `.equals(Object obj)  .hashCode()  .toString()`这些方法