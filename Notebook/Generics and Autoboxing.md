# Type-cast
我们在泛型使用时提到过Java基本类型都有自己的类 in [here](./generics.md)  
```java
int,double,char,boolean,long,short,byte,float  分别对应
Integer,Double,Character,Boolean,Long,Short,Byte,Float
```
但是日常使用不受影响,java编译器会隐式进行转换 autoboxing and unboxing  
将int x传给Integer时,隐式调用 **`new Integer(x)`**   
将Integer x传给int时,隐式调用 **`x.valueOf()`**  
- 数组不会进行*autoboxing and unboxing* , 这么做会编译错误.
- 这一过程性能有损失,且包装类的内存更大.  

另外就是不同类型之间的转换,小转大默认隐式,大转小需要手动调用,和C一样

# Immutability 
`final` 关键字，声明时该变量不可重新赋值  
如果是指针变量，就是这个标识符指向的内存空间不可更换，内存空间可以被更改

## Autoboxing Puzzle
当 expected 为 int , get 返回值为 Integer 时 (因为使用了泛型不得不这样)  
以下代码会编译错误:
```java
assertEquals(expected, am.get(2));
```
编译器不知道选择 `assertEquals(int,int);` 还是 `assertEquals(Object,Object);`  
可以转换 expected , 也可以使用 am.get(2).valueOf()  

# Static generics method
类中的泛型会在类的实例化之后进行推断,但是类的静态方法可以**不需要**类的实例化  

因此 此类声明会出错: `public static V get(Map61B<K, V> map, String key)`  

需要这样声明: **`public static <K,V> V get(Map61B<K,V> map, K key)`**  

就是说,要声明一个泛型方法，形式类型参数必须在返回类型之前指定.  

形式类型参数还可以这样指定: `<K extends Comparable<K>, V>` , 表示K类型必须可比较.  
